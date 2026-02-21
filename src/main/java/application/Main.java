package application;

import component.MapPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.MapGenerator;
import logic.MapNode;

import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage){
        MapPane pane = new MapPane();

        Group mapGroup = new Group(pane);

        StackPane root = new StackPane(mapGroup);
        root.setStyle("-fx-background-color: #2b2b2b;");

        new Thread(() -> {
            List<List<MapNode>> map = MapGenerator.generateMap();

            Platform.runLater(() -> {
                pane.drawMap(map);
            });
        }).start();

        Scene scene = new Scene(root,1024, 768);
        primaryStage.setTitle("Slay the Spire Map");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
