package map.room;

import Entity.Player;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import map.Room;

public class RoomFactory {

    public static Scene createRoom(Room room, Player player, Runnable onComplete) {

        switch (room.getType()) {

            case ENEMY:
            case ELITE:
            case BOSS:
                return EnemyRoomScene.create(room, player, () -> {
                    room.setCleared(true);
                    onComplete.run();
                });

            case SHOP:
                return ShopRoomScene.create(
                        room.getShop(),
                        player,
                        () -> {
                            room.setCleared(true);
                            onComplete.run();
                        }
                );

            case REST:
                return RestRoomScene.create(
                        room,
                        player,
                        () -> {
                            room.setCleared(true);
                            onComplete.run();
                        }
                );

            case EVENT:
                return EventRoomScene.create(player, onComplete);

            default:
                return createSimpleRoom("Empty Room", onComplete);
        }
    }

    // 🔥 ห้องต่อสู้ (ชั่วคราว)
    private static Scene createBattleRoom(Room room, Player player, Runnable onComplete) {

        Button finishBtn = new Button("Win Battle");

        finishBtn.setOnAction(e -> {
            room.setCleared(true);
            onComplete.run();
        });

        StackPane root = new StackPane(finishBtn);
        root.setStyle("-fx-background-color: black;");

        return new Scene(root, 1024, 768);
    }

    // 🔥 ห้องธรรมดา (fallback)
    private static Scene createSimpleRoom(String title, Runnable onComplete) {

        Button leaveBtn = new Button("Leave " + title);

        leaveBtn.setOnAction(e -> onComplete.run());

        StackPane root = new StackPane(leaveBtn);
        root.setStyle("-fx-background-color: #1e1e1e;");

        return new Scene(root, 1024, 768);
    }
}