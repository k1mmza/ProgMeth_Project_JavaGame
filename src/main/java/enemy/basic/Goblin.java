package enemy.basic;

import character.Character;
import enemy.Enemy;
import java.util.Random;

public class Goblin extends Enemy {

    private boolean isCharging = false;
    private Random random = new Random();

    public Goblin() {
        super("Goblin");
        setMaxHp(13);
        setHp(13);
        setAttack(3);
        setDefense(2);
    }

    @Override
    public void performAction(Character target) {

        // If charging, unleash heavy attack
        if (isCharging) {
            System.out.println(getName() + " unleashes a HEAVY SLASH!");
            target.takeDamage(getAttack() + 3);
            isCharging = false;
            return;
        }

        int roll = random.nextInt(100); // 0–99

        if (roll < 50) {
            goblinNormalAttack(target);
        } else if (roll < 75) {
            defend();
        } else {
            charge();
        }
    }


    private void goblinNormalAttack(Character target) {
        System.out.println(getName() + " swings wildly!");
        target.takeDamage(getAttack());
    }

    private void defend() {
        System.out.println(getName() + " raises its guard!");
        addShield(4);
    }

    private void charge() {
        System.out.println(getName() + " starts charging a heavy attack! It looks vulnerable!");
        isCharging = true;
        applyVulnerable(1);
    }
}
