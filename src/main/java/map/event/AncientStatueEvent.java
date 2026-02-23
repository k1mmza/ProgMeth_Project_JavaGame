package map.event;

import Entity.Player;

import java.util.Scanner;

public class AncientStatueEvent implements GameEvent {

    @Override
    public void execute(Player player, Scanner scanner) {

        System.out.println("You discover an ancient statue glowing faintly.");
        System.out.println("1. Gain +1 Attack");
        System.out.println("2. Gain +5 Max HP");
        System.out.println("3. Leave");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                player.setAttack(player.getAttack() + 1);
                System.out.println("Your strength increases!");
                break;
            case 2:
                player.setMaxHp(player.getMaxHp() + 5);
                player.heal(5);
                System.out.println("Your vitality increases!");
                break;
            default:
                System.out.println("You leave the statue untouched.");
        }
    }

    @Override
    public String getName() {
        return "Ancient Statue";
    }
}

