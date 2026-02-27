package map.logic;

import enums.RoomType;
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

        // 1️⃣ Create grid with jittered positions
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

        // 2️⃣ Pick starting columns
        List<Integer> startCols = getStartingPoints();

        // 3️⃣ Create connections
        for (int startCol : startCols) {

            int currentCol = startCol;

            for (int floor = 0; floor < Constants.FLOORS - 1; floor++) {
                currentCol = setupConnection(grid, floor, currentCol);
            }
        }

        // 4️⃣ Create Boss Node
        double bossX = (Constants.WIDTH / 2.0) * Constants.SPACING_X;
        double bossY = 0;

        MapNode bossRoom = new MapNode(Constants.FLOORS, Constants.WIDTH / 2, bossX, bossY);
        bossRoom.setType(RoomType.BOSS);
        bossRoom.createRoomFromType(); // 🔥 integrate your Room

        // Connect top floor nodes to boss if used
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

// 🔥 รวม node ทั้งหมดเข้า list เดียว
        List<MapNode> flatList = new ArrayList<>();

        for (List<MapNode> row : map) {
            flatList.addAll(row);
        }

// 🔥 ส่งให้ MapLogic ใช้สำหรับล็อกเส้นทาง
        MapLogic.setAllNodes(flatList);

        return map;
    }

    // --------------------------------------------------

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

    // --------------------------------------------------

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

    // --------------------------------------------------

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

    // --------------------------------------------------

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

    // --------------------------------------------------

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

    private static void replaceRandom(List<MapNode> nodes, RoomType newType) {

        MapNode node = nodes.get(random.nextInt(nodes.size()));
        node.setType(newType);
        node.createRoomFromType();
    }
}