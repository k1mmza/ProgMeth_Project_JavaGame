package potion;

import Entity.Player;

public class EnergyPotion extends Potion{
    public EnergyPotion() {
        super("EnergyPotion",15,10);
    }

    @Override
    public boolean use(Player character) {
        character.setEnergy(character.getEnergy() + 1);
        character.getInventory().removePotion(this);
        return true;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public int getSellCost() {
        return sellCost;
    }
}
