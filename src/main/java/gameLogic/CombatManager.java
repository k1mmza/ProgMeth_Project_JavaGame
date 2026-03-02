package gameLogic;

import Entity.Player;
import Entity.enemy.Enemy;
import inventory.potion.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;
import java.util.function.Supplier;

public class CombatManager {

    private final Scanner scanner = new Scanner(System.in);

    public void startBattle(Player player, List<Enemy> enemies) {

        List<Enemy> enemiesSnapshot = new ArrayList<>(enemies);

        System.out.println("=== BATTLE START ===");
        player.resetEnergyToZero();

        while (player.isAlive() && !enemies.isEmpty()) {

            // =========================
            // ===== PLAYER TURN =======
            // =========================

            player.startTurn();
            if (!player.isAlive()) break;

            startPlayerTurn(player);
            displayBattleState(player, enemies);

            playerTurn(player, enemies);

            removeDeadEnemies(enemies);
            if (enemies.isEmpty()) break;

            // =========================
            // ===== ENEMY TURN ========
            // =========================

            System.out.println("\n=== ENEMY TURN ===");

            for (Enemy enemy : enemies) {
                if (enemy.isAlive()) {
                    enemy.startTurn();
                }
            }

            for (Enemy enemy : enemies) {
                if (enemy.isAlive()) {
                    enemy.performAction(player);
                }
            }

            removeDeadEnemies(enemies);

            // =========================
            // ===== END TURN ==========
            // =========================

            player.endTurn();

            for (Enemy enemy : enemies) {
                if (enemy.isAlive()) {
                    enemy.endTurn();
                }
            }
        }

        // =========================
        // ===== BATTLE END ========
        // =========================

        if (player.isAlive()) {
            System.out.println("You Win!");
            grantRewards(player, enemiesSnapshot);
            player.resetCombatState(); // <-- IMPORTANT
        } else {
            System.out.println("You Lose...");
        }
    }

    public void startBattle(Player player, Enemy enemy) {
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);
        startBattle(player, enemies);
    }


    private void displayBattleState(Player player, List<Enemy> enemies) {
        System.out.println("\n------------------");
        System.out.println(player.getName() +
                " HP: " + player.getHp() +
                " | Shield: " + player.getShield() +
                " | Energy: " + player.getEnergy());

        System.out.println("\nEnemies:");
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if(e.getShield() > 0) {
                System.out.println(i + ". " + e.getName() +
                        " HP: " + e.getHp() +
                        " | Shield: " + e.getShield());
            } else {
                System.out.println(i + ". " + e.getName() +
                        " HP: " + e.getHp());
            }
        }
    }

    private void startPlayerTurn(Player player) {
        player.gainEnergy(1);
        System.out.println("\nEnergy increased! Current Energy: " + player.getEnergy());
    }

    private void playerTurn(Player player, List<Enemy> enemies) {
        boolean actionPerformed = false;
        while (!actionPerformed) {

            System.out.println("\n1. Attack");
            System.out.println("2. Block");
            System.out.println("3. Focus (+1 Energy)");
            System.out.println("4. Skill");
            System.out.println("5. Item");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    Enemy target = chooseTarget(enemies);
                    if (target != null) {
                        player.normalAttack(target);
                        actionPerformed = true;
                    }
                    break;

                case 2:
                    player.block();
                    actionPerformed = true;
                    break;

                case 3:
                    int energyBeforeFocus = player.getEnergy();
                    player.focus();
                    if (player.getEnergy() == energyBeforeFocus) {
                        System.out.println("Energy is already full.");
                    } else {
                        actionPerformed = true;
                    }
                    break;

                case 4:
                    actionPerformed = chooseSkill(player, enemies);
                    break;

                case 5:
                    useItem(player);
                    actionPerformed = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private Enemy chooseTarget(List<Enemy> enemies) {
        System.out.println("Choose target:");

        for (int i = 0; i < enemies.size(); i++) {
            System.out.println(i + ". " + enemies.get(i).getName());
        }

        int index = scanner.nextInt();

        if (index >= 0 && index < enemies.size()) {
            return enemies.get(index);
        }

        System.out.println("Invalid target.");
        return null;
    }

    private void enemiesTurn(Player player, List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                enemy.performAction(player);
            }
        }
    }

    private void removeDeadEnemies(List<Enemy> enemies) {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (!enemy.isAlive()) {
                System.out.println(enemy.getName() + " is defeated!");
                iterator.remove();
            }
        }
    }

    private void useItem(Player player) {

        if (player.getInventory().getPotions().isEmpty()) {
            System.out.println("No items!");
            return;
        }

        for (int i = 0; i < player.getInventory().getPotions().size(); i++) {
            System.out.println(i + ". "
                    + player.getInventory().getPotions().get(i).getName());
        }

        int index = scanner.nextInt();

        if (index >= 0 &&
                index < player.getInventory().getPotions().size()) {

            var potion = player.getInventory().getPotions().get(index);
            potion.use(player);
            player.getInventory().removePotion(potion);
        }
    }

    private boolean chooseSkill(Player player, List<Enemy> enemies) {

        System.out.println("\nChoose Skill:");
        System.out.println("1. Skill 1 " + player.getSkill1Name() + "(Cost: " + player.getSkill1Cost() + " Energy)");
        System.out.println("2. Skill 2 " + player.getSkill2Name() + "(Cost: " + player.getSkill2Cost() + " Energy)");
        System.out.println("3. Skill 3 " + player.getSkill3Name() + "(Cost: " + player.getSkill3Cost() + " Energy)");

        int skillChoice = scanner.nextInt();

        Enemy target = chooseTarget(enemies);
        if (target == null) return false;

        switch (skillChoice) {
            case 1:
                return player.skill1(target);
            case 2:
                return player.skill2(target);
            case 3:
                return player.skill3(target);
            default:
                System.out.println("Invalid skill choice.");
                return false;
        }
    }


    private void grantRewards(Player player, List<Enemy> defeatedEnemies) {
        int totalGold = 0;
        for (Enemy enemy : defeatedEnemies) {
            totalGold += enemy.getGoldReward();
        }

        player.addGold(totalGold);
        System.out.println("You gained " + totalGold + " gold!");

        // 30% chance to drop potion
        if (Math.random() < 0.3) {
            Potion potion = getRandomPotion();
            player.getInventory().addPotion(potion);
            System.out.println("You found a " + potion.getName() + "!");
        }

    }

    private final List<Supplier<Potion>> potionPool = List.of(
            HealingPotion::new,
            EnergyPotion::new,
            AtkPotion::new,
            DefPotion::new
    );

    private Potion getRandomPotion() {
        int index = (int)(Math.random() * potionPool.size());
        return potionPool.get(index).get();
    }

}
