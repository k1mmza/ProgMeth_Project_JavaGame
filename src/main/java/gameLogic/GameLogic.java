package gameLogic;

import Entity.Player;
import enums.RoomType;
import map.Room;
import map.logic.MapGenerator;
import map.logic.MapNode;

import java.util.List;

public class GameLogic {

    private final Player player;
    private final List<List<MapNode>> map;
    private MapNode currentNode;
    private final CombatManager combatManager;

    public GameLogic(Player player) {
        this.player = player;
        this.map = MapGenerator.generateMap();
        this.currentNode = findStartNode();
        this.combatManager = new CombatManager();
    }

    // ==========================
    // FLOW CONTROL (GUI CALLS THESE)
    // ==========================

    public Room getCurrentRoom() {
        return currentNode.getRoom();
    }

    public void enterCurrentRoom() {
        currentNode.getRoom().enter(player, combatManager);
    }

    public List<MapNode> getNextNodes() {
        return currentNode.getNextNodes().stream().toList();
    }

    public void moveToNode(MapNode node) {
        currentNode = node;
    }

    public boolean isGameOver() {
        return !player.isAlive();
    }

    public boolean isFinalBossCleared() {
        Room room = currentNode.getRoom();
        return room.getType() == RoomType.BOSS && room.isCleared();
    }

    public Player getPlayer() {
        return player;
    }

    // ==========================
    // INTERNAL
    // ==========================

    private MapNode findStartNode() {
        for (MapNode node : map.get(0)) {
            if (node.isUsed()) {
                return node;
            }
        }
        throw new RuntimeException("No valid start node found!");
    }
}