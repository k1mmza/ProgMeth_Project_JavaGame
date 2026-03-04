import Entity.classes.Knight;
import inventory.potion.AtkPotion;
import inventory.potion.DefPotion;
import inventory.potion.EnergyPotion;
import inventory.potion.HealingPotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Potion Effects Tests")
class PotionTest {

    private Knight player;

    @BeforeEach
    void setUp() {
        // HP 100, Max Energy 6
        player = new Knight("Hero");
    }

    // ==================================================
    // PERMANENT STAT BUFF POTIONS
    // ==================================================

    @Test
    @DisplayName("AtkPotion should permanently increase attack by 1")
    void testAtkPotion() {
        int initialAtk = player.getAttack();
        AtkPotion potion = new AtkPotion();

        assertTrue(potion.use(player));
        assertEquals(initialAtk + 1, player.getAttack());
    }

    @Test
    @DisplayName("DefPotion should permanently increase defense by 1")
    void testDefPotion() {
        int initialDef = player.getDefense();
        DefPotion potion = new DefPotion();

        assertTrue(potion.use(player));
        assertEquals(initialDef + 1, player.getDefense());
    }

    // ==================================================
    // RECOVERY POTIONS (HP & ENERGY)
    // ==================================================

    @Test
    @DisplayName("HealingPotion should restore 25 HP but not exceed Max HP")
    void testHealingPotion() {
        HealingPotion potion = new HealingPotion();

        player.setHp(50);
        potion.use(player);
        assertEquals(75, player.getHp());

        player.setHp(90);
        potion.use(player);
        assertEquals(100, player.getHp());
    }

    @Test
    @DisplayName("EnergyPotion should restore 2 Energy but not exceed Max Energy")
    void testEnergyPotion() {
        EnergyPotion potion = new EnergyPotion();

        player.setEnergy(0);
        potion.use(player);
        assertEquals(2, player.getEnergy());

        player.setEnergy(4);
        potion.use(player);
        assertEquals(5, player.getEnergy());
    }
}