package Entity.enemy.boss;

import Entity.enemy.basic.Slime;
import Entity.Entity;

public class SlimeTyrant extends Slime {

    private boolean overloaded = false;

    public SlimeTyrant() {
        super();

        setName("Slime Tyrant");
        setMaxHp(80);
        setHp(80);
        setAttack(4);
        setDefense(8);
        setGoldReward(30);
    }

    @Override
    public void performAction(Entity target) {

        // Phase 2: Overload mode
        if (!overloaded && getHp() <= getMaxHp() / 2) {
            overloaded = true;
            setAttack(getAttack() + 4);
            System.out.println(getName() + " becomes unstable and OVERLOADS!");
        }

        // Gain massive shield
        int shieldAmount = overloaded ? 10 : 6;
        System.out.println(getName() + " hardens massively and gains " + shieldAmount + " shield!");
        addShield(shieldAmount);

        // Regeneration mechanic
        int regen = overloaded ? 8 : 5;
        System.out.println(getName() + " regenerates " + regen + " HP!");
        setHp(Math.min(getHp() + regen, getMaxHp()));

        // Attack
        normalAttack(target);
        target.addPoison(2);
        System.out.println(getName() + " poisons the target for 2 turns!");
    }
}
