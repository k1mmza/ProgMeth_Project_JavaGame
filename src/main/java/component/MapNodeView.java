package component;

import application.MainMap;
import enums.RoomType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import map.logic.MapLogic;
import map.logic.MapNode;
import map.room.RoomFactory;
import util.Constants;

/**
 * คลาสแสดงผลโหนด (Node) บนแผนที่เกม
 * <p>
 * ทำหน้าที่เป็นตัวแทนภาพของ {@link MapNode} บนหน้าจอ
 * โดยแสดงไอคอนตามประเภทห้อง ({@link RoomType})
 * และจัดการการโต้ตอบของผู้เล่น เช่น การ hover และการคลิกเพื่อเข้าสู่ห้อง
 * </p>
 */
public class MapNodeView extends StackPane {

    /**
     * โหนดข้อมูลที่เชื่อมกับ View นี้
     */
    private final MapNode node;

    /**
     * ตัวแสดงรูปภาพของโหนด
     */
    private final ImageView imageView;

    /**
     * สร้าง MapNodeView สำหรับโหนดที่กำหนด
     *
     * @param node    โหนดข้อมูลของแผนที่
     * @param mapPane ออบเจกต์ MapPane ที่ใช้สำหรับรีเฟรชสถานะหลังจบห้อง
     */
    public MapNodeView(MapNode node, MapPane mapPane) {
        this.node = node;

        double radius = (node.getType() == RoomType.BOSS)
                ? Constants.BOSS_RADIUS
                : Constants.NODE_RADIUS;
        double size = radius * 2;

        this.setMinSize(size, size);
        this.setMaxSize(size, size);

        imageView = new ImageView();
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);

        Image icon = getImageByType(node.getType());
        if (icon != null) {
            imageView.setImage(icon);
        }

        getChildren().add(imageView);

        setOnMouseEntered(e -> {
            if (MapLogic.canSelect(node)) {
                imageView.setFitWidth(size + 6);
                imageView.setFitHeight(size + 6);
            }
        });

        setOnMouseExited(e -> {
            imageView.setFitWidth(size);
            imageView.setFitHeight(size);
        });

        setOnMouseClicked(event -> {
            if (MapLogic.canSelect(node)) {
                openRoom(mapPane);
            }
        });
    }

    /**
     * อัปเดตลักษณะการแสดงผลของโหนดตามสถานะปัจจุบัน
     * <ul>
     *     <li>โหนดปัจจุบัน: แสดงเอฟเฟกต์เรืองแสง</li>
     *     <li>โหนดที่เลือกได้: แสดงปกติ</li>
     *     <li>โหนดที่เลือกไม่ได้: ทำให้จางลง</li>
     * </ul>
     */
    public void updateVisuals() {
        if (MapLogic.getCurrentNode() == node) {
            DropShadow glow = new DropShadow(20, Color.ORANGE);
            imageView.setEffect(glow);
            this.setOpacity(1.0);
        } else if (MapLogic.canSelect(node)) {
            imageView.setEffect(null);
            this.setOpacity(1.0);
        } else {
            imageView.setEffect(null);
            this.setOpacity(0.35);
        }
    }

    /**
     * คืนค่า Image ตามประเภทของห้อง
     *
     * @param type ประเภทห้อง
     * @return ไฟล์รูปภาพของโหนด หรือ null หากไม่พบ
     */
    private Image getImageByType(RoomType type) {
        if (type == null) return null;

        String path = "";
        switch (type) {
            case ENEMY: path = "/map/Enemy.png"; break;
            case ELITE: path = "/map/Elite.png"; break;
            case SHOP: path = "/map/Shop.png"; break;
            case REST: path = "/map/Rest.png"; break;
            case BOSS: path = "/map/Boss.png"; break;
            case TREASURE: path = "/map/Treasure.png"; break;
            case EVENT: path = "/map/Event.png"; break;
        }

        try {
            return new Image(getClass().getResourceAsStream(path), 60, 60, true, true);
        } catch (Exception e) {
            System.out.println("ไม่พบไฟล์รูปภาพ: " + path);
            return null;
        }
    }

    /**
     * เปิดห้องตามโหนดที่ถูกเลือก
     * <p>
     * จะสร้าง Scene ของห้องผ่าน {@link RoomFactory}
     * และสลับ Scene ไปยังห้องนั้น พร้อมจัดการเพลงและสถานะของแผนที่
     * </p>
     *
     * @param mapPane แผงแผนที่ที่ใช้สำหรับรีเฟรชสถานะโหนด
     */
    private void openRoom(MapPane mapPane) {

        MapLogic.setCurrentNode(node);

        Scene roomScene = RoomFactory.createRoom(
                node.getRoom(),
                MainMap.getPlayer(),
                () -> {

                    MapLogic.completeCurrentNode();

                    if (node.getType() == RoomType.BOSS) {
                        MainMap.stopMapMusic();
                        MainMap.switchScene(createEndGameScene());
                        return;
                    }

                    mapPane.refreshNodes();
                    MainMap.switchScene(MainMap.getMapScene());
                    MainMap.playMapMusic();
                }
        );

        MainMap.playRoomMusic(node.getType());
        MainMap.switchScene(roomScene);
    }

    /**
     * สร้าง Scene จบเกมเมื่อผู้เล่นเอาชนะ BOSS ได้
     *
     * @return Scene สำหรับหน้าจอจบเกม
     */
    private Scene createEndGameScene() {

        Label winLabel = new Label("BOSS DEFEATED");
        winLabel.setStyle("""
        -fx-font-size: 56px;
        -fx-text-fill: #aa0000;
        -fx-font-weight: bold;
    """);

        DropShadow redGlow = new DropShadow();
        redGlow.setColor(Color.DARKRED);
        redGlow.setRadius(60);
        winLabel.setEffect(redGlow);

        Label subText = new Label("Silence falls across the dungeon...");
        subText.setStyle("""
        -fx-font-size: 18px;
        -fx-text-fill: #888888;
        -fx-font-style: italic;
    """);

        Button exitBtn = new Button("EXIT");
        exitBtn.setStyle(darkButtonStyle());
        exitBtn.setOnAction(e -> System.exit(0));

        VBox root = new VBox(30, winLabel, subText, exitBtn);
        root.setAlignment(Pos.CENTER);

        root.setStyle("""
        -fx-background-color: linear-gradient(to bottom, #000000, #0a0a0f, #140000);
    """);

        return new Scene(root, 900, 600);
    }

    /**
     * คืนค่า CSS style สำหรับปุ่มธีมมืด
     *
     * @return สตริง CSS สำหรับปุ่ม
     */
    private String darkButtonStyle() {
        return """
        -fx-background-color: #111111;
        -fx-text-fill: #aa0000;
        -fx-font-size: 14px;
        -fx-font-weight: bold;
        -fx-border-color: #550000;
        -fx-border-width: 2;
        -fx-background-radius: 5;
        -fx-padding: 8 25 8 25;
        -fx-cursor: hand;
    """;
    }
}