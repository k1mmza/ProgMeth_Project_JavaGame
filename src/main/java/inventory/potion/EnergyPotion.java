package inventory.potion;

import Entity.Player;

public class EnergyPotion extends Potion{
    public EnergyPotion() {
        super("EnergyPotion",15,10);
        this.description = "Restore 2 Energy.";
    }

    @Override
    public boolean use(Player character) {
        character.gainEnergy(2);
        System.out.println("You restore 2 Energy.");
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
