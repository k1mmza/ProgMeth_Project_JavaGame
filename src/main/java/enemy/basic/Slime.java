package enemy.basic;

import Entity.Entity;
import enemy.Enemy;

public class Slime extends Enemy {

    public Slime() {
        super("Slime", 15, 2, 1, 3);
    }

    @Override
    public void performAction(Entity target) {
        System.out.println(getName() + " hardens and gains 3 shield!");
        addShield(3);

        normalAttack(target);
    }
}
