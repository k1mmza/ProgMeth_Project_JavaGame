import Entity.Entity;
import Entity.classes.Knight;
import Entity.classes.Mage;
import Entity.classes.Rogue;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class Skill Tests")
class SkillTest {

    private Knight knight;
    private Mage mage;
    private Rogue rogue;

    @BeforeEach
    void setUp() {
        knight = new Knight("Knight");
        mage = new Mage("Mage");
        rogue = new Rogue("Rogue");
    }

    // =====================================================
    // DAMAGE CALCULATOR (match Entity.takeDamage)
    // =====================================================

    private int calculateFinalDamage(int rawDamage, Entity target) {

        int damage = rawDamage;

        if (target.isVulnerable()) {
            damage = (int)(damage * 1.5);
        }

        double reduction = target.getDefense() * 0.05;
        reduction = Math.min(reduction, 0.60);

        int reducedAmount = (int)(damage * reduction);
        damage -= reducedAmount;

        damage = Math.max(damage, 1);

        return damage;
    }

    // =====================================================
    // KNIGHT SKILLS
    // =====================================================

    @Nested
    class KnightSkills {

        @Test
        void skill1_ShieldStrike_success() {
            knight.setEnergy(1);
            Mage target = new Mage("Target");

            int initialHp = target.getHp();

            int rawDamage = knight.getAttack() + 2;
            int expectedDamage = calculateFinalDamage(rawDamage, target);

            boolean success = knight.skill1(target);

            assertAll(
                    () -> assertTrue(success),
                    () -> assertEquals(0, knight.getEnergy()),
                    () -> assertEquals(initialHp - expectedDamage, target.getHp()),
                    () -> assertEquals(5, knight.getShield())
            );
        }

        @Test
        void skill2_Fortify_success() {
            knight.setEnergy(2);

            boolean success = knight.skill2(mage);

            assertAll(
                    () -> assertTrue(success),
                    () -> assertEquals(0, knight.getEnergy()),
                    () -> assertEquals(15, knight.getShield())
            );
        }

        @Test
        void skill3_ShieldSlam_success() {
            knight.setEnergy(3);
            knight.addShield(10);

            Mage target = new Mage("Target");
            int initialHp = target.getHp();

            int rawDamage = knight.getAttack() + 10;
            int expectedDamage = calculateFinalDamage(rawDamage, target);

            boolean success = knight.skill3(target);

            assertAll(
                    () -> assertTrue(success),
                    () -> assertEquals(initialHp - expectedDamage, target.getHp()),
                    () -> assertTrue(target.isVulnerable()),
                    () -> assertEquals(0, knight.getShield())
            );
        }
    }

    // =====================================================
    // MAGE SKILLS
    // =====================================================

    @Nested
    class MageSkills {

        @Test
        void skill1_Firebolt_success() {
            mage.setEnergy(2);
            Knight target = new Knight("Target");

            int initialHp = target.getHp();

            int rawDamage = mage.getAttack() + 6;
            int expectedDamage = calculateFinalDamage(rawDamage, target);

            boolean success = mage.skill1(target);

            assertAll(
                    () -> assertTrue(success),
                    () -> assertEquals(0, mage.getEnergy()),
                    () -> assertEquals(initialHp - expectedDamage, target.getHp())
            );
        }

        @Test
        void skill2_ManaBarrier_success() {
            mage.setEnergy(2);

            boolean success = mage.skill2(knight);

            assertAll(
                    () -> assertTrue(success),
                    () -> assertEquals(15, mage.getShield())
            );
        }

        @Test
        void skill3_ArcaneNova_success() {
            mage.setEnergy(3);
            Knight target = new Knight("Target");

            int initialHp = target.getHp();

            int rawDamage = mage.getAttack() * 2 + 6;
            int expectedDamage = calculateFinalDamage(rawDamage, target);

            boolean success = mage.skill3(target);

            assertAll(
                    () -> assertTrue(success),
                    () -> assertEquals(initialHp - expectedDamage, target.getHp())
            );
        }
    }

    // =====================================================
    // ROGUE SKILLS
    // =====================================================

    @Nested
    class RogueSkills {

        @Test
        void skill1_Backstab_normalDamage() {
            rogue.setEnergy(1);
            Mage target = new Mage("Target");

            int initialHp = target.getHp();

            int rawDamage = rogue.getAttack() + 5;
            int expectedDamage = calculateFinalDamage(rawDamage, target);

            boolean success = rogue.skill1(target);

            assertAll(
                    () -> assertTrue(success),
                    () -> assertEquals(initialHp - expectedDamage, target.getHp())
            );
        }

        @Test
        void skill1_Backstab_bonusDamage_whenTargetVulnerable() {
            rogue.setEnergy(1);
            Mage target = new Mage("Target");
            target.applyVulnerable(1);

            int initialHp = target.getHp();

            int rawDamage = (int)((rogue.getAttack() + 5) * 1.5); // rogue multiplier
            int expectedDamage = calculateFinalDamage(rawDamage, target);

            rogue.skill1(target);

            assertEquals(initialHp - expectedDamage, target.getHp());
        }

        @Test
        void skill2_SmokeBomb_success() {
            rogue.setEnergy(1);
            Mage target = new Mage("Target");

            boolean success = rogue.skill2(target);

            assertAll(
                    () -> assertTrue(success),
                    () -> assertTrue(target.isVulnerable()),
                    () -> assertTrue(rogue.getEvadeStacks() > 0)
            );
        }

        @Test
        void skill3_PoisonedBlade_success() {
            rogue.setEnergy(2);
            Mage target = new Mage("Target");

            int initialHp = target.getHp();

            int rawDamage = rogue.getAttack();
            int expectedDamage = calculateFinalDamage(rawDamage, target);

            boolean success = rogue.skill3(target);

            assertAll(
                    () -> assertTrue(success),
                    () -> assertEquals(initialHp - expectedDamage, target.getHp()),
                    () -> assertEquals(4, target.getPoisonTurns())
            );
        }
    }
}