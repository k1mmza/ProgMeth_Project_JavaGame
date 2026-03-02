package util;

/**
 * คลาส Constants เก็บค่าคงที่ทั้งหมดที่ใช้ในระบบแผนที่ของเกม
 * <p>
 * ใช้กำหนดโครงสร้างของแผนที่ เช่น
 * จำนวนชั้น (FLOORS), ความกว้าง (WIDTH),
 * จำนวนเส้นทางเริ่มต้น (PATHS),
 * รวมถึงค่าระยะห่างและขนาดของโหนดสำหรับแสดงผล
 * </p>
 */
public class Constants {

    /**
     * จำนวนชั้นทั้งหมดของแผนที่ (ไม่รวมชั้น Boss)
     */
    public static final int FLOORS = 15;

    /**
     * จำนวนคอลัมน์ของแผนที่ในแต่ละชั้น
     */
    public static final int WIDTH = 7;

    /**
     * จำนวนเส้นทางเริ่มต้นจากชั้นแรก
     */
    public static final int PATHS = 4;

    /**
     * ระยะห่างแนวนอนระหว่างโหนด
     */
    public static final double SPACING_X = 65;

    /**
     * ระยะห่างแนวตั้งระหว่างชั้น
     */
    public static final double SPACING_Y = 45;

    /**
     * ค่าความสุ่มของตำแหน่งโหนด
     * ใช้เพิ่มความกระจัดกระจายเพื่อไม่ให้โหนดเรียงตรงเกินไป
     */
    public static final double PLACEMENT_RANDOMNESS = 12.0;

    /**
     * รัศมีของโหนดปกติ
     */
    public static final double NODE_RADIUS = 12;

    /**
     * รัศมีของโหนด Boss
     */
    public static final double BOSS_RADIUS = 20;
}