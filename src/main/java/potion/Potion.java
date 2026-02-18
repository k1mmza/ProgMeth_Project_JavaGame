package potion;

import character.Character;

public abstract class Potion implements Tradable, Useable{
    protected String name;

    public Potion(String name) {
        this.name = name;
    }

    @Override
    public abstract boolean buy(Character character);

    @Override
    public abstract boolean sell(Character character);

    @Override
    public abstract boolean use(Character character);
}
