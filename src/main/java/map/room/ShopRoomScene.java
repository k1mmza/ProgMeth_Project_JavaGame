package map.room;

import Entity.Player;
import inventory.potion.Potion;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import map.shop.Shop;

import java.util.List;

/**
 * คลาส ShopRoomScene ใช้สร้างหน้าจอร้านค้า (Shop Room) สำหรับระบบ GUI
 * <p>
 * หน้าจอจะแสดง:
 * - รูปภาพพ่อค้า (Merchant)
 * - จำนวน Gold ของผู้เล่น
 * - รายการ Potion ที่สามารถซื้อได้
 * - ปุ่ม Leave เพื่อออกจากร้าน
 * </p>
 *
 * <p>
 * เมื่อผู้เล่นกดปุ่ม Buy:
 * - หากมี Gold เพียงพอ จะหัก Gold และเพิ่ม Potion เข้า Inventory
 * - หาก Gold ไม่พอ จะแสดงข้อความแจ้งเตือน
 * </p>
 */
public class ShopRoomScene {

    /**
     * สร้าง Scene สำหรับห้องร้านค้า
     *
     * @param shop ร้านค้าที่มีรายการสินค้า
     * @param player ผู้เล่นที่กำลังซื้อของ
     * @param onLeave Runnable ที่จะถูกเรียกเมื่อกดปุ่ม Leave
     * @return Scene สำหรับแสดงหน้าร้านค้า
     */
    public static Scene create(Shop shop, Player player, Runnable onLeave) {

        // ===== โหลดรูป Merchant =====
        Image merchantImg = new Image(
                ShopRoomScene.class.getResourceAsStream("/images/merchant.png")
        );

        ImageView merchantView = new ImageView(merchantImg);
        merchantView.setFitHeight(400);
        merchantView.setPreserveRatio(true);

        // ===== ฝั่งขวา (UI) =====
        Label title = new Label("Merchant");
        title.setStyle("-fx-text-fill: #dddddd; -fx-font-size: 28px;");
        Label dialogue = new Label("\"Looking for something special?\"");
        dialogue.setStyle("-fx-text-fill: #aaaaaa;");

        Label goldLabel = new Label("Gold: " + player.getGold());
        goldLabel.setStyle("-fx-text-fill: gold; -fx-font-size: 18px;");

        VBox itemsBox = new VBox(15);
        itemsBox.setAlignment(Pos.CENTER);

        List<Potion> stock = shop.getStock();

        for (Potion potion : stock) {

            Label itemLabel = new Label(
                    potion.getName() + " - " + potion.getBuyCost() + "g"
            );
            itemLabel.setStyle("-fx-text-fill: white;");

            Button buyBtn = new Button("Buy");

            buyBtn.setOnAction(e -> {

                if (player.getInventory().isFull()) {
                    buyBtn.setText("Inventory full");
                } else if (player.getGold() >= potion.getBuyCost()) {
                    boolean added = player.getInventory().addPotion(potion);
                    if (added) {
                        player.setGold(player.getGold() - potion.getBuyCost());
                        goldLabel.setText("Gold: " + player.getGold());
                        buyBtn.setDisable(true);
                    } else {
                        buyBtn.setText("Inventory full");
                    }
                } else {
                    buyBtn.setText("Not enough gold");
                }
            });

            VBox itemRow = new VBox(5, itemLabel, buyBtn);
            itemRow.setAlignment(Pos.CENTER);

            itemsBox.getChildren().add(itemRow);
        }

        Button leaveBtn = new Button("Leave");
        leaveBtn.setOnAction(e -> onLeave.run());

        VBox rightPanel = new VBox(25, title, goldLabel, itemsBox, leaveBtn);
        rightPanel.setAlignment(Pos.CENTER);

        // ===== Layout หลัก (ซ้าย = รูป / ขวา = ร้าน) =====
        HBox root = new HBox(60, merchantView, rightPanel);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #0f0f0f;");

        return new Scene(root, 1024, 768);
    }
}
