package test;

import Entity.Player;
import Entity.classes.Knight;
import Entity.classes.Mage;
import combat.CombatManager;
import enemy.Enemy;
import enemy.basic.Goblin;
import enemy.boss.GoblinChief;

public class CombatTest {
    public static void main(String[] args) {
        Player player = new Mage("Hero");
        Enemy goblin = new Goblin();
        Enemy goblinChief = new GoblinChief();

        CombatManager combatManager = new CombatManager();
        combatManager.startBattle(player,goblinChief);
    }
}
