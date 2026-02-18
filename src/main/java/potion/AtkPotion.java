package potion;

import character.Character;

public class AtkPotion extends Potion{
    private int increaseAtk;
    private int buyCost;
    private int sellCost;

    public AtkPotion(int buyCost) {
        super("AtkPotion");
        setBuyCost(buyCost);
        setSellCost(10);
        setIncreaseAtk(5);
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
        character.setAttack(character.getAttack() + getIncreaseAtk());
        character.getInventory().removePotion(this);
        return true;
    }

    public int getIncreaseAtk() {
        return increaseAtk;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public int getSellCost() {
        return sellCost;
    }

    public void setIncreaseAtk(int increaseAtk) {
        this.increaseAtk = increaseAtk;
    }

    public void setBuyCost(int buyCost) {
        this.buyCost = buyCost;
    }

    public void setSellCost(int sellCost) {
        this.sellCost = sellCost;
    }
}
