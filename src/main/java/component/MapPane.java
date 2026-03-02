package component;

import enums.RoomType;
import javafx.scene.layout.Pane;
import map.logic.MapNode;
import util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * แผงแสดงผลแผนที่ของเกม
 * <p>
 * ทำหน้าที่วาดเส้นเชื่อมระหว่างโหนด และแสดง {@link MapNodeView}
 * บนหน้าจอ โดยรับข้อมูลโครงสร้างแผนที่จาก {@code List<List<MapNode>>}
 * </p>
 */
public class MapPane extends Pane {

    /**
     * รายการของ MapNodeView ทั้งหมดที่ถูกสร้างบนแผนที่
     */
    private final List<MapNodeView> nodeViews = new ArrayList<>();

    /**
     * สร้าง MapPane เปล่าสำหรับใช้วาดแผนที่
     */
    public MapPane() {

    }

    /**
     * วาดแผนที่ลงบน Pane
     * <p>
     * ขั้นตอนการวาด:
     * <ol>
     *     <li>ล้างองค์ประกอบเดิมทั้งหมด</li>
     *     <li>วาดเส้นเชื่อม (ConnectionLine) ระหว่างโหนด</li>
     *     <li>วาดโหนด (MapNodeView) ทับบนเส้น</li>
     *     <li>อัปเดตสถานะการแสดงผลของโหนด</li>
     * </ol>
     *
     * @param map โครงสร้างแผนที่ในรูปแบบรายการหลายชั้น (floor)
     */
    public void drawMap(List<List<MapNode>> map) {
        getChildren().clear();
        nodeViews.clear();

        // 1. วาดเส้นก่อน
        for (List<MapNode> floor : map) {
            for (MapNode node : floor) {
                if (!node.isUsed()) continue;

                for (MapNode next : node.getNextNodes()) {
                    ConnectionLine line = new ConnectionLine(
                            node.getX(),
                            node.getY(),
                            next.getX(),
                            next.getY()
                    );
                    getChildren().add(line);
                }
            }
        }

        // 2. วาดโหนดวางทับเส้น
        for (List<MapNode> floor : map) {
            for (MapNode node : floor) {
                if (!node.isUsed()) continue;

                MapNodeView view = new MapNodeView(node, this);

                // ปรับตำแหน่งให้กึ่งกลางของโหนดตรงกับพิกัดที่กำหนด
                double radius = (node.getType() == RoomType.BOSS)
                        ? Constants.BOSS_RADIUS
                        : Constants.NODE_RADIUS;

                view.setLayoutX(node.getX() - radius);
                view.setLayoutY(node.getY() - radius);

                nodeViews.add(view);
                getChildren().add(view);
            }
        }

        refreshNodes();
    }

    /**
     * รีเฟรชสถานะการแสดงผลของโหนดทั้งหมดบนแผนที่
     * <p>
     * จะเรียก {@link MapNodeView#updateVisuals()} เพื่อปรับเอฟเฟกต์
     * และความโปร่งใสตามสถานะของแต่ละโหนด
     * </p>
     */
    public void refreshNodes() {
        for (MapNodeView view : nodeViews) {
            view.updateVisuals();
        }
    }
}