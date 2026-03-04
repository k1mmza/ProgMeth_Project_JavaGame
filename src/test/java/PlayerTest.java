import Entity.classes.Knight;
import Entity.classes.Mage;
import Entity.classes.Rogue;
import inventory.Inventory;
import inventory.potion.HealingPotion;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Player System Tests")
class PlayerTest {

    private static final int INVENTORY_CAPACITY = 10;
    private static final int MAX_ENERGY = 5;

    private Knight knight;
    private Mage mage;
    private Rogue rogue;

    @BeforeEach
    void setUp() {
        knight = new Knight("Knight");
        mage = new Mage("Mage");
        rogue = new Rogue("Rogue");
    }

    // ==================================================
    // ENERGY TESTS
    // ==================================================

    @Nested
    class EnergyTests {

        @Test
        void shouldStartWithZeroEnergy() {
            assertAll(
                    () -> assertEquals(0, knight.getEnergy()),
                    () -> assertEquals(0, mage.getEnergy()),
                    () -> assertEquals(0, rogue.getEnergy())
            );
        }

        @Test
        void gainEnergy_shouldCapAtMax() {
            knight.setEnergy(MAX_ENERGY);
            knight.gainEnergy(10);

            assertEquals(MAX_ENERGY, knight.getEnergy());
        }

        @Test
        void useEnergy_shouldFailIfInsufficient() {
            knight.setEnergy(1);

            boolean success = knight.useEnergy(2);

            assertFalse(success);
            assertEquals(1, knight.getEnergy());
        }

        @Test
        void resetEnergy_shouldSetToZero() {
            knight.setEnergy(5);
            knight.resetEnergyToZero();

            assertEquals(0, knight.getEnergy());
        }
    }

    // ==================================================
    // GOLD TESTS
    // ==================================================

    @Nested
    class GoldTests {

        @Test
        void shouldStartWithZeroGold() {
            assertEquals(0, knight.getGold());
        }

        @Test
        void addGold_shouldAccumulate() {
            knight.addGold(30);
            knight.addGold(20);

            assertEquals(50, knight.getGold());
        }

        @Test
        void setGold_shouldOverride() {
            knight.setGold(100);

            assertEquals(100, knight.getGold());
        }
    }

    // ==================================================
    // INVENTORY TESTS
    // ==================================================

    @Nested
    class InventoryTests {

        private Inventory inventory;

        @BeforeEach
        void setupInventory() {
            inventory = knight.getInventory();
        }

        private void fillInventory() {
            for (int i = 0; i < INVENTORY_CAPACITY; i++) {
                inventory.addPotion(new HealingPotion());
            }
        }

        @Test
        void inventory_shouldStartEmpty() {
            assertEquals(0, inventory.getSize());
            assertFalse(inventory.isFull());
        }

        @Test
        void inventory_shouldRespectCapacity() {
            fillInventory();

            boolean success = inventory.addPotion(new HealingPotion());

            assertAll(
                    () -> assertTrue(inventory.isFull()),
                    () -> assertFalse(success),
                    () -> assertEquals(INVENTORY_CAPACITY, inventory.getSize())
            );
        }

        @Test
        void removePotion_shouldDecreaseSize() {
            HealingPotion potion = new HealingPotion();
            inventory.addPotion(potion);

            inventory.removePotion(potion);

            assertEquals(0, inventory.getSize());
        }
    }

    // ==================================================
    // COMBAT RESET
    // ==================================================

    @Test
    void resetCombatState_shouldClearCombatEffects() {
        knight.addShield(20);
        knight.setEnergy(5);
        knight.applyVulnerable(2);

        knight.resetCombatState();

        assertAll(
                () -> assertEquals(0, knight.getShield()),
                () -> assertEquals(0, knight.getEnergy()),
                () -> assertFalse(knight.isVulnerable())
        );
    }

    // ==================================================
    // CLASS STAT TESTS
    // ==================================================

    @Nested
    class ClassStatTests {

        @Test
        void knightStats_shouldMatchDesign() {
            assertAll(
                    () -> assertEquals(100, knight.getMaxHp()),
                    () -> assertEquals(8, knight.getAttack()),
                    () -> assertEquals(6, knight.getDefense())
            );
        }

        @Test
        void mageStats_shouldMatchDesign() {
            assertAll(
                    () -> assertEquals(70, mage.getMaxHp()),
                    () -> assertEquals(13, mage.getAttack()),
                    () -> assertEquals(4, mage.getDefense())
            );
        }

        @Test
        void rogueStats_shouldMatchDesign() {
            assertAll(
                    () -> assertEquals(80, rogue.getMaxHp()),
                    () -> assertEquals(10, rogue.getAttack()),
                    () -> assertEquals(6, rogue.getDefense())
            );
        }
    }

    // ==================================================
    // KNIGHT SKILL TEST
    // ==================================================

    @Test
    void knightSkill1_shouldConsumeEnergyAndDealDamage() {
        knight.setEnergy(1);
        Mage target = new Mage("Target");

        int initialHp = target.getHp();

        boolean success = knight.skill1(target);

        assertAll(
                () -> assertTrue(success),
                () -> assertEquals(0, knight.getEnergy()),
                () -> assertTrue(target.getHp() < initialHp)
        );
    }
}