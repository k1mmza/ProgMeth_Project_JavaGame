package map.event;

import Entity.Player;
import java.util.Scanner;

public interface GameEvent {

    void execute(Player player, Scanner scanner); // console

    // 🔥 เพิ่มอันนี้
    void applyChoice(Player player, int choice);

    String getName();

    String getDescription();

    String getImagePath();

    String[] getOptions();
}
