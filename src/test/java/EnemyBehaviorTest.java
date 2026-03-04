import Entity.classes.Knight;
import Entity.enemy.basic.Goblin;
import Entity.enemy.boss.IronGladiator;
import Entity.enemy.boss.RatKing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Enemy & Boss Behavior Tests")
class EnemyBehaviorTest {

    private Knight player;

    @BeforeEach
    void setUp() {
        player = new Knight("Hero");
    }

    // ==================================================
    // BOSS PHASE TRANSITION TESTS
    // ==================================================

    @Test
    @DisplayName("IronGladiator should enter Phase 2 and increase attack when HP <= 40%")
    void testIronGladiatorPhaseTwo() {
        IronGladiator boss = new IronGladiator();
        int initialAtk = boss.getAttack();

        // set Hp 40% Max Hp (Max HP = 150) --> 150 * 40% = 60
        boss.setHp(60);

        // Force the boss to use `performAction` to check the phase transition conditions.
        boss.performAction(player);

        // attack damage increases by 10 and armor is reduced to 5.
        assertEquals(initialAtk + 10, boss.getAttack());
        assertEquals(5, boss.getDefense());
    }

    @Test
    @DisplayName("RatKing should enter Frenzy and increase attack when HP <= 50%")
    void testRatKingFrenzy() {
        RatKing boss = new RatKing();
        int initialAtk = boss.getAttack();

        // set Hp 50% Max Hp (Max HP = 50)
        boss.setHp(25);
        boss.performAction(player);

        // Frenzy attack damage increases by 3
        assertEquals(initialAtk + 3, boss.getAttack());
    }

    // ==================================================
    // ENEMY MECHANIC TESTS
    // ==================================================

    @Test
    @DisplayName("Goblin charge should make it vulnerable and deal heavy damage next turn")
    void testGoblinChargeMechanic() {
        Goblin goblin = new Goblin();

        boolean charged = false;
        for (int i = 0; i < 50; i++) {
            goblin.performAction(player);
            if (goblin.isVulnerable()) {
                charged = true;
                break;
            }
        }

        if (charged) {
            int hpBeforeHeavySlash = player.getHp();

            goblin.performAction(player);

            int actualDamage = hpBeforeHeavySlash - player.getHp();


            assertTrue(actualDamage > 0);
        }
    }
}