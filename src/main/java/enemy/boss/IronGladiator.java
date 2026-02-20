package enemy.boss;

import enemy.Enemy;
import Entity.Entity;
import java.util.Random;

public class IronGladiator extends Enemy {

    private boolean phaseTwo = false;
    private Random random = new Random();

    public IronGladiator() {
        super("Iron Gladiator", 100, 14, 12, 120);
    }

    @Override
    public void performAction(Entity target) {

        // Phase transition at 40%
        if (!phaseTwo && getHp() <= getMaxHp() * 0.4) {
            enterPhaseTwo();
        }

        if (!phaseTwo) {
            phaseOneBehavior(target);
        } else {
            phaseTwoBehavior(target);
        }
    }

    private void enterPhaseTwo() {
        phaseTwo = true;

        System.out.println("=================================");
        System.out.println("⚔ The Iron Gladiator's armor SHATTERS!");
        System.out.println("⚔ He roars and enters PHASE TWO!");
        System.out.println("=================================");

        setDefense(5);              // defense drops
        setAttack(getAttack() + 10); // damage spikes
    }

    private void phaseOneBehavior(Entity target) {

        int roll = random.nextInt(100);

        // 25% chance for heavy slam, 15% for fortified guard, 50% normal attack
        if (roll < 25) {
            heavySlam(target);
        } else if (roll < 40) {
            fortifiedGuard();
        } else {
            normalAttack(target);
        }
    }

    private void phaseTwoBehavior(Entity target) {

        System.out.println(getName() + " attacks with relentless fury!");

        normalAttack(target);

        // 25% chance second hit
        if (random.nextInt(100) < 25) {
            System.out.println(getName() + " strikes again instantly!");
            normalAttack(target);
        }
    }

    private void heavySlam(Entity target) {
        System.out.println(getName() + " uses HEAVY SLAM!");
        target.takeDamage(getAttack() + 6);
    }

    private void fortifiedGuard() {
        System.out.println(getName() + " fortifies himself with iron defense!");
        addShield(10);
    }
}
