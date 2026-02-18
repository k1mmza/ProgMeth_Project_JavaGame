package character;

public abstract class Player extends Character {

    public Player(String name) {
        super(name);
    }

    public abstract void skill1(Character target);
    public abstract void skill2(Character target);
    public abstract void skill3(Character target);
}
