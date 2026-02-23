package Entity.classes;

import Entity.Entity;
import Entity.Player;

public class Knight extends Player {

    public Knight(String name) {
        super(name, 100, 8, 6);
        setEnergy(0);
    }

    // Shield Strike (Cost: 1)
    @Override
    public boolean skill1(Entity target) {

        int cost = 1;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " uses Shield Strike!");
        int damage = getAttack() + 2;
        target.takeDamage(damage);
        addShield(5);

        return true;
    }

    @Override
    public int getSkill1Cost() {
        return 1;
    }

    // Fortify (Cost: 2)
    @Override
    public boolean skill2(Entity target) {

        int cost = 2;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " uses Fortify!");
        addShield(15);

        return true;
    }

    @Override
    public int getSkill2Cost() {
        return 2;
    }

    // Shield Slam (Cost: 3)
    @Override
    public boolean skill3(Entity target) {

        int cost = 3;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " uses Shield Slam!");
        int damage = getAttack() + getShield();
        target.takeDamage(damage);
        target.applyVulnerable(1);
        resetShield();

        return true;
    }

    @Override
    public int getSkill3Cost() {
        return 3;
    }

    @Override
    public String getSkill1Name() {
        return "Shield Strike";
    }

    @Override
    public String getSkill2Name() {
        return "Fortify";
    }

    @Override
    public String getSkill3Name() {
        return "Shield Slam";
    }
}
