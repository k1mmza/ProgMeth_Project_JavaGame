package map.event;

import Entity.Player;

import java.util.Scanner;

public class RiskGoldEvent implements GameEvent {

    @Override
    public void execute(Player player, Scanner scanner) {

        System.out.println("A shady merchant offers risky gold.");
        System.out.println("1. Gain 25 gold but lose 8 HP");
        System.out.println("2. Refuse");

        int choice = scanner.nextInt();

        if (choice == 1) {
            player.takeDamage(8);
            player.addGold(25);
            System.out.println("You took the risk!");
        } else {
            System.out.println("You walk away.");
        }
    }

    @Override
    public String getName() {
        return "Risky Deal";
    }
}

