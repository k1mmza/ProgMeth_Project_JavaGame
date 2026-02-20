package Entity.classes;

import Entity.Entity;
import Entity.Player;

public class Rogue extends Player {

    public Rogue(String name) {
        super(name, 19, 5, 2);
        setEnergy(0);
    }

    // Backstab
    @Override
    public void skill1(Entity target) {
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
    public void skill2(Entity target) {
        if (getEnergy() >= 1) {
            addEvade(1);              // Avoid next attack
            target.applyVulnerable(1); // Target takes extra damage
            setEnergy(getEnergy() - 1);
        }
    }

    // Poisoned Blade
    @Override
    public void skill3(Entity target) {
        if (getEnergy() >= 2) {
            target.takeDamage(getAttack());
            target.addPoison(4);  // 4 damage over time
            setEnergy(getEnergy() - 2);
        }
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
}
