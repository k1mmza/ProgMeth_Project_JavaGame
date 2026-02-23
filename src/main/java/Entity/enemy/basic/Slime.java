package Entity.enemy.basic;

import Entity.Entity;
import Entity.enemy.Enemy;

public class Slime extends Enemy {

    public Slime() {
        super("Slime", 30, 3, 6, 10);
    }

    @Override
    public void performAction(Entity target) {
        System.out.println(getName() + " hardens and gains 1 shield!");
        addShield(1);

        normalAttack(target);
        System.out.println(getName() + " poisons the target for 1 turns!");
        target.addPoison(1);
    }
}
