package enemy.basic;

import character.Character;
import enemy.Enemy;

public class Slime extends Enemy {

    public Slime() {
        super("Slime");
        setMaxHp(15);
        setHp(15);
        setAttack(2);
        setDefense(1);
    }

    @Override
    public void performAction(Character target) {
        System.out.println(getName() + " hardens and gains 3 shield!");
        addShield(3);

        normalAttack(target);
    }
}
