package Entity.classes;

import Entity.Entity;
import Entity.Player;

public class Mage extends Player {

    public Mage(String name) {
        super(name, 17, 6, 1);
        setEnergy(1);
    }

    // Firebolt
    @Override
    public void skill1(Entity target) {
        if (getEnergy() >= 1) {
            int damage = getAttack() + 4;
            target.takeDamage(damage);
            setEnergy(getEnergy() - 1);
        }
    }

    // Mana Barrier
    @Override
    public void skill2(Entity target) {
        if (getEnergy() >= 1) {
            addShield(5);
            setEnergy(getEnergy() - 1);
        }
    }

    // Arcane Nova
    @Override
    public void skill3(Entity target) {
        if (getEnergy() >= 3) {
            int damage = getAttack() * 2 + 6;
            target.takeDamage(damage);
            setEnergy(getEnergy() - 3);
        }
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

}
