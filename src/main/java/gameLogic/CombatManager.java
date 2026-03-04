package gameLogic;

import Entity.Player;
import Entity.enemy.Enemy;
import inventory.potion.*;

import java.util.*;
import java.util.function.Supplier;

/**
 * คลาสจัดการระบบการต่อสู้แบบ Turn-Based
 * ระหว่าง Player และกลุ่ม Enemy
 *
 * <p>
 * หน้าที่ของคลาสนี้:
 * <ul>
 *     <li>ควบคุมลำดับเทิร์น (Player → Enemy → End Turn)</li>
 *     <li>จัดการการโจมตีในแต่ละเทิร์น</li>
 *     <li>ลบศัตรูที่ถูกกำจัดออกจากรายการ</li>
 *     <li>มอบรางวัลหลังจบการต่อสู้</li>
 * </ul>
 * </p>
 */
public class CombatManager {

    private final Scanner scanner;
    private final Random random;

    /**
     * Constructor เริ่มต้น
     * ใช้ Scanner และ Random แบบปกติ
     */
    public CombatManager() {
        this(new Scanner(System.in), new Random());
    }

    /**
     * Constructor สำหรับกำหนด Scanner และ Random เอง
     * (เหมาะสำหรับใช้ในการเขียน Unit Test)
     *
     * @param scanner แหล่งรับค่าจากผู้ใช้
     * @param random  ตัวสร้างค่าการสุ่ม
     */
    public CombatManager(Scanner scanner, Random random) {
        this.scanner = scanner;
        this.random = random;
    }

    /**
     * เริ่มต้นการต่อสู้ระหว่างผู้เล่นกับศัตรูหลายตัว
     *
     * @param player  ผู้เล่น
     * @param enemies รายชื่อศัตรูที่ต้องต่อสู้
     */
    public void startBattle(Player player, List<Enemy> enemies) {

        List<Enemy> snapshot = new ArrayList<>(enemies);
        player.resetEnergyToZero();
        int turnCounter = 1;

        while (player.isAlive() && !enemies.isEmpty()) {

            // ===== เทิร์นของผู้เล่น =====
            executePlayerTurn(player, enemies);

            removeDeadEnemies(enemies);
            if (enemies.isEmpty()) break;

            // ===== เทิร์นของศัตรู =====
            executeEnemyTurn(player, enemies);

            removeDeadEnemies(enemies);

            // ===== จบเทิร์น =====
            player.endTurn();
            enemies.forEach(Enemy::endTurn);

            turnCounter++;
        }

        // ===== จบการต่อสู้ =====
        if (player.isAlive()) {
            grantRewards(player, snapshot);
            player.resetCombatState();
        }
    }

    /**
     * ดำเนินการเทิร์นของผู้เล่น
     *
     * @param player  ผู้เล่น
     * @param enemies รายชื่อศัตรู
     */
    protected void executePlayerTurn(Player player, List<Enemy> enemies) {

        player.startTurn();
        if (!player.isAlive()) return;

        player.gainEnergy(1);

        if (!enemies.isEmpty()) {
            player.normalAttack(enemies.get(0));
        }
    }

    /**
     * ดำเนินการเทิร์นของศัตรูทั้งหมด
     *
     * @param player  ผู้เล่น
     * @param enemies รายชื่อศัตรู
     */
    protected void executeEnemyTurn(Player player, List<Enemy> enemies) {

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                enemy.startTurn();
                enemy.performAction(player);
            }
        }
    }

    /**
     * ลบศัตรูที่พลังชีวิตเหลือ 0 ออกจากรายการ
     *
     * @param enemies รายชื่อศัตรู
     */
    protected void removeDeadEnemies(List<Enemy> enemies) {
        enemies.removeIf(e -> !e.isAlive());
    }

    /**
     * มอบรางวัลให้ผู้เล่นหลังจากชนะการต่อสู้
     *
     * <ul>
     *     <li>รวมทองจากศัตรูที่ถูกกำจัดทั้งหมด</li>
     *     <li>มีโอกาส 30% ได้รับ Potion แบบสุ่ม</li>
     * </ul>
     *
     * @param player   ผู้เล่น
     * @param defeated รายชื่อศัตรูที่ถูกกำจัด
     */
    protected void grantRewards(Player player, List<Enemy> defeated) {

        int totalGold = defeated.stream()
                .mapToInt(Enemy::getGoldReward)
                .sum();

        player.addGold(totalGold);

        if (random.nextDouble() < 0.3) {
            Potion potion = getRandomPotion();
            player.getInventory().addPotion(potion);
        }
    }

    /**
     * สุ่ม Potion จากรายการที่กำหนดไว้
     *
     * @return วัตถุ Potion แบบสุ่ม
     */
    private Potion getRandomPotion() {
        int index = random.nextInt(potionPool.size());
        return potionPool.get(index).get();
    }

    /**
     * รายการชนิด Potion ที่สามารถสุ่มได้
     */
    private final List<Supplier<Potion>> potionPool = List.of(
            HealingPotion::new,
            EnergyPotion::new,
            AtkPotion::new,
            DefPotion::new
    );
}