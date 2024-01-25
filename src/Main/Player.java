package Main;

import Util.Item;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public List<Item> inventory = new ArrayList<Item>();

    public Player() {

    }

    public boolean player_has_item(Item item) {
        return inventory.contains(item);
    }

    public void add_item (Item item) {
        inventory.add(item);
    }

    public void remove_item(Item item) {
        inventory.remove(item);
    }

}
