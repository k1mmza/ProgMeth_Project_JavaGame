package character.classes;

import character.Player;
import character.Character;

public class Mage extends Player {

    public Mage(String name) {
        super(name);
        setMaxHp(17);
        setHp(17);
        setAttack(6);
        setDefense(1);
        setEnergy(1);
    }

    // Firebolt
    @Override
    public void skill1(Character target) {
        if (getEnergy() >= 1) {
            int damage = getAttack() + 4;
            target.takeDamage(damage);
            setEnergy(getEnergy() - 1);
        }
    }

    // Mana Barrier
    @Override
    public void skill2(Character target) {
        if (getEnergy() >= 1) {
            addShield(5);
            setEnergy(getEnergy() - 1);
        }
    }

    // Arcane Nova
    @Override
    public void skill3(Character target) {
        if (getEnergy() >= 3) {
            int damage = getAttack() * 2 + 6;
            target.takeDamage(damage);
            setEnergy(getEnergy() - 3);
        }
    }
}
