package logic;

public class MapLogic {
    private static MapNode currentNode;

    public static void  setCurrentNode(MapNode node){
        currentNode = node;
        System.out.println("Selected: "+ node.getType());
    }

    public static MapNode getCurrentNode(){
        return currentNode;
    }

    public static void reset() {
        currentNode = null;
    }

    public static boolean canSelect(MapNode node) {
        if (currentNode == null) {
            return node.getFloor() == 0;
        } else {
            return currentNode.getNextNodes().contains(node);
        }
    }
}
