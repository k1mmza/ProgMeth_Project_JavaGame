package potion;

import character.Character;

public class HealingPotion extends Potion{
    private int increaseHp;
    private int buyCost;
    private int sellCost;

    public HealingPotion(int buyCost) {
        super("HealingPotion");
        setBuyCost(buyCost);
        setSellCost(10);
        setIncreaseHp(15);
    }

    @Override
    public boolean buy(Character character) {
        if(character.getGold() >= getBuyCost()) {
            character.setGold(character.getGold() - getBuyCost());
            character.getInventory().addPotion(this);
            return true;
        } return false;
    }

    @Override
    public boolean sell(Character character) {
        character.getInventory().removePotion(this);
        character.setGold(character.getGold() + getSellCost());
        return true;
    }

    @Override
    public boolean use(Character character) {
        character.setHp(character.getHp() + getIncreaseHp());
        character.getInventory().removePotion(this);
        return true;
    }

    public int getIncreaseHp() {
        return increaseHp;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public int getSellCost() {
        return sellCost;
    }

    public void setIncreaseHp(int increaseHp) {
        this.increaseHp = increaseHp;
    }

    public void setBuyCost(int buyCost) {
        this.buyCost = buyCost;
    }

    public void setSellCost(int sellCost) {
        this.sellCost = sellCost;
    }
}
