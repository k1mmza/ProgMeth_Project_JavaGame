package application;

import Entity.Player;
import component.MapPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import map.logic.MapGenerator;
import map.logic.MapNode;

import java.util.List;

public class MainMap extends Application {
    private static Scene mapScene;
    private static Stage primaryStage;
    private static Player player;

    @Override
    public void start(Stage stage) {

        primaryStage = stage; // เก็บ stage ไว้ใช้เปลี่ยน scene

        MapPane pane = new MapPane();
        Group mapGroup = new Group(pane);
        StackPane root = new StackPane(mapGroup);
        root.setStyle("-fx-background-color: #2b2b2b;");

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

        mapScene = new Scene(root, 1024, 768); // ✅ เก็บ scene

        stage.setTitle("SLAY THE GPA");
        stage.setScene(mapScene);
        stage.show();
    }

    public static void switchScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static Scene getMapScene() {
        return mapScene;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setPlayer(Player p) {
        player = p;
    }

    public static Player getPlayer() {
        return player;
    }
}