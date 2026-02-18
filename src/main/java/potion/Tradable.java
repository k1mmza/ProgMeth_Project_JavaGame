package potion;

import character.Character;

public interface Tradable {
    boolean buy(Character character);
    boolean sell(Character character);
}
