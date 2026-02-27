package map.event;

import Entity.Player;
import java.util.Scanner;

public class HealingEvent implements GameEvent {

    @Override
    public void execute(Player player, Scanner scanner) {
        // console version
    }

    @Override
    public void applyChoice(Player player, int choice) {

        if (choice == 0) {
            player.heal(10);
        }
    }

    @Override
    public String getName() {
        return "Healing Fountain";
    }

    @Override
    public String getDescription() {
        return "You find a mysterious healing fountain.";
    }

    @Override
    public String getImagePath() {
        return "/events/fountain.png";
    }

    @Override
    public String[] getOptions() {
        return new String[]{
                "Heal 10 HP",
                "Leave"
        };
    }
}