package potion;

import character.Character;

public class DefPotion extends Potion{
    private int increaseDef;
    private int buyCost;
    private int sellCost;

    public DefPotion(int buyCost) {
        super("DefPotion");
        setBuyCost(buyCost);
        setSellCost(10);
        setIncreaseDef(5);
    }

    @Override
    public boolean buy(character.Character character) {
        if(character.getGold() >= getBuyCost()) {
            character.setGold(character.getGold() - getBuyCost());
            character.getInventory().addPotion(this);
            return true;
        } return false;
    }

    @Override
    public boolean sell(character.Character character) {
        character.getInventory().removePotion(this);
        character.setGold(character.getGold() + getSellCost());
        return true;
    }

    @Override
    public boolean use(Character character) {
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

    public void setBuyCost(int buyCost) {
        this.buyCost = buyCost;
    }

    public void setSellCost(int sellCost) {
        this.sellCost = sellCost;
    }
}
