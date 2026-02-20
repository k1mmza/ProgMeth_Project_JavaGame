package potion;

import Entity.Player;


public class AtkPotion extends Potion{
    private int increaseAtk;

    public AtkPotion() {
        super("AtkPotion",30,10);
        setIncreaseAtk(1);
    }

    @Override
    public boolean use(Player character) {
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
}
