package combat;

import Entity.Player;
import enemy.Enemy;

import java.util.Scanner;

public class CombatManager {

    private Scanner scanner = new Scanner(System.in);

    public void startBattle(Player player, Enemy enemy) {

        System.out.println("=== BATTLE START ===");

        while (player.isAlive() && enemy.isAlive()) {

            System.out.println("\n------------------");
            System.out.println(player.getName() +
                    " HP: " + player.getHp() +
                    " | Shield: " + player.getShield() +
                    " | Energy: " + player.getEnergy());

            System.out.println(enemy.getName() +
                    " HP: " + enemy.getHp());

            playerTurn(player, enemy);

            if (!enemy.isAlive()) break;

            enemyTurn(player, enemy);

            player.endTurn();
            enemy.endTurn();
        }

        if (player.isAlive()) {
            System.out.println("You Win!");
        } else {
            System.out.println("You Lose...");
        }
    }

    private void playerTurn(Player player, Enemy enemy) {
        player.addEnergy(1);
        System.out.println("\n1. Attack");
        System.out.println("2. Block");
        System.out.println("3. Focus (+1 Energy)");
        System.out.println("4. Skill");
        System.out.println("5. Item");

        int choice = scanner.nextInt();

        switch (choice) {

            case 1:
                player.normalAttack(enemy);
                break;

            case 2:
                player.block();
                break;

            case 3:
                player.focus();
                break;

            case 4:
                chooseSkill(player, enemy);
                break;

            case 5:
                useItem(player);
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    private void enemyTurn(Player player, Enemy enemy) {
        System.out.println("\nEnemy Turn!");
        enemy.performAction(player);
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

    private void chooseSkill(Player player, Enemy enemy) {

        System.out.println("\nChoose Skill:");
        System.out.println("1. Skill 1 " + player.getSkill1Name());
        System.out.println("2. Skill 2 " + player.getSkill2Name());
        System.out.println("3. Skill 3 " + player.getSkill3Name());

        int skillChoice = scanner.nextInt();

        switch (skillChoice) {

            case 1:
                player.skill1(enemy);
                break;

            case 2:
                player.skill2(enemy);
                break;

            case 3:
                player.skill3(enemy);
                break;

            default:
                System.out.println("Invalid skill choice.");
        }
    }

}
