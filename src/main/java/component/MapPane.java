package component;

import javafx.scene.layout.Pane;
import logic.MapNode;
import logic.RoomType;
import util.Constants;

import java.util.ArrayList;
import java.util.List;

public class MapPane extends Pane {

    private final List<MapNodeView> nodeViews = new ArrayList<>();

    public MapPane() {

    }

    public void drawMap(List<List<MapNode>> map) {
        getChildren().clear();
        nodeViews.clear();

        // 1. วาดเส้นก่อน
        for (List<MapNode> floor : map) {
            for (MapNode node : floor) {
                if (!node.isUsed()) continue;

                for (MapNode next : node.getNextNodes()) {
                    ConnectionLine line = new ConnectionLine(node.getX(), node.getY(), next.getX(), next.getY());
                    getChildren().add(line);
                }
            }
        }

        // 2. วาดโหนดวางทับเส้น
        for (List<MapNode> floor : map) {
            for (MapNode node : floor) {
                if (!node.isUsed()) continue;

                MapNodeView view = new MapNodeView(node, this);

                // หักลบรัศมีเพื่อให้จุดกึ่งกลางวงกลมอยู่ที่พิกัด node.getX(), node.getY() พอดี
                double radius = (node.getType() == RoomType.BOSS) ? Constants.BOSS_RADIUS : Constants.NODE_RADIUS;
                view.setLayoutX(node.getX() - radius);
                view.setLayoutY(node.getY() - radius);

                nodeViews.add(view);
                getChildren().add(view);
            }
        }
        refreshNodes();
    }
    public void refreshNodes() {
        for (MapNodeView view : nodeViews) {
            view.updateVisuals();
        }
    }
}


