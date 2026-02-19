package potion;

import Entity.Player;

public class DefPotion extends Potion{
    private int increaseDef;

    public DefPotion() {
        super("DefPotion",30,10);
        setIncreaseDef(1);
    }

    @Override
    public boolean use(Player character) {
        character.setDefense(character.getDefense() + getIncreaseDef());
        character.getInventory().removePotion(this);
        return true;
    }

    public int getIncreaseDef() {
        return increaseDef;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public int getSellCost() {
        return sellCost;
    }

    public void setIncreaseDef(int increaseDef) {
        this.increaseDef = increaseDef;
    }
}
