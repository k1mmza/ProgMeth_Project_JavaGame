package map.event;

import Entity.Player;
import java.util.Scanner;

public interface GameEvent {

    void execute(Player player, Scanner scanner);

    String getName();
}
