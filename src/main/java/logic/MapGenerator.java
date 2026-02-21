package logic;

import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator {
    private static final Random random = new Random();

    public static List<List<MapNode>> generateMap() {
        MapLogic.reset();
        List<List<MapNode>> map = new ArrayList<>();
        MapNode[][] grid = new MapNode[Constants.FLOORS][Constants.WIDTH];

        // 1. สร้าง Grid และ Jitter ตำแหน่ง
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

        // 3. เชื่อมเส้นทาง (เฉพาะชั้น 0 ถึง ชั้นรองสุดท้าย)
        for (int startCol : startCols) {
            int currentCol = startCol;
            for (int floor = 0; floor < Constants.FLOORS - 1; floor++) {
                currentCol = setupConnection(grid, floor, currentCol);
            }
        }

        // 4. สร้างห้อง Boss และบังคับเชื่อมชั้นสุดท้ายไปหาบอส
        double bossX = (3 * Constants.SPACING_X); // ตรงกลางเป๊ะๆ (คอลัมน์ 3 จาก 0-6)
        double bossY = 0;

        MapNode bossRoom = new MapNode(Constants.FLOORS, 3, bossX, bossY);
        bossRoom.setType(RoomType.BOSS);

        // ล็อกให้โหนดทุกตัวบนชั้นบนสุด ลากเส้นเข้าหาบอส
        for (int col = 0; col < Constants.WIDTH; col++) {
            MapNode topNode = grid[Constants.FLOORS - 1][col];
            boolean hasIncomingPath = false;

            // เช็คว่าชั้นก่อนหน้า (FLOORS-2) มีโหนดไหนดึงเส้นมาหา topNode นี้ไหม
            for (int prevCol = 0; prevCol < Constants.WIDTH; prevCol++) {
                if (grid[Constants.FLOORS - 2][prevCol].getNextNodes().contains(topNode)) {
                    hasIncomingPath = true;
                    break;
                }
            }

            // ถ้ามีเส้นทางชี้มาหาโหนดนี้ แสดงว่าโหนดนี้ถูกใช้งาน ให้ลากเส้นต่อไปหาบอส!
            if (hasIncomingPath) {
                topNode.connect(bossRoom);
            }
        }

        List<MapNode> bossLayer = new ArrayList<>();
        bossLayer.add(bossRoom);
        map.add(bossLayer);

        // 5. กำหนดประเภทห้อง
        assignRoomTypes(map);

        return map;
    }

    private static List<Integer> getStartingPoints() {
        List<Integer> starts = new ArrayList<>();
        int uniquePoints = 0;
        while (uniquePoints < 2) {
            starts.clear();
            uniquePoints = 0;
            for (int i = 0; i < Constants.PATHS; i++) {
                int startNode = random.nextInt(Constants.WIDTH);
                if (!starts.contains(startNode)) {
                    uniquePoints++;
                }
                starts.add(startNode);
            }
        }
        return starts;
    }

    private static int setupConnection(MapNode[][] grid, int floor, int col) {
        MapNode current = grid[floor][col];
        while (true) {
            int direction = random.nextInt(3) - 1;
            int targetCol = col + direction;

            if (targetCol < 0 || targetCol >= Constants.WIDTH) continue;

            if (!crossesExistingPath(grid, floor, col, targetCol)) {
                MapNode nextRoom = grid[floor + 1][targetCol];
                current.connect(nextRoom);
                return targetCol;
            }
        }
    }

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

    private static void assignRoomTypes(List<List<MapNode>> map) {
        for (int floor = 0; floor < Constants.FLOORS; floor++) {
            for (MapNode room : map.get(floor)) {
                if (!room.isUsed()) continue;

                if (floor == 0) room.setType(RoomType.ENEMY);
                else if (floor == 8) room.setType(RoomType.TREASURE);
                else if (floor == Constants.FLOORS - 1) room.setType(RoomType.REST);
                else room.setType(randomRoomType());
            }
        }
    }

    private static RoomType randomRoomType() {
        int roll = random.nextInt(100);
        if (roll < 45) return RoomType.ENEMY;
        if (roll < 65) return RoomType.EVENT;
        if (roll < 80) return RoomType.ELITE;
        if (roll < 90) return RoomType.REST;
        return RoomType.SHOP;
    }
}