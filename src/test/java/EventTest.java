import Entity.classes.Rogue;
import map.event.AncientStatueEvent;
import map.event.FreePotionEvent;
import map.event.HealingEvent;
import map.event.RiskGoldEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Event Logic Tests")
class EventTest {

    private Rogue player;

    @BeforeEach
    void setUp() {
        player = new Rogue("Hero"); // Max HP 80
    }

    // ==================================================
    // RISK & REWARD EVENT TESTS
    // ==================================================

    @Test
    @DisplayName("RiskGoldEvent: Choice 0 should deal 8 damage and give 25 gold")
    void testRiskGoldEventAccept() {
        RiskGoldEvent event = new RiskGoldEvent();
        player.setHp(80);
        player.setGold(0);

        // takeDamage(8)
        // addGold(25)
        event.applyChoice(player, 0);

        // TakeDamage 8 def 30% (Def 6 * 5%) Dealt Damage = 8 * 0.7 = 6
        // 80 - 6 = 74
        assertEquals(74, player.getHp());
        assertEquals(25, player.getGold());
    }

    // ==================================================
    // HEALING EVENT TESTS
    // ==================================================

    @Test
    @DisplayName("HealingEvent: Choice 0 should heal 10 HP")
    void testHealingEventAccept() {
        HealingEvent event = new HealingEvent();
        player.setHp(50);

        //heal(10)
        event.applyChoice(player, 0);

        assertEquals(60, player.getHp());
    }

    // ==================================================
    // MULTI-CHOICE EVENT TESTS
    // ==================================================

    @Test
    @DisplayName("AncientStatueEvent: Options should grant correct buffs")
    void testAncientStatueEvent() {
        AncientStatueEvent event = new AncientStatueEvent();

        // Test Option 0: +1 Attack
        int initialAtk = player.getAttack();
        event.applyChoice(player, 0);
        assertEquals(initialAtk + 1, player.getAttack());

        // Test Option 1: +5 Max HP and heal 5
        int initialMaxHp = player.getMaxHp();
        player.setHp(50);
        event.applyChoice(player, 1);
        assertEquals(initialMaxHp + 5, player.getMaxHp());
        assertEquals(55, player.getHp());
    }

    // ==================================================
// FREE POTION EVENT TESTS
// ==================================================

    @Test
    @DisplayName("FreePotionEvent: Player should receive a potion")
    void testFreePotionEvent() {

        FreePotionEvent event = new FreePotionEvent();

        int initialPotionCount = player.getInventory().getSize();

        // apply event
        event.applyChoice(player, 0);

        // inventory should increase
        assertEquals(initialPotionCount + 1, player.getInventory().getSize());
    }
}