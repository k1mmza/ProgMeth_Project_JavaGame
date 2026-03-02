package map;

import Entity.Player;
import Entity.enemy.Enemy;
import Entity.enemy.basic.*;
import Entity.enemy.boss.*;
import enums.RoomType;
import gameLogic.CombatManager;
import inventory.potion.Potion;
import map.shop.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * คลาส Room แทนห้องหนึ่งห้องในแผนที่ของเกม
 * <p>
 * ห้องจะถูกกำหนดประเภทด้วย RoomType เช่น:
 * ENEMY, ELITE, BOSS, SHOP, EVENT หรือ REST
 * และจะสร้างเนื้อหาภายในห้องตามประเภทนั้นโดยอัตโนมัติ
 * </p>
 *
 * <p>
 * ห้องสามารถ:
 * - สร้างศัตรูตามประเภท
 * - เริ่มการต่อสู้
 * - ฟื้นฟูผู้เล่น (ห้องพัก)
 * - เปิดร้านค้า
 * - ใช้โพชั่น
 * </p>
 */
public class Room {

    private RoomType type;
    private List<Enemy> enemies;
    private boolean cleared;
    private Shop shop;
    private static final Random random = new Random();

    /**
     * สร้างห้องตามประเภทที่กำหนด
     * และเรียก spawn logic ตามประเภทห้อง
     *
     * @param type ประเภทของห้อง
     */
    public Room(RoomType type) {
        this.type = type;
        this.enemies = new ArrayList<>();
        this.cleared = false;

        switch (type) {
            case ENEMY -> spawnNormalEnemies();
            case ELITE -> spawnEliteEnemy();
            case BOSS -> spawnBoss();
            case SHOP -> this.shop = new Shop();
        }
    }

    // ========================
    // Getter
    // ========================

    /**
     * @return ประเภทของห้อง
     */
    public RoomType getType() { return type; }

    /**
     * @return true หากห้องถูกเคลียร์แล้ว
     */
    public boolean isCleared() { return cleared; }

    /**
     * กำหนดสถานะ cleared ของห้อง
     *
     * @param cleared ค่าสถานะใหม่
     */
    public void setCleared(boolean cleared) { this.cleared = cleared; }

    /**
     * @return รายการศัตรูภายในห้อง
     */
    public List<Enemy> getEnemies() { return enemies; }

    /**
     * @return ร้านค้า (ถ้าเป็นห้อง SHOP)
     */
    public Shop getShop() { return shop; }

    // ========================
    // ENTER ROOM
    // ========================

    /**
     * เรียกเมื่อผู้เล่นเข้าสู่ห้อง
     * <p>
     * - ห้องต่อสู้จะเริ่ม Combat
     * - ห้องประเภทอื่นให้ GUI จัดการ
     * </p>
     *
     * @param player ผู้เล่น
     * @param combatManager ตัวจัดการระบบต่อสู้
     */
    public void enter(Player player, CombatManager combatManager) {

        if (cleared) return;

        switch (type) {
            case ENEMY, ELITE, BOSS -> handleCombat(player, combatManager);
            case SHOP, EVENT, REST -> {
                // GUI handles these
            }
        }
    }

    /**
     * จัดการระบบต่อสู้ในห้อง
     *
     * @param player ผู้เล่น
     * @param combatManager ตัวจัดการระบบต่อสู้
     */
    private void handleCombat(Player player, CombatManager combatManager) {

        if (enemies.isEmpty()) return;

        combatManager.startBattle(player, enemies);

        if (player.isAlive()) {
            enemies.clear();
            cleared = true;
        }
    }

    // ========================
    // REST LOGIC (GUI CALLS)
    // ========================

    /**
     * ฟื้นฟูพลังชีวิตผู้เล่น 30% ของ Max HP
     *
     * @param player ผู้เล่น
     */
    public void restHeal(Player player) {
        int healAmount = (int)(player.getMaxHp() * 0.3);
        player.heal(healAmount);
    }

    /**
     * เพิ่ม Max HP ของผู้เล่น 5 หน่วย
     *
     * @param player ผู้เล่น
     */
    public void restIncreaseMaxHp(Player player) {
        player.setMaxHp(player.getMaxHp() + 5);
    }

    /**
     * ใช้โพชั่นจาก inventory ของผู้เล่น
     *
     * @param player ผู้เล่น
     * @param potion โพชั่นที่ต้องการใช้
     */
    public void usePotion(Player player, Potion potion) {
        potion.use(player);
        player.getInventory().removePotion(potion);
    }

    // ========================
    // SPAWN LOGIC
    // ========================

    /**
     * สร้างศัตรูปกติ (1–2 ตัว)
     */
    private void spawnNormalEnemies() {
        enemies.add(getRandomBasicEnemy());
        if (random.nextDouble() < 0.3) {
            enemies.add(getRandomBasicEnemy());
        }
    }

    /**
     * สุ่มสร้างศัตรูพื้นฐาน
     *
     * @return ศัตรูพื้นฐานแบบสุ่ม
     */
    private Enemy getRandomBasicEnemy() {
        return switch (random.nextInt(3)) {
            case 0 -> new Goblin();
            case 1 -> new Rat();
            default -> new Slime();
        };
    }

    /**
     * สร้างศัตรูระดับ Elite
     */
    private void spawnEliteEnemy() {
        switch (random.nextInt(3)) {
            case 0 -> {
                enemies.add(new GoblinChief());
                enemies.add(new Goblin());
            }
            case 1 -> {
                enemies.add(new RatKing());
                enemies.add(new Rat());
            }
            case 2 -> {
                enemies.add(new SlimeTyrant());
                enemies.add(new Slime());
            }
        }
    }

    /**
     * สร้างศัตรู Boss
     */
    private void spawnBoss() {
        enemies.add(new IronGladiator());
    }
}