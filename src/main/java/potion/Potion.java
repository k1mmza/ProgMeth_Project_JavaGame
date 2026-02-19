package potion;

import Entity.Player;

public abstract class Potion implements Tradable, Usable {
    protected String name;
    protected int buyCost;
    protected int sellCost;

    public Potion(String name, int buyCost, int sellCost) {
        this.name = name;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
    }

    @Override
    public boolean buy(Player character) {
        if (character.getGold() >= buyCost) {
            character.setGold(character.getGold() - buyCost);
            character.getInventory().addPotion(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean sell(Player character) {
        character.setGold(character.getGold() + sellCost);
        character.getInventory().removePotion(this);
        return true;
    }

    @Override
    public abstract boolean use(Player character);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellCost() {
        return sellCost;
    }

    public void setSellCost(int sellCost) {
        this.sellCost = sellCost;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public void setBuyCost(int buyCost) {
        this.buyCost = buyCost;
    }


}
