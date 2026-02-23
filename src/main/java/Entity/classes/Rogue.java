package Entity.classes;

import Entity.Entity;
import Entity.Player;

public class Rogue extends Player {

    public Rogue(String name) {
        super(name, 80, 10, 6);
        setEnergy(0);
    }

    // Backstab (Cost: 1)
    @Override
    public boolean skill1(Entity target) {

        int cost = 1;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " uses Backstab!");

        int damage = getAttack() + 5;

        if (target.isVulnerable()) {
            damage = (int) (damage * 1.5);
        }

        target.takeDamage(damage);
        return true;
    }

    // Smoke Bomb (Cost: 1)
    @Override
    public boolean skill2(Entity target) {

        int cost = 1;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " uses Smoke Bomb!");

        addEvade(1);                // Avoid next attack
        target.applyVulnerable(1);  // Target takes extra damage

        return true;
    }

    // Poisoned Blade (Cost: 2)
    @Override
    public boolean skill3(Entity target) {

        int cost = 2;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " uses Poisoned Blade!");

        target.takeDamage(getAttack());
        target.addPoison(4);

        return true;
    }

    @Override
    public String getSkill1Name() {
        return "Backstab";
    }

    @Override
    public String getSkill2Name() {
        return "Smoke Bomb";
    }

    @Override
    public String getSkill3Name() {
        return "Poisoned Blade";
    }

    @Override
    public int getSkill1Cost() {
        return 1;
    }

    @Override
    public int getSkill2Cost() {
        return 1;
    }

    @Override
    public int getSkill3Cost() {
        return 2;
    }
}
