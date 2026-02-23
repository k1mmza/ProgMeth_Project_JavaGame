package Entity.enemy.boss;

import Entity.enemy.basic.Goblin;
import Entity.Entity;
import java.util.Random;

public class GoblinChief extends Goblin {

    private boolean enraged = false;
    private Random random = new Random();

    public GoblinChief() {
        super();

        // Override base goblin stats with boss stats
        setName("Goblin Chief");
        setMaxHp(60);
        setHp(60);
        setAttack(12);
        setDefense(5);
        setGoldReward(25);
    }

    @Override
    public void performAction(Entity target) {

        // Phase 2 mechanic
        if (!enraged && getHp() <= getMaxHp() / 2) {
            enraged = true;
            setAttack(getAttack() + 4);
            System.out.println(getName() + " becomes ENRAGED! Its power surges!");
        }

        // 20% chance to command attack (boss-only move)
        int roll = random.nextInt(100);

        if (roll < 20) {
            commandStrike(target);
        } else {
            super.performAction(target); // use normal goblin behavior
        }
    }

    private void commandStrike(Entity target) {
        System.out.println(getName() + " commands a brutal strike!");
        target.takeDamage(getAttack() + 5);
    }
}
