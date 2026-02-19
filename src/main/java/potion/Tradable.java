package potion;

import Entity.Player;

public interface Tradable {
    boolean buy(Player character);
    boolean sell(Player character);
}
