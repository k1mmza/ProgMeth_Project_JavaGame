package map;

import Entity.Player;
import Entity.enemy.basic.Goblin;
import Entity.enemy.basic.Rat;
import Entity.enemy.basic.Slime;
import Entity.enemy.boss.GoblinChief;
import Entity.enemy.boss.IronGladiator;
import Entity.enemy.boss.RatKing;
import Entity.enemy.boss.SlimeTyrant;
import enums.RoomType;
import Entity.enemy.Enemy;
import gameLogic.CombatManager;
import inventory.potion.Potion;
import map.event.EventManager;
import map.event.GameEvent;
import map.shop.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Room {

    private RoomType type;
    private List<Enemy> enemies;
    private boolean cleared;
    private Shop shop;
    private EventManager eventManager = new EventManager();
    private static final Random random = new Random();

    public Room(RoomType type) {
        this.type = type;
        this.enemies = new ArrayList<>();
        this.cleared = false;

        switch (type) {
            case ENEMY:
                spawnNormalEnemies();
                break;
            case ELITE:
                spawnEliteEnemy();
                break;
            case BOSS:
                spawnBoss();
                break;
            case SHOP:
                this.shop = new Shop();
                break;
        }
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public RoomType getType() {
        return type;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }

    public boolean hasEnemies() {
        return !enemies.isEmpty();
    }

    public void enter(Player player, CombatManager combatManager, Scanner scanner) {

        if (cleared) {
            System.out.println("This room has already been cleared.");
            return;
        }

        System.out.println("Entering room: " + type);

        switch (type) {

            case START:
                System.out.println("You take a deep breath and begin your journey.");
                break;

            case ENEMY:
            case ELITE:
            case BOSS:
                handleCombat(player, combatManager);
                break;

            case SHOP:
                System.out.println("Welcome to the shop!");
                handleShop(player, scanner);
                break;

            case EVENT:
                System.out.println("A mysterious event occurs...");
                handleEvent(player, scanner);
                break;

            case REST:
                handleRest(player, scanner);
                break;
        }

        cleared = true;
    }

    private void handleCombat(Player player, CombatManager combatManager) {

        if (enemies.isEmpty()) {
            System.out.println("No enemies here.");
            return;
        }

        combatManager.startBattle(player, enemies);

        if (player.isAlive()) {
            System.out.println("Room Cleared!");
            enemies.clear(); // prevent re-entry issues
        } else {
            System.out.println("You were defeated...");
        }
    }

    private void handleShop(Player player, Scanner scanner) {
        boolean shopping = true;

        while (shopping) {

            shop.displayStock();
            System.out.println("Your gold: " + player.getGold());
            System.out.println("Choose item to buy (-1 to leave):");

            int choice = scanner.nextInt();

            if (choice == -1) {
                shopping = false;
            } else {
                shop.buyPotion(choice, player);
            }
        }

        System.out.println("You leave the shop.");
    }

    private void handleEvent(Player player, Scanner scanner) {

        GameEvent event = eventManager.getRandomEvent();

        System.out.println("Event: " + event.getName());
        event.execute(player, scanner);
    }

    private void handleRest(Player player, Scanner scanner) {

        boolean resting = true;

        while (resting) {

            System.out.println("\nYou arrive at a peaceful Rest Site.");
            System.out.println("1. Heal 30% of Max HP");
            System.out.println("2. Increase Max HP by 5");
            System.out.println("3. Use a Potion");
            System.out.println("4. Leave");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    int healAmount = (int)(player.getMaxHp() * 0.3);
                    player.heal(healAmount);
                    System.out.println("You recovered " + healAmount + " HP.");
                    resting = false;
                    break;

                case 2:
                    player.setMaxHp(player.getMaxHp() + 5);
                    System.out.println("Your vitality increases!");
                    resting = false;
                    break;

                case 3:
                    usePotionMenu(player, scanner);
                    break;

                case 4:
                    System.out.println("You leave the Rest Site.");
                    resting = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void usePotionMenu(Player player, Scanner scanner) {

        List<Potion> potions = player.getInventory().getPotions();

        if (potions.isEmpty()) {
            System.out.println("You have no potions.");
            return;
        }

        System.out.println("Choose a potion to use:");

        for (int i = 0; i < potions.size(); i++) {
            System.out.println((i + 1) + ". " + potions.get(i).getName());
        }

        int choice = scanner.nextInt();

        if (choice < 1 || choice > potions.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Potion selectedPotion = potions.get(choice - 1);

        selectedPotion.use(player);
        player.getInventory().removePotion(selectedPotion);

        System.out.println(selectedPotion.getName() + " consumed.");
    }

    private void spawnNormalEnemies() {

        Enemy first = getRandomBasicEnemy();
        enemies.add(first);

        // 30% chance to spawn second basic
        if (random.nextDouble() < 0.3) {
            enemies.add(getRandomBasicEnemy());
        }
    }

    private Enemy getRandomBasicEnemy() {

        int roll = random.nextInt(3);

        switch (roll) {
            case 0: return new Goblin();
            case 1: return new Rat();
            default: return new Slime();
        }
    }

    private void spawnEliteEnemy() {

        int roll = random.nextInt(3);

        switch (roll) {
            case 0:
                enemies.add(new GoblinChief());
                enemies.add(new Goblin());
                break;

            case 1:
                enemies.add(new RatKing());
                enemies.add(new Rat());
                break;

            case 2:
                enemies.add(new SlimeTyrant());
                enemies.add(new Slime());
                break;
        }
    }

    private void spawnBoss() {
        enemies.add(new IronGladiator());
    }
}
