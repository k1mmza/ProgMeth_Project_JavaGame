package character.classes;

import character.Player;
import character.Character;

public class Knight extends Player {

    public Knight(String name) {
        super(name);
        setMaxHp(24);
        setHp(24);
        setAttack(4);
        setDefense(3);
        setEnergy(0);
    }

    // Shield Strike
    @Override
    public void skill1(Character target) {
        if (getEnergy() >= 1) {
            int damage = getAttack() + 2;
            target.takeDamage(damage);
            addShield(2);
            setEnergy(getEnergy() - 1);
        }
    }

    // Fortify
    @Override
    public void skill2(Character target) {
        if (getEnergy() >= 1) {
            addShield(7);
            setEnergy(getEnergy() - 1);
        }
    }

    // Shield Slam
    @Override
    public void skill3(Character target) {
        if (getEnergy() >= 2) {
            int damage = getAttack() + getShield();
            target.takeDamage(damage);
            resetShield();
            setEnergy(getEnergy() - 2);
        }
    }
}
