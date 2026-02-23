package map.event;

import Entity.Player;
import inventory.potion.*;
import map.event.GameEvent;
import java.util.Random;
import java.util.Scanner;

public class FreePotionEvent implements GameEvent {

    private Random random = new Random();

    @Override
    public void execute(Player player, Scanner scanner) {

        System.out.println("You find a mysterious potion.");

        Potion potion;

        int roll = random.nextInt(4);

        switch (roll) {
            case 0: potion = new HealingPotion(); break;
            case 1: potion = new AtkPotion(); break;
            case 2: potion = new DefPotion(); break;
            default: potion = new EnergyPotion();
        }

        player.getInventory().addPotion(potion);

        System.out.println("You obtained: " + potion.getName());
    }

    @Override
    public String getName() {
        return "Mysterious Potion";
    }
}
