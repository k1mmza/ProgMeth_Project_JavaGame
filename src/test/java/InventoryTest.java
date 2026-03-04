import inventory.Inventory;
import inventory.potion.HealingPotion;
import inventory.potion.Potion;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Inventory System Tests")
class InventoryTest {

    private static final int CAPACITY = 10;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(CAPACITY);
    }

    // -------------------------
    // Helper Methods
    // -------------------------

    private void fillInventory() {
        for (int i = 0; i < CAPACITY; i++) {
            inventory.addPotion(new HealingPotion());
        }
    }

    // -------------------------
    // Initialization
    // -------------------------

    @Test
    void newInventory_shouldBeEmpty() {
        assertAll(
                () -> assertEquals(0, inventory.getSize()),
                () -> assertFalse(inventory.isFull()),
                () -> assertTrue(inventory.getPotions().isEmpty())
        );
    }

    // -------------------------
    // Add Potion
    // -------------------------

    @Test
    void addPotion_shouldIncreaseSize() {
        boolean success = inventory.addPotion(new HealingPotion());

        assertAll(
                () -> assertTrue(success),
                () -> assertEquals(1, inventory.getSize())
        );
    }

    @Test
    void addPotion_shouldFailWhenFull() {
        fillInventory();

        boolean success = inventory.addPotion(new HealingPotion());

        assertAll(
                () -> assertFalse(success),
                () -> assertEquals(CAPACITY, inventory.getSize()),
                () -> assertTrue(inventory.isFull())
        );
    }

    @Test
    void addPotion_shouldWorkUpToCapacity() {
        for (int i = 0; i < CAPACITY; i++) {
            assertTrue(inventory.addPotion(new HealingPotion()));
        }

        assertEquals(CAPACITY, inventory.getSize());
    }

    // -------------------------
    // Remove Potion
    // -------------------------

    @Test
    void removePotion_shouldDecreaseSize() {
        HealingPotion potion = new HealingPotion();
        inventory.addPotion(potion);

        boolean success = inventory.removePotion(potion);

        assertAll(
                () -> assertTrue(success),
                () -> assertEquals(0, inventory.getSize())
        );
    }

    @Test
    void removePotion_shouldFailIfNotPresent() {
        inventory.addPotion(new HealingPotion());

        boolean success = inventory.removePotion(new HealingPotion());

        assertFalse(success);
        assertEquals(1, inventory.getSize());
    }

    @Test
    void inventory_shouldBeReusableAfterRemoval() {
        fillInventory();
        assertTrue(inventory.isFull());

        Potion first = inventory.getPotions().get(0);
        inventory.removePotion(first);

        assertFalse(inventory.isFull());
        assertTrue(inventory.addPotion(new HealingPotion()));
    }

    // -------------------------
    // Capacity Behavior
    // -------------------------

    @Test
    void isFull_shouldBeAccurate() {
        assertFalse(inventory.isFull());

        fillInventory();

        assertTrue(inventory.isFull());
    }

    // -------------------------
    // Order Preservation
    // -------------------------

    @Test
    void inventory_shouldMaintainInsertionOrder() {
        Potion p1 = new HealingPotion();
        Potion p2 = new HealingPotion();
        Potion p3 = new HealingPotion();

        inventory.addPotion(p1);
        inventory.addPotion(p2);
        inventory.addPotion(p3);

        assertAll(
                () -> assertEquals(p1, inventory.getPotions().get(0)),
                () -> assertEquals(p2, inventory.getPotions().get(1)),
                () -> assertEquals(p3, inventory.getPotions().get(2))
        );
    }

    // -------------------------
    // Different Capacity
    // -------------------------

    @Test
    void differentCapacity_shouldWorkCorrectly() {
        Inventory small = new Inventory(3);

        for (int i = 0; i < 3; i++) {
            small.addPotion(new HealingPotion());
        }

        assertAll(
                () -> assertTrue(small.isFull()),
                () -> assertEquals(3, small.getSize())
        );
    }
}