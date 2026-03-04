package map.logic;

import enums.RoomType;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * คลาส MapGenerator ทำหน้าที่สร้างแผนที่ของเกมแบบสุ่ม (Procedural Generation)
 * <p>
 * ระบบจะสร้าง grid ของ MapNode ตามจำนวนชั้น (FLOORS) และความกว้าง (WIDTH)
 * จากนั้นสุ่มสร้างเส้นทาง, เชื่อมต่อโหนด, กำหนดประเภทห้อง (RoomType)
 * และสร้าง Boss Room ชั้นบนสุด
 * </p>
 *
 * <p>
 * ขั้นตอนหลัก:
 * 1. สร้าง grid พร้อมตำแหน่งแบบสุ่มเล็กน้อย (jitter)
 * 2. เลือกจุดเริ่มต้นหลายเส้นทาง
 * 3. สร้างการเชื่อมต่อแต่ละชั้น
 * 4. เพิ่ม Boss Node ด้านบนสุด
 * 5. กำหนดประเภทห้องตามความคืบหน้าของเกม
 * 6. ตรวจสอบว่ามีห้องพิเศษขั้นต่ำ (Shop / Elite / Event)
 * </p>
 */
public class MapGenerator {

    private static final Random random = new Random();

    /**
     * สร้างและคืนค่าแผนที่ของเกม
     *
     * @return โครงสร้างแผนที่ในรูปแบบ List ของแต่ละชั้น
     */
    public static List<List<MapNode>> generateMap() {

        MapLogic.reset();

        List<List<MapNode>> map = new ArrayList<>();
        MapNode[][] grid = new MapNode[Constants.FLOORS][Constants.WIDTH];

        for (int floor = 0; floor < Constants.FLOORS; floor++) {

            List<MapNode> row = new ArrayList<>();

            for (int col = 0; col < Constants.WIDTH; col++) {

                double offsetX = (random.nextDouble() * 2 - 1) * Constants.PLACEMENT_RANDOMNESS;
                double offsetY = (random.nextDouble() * 2 - 1) * Constants.PLACEMENT_RANDOMNESS;

                double x = (col * Constants.SPACING_X) + offsetX;
                double y = ((Constants.FLOORS - floor) * Constants.SPACING_Y) + offsetY;

                MapNode node = new MapNode(floor, col, x, y);
                grid[floor][col] = node;
                row.add(node);
            }

            map.add(row);
        }

        List<Integer> startCols = getStartingPoints();

        for (int startCol : startCols) {

            int currentCol = startCol;

            for (int floor = 0; floor < Constants.FLOORS - 1; floor++) {
                currentCol = setupConnection(grid, floor, currentCol);
            }
        }

        double bossX = (Constants.WIDTH / 2.0) * Constants.SPACING_X;
        double bossY = 0;

        MapNode bossRoom = new MapNode(Constants.FLOORS, Constants.WIDTH / 2, bossX, bossY);
        bossRoom.setType(RoomType.BOSS);
        bossRoom.createRoomFromType();

        for (int col = 0; col < Constants.WIDTH; col++) {

            MapNode topNode = grid[Constants.FLOORS - 1][col];

            boolean hasIncomingPath = false;

            for (int prevCol = 0; prevCol < Constants.WIDTH; prevCol++) {
                if (grid[Constants.FLOORS - 2][prevCol].getNextNodes().contains(topNode)) {
                    hasIncomingPath = true;
                    break;
                }
            }

            if (hasIncomingPath) {
                topNode.connect(bossRoom);
            }
        }

        List<MapNode> bossLayer = new ArrayList<>();
        bossLayer.add(bossRoom);
        map.add(bossLayer);

        assignRoomTypes(map);

        List<MapNode> flatList = new ArrayList<>();

        for (List<MapNode> row : map) {
            flatList.addAll(row);
        }

        MapLogic.setAllNodes(flatList);

        return map;
    }

    /**
     * สุ่มเลือกคอลัมน์เริ่มต้นของเส้นทาง
     * ต้องมีอย่างน้อย 2 จุดที่ไม่ซ้ำกัน
     *
     * @return รายการ index คอลัมน์เริ่มต้น
     */
    private static List<Integer> getStartingPoints() {

        List<Integer> starts = new ArrayList<>();
        int uniquePoints = 0;
        int attempts = 0;
        int maxAttempts = 1000;

        while (uniquePoints < 2 && attempts < maxAttempts) {

            starts.clear();
            uniquePoints = 0;
            attempts++;

            for (int i = 0; i < Constants.PATHS; i++) {

                int startNode = random.nextInt(Constants.WIDTH);

                if (!starts.contains(startNode)) {
                    uniquePoints++;
                }

                starts.add(startNode);
            }
        }

        if (uniquePoints < 2) {
            starts.clear();
            int fallbackCount = Math.min(2, Constants.WIDTH);
            for (int i = 0; i < fallbackCount; i++) {
                starts.add(i);
            }
        }

        return starts;
    }

    /**
     * สร้างการเชื่อมต่อจากโหนดปัจจุบันไปยังชั้นถัดไป
     *
     * @param grid  grid ของแผนที่ทั้งหมด
     * @param floor ชั้นปัจจุบัน
     * @param col   คอลัมน์ปัจจุบัน
     * @return คอลัมน์ปลายทางที่เชื่อมต่อสำเร็จ
     */
    private static int setupConnection(MapNode[][] grid, int floor, int col) {

        MapNode current = grid[floor][col];

        while (true) {

            int direction = random.nextInt(3) - 1; // -1, 0, +1
            int targetCol = col + direction;

            if (targetCol < 0 || targetCol >= Constants.WIDTH) continue;

            if (!crossesExistingPath(grid, floor, col, targetCol)) {

                MapNode nextRoom = grid[floor + 1][targetCol];
                current.connect(nextRoom);

                return targetCol;
            }
        }
    }

