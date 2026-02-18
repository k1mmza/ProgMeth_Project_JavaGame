package character.classes;

import character.Player;
import character.Character;

public class Rogue extends Player {

    public Rogue(String name) {
        super(name);
        setMaxHp(19);
        setHp(19);
        setAttack(5);
        setDefense(2);
        setEnergy(0);
    }

    // Backstab
    @Override
    public void skill1(Character target) {
        if (getEnergy() >= 1) {
            int damage = getAttack() + 5;

            if (target.isVulnerable()) {
                damage = (int)(damage * 1.5);
            }

            target.takeDamage(damage);
            setEnergy(getEnergy() - 1);
        }
    }

    // Smoke Bomb (Evade + Apply Vulnerable)
    @Override
    public void skill2(Character target) {
        if (getEnergy() >= 1) {
            addEvade(1);              // Avoid next attack
            target.applyVulnerable(1); // Target takes extra damage
            setEnergy(getEnergy() - 1);
        }
    }

    // Poisoned Blade
    @Override
    public void skill3(Character target) {
        if (getEnergy() >= 2) {
            target.takeDamage(getAttack());
            target.addPoison(4);  // 4 damage over time
            setEnergy(getEnergy() - 2);
        }
    }
}
