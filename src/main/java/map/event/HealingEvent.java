package map.event;

import Entity.Player;
import map.event.GameEvent;

import java.util.Scanner;

public class HealingEvent implements GameEvent {

    @Override
    public void execute(Player player, Scanner scanner) {

        System.out.println("You find a healing fountain.");
        System.out.println("1. Heal 10 HP");
        System.out.println("2. Leave");

        int choice = scanner.nextInt();

        if (choice == 1) {
            player.heal(10);
            System.out.println("You feel refreshed!");
        } else {
            System.out.println("You leave it alone.");
        }
    }

    @Override
    public String getName() {
        return "Healing Fountain";
    }
}