    /**
     * สร้างการเชื่อมต่อจากโหนดปัจจุบันไปยังชั้นถัดไป
     *
     * @param grid  grid ของแผนที่ทั้งหมด
     * @param floor ชั้นปัจจุบัน
     * @param col   คอลัมน์ปัจจุบัน
     * @return คอลัมน์ปลายทางที่เชื่อมต่อสำเร็จ
     */
    private static boolean crossesExistingPath(MapNode[][] grid, int floor, int col, int targetCol) {

        if (targetCol > col) {

            if (col + 1 < Constants.WIDTH) {

                for (MapNode neighborNext : grid[floor][col + 1].getNextNodes()) {
                    if (neighborNext.getColumn() <= targetCol) return true;
                }
            }

        } else if (targetCol < col) {

            if (col - 1 >= 0) {

                for (MapNode neighborNext : grid[floor][col - 1].getNextNodes()) {
                    if (neighborNext.getColumn() >= targetCol) return true;
                }
            }
        }

        return false;
    }

    /**
     * กำหนดประเภทห้องให้แต่ละโหนดตามตำแหน่งชั้น
     *
     * @param map แผนที่ทั้งหมด
     */
    private static void assignRoomTypes(List<List<MapNode>> map) {

        for (int floor = 0; floor < Constants.FLOORS; floor++) {

            for (MapNode node : map.get(floor)) {

                if (!node.isUsed()) continue;

                if (floor == 0) {
                    node.setType(RoomType.ENEMY);
                    node.createRoomFromType();
                }
                else if (floor == Constants.FLOORS - 1) {
                    node.setType(RoomType.REST);
                    node.createRoomFromType();
                }
                else {
                    node.setType(weightedRoomType(floor));
                    node.createRoomFromType();
                }

                node.createRoomFromType();
            }
        }

        ensureMinimumRooms(map);
    }

    /**
     * สุ่มประเภทห้องตามความคืบหน้าของเกม (Early / Mid / Late)
     *
     * @param floor ชั้นปัจจุบัน
     * @return RoomType ที่ถูกสุ่มตามน้ำหนัก
     */
    private static RoomType weightedRoomType(int floor) {

        double progress = (double) floor / Constants.FLOORS;
        int roll = random.nextInt(100);

        // --- Early Game (0% - 40%)
        if (progress < 0.4) {

            if (roll < 60) return RoomType.ENEMY;
            if (roll < 80) return RoomType.EVENT;
            if (roll < 95) return RoomType.REST;

            return RoomType.SHOP; // rare early shop
        }

        // --- Mid Game (40% - 70%)
        if (progress < 0.7) {

            if (roll < 45) return RoomType.ENEMY;
            if (roll < 65) return RoomType.EVENT;
            if (roll < 80) return RoomType.SHOP;   // higher shop chance
            if (roll < 95) return RoomType.REST;

            return RoomType.ELITE; // rare early elite
        }

        // --- Late Game (70%+)
        if (roll < 35) return RoomType.ENEMY;
        if (roll < 55) return RoomType.EVENT;
        if (roll < 70) return RoomType.REST;
        if (roll < 85) return RoomType.SHOP;

        return RoomType.ELITE; // elites mostly late
    }

    /**
     * ตรวจสอบให้แน่ใจว่าแผนที่มีห้องพิเศษขั้นต่ำ
     * (อย่างน้อย 1 Shop, 1 Elite และ 1 Event)
     *
     * @param map แผนที่ทั้งหมด
     */
    private static void ensureMinimumRooms(List<List<MapNode>> map) {

        boolean hasShop = false;
        boolean hasElite = false;
        boolean hasEvent = false;

        List<MapNode> candidates = new ArrayList<>();

        for (int floor = 1; floor < Constants.FLOORS - 1; floor++) {

            for (MapNode node : map.get(floor)) {

                if (!node.isUsed()) continue;

                RoomType type = node.getType();

                if (type == RoomType.SHOP) hasShop = true;
                if (type == RoomType.ELITE) hasElite = true;
                if (type == RoomType.EVENT) hasEvent = true;

                if (type == RoomType.ENEMY) {
                    candidates.add(node); // can convert these
                }
            }
        }

        if (!hasShop && !candidates.isEmpty()) {
            replaceRandom(candidates, RoomType.SHOP);
        }

        if (!hasElite && !candidates.isEmpty()) {
            replaceRandom(candidates, RoomType.ELITE);
        }

        if (!hasEvent && !candidates.isEmpty()) {
            replaceRandom(candidates, RoomType.EVENT);
        }
    }

    /**
     * เปลี่ยนประเภทของโหนดแบบสุ่มเป็นประเภทที่กำหนด
     *
     * @param nodes   รายชื่อโหนดที่สามารถเปลี่ยนได้
     * @param newType ประเภทห้องใหม่
     */
    private static void replaceRandom(List<MapNode> nodes, RoomType newType) {

        MapNode node = nodes.get(random.nextInt(nodes.size()));
        node.setType(newType);
        node.createRoomFromType();
    }
}
