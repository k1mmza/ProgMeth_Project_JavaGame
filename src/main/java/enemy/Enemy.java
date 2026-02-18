package enemy;

import character.Character;

public abstract class Enemy extends Character {

    public Enemy(String name) {
        super(name);
    }

    public abstract void performAction(Character target);
}
