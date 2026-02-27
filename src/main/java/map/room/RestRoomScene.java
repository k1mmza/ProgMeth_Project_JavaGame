package map.room;

import Entity.Player;
import inventory.potion.Potion;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import map.Room;

import java.util.ArrayList;
import java.util.List;

public class RestRoomScene {

    public static Scene create(Room room, Player player, Runnable onLeave) {

        // ======================
        // Heal อัตโนมัติ
        // ======================
        int beforeHp = player.getHp();
        room.restHeal(player);
        int healedAmount = player.getHp() - beforeHp;

        // ======================
        // รูปกองไฟ
        // ======================
        Image fireImg = new Image(
                RestRoomScene.class.getResourceAsStream("/images/campfire.png")
        );

        ImageView fireView = new ImageView(fireImg);
        fireView.setFitHeight(280);
        fireView.setPreserveRatio(true);

        // ======================
        // ข้อความ
        // ======================
        Label title = new Label("Campfire");
        title.setStyle("-fx-text-fill: orange; -fx-font-size: 28px;");

        Label healText = new Label(
                "Recovered " + healedAmount + " HP"
        );
        healText.setStyle("-fx-text-fill: white;");

        // ======================
        // กล่อง Potion
        // ======================
        VBox potionBox = new VBox(10);
        potionBox.setAlignment(Pos.CENTER);

        refreshPotionButtons(player, potionBox);

        // ======================
        // Leave
        // ======================
        Button leaveBtn = new Button("Leave");
        leaveBtn.setOnAction(e -> {
            room.setCleared(true);
            onLeave.run();
        });

        VBox root = new VBox(20,
                title,
                fireView,
                healText,
                potionBox,
                leaveBtn
        );

        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #1b1b1b;");

        return new Scene(root, 1024, 768);
    }

    // ============================
    // สร้างปุ่ม potion ใหม่ทุกครั้ง
    // ============================
    private static void refreshPotionButtons(Player player, VBox potionBox) {

        potionBox.getChildren().clear();

        List<Potion> potions =
                new ArrayList<>(player.getInventory().getPotions());

        if (potions.isEmpty()) {

            Label noPotion = new Label("No potions available.");
            noPotion.setStyle("-fx-text-fill: gray;");
            potionBox.getChildren().add(noPotion);
            return;
        }

        for (Potion potion : potions) {

            Button potionBtn = new Button("Use " + potion.getName());

            potionBtn.setOnAction(e -> {

                if (player.getInventory().getPotions().contains(potion)) {

                    if (potion.use(player)) {

                        // 🔥 ลบออกจาก inventory ที่นี่
                        player.getInventory().removePotion(potion);

                        refreshPotionButtons(player, potionBox);
                    }
                }
            });

            potionBox.getChildren().add(potionBtn);
        }
    }
}