package map.event;

import Entity.Player;
import java.util.Scanner;

public class RiskGoldEvent implements GameEvent {

    @Override
    public void execute(Player player, Scanner scanner) {
        // console version
    }

    @Override
    public void applyChoice(Player player, int choice) {

        if (choice == 0) {
            player.takeDamage(8);
            player.addGold(25);
        }
    }

    @Override
    public String getName() {
        return "Risky Deal";
    }

    @Override
    public String getDescription() {
        return "A shady merchant offers you gold... for a price.";
    }

    @Override
    public String getImagePath() {
        return "/events/merchant.png";
    }

    @Override
    public String[] getOptions() {
        return new String[]{
                "Gain 25 Gold (Lose 8 HP)",
                "Refuse"
        };
    }
}