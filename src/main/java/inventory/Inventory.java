package inventory;

import potion.Potion;
import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private int maxPotion;
    private List<Potion> potions;

    public Inventory(int maxPotion) {
        this.maxPotion = maxPotion;
        this.potions = new ArrayList<>();
    }

    public List<Potion> getPotions() {
        return potions;
    }

    public boolean addPotion(Potion potion) {
        if (potions.size() >= maxPotion) {
            return false;
        }
        return potions.add(potion);
    }

    public boolean removePotion(Potion potion) {
        return potions.remove(potion);
    }

    public boolean isFull() {
        return potions.size() >= maxPotion;
    }

    public int getSize() {
        return potions.size();
    }
}
