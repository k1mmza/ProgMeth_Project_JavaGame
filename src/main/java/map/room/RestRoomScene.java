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

/**
 * คลาส RestRoomScene ใช้สร้าง Scene สำหรับห้องพัก (Campfire)
 * <p>
 * เมื่อผู้เล่นเข้าห้อง:
 * - จะได้รับการฟื้นฟู HP อัตโนมัติผ่าน room.restHeal(player)
 * - แสดงจำนวน HP ที่ฟื้นฟู
 * - สามารถใช้ Potion จาก inventory ได้
 * </p>
 *
 * <p>
 * ห้องนี้จะถูกตั้งค่าเป็น cleared เมื่อผู้เล่นกด Leave
 * </p>
 */
public class RestRoomScene {

    /**
     * สร้าง Scene ของห้องพัก
     *
     * @param room ห้องปัจจุบัน (ใช้สำหรับ heal และตั้งค่า cleared)
     * @param player ผู้เล่นปัจจุบัน
     * @param onLeave Runnable ที่ถูกเรียกเมื่อผู้เล่นออกจากห้อง
     * @return Scene สำหรับแสดงผลห้องพัก
     */
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

    /**
     * รีเฟรชปุ่ม Potion ทั้งหมดในกล่อง potionBox
     * <p>
     * - ถ้าไม่มี Potion จะแสดงข้อความแจ้งเตือน
     * - ถ้าใช้ Potion สำเร็จ จะลบออกจาก inventory
     *   และรีเฟรชปุ่มใหม่ทันที
     * </p>
     *
     * @param player ผู้เล่นที่ถือ Potion
     * @param potionBox VBox ที่ใช้แสดงปุ่ม Potion
     */
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