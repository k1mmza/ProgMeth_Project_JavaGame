package enemy.boss;

import enemy.basic.Rat;
import Entity.Entity;
import java.util.Random;

public class RatKing extends Rat {

    private Random random = new Random();
    private boolean frenzy = false;

    public RatKing() {
        super();

        setName("Rat King");
        setMaxHp(20);
        setHp(20);
        setAttack(6);
        setDefense(2);
        setGoldReward(20);
    }

    @Override
    public void performAction(Entity target) {

        // Frenzy phase (under 50% HP)
        if (!frenzy && getHp() <= getMaxHp() / 2) {
            frenzy = true;
            setAttack(getAttack() + 3);
            System.out.println(getName() + " enters a BLOOD FRENZY!");
        }

        System.out.println(getName() + " commands the swarm!");

        // Triple attack boss move
        int attacks = frenzy ? 3 : 2;

        for (int i = 0; i < attacks; i++) {
            System.out.println(getName() + " viciously bites!");
            normalAttack(target);
        }

        if (random.nextInt(100) < 20) {
            System.out.println(getName() + " inflicts poison!");
            target.addPoison(2);
        }
    }
}
