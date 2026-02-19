package Entity.classes;

import Entity.Entity;
import Entity.Player;

public class Knight extends Player {

    public Knight(String name) {
        super(name, 24, 4, 3);
        setEnergy(0);
    }

    // Shield Strike
    @Override
    public void skill1(Entity target) {
        if (getEnergy() >= 1) {
            int damage = getAttack() + 2;
            target.takeDamage(damage);
            addShield(2);
            setEnergy(getEnergy() - 1);
        }
    }

    // Fortify
    @Override
    public void skill2(Entity target) {
        if (getEnergy() >= 1) {
            addShield(7);
            setEnergy(getEnergy() - 1);
        }
    }

    // Shield Slam
    @Override
    public void skill3(Entity target) {
        if (getEnergy() >= 2) {
            int damage = getAttack() + getShield();
            target.takeDamage(damage);
            resetShield();
            setEnergy(getEnergy() - 2);
        }
    }
}
