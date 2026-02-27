package map;

import Entity.Player;
import Entity.enemy.Enemy;
import Entity.enemy.basic.*;
import Entity.enemy.boss.*;
import enums.RoomType;
import gameLogic.CombatManager;
import inventory.potion.Potion;
import map.shop.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {

    private RoomType type;
    private List<Enemy> enemies;
    private boolean cleared;
    private Shop shop;
    private static final Random random = new Random();

    public Room(RoomType type) {
        this.type = type;
        this.enemies = new ArrayList<>();
        this.cleared = false;

        switch (type) {
            case ENEMY -> spawnNormalEnemies();
            case ELITE -> spawnEliteEnemy();
            case BOSS -> spawnBoss();
            case SHOP -> this.shop = new Shop();
        }
    }

    // ========================
    // Getter
    // ========================

    public RoomType getType() { return type; }
    public boolean isCleared() { return cleared; }
    public void setCleared(boolean cleared) { this.cleared = cleared; }
    public List<Enemy> getEnemies() { return enemies; }
    public Shop getShop() { return shop; }

    // ========================
    // ENTER ROOM
    // ========================

    public void enter(Player player, CombatManager combatManager) {

        if (cleared) return;

        switch (type) {
            case ENEMY, ELITE, BOSS -> handleCombat(player, combatManager);
            case SHOP, EVENT, REST -> {
                // GUI handles these
            }
        }
    }

    private void handleCombat(Player player, CombatManager combatManager) {

        if (enemies.isEmpty()) return;

        combatManager.startBattle(player, enemies);

        if (player.isAlive()) {
            enemies.clear();
            cleared = true;
        }
    }

    // ========================
    // REST LOGIC (GUI CALLS)
    // ========================

    public void restHeal(Player player) {
        int healAmount = (int)(player.getMaxHp() * 0.3);
        player.heal(healAmount);
    }

    public void restIncreaseMaxHp(Player player) {
        player.setMaxHp(player.getMaxHp() + 5);
    }

    public void usePotion(Player player, Potion potion) {
        potion.use(player);
        player.getInventory().removePotion(potion);
    }

    // ========================
    // SPAWN LOGIC
    // ========================

    private void spawnNormalEnemies() {
        enemies.add(getRandomBasicEnemy());
        if (random.nextDouble() < 0.3) {
            enemies.add(getRandomBasicEnemy());
        }
    }

    private Enemy getRandomBasicEnemy() {
        return switch (random.nextInt(3)) {
            case 0 -> new Goblin();
            case 1 -> new Rat();
            default -> new Slime();
        };
    }

    private void spawnEliteEnemy() {
        switch (random.nextInt(3)) {
            case 0 -> {
                enemies.add(new GoblinChief());
                enemies.add(new Goblin());
            }
            case 1 -> {
                enemies.add(new RatKing());
                enemies.add(new Rat());
            }
            case 2 -> {
                enemies.add(new SlimeTyrant());
                enemies.add(new Slime());
            }
        }
    }

    private void spawnBoss() {
        enemies.add(new IronGladiator());
    }
}