package application;

import Entity.Player;
import component.MapPane;
import enums.RoomType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import map.logic.MapGenerator;
import map.logic.MapNode;

import java.util.List;

/**
 * คลาสหลักสำหรับแสดงแผนที่ของเกมด้วย JavaFX
 * <p>
 * ทำหน้าที่:
 * <ul>
 *     <li>สร้างและแสดงหน้าจอแผนที่ (Map Scene)</li>
 *     <li>สร้างข้อมูลแผนที่ด้วย {@link MapGenerator}</li>
 *     <li>จัดการการเปลี่ยน Scene ภายในเกม</li>
 *     <li>จัดการเพลงพื้นหลังของแผนที่และห้องประเภทต่าง ๆ</li>
 *     <li>เก็บข้อมูล {@link Player} ปัจจุบันของเกม</li>
 * </ul>
 */
public class MainMap extends Application {

    /**
     * Scene หลักของหน้าแผนที่
     */
    private static Scene mapScene;

    /**
     * Stage หลักของแอปพลิเคชัน
     */
    private static Stage primaryStage;

    /**
     * ผู้เล่นปัจจุบันที่ใช้งานอยู่ในเกม
     */
    private static Player player;

    /**
     * ตัวจัดการเพลงพื้นหลัง
     */
    private static MediaPlayer mapMusic;

    /**
     * เมธอดเริ่มต้นของ JavaFX Application
     * <p>
     * สร้างหน้าจอแผนที่, โหลดข้อมูลแผนที่แบบ background thread
     * และแสดงผลบน JavaFX Application Thread
     * </p>
     *
     * @param stage เวทีหลักของ JavaFX
     */
    @Override
    public void start(Stage stage) {

        primaryStage = stage;

        MapPane pane = new MapPane();
        Group mapGroup = new Group(pane);
        StackPane root = new StackPane(mapGroup);
        root.setStyle("-fx-background-color: #2b2b2b;");

        // สร้างแผนที่ใน thread แยก เพื่อไม่ให้ UI ค้าง
        new Thread(() -> {
            List<List<MapNode>> map = MapGenerator.generateMap();

            // ปลดล็อก floor แรก
            for (MapNode node : map.get(0)) {
                if (node.isUsed()) {
                    node.setAccessible(true);
                }
            }

            Platform.runLater(() -> {
                pane.drawMap(map);
            });

        }).start();

        mapScene = new Scene(root, 1024, 768);

        stage.setTitle("SLAY THE GPA");
        stage.setScene(mapScene);
        playMapMusic();
        stage.show();
    }

    /**
     * เปลี่ยน Scene ของ Stage หลัก
     *
     * @param scene Scene ใหม่ที่ต้องการแสดง
     */
    public static void switchScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    /**
     * คืนค่า Scene ของหน้าแผนที่
     *
     * @return Scene หลักของแผนที่
     */
    public static Scene getMapScene() {
        return mapScene;
    }

    /**
     * เมธอด main สำหรับรัน JavaFX Application โดยตรง
     *
     * @param args อาร์กิวเมนต์จาก command line
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * กำหนดผู้เล่นปัจจุบันของเกม
     *
     * @param p ออบเจกต์ผู้เล่น
     */
    public static void setPlayer(Player p) {
        player = p;
    }

    /**
     * คืนค่าผู้เล่นปัจจุบัน
     *
     * @return ผู้เล่นที่กำลังใช้งาน
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * เล่นเพลงพื้นหลังของหน้าแผนที่
     * <p>
     * หากมีเพลงเดิมกำลังเล่นอยู่ จะหยุดก่อนเริ่มเพลงใหม่
     * </p>
     */
    public static void playMapMusic() {
        try {
            if (mapMusic != null) {
                mapMusic.stop();
            }

            Media media = new Media(
                    MainMap.class.getResource("/music/map.mp3").toExternalForm()
            );

            mapMusic = new MediaPlayer(media);
            mapMusic.setCycleCount(MediaPlayer.INDEFINITE);
            mapMusic.setVolume(0.5);
            mapMusic.play();

        } catch (Exception e) {
            System.out.println("Map music load error");
        }
    }

    /**
     * เล่นเพลงตามประเภทของห้อง
     *
     * @param type ประเภทของห้อง ({@link RoomType})
     */
    public static void playRoomMusic(RoomType type) {
        String resourcePath = getRoomMusicPath(type);
        try {
            if (mapMusic != null) {
                mapMusic.stop();
            }

            Media media = new Media(
                    MainMap.class.getResource(resourcePath).toExternalForm()
            );

            mapMusic = new MediaPlayer(media);
            mapMusic.setCycleCount(MediaPlayer.INDEFINITE);
            mapMusic.setVolume(0.5);
            mapMusic.play();

        } catch (Exception e) {
            System.out.println("Room music load error");
        }
    }

    /**
     * คืนค่า path ของไฟล์เพลงตามประเภทห้อง
     *
     * @param type ประเภทของห้อง
     * @return path ของไฟล์เสียงที่อยู่ใน resources
     */
    private static String getRoomMusicPath(RoomType type) {
        if (type == null) return "/music/map.mp3";

        return switch (type) {
            case ENEMY -> "/music/enemy.mp3";
            case ELITE -> "/music/enemy.mp3";
            case SHOP -> "/music/shop.mp3";
            case REST -> "/music/rest.mp3";
            case BOSS -> "/music/final_boss.mp3";
            case TREASURE -> "/music/treasure.mp3";
            case EVENT -> "/music/rest.mp3";
            default -> "/music/map.mp3";
        };
    }

    /**
     * หยุดเพลงที่กำลังเล่นอยู่
     */
    public static void stopMapMusic() {
        if (mapMusic != null) {
            mapMusic.stop();
        }
    }
}