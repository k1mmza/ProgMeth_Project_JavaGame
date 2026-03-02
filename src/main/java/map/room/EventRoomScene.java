package map.room;

import Entity.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import map.event.EventManager;
import map.event.GameEvent;

/**
 * คลาส EventRoomScene ใช้สร้าง Scene สำหรับห้องประเภท Event
 * <p>
 * เมื่อผู้เล่นเข้าห้อง:
 * - ระบบจะสุ่ม GameEvent จาก EventManager
 * - แสดงชื่อ, คำอธิบาย และรูปภาพของเหตุการณ์
 * - แสดงตัวเลือก (Options) ให้ผู้เล่นเลือก
 * </p>
 *
 * <p>
 * เมื่อผู้เล่นเลือกตัวเลือก:
 * - จะเรียก event.applyChoice(player, index)
 * - ล้างปุ่มตัวเลือกทั้งหมด
 * - แสดงปุ่ม Continue เพื่อออกจากห้อง
 * </p>
 */
public class EventRoomScene {

    /**
     * สร้าง Scene สำหรับห้อง Event
     *
     * @param player ผู้เล่นปัจจุบัน (ใช้สำหรับรับผลจาก event)
     * @param onComplete Runnable ที่ถูกเรียกเมื่อผู้เล่นกด Continue
     * @return Scene สำหรับแสดงเหตุการณ์แบบ interactive
     */
    public static Scene create(Player player, Runnable onComplete) {

        EventManager manager = new EventManager();
        GameEvent event = manager.getRandomEvent();

        // 🔥 Title
        Label title = new Label(event.getName());
        title.setStyle("""
                -fx-font-size: 32px;
                -fx-text-fill: #ff4444;
                -fx-font-weight: bold;
                """);

        // 🔥 Description
        Label description = new Label(event.getDescription());
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.CENTER);
        description.setStyle("""
                -fx-text-fill: #cccccc;
                -fx-font-size: 16px;
                """);

        // 🔥 Image
        ImageView imageView = new ImageView(
                new Image(EventRoomScene.class.getResourceAsStream(event.getImagePath()))
        );
        imageView.setFitWidth(350);
        imageView.setPreserveRatio(true);

        // 🔥 Options
        VBox optionsBox = new VBox(15);
        optionsBox.setAlignment(Pos.CENTER);

        String[] options = event.getOptions();

        for (int i = 0; i < options.length; i++) {
            int index = i;

            Button optionBtn = new Button(options[i]);

            optionBtn.setStyle("""
                    -fx-background-color: #1e1e1e;
                    -fx-text-fill: white;
                    -fx-font-size: 14px;
                    -fx-background-radius: 10;
                    -fx-padding: 10 20 10 20;
                    """);

            optionBtn.setOnMouseEntered(e ->
                    optionBtn.setStyle("""
                            -fx-background-color: #ff4444;
                            -fx-text-fill: black;
                            -fx-font-size: 14px;
                            -fx-background-radius: 10;
                            -fx-padding: 10 20 10 20;
                            """)
            );

            optionBtn.setOnMouseExited(e ->
                    optionBtn.setStyle("""
                            -fx-background-color: #1e1e1e;
                            -fx-text-fill: white;
                            -fx-font-size: 14px;
                            -fx-background-radius: 10;
                            -fx-padding: 10 20 10 20;
                            """)
            );

            optionBtn.setOnAction(e -> {

                event.applyChoice(player, index);

                // ล้างปุ่มเก่า
                optionsBox.getChildren().clear();

                Button continueBtn = new Button("Continue");
                continueBtn.setStyle("""
                        -fx-background-color: #ff4444;
                        -fx-text-fill: black;
                        -fx-font-size: 16px;
                        -fx-background-radius: 10;
                        -fx-padding: 12 25 12 25;
                        """);

                continueBtn.setOnAction(ev -> onComplete.run());

                optionsBox.getChildren().add(continueBtn);
            });

            optionsBox.getChildren().add(optionBtn);
        }

        VBox root = new VBox(25, title, imageView, description, optionsBox);
        root.setAlignment(Pos.CENTER);
        root.setStyle("""
                -fx-background-color: linear-gradient(to bottom, #0f0f0f, #1a1a1a);
                -fx-padding: 40;
                """);

        return new Scene(root, 1000, 700);
    }
}