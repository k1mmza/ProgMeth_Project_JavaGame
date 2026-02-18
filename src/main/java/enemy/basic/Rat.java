package enemy.basic;

import character.Character;
import enemy.Enemy;
import java.util.Random;

public class Rat extends Enemy {

    private Random random = new Random();

    public Rat() {
        super("Rat");
        setMaxHp(8);
        setHp(8);
        setAttack(3);
        setDefense(1);
    }

    @Override
    public void performAction(Character target) {
        System.out.println(getName() + " bites!");

        normalAttack(target);

        // 20% chance to bite again
        if (random.nextInt(100) < 20) {
            System.out.println(getName() + " bites again!");
            normalAttack(target);
        }
    }
}
