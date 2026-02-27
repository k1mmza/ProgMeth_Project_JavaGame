package map.event;

import Entity.Player;
import java.util.Scanner;

public class AncientStatueEvent implements GameEvent {

    @Override
    public void execute(Player player, Scanner scanner) {
        // ของเดิม (เผื่อ console)
    }

    @Override
    public void applyChoice(Player player, int choice) {

        switch (choice) {
            case 0:
                player.setAttack(player.getAttack() + 1);
                break;
            case 1:
                player.setMaxHp(player.getMaxHp() + 5);
                player.heal(5);
                break;
        }
    }

    @Override
    public String getName() {
        return "Ancient Statue";
    }

    @Override
    public String getDescription() {
        return "You discover an ancient statue glowing faintly.";
    }

    @Override
    public String getImagePath() {
        return "/events/statue.png";
    }

    @Override
    public String[] getOptions() {
        return new String[]{
                "Gain +1 Attack",
                "Gain +5 Max HP",
                "Leave"
        };
    }
}