package application;

import javafx.application.Platform;
import javafx.stage.Stage;

public class FXLauncher {

    private static boolean started = false;

    public static void openMap() {

        if (!started) {
            Platform.startup(() -> {
                started = true;
                new MainMap().start(new Stage());
            });
        } else {
            Platform.runLater(() -> {
                new MainMap().start(new Stage());
            });
        }
    }
}