import static org.junit.jupiter.api.Assertions.*;

import Entity.Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EntityTest {

    // -------------------------
    // Test Double
    // -------------------------

    static class TestEntity extends Entity {
        public TestEntity(String name, int hp, int attack, int defense) {
            super(name, hp, attack, defense);
        }
    }

    private TestEntity newAttacker() {
        return new TestEntity("Attacker", 100, 20, 5);
    }

    private TestEntity newDefender() {
        return new TestEntity("Defender", 100, 10, 5);
    }

    // -------------------------
    // Basic Combat
    // -------------------------

    @Nested
    class BasicCombat {

        @Test
        @DisplayName("Normal attack should reduce HP")
        void normalAttack_shouldReduceHp() {
            var attacker = newAttacker();
            var defender = newDefender();

            int initialHp = defender.getHp();

            attacker.normalAttack(defender);

            assertTrue(defender.getHp() < initialHp);
        }

        @Test
        @DisplayName("Damage should never be less than 1")
        void damage_shouldHaveMinimumOne() {
            var attacker = newAttacker();
            var defender = newDefender();

            defender.setDefense(999);

            int initialHp = defender.getHp();
            attacker.normalAttack(defender);

            int damage = initialHp - defender.getHp();
            assertEquals(1, damage);
        }
    }

    // -------------------------
    // Shield
    // -------------------------

    @Nested
    class ShieldTests {

        @Test
        void shield_shouldAbsorbDamage() {
            var attacker = newAttacker();
            var defender = newDefender();

            defender.addShield(10);
            int initialHp = defender.getHp();

            attacker.normalAttack(defender);

            assertTrue(defender.getShield() >= 0);
            assertTrue(defender.getHp() <= initialHp);
        }

        @Test
        void shield_shouldNeverBeNegative() {
            var attacker = newAttacker();
            var defender = newDefender();

            defender.addShield(1);
            attacker.normalAttack(defender);

            assertTrue(defender.getShield() >= 0);
        }
    }

    // -------------------------
    // Status Effects
    // -------------------------

    @Nested
    class StatusEffectTests {

        @Test
        void vulnerable_shouldIncreaseDamage() {
            var attacker = newAttacker();
            var defender1 = newDefender();
            var defender2 = newDefender();

            int baseHp = defender1.getHp();
            attacker.normalAttack(defender1);
            int normalDamage = baseHp - defender1.getHp();

            defender2.applyVulnerable(1);
            baseHp = defender2.getHp();
            attacker.normalAttack(defender2);
            int vulnerableDamage = baseHp - defender2.getHp();

            assertTrue(vulnerableDamage > normalDamage);
        }

        @Test
        void poison_shouldDamageOnStartTurn() {
            var defender = newDefender();
            defender.addPoison(3);

            int initialHp = defender.getHp();
            defender.startTurn();

            assertTrue(defender.getHp() < initialHp);
        }

        @Test
        void evade_shouldBlockAttack() {
            var attacker = newAttacker();
            var defender = newDefender();

            defender.addEvade(1);

            int initialHp = defender.getHp();
            attacker.normalAttack(defender);

            assertEquals(initialHp, defender.getHp());
        }
    }

    // -------------------------
    // Life & Death
    // -------------------------

    @Nested
    class LifeCycleTests {

        @Test
        void entity_shouldDieAtZeroHp() {
            var attacker = newAttacker();
            var defender = newDefender();

            defender.setHp(1);
            attacker.normalAttack(defender);

            assertFalse(defender.isAlive());
        }

        @Test
        void heal_shouldNotReviveDeadEntity() {
            var defender = newDefender();
            defender.setHp(0);

            defender.heal(50);

            assertFalse(defender.isAlive());
        }
    }
}