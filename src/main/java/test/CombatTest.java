package test;

import Entity.Player;
import Entity.classes.Knight;
import Entity.classes.Mage;
import Entity.classes.Rogue;
import Entity.enemy.basic.Goblin;
import Entity.enemy.basic.Rat;
import Entity.enemy.basic.Slime;
import gameLogic.CombatManager;
import Entity.enemy.Enemy;
import Entity.enemy.boss.GoblinChief;

import java.util.ArrayList;
import java.util.List;

public class CombatTest {

    public static void main(String[] args) {

        Player player = new Knight("Hero");
        Enemy goblin = new Goblin();
        Enemy slime = new Slime();
        Enemy rat = new Rat();
        Enemy goblinChief = new GoblinChief();
        Enemy slimeTyrant = new Entity.enemy.boss.SlimeTyrant();
        Enemy ratKing = new Entity.enemy.boss.RatKing();
        Enemy ironGladiator = new Entity.enemy.boss.IronGladiator();

        List<Enemy> enemies = new ArrayList<>();
//        enemies.add(goblin);
//        enemies.add(slime);
       enemies.add(rat);
//        enemies.add(goblinChief);
//        enemies.add(slimeTyrant);
//        enemies.add(ratKing);
//        enemies.add(ironGladiator);


        CombatManager combatManager = new CombatManager();
        combatManager.startBattle(player, enemies);
    }
}
