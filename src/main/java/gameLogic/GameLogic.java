package gameLogic;

import Entity.Player;
import enums.RoomType;
import map.Room;
import map.logic.MapGenerator;
import map.logic.MapNode;

import java.util.List;
import java.util.Scanner;

public class GameLogic {

    private final Player player;
    private final List<List<MapNode>> map;
    private MapNode currentNode;
    private final CombatManager combatManager;
    private final Scanner scanner;

    public GameLogic(Player player) {
        this.player = player;
        this.map = MapGenerator.generateMap();
        this.currentNode = findStartNode();
        this.combatManager = new CombatManager();
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {

        printGameStart();

        while (player.isAlive()) {

            processCurrentRoom();

            if (!player.isAlive()) break;

            if (isFinalBossCleared()) {
                printVictory();
                return;
            }

            moveToNextNode();
        }

        printGameOver();
    }

    // ==========================
    // Core Flow Methods
    // ==========================

    private void processCurrentRoom() {
        Room room = currentNode.getRoom();
        room.enter(player, combatManager, scanner);
    }

    private boolean isFinalBossCleared() {
        Room room = currentNode.getRoom();
        return room.getType() == RoomType.BOSS && room.isCleared();
    }

    private void moveToNextNode() {

        List<MapNode> nextNodes = currentNode.getNextNodes().stream().toList();

        if (nextNodes.isEmpty()) {
            System.out.println("There are no more paths...");
            return;
        }

        while (true) {

            System.out.println("\nChoose your path:");

            for (int i = 0; i < nextNodes.size(); i++) {
                System.out.println(i + ". " + nextNodes.get(i).getRoom().getType());
            }

            System.out.print("Choice: ");
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= 0 && choice < nextNodes.size()) {
                    currentNode = nextNodes.get(choice);
                    return;
                }

            } catch (NumberFormatException ignored) {}

            System.out.println("Invalid input. Try again.");
        }
    }

    // ==========================
    // Helper Methods
    // ==========================

    private MapNode findStartNode() {
        // First floor is index 0
        for (MapNode node : map.get(0)) {
            if (node.isUsed()) {
                return node;
            }
        }
        throw new RuntimeException("No valid start node found!");
    }

    // ==========================
    // UI Print Methods
    // ==========================

    private void printGameStart() {
        System.out.println("=================================");
        System.out.println("         GAME START              ");
        System.out.println("=================================");
    }

    private void printVictory() {
        System.out.println("\n=================================");
        System.out.println("        DUNGEON CLEARED          ");
        System.out.println("=================================");
    }

    private void printGameOver() {
        System.out.println("\n=================================");
        System.out.println("           GAME OVER             ");
        System.out.println("=================================");
    }
}