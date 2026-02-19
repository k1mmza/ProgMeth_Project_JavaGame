package shop;

import Entity.Entity;
import potion.*;
import Entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private List<Potion> stock;

    public Shop() {
        stock = new ArrayList<>();

        // Fixed stock
        stock.add(new HealingPotion());
        stock.add(new AtkPotion());
        stock.add(new DefPotion());
        stock.add(new EnergyPotion());
    }

    public void displayStock() {
        System.out.println("=== Shop ===");
        for (int i = 0; i < stock.size(); i++) {
            Potion potion = stock.get(i);
            System.out.println(i + ". " + potion.getName()
                    + " | Buy: " + potion.getBuyCost()
                    + " | Sell: " + potion.getSellCost());
        }
    }

    public boolean buyPotion(int index, Player character) {
        if (index < 0 || index >= stock.size()) {
            return false;
        }

        Potion potion = stock.get(index);

        if (potion.buy(character)) {
            System.out.println("You bought " + potion.getName());
            return true;
        }

        System.out.println("Not enough gold!");
        return false;
    }
}
