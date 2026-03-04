import Entity.classes.Knight;
import Entity.enemy.Enemy;
import gameLogic.CombatManager;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CombatManager Refactored Tests")
class CombatManagerTest {

    private Knight player;
    private List<Enemy> enemies;

    @BeforeEach
    void setup() {
        player = new Knight("Hero");
        enemies = new ArrayList<>();
    }

    private CombatManager createManagerWithFixedRandom(double value) {
        Random fakeRandom = new Random() {
            @Override
            public double nextDouble() {
                return value;
            }

            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };

        return new CombatManager(new Scanner(""), fakeRandom);
    }

    // ================================
    // Player Death Test
    // ================================

    @Test
    void testPlayerDies() {
        CombatManager manager = createManagerWithFixedRandom(1.0);

        MockEnemy enemy = new MockEnemy("StrongEnemy");
        enemy.setAttack(999);

        enemies.add(enemy);

        manager.startBattle(player, enemies);

        assertFalse(player.isAlive());
    }

    // ================================
    // Enemy Defeat Test
    // ================================

    @Test
    void testEnemyDiesAndRemoved() {
        CombatManager manager = createManagerWithFixedRandom(1.0);

        MockEnemy enemy = new MockEnemy("WeakEnemy");
        enemy.setHp(1);
        enemy.setAttack(0);

        enemies.add(enemy);

        manager.startBattle(player, enemies);

        assertTrue(player.isAlive());
        assertTrue(enemies.isEmpty());
    }

    // ================================
    // Gold Reward Test
    // ================================

    @Test
    void testGoldRewardCorrect() {
        CombatManager manager = createManagerWithFixedRandom(1.0);

        MockEnemy enemy = new MockEnemy("GoldEnemy");
        enemy.setHp(1);
        enemy.setAttack(0);
        enemy.setGoldReward(50);

        enemies.add(enemy);

        manager.startBattle(player, enemies);

        assertEquals(50, player.getGold());
    }

    // ================================
    // Multiple Enemy Gold
    // ================================

    @Test
    void testMultipleEnemyGold() {
        CombatManager manager = createManagerWithFixedRandom(1.0);

        MockEnemy e1 = new MockEnemy("E1");
        e1.setHp(1);
        e1.setAttack(0);
        e1.setGoldReward(30);

        MockEnemy e2 = new MockEnemy("E2");
        e2.setHp(1);
        e2.setAttack(0);
        e2.setGoldReward(40);

        enemies.add(e1);
        enemies.add(e2);

        manager.startBattle(player, enemies);

        assertEquals(70, player.getGold());
    }

    // ================================
    // Potion Drop Test (Forced)
    // ================================

    @Test
    void testPotionDropOccurs() {
        CombatManager manager = createManagerWithFixedRandom(0.1);

        MockEnemy enemy = new MockEnemy("PotionEnemy");
        enemy.setHp(1);
        enemy.setAttack(0);

        enemies.add(enemy);

        manager.startBattle(player, enemies);

        assertFalse(player.getInventory().getPotions().isEmpty());
    }

    // ================================
    // No Potion Drop
    // ================================

    @Test
    void testPotionNotDropped() {
        CombatManager manager = createManagerWithFixedRandom(0.9);

        MockEnemy enemy = new MockEnemy("NoPotionEnemy");
        enemy.setHp(1);
        enemy.setAttack(0);

        enemies.add(enemy);

        manager.startBattle(player, enemies);

        assertTrue(player.getInventory().getPotions().isEmpty());
    }

    // ================================
    // Mock Enemy
    // ================================

    private static class MockEnemy extends Enemy {

        public MockEnemy(String name) {
            super(name, 30, 10, 2, 25);
        }

        @Override
        public void performAction(Entity.Entity target) {
            if (isAlive()) {
                normalAttack(target);
            }
        }
    }
}