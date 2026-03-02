package application;

import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * คลาสสำหรับจัดการการเปิดใช้งาน JavaFX ภายในแอปพลิเคชัน
 * <p>
 * ใช้ในกรณีที่โปรแกรมหลักเป็น Swing แต่ต้องการเปิดหน้าจอที่พัฒนาโดย JavaFX
 * โดยจะทำหน้าที่เริ่มต้น JavaFX Runtime เพียงครั้งเดียว และเปิดหน้าต่าง
 * {@link MainMap} ทุกครั้งที่ถูกเรียกใช้งาน
 * </p>
 */
public class FXLauncher {

    /**
     * ตัวแปรสถานะสำหรับตรวจสอบว่า JavaFX Platform ถูกเริ่มต้นแล้วหรือไม่
     */
    private static boolean started = false;

    /**
     * เปิดหน้าจอแผนที่หลักของเกมด้วย JavaFX
     * <p>
     * หากยังไม่เคยเริ่มต้น JavaFX Platform จะเรียก {@link Platform#startup(Runnable)}
     * เพื่อเริ่มระบบก่อน แล้วจึงสร้างและแสดง {@link MainMap}
     * </p>
     * <p>
     * หาก JavaFX ถูกเริ่มต้นแล้ว จะใช้ {@link Platform#runLater(Runnable)}
     * เพื่อสร้างหน้าต่างใหม่บน JavaFX Application Thread
     * </p>
     */
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