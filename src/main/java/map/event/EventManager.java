package map.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * คลาส EventManager ใช้จัดการรายการอีเวนต์ทั้งหมดในเกม
 * <p>
 * ทำหน้าที่เก็บ GameEvent ที่มีอยู่ในระบบ
 * และสุ่มเลือกอีเวนต์หนึ่งรายการเมื่อถูกเรียกใช้งาน
 * </p>
 */
public class EventManager {

    private List<GameEvent> events;
    private Random random;

    /**
     * สร้าง EventManager และลงทะเบียนอีเวนต์ทั้งหมดที่สามารถสุ่มได้
     */
    public EventManager() {
        random = new Random();
        events = new ArrayList<>();

        events.add(new HealingEvent());
        events.add(new RiskGoldEvent());
        events.add(new AncientStatueEvent());
        events.add(new FreePotionEvent());
        // สามารถเพิ่มอีเวนต์ใหม่ได้ที่นี่
    }

    /**
     * สุ่มและคืนค่าอีเวนต์ 1 รายการจากรายการทั้งหมด
     *
     * @return GameEvent แบบสุ่ม
     */
    public GameEvent getRandomEvent() {
        return events.get(random.nextInt(events.size()));
    }
}