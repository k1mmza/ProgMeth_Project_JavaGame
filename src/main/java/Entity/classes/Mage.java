package Entity.classes;

import Entity.Entity;
import Entity.Player;

public class Mage extends Player {

    public Mage(String name) {
        super(name, 70, 13, 4);
        setEnergy(0); // match your new energy system (start at 0)
    }

    // Firebolt (Cost: 2)
    @Override
    public boolean skill1(Entity target) {

        int cost = 2;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " casts Firebolt!");
        int damage = getAttack() + 6;
        target.takeDamage(damage);

        return true;
    }

    // Mana Barrier (Cost: 2)
    @Override
    public boolean skill2(Entity target) {

        int cost = 2;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " casts Mana Barrier!");
        addShield(15);

        return true;
    }

    // Arcane Nova (Cost: 3)
    @Override
    public boolean skill3(Entity target) {

        int cost = 3;

        if (!useEnergy(cost)) {
            System.out.println("Not enough energy!");
            return false;
        }

        System.out.println(getName() + " casts Arcane Nova!");
        int damage = getAttack() * 2 + 6;
        target.takeDamage(damage);
        return true;
    }

    @Override
    public String getSkill1Name() {
        return "Firebolt";
    }

    @Override
    public String getSkill2Name() {
        return "Mana Barrier";
    }

    @Override
    public String getSkill3Name() {
        return "Arcane Nova";
    }

    @Override
    public int getSkill1Cost() {
        return 2;
    }

    @Override
    public int getSkill2Cost() {
        return 2;
    }

    @Override
    public int getSkill3Cost() {
        return 3;
    }
}
