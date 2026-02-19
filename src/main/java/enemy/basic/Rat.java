package enemy.basic;

import Entity.Entity;
import enemy.Enemy;
import java.util.Random;

public class Rat extends Enemy {

    private Random random = new Random();

    public Rat() {
        super("Rat", 8, 3, 1, 2);
    }

    @Override
    public void performAction(Entity target) {
        System.out.println(getName() + " bites!");

        normalAttack(target);

        // 20% chance to bite again
        if (random.nextInt(100) < 20) {
            System.out.println(getName() + " bites again!");
            normalAttack(target);
        }
    }
}
