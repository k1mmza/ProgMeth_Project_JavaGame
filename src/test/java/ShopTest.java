import Entity.classes.Mage;
import map.shop.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Shop and Economy Tests")
class ShopTest {

    private Shop shop;
    private Mage player;

    @BeforeEach
    void setUp() {
        shop = new Shop();
        player = new Mage("Hero");
        player.setGold(50);
    }

    // ==================================================
    // SUCCESSFUL TRANSACTION TESTS
    // ==================================================

    @Test
    @DisplayName("Player can buy potion if they have enough gold and space")
    void testBuyPotionSuccess() {
        // Healing Potion (index 0) cost 15
        boolean success = shop.buyPotion(0, player);

        assertTrue(success);
        assertEquals(35, player.getGold()); // 50 - 15 = 35
        assertEquals(1, player.getInventory().getSize());
    }

    // ==================================================
    // FAILED TRANSACTION TESTS
    // ==================================================

    @Test
    @DisplayName("Player cannot buy potion if gold is insufficient")
    void testBuyPotionNotEnoughGold() {
        player.setGold(10);

        boolean success = shop.buyPotion(0, player);

        assertFalse(success);
        assertEquals(10, player.getGold());
        assertEquals(0, player.getInventory().getSize());
    }

    @Test
    @DisplayName("Player cannot buy potion if inventory is full")
    void testBuyPotionInventoryFull() {
        // Size inventory = 10
        for (int i = 0; i < 10; i++) {
            player.getInventory().addPotion(shop.getStock().get(0));
        }

        // Try buying more
        boolean success = shop.buyPotion(1, player); // Buy Atk Potion

        assertFalse(success);
        assertEquals(50, player.getGold());
        assertEquals(10, player.getInventory().getSize());
    }
}