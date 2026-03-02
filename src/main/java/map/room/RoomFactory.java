package map.room;

import Entity.Player;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import map.Room;


/**
 * คลาส RoomFactory ทำหน้าที่สร้าง Scene ของห้องแต่ละประเภท
 * ตาม RoomType ของ Room ที่ส่งเข้ามา
 * <p>
 * ใช้หลัก Factory Pattern เพื่อแยกการสร้าง UI ของแต่ละห้องออกจาก logic หลัก
 * ทำให้โค้ดจัดการง่ายและขยายประเภทห้องใหม่ได้สะดวก
 * </p>
 *
 * <p>
 * ประเภทห้องที่รองรับ:
 * - ENEMY / ELITE / BOSS → ห้องต่อสู้
 * - SHOP → ห้องร้านค้า
 * - REST → ห้องพัก
 * - EVENT → ห้องอีเวนต์
 * - อื่น ๆ → ห้องว่าง (fallback)
 * </p>
 */
public class RoomFactory {

    /**
     * สร้าง Scene ของห้องตามประเภทที่กำหนด
     *
     * @param room ห้องที่ต้องการสร้าง Scene
     * @param player ผู้เล่นปัจจุบัน
     * @param onComplete Runnable ที่จะถูกเรียกเมื่อผู้เล่นเล่นห้องเสร็จ
     * @return Scene สำหรับแสดงผลห้องนั้น
     */
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

    /**
     * สร้างห้องแบบพื้นฐาน (Fallback)
     * ใช้ในกรณีที่ไม่มีประเภทห้องรองรับ
     *
     * @param title ชื่อห้อง
     * @param onComplete Runnable ที่จะถูกเรียกเมื่อกดปุ่มออก
     * @return Scene แบบเรียบง่าย
     */
    private static Scene createSimpleRoom(String title, Runnable onComplete) {

        Button leaveBtn = new Button("Leave " + title);

        leaveBtn.setOnAction(e -> onComplete.run());

        StackPane root = new StackPane(leaveBtn);
        root.setStyle("-fx-background-color: #1e1e1e;");

        return new Scene(root, 1024, 768);
    }
}