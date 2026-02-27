package inventory.potion;

import Entity.Player;


public class AtkPotion extends Potion{
    private int increaseAtk;

    public AtkPotion() {
        super("AtkPotion",20,10);
        setIncreaseAtk(1);
        this.description = "Increase attack by 1.";
    }

    @Override
    public boolean use(Player character) {
        character.setAttack(character.getAttack() + getIncreaseAtk());
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
