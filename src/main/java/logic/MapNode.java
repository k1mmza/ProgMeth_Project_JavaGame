package logic;

import java.util.HashSet;
import java.util.Set;

public class MapNode {
    private final int floor;
    private final int column;
    private double x;
    private double y;
    private RoomType type;

    private final Set<MapNode> nextNodes = new HashSet<>();

    public MapNode(int floor, int column, double x, double y) {
        this.floor = floor;
        this.column = column;
        this.x = x;
        this.y = y;
        this.type = null;
    }

    public void connect(MapNode node){
        nextNodes.add(node);
    }

    public boolean isUsed() {
        return !nextNodes.isEmpty() || type == RoomType.BOSS;
    }

    public int getFloor() {
        return floor;
    }

    public int getColumn() {
        return column;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public Set<MapNode> getNextNodes() {
        return nextNodes;
    }
}
