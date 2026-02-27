package map.logic;

import java.util.ArrayList;
import java.util.List;

public class MapLogic {
    private static MapNode currentNode;
    private static List<MapNode> allNodes = new ArrayList<>();

    public static void setCurrentNode(MapNode node) {
        currentNode = node;
    }

    public static MapNode getCurrentNode() {
        return currentNode;
    }

    public static boolean canSelect(MapNode node) {
        return node.isAccessible() && !node.isVisited();
    }

    public static void completeCurrentNode() {

        if (currentNode == null) return;

        // 1️⃣ ทำให้ node ปัจจุบันเป็น visited
        currentNode.setVisited(true);
        currentNode.setAccessible(false);

        int currentFloor = currentNode.getFloor();

        // 2️⃣ ล็อก node อื่นใน floor เดียวกันทั้งหมด
        for (MapNode node : allNodes) {
            if (node.getFloor() == currentFloor && node != currentNode) {
                node.setAccessible(false);
            }
        }

        // 3️⃣ เปิดเฉพาะ nextNodes
        for (MapNode next : currentNode.getNextNodes()) {
            if (!next.isVisited()) {
                next.setAccessible(true);
            }
        }
    }

    public static void reset() {
        currentNode = null;
    }

    public static void setAllNodes(List<MapNode> nodes) {
        allNodes.clear();
        allNodes.addAll(nodes);
    }
}
