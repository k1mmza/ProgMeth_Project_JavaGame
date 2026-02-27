package map.event;

import Entity.Player;
import inventory.potion.*;
import java.util.Random;
import java.util.Scanner;

public class FreePotionEvent implements GameEvent {

    private Random random = new Random();

    @Override
    public void execute(Player player, Scanner scanner) {
        // console version
    }

    @Override
    public void applyChoice(Player player, int choice) {

        if (choice == 0) {

            Potion potion;
            int roll = random.nextInt(4);

            switch (roll) {
                case 0: potion = new HealingPotion(); break;
                case 1: potion = new AtkPotion(); break;
                case 2: potion = new DefPotion(); break;
                default: potion = new EnergyPotion();
            }

            player.getInventory().addPotion(potion);
        }
    }

    @Override
    public String getName() {
        return "Mysterious Potion";
    }

    @Override
    public String getDescription() {
        return "You find a mysterious glowing potion.";
    }

    @Override
    public String getImagePath() {
        return "/events/potion.png";
    }

    @Override
    public String[] getOptions() {
        return new String[]{
                "Take the potion",
                "Leave it"
        };
    }
}