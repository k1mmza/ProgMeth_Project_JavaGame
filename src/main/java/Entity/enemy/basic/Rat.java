package Entity.enemy.basic;

import Entity.Entity;
import Entity.enemy.Enemy;
import java.util.Random;

public class Rat extends Enemy {

    private Random random = new Random();

    public Rat() {
        super("Rat", 20, 6, 3, 5);
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
