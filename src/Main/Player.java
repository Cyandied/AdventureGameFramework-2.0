package Main;

import Util.Item;
import Util.Location;
import Util.SQLResult;
import Util.SQLiteJDBC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Player {
    SQLiteJDBC database;
    public List<Item> inventory = new ArrayList<Item>();

    public Player(SQLiteJDBC database) {
        this.database = database;
    }

    public boolean player_has_item(Item item) {
        return inventory.contains(item);
    }

    public void add_item (Item item) {
        inventory.add(item);
        database.update_db("meta","start_items",inventory.stream().map(i -> i.id).collect(Collectors.joining(":")));
    }

    public void remove_item(Item item) {
        for(Item item_from_inv : inventory) {
            if(item_from_inv.id.equals(item.id)){
                inventory.remove(item_from_inv);
            }
        }
        database.update_db("meta","start_items",inventory.stream().map(i -> i.id).collect(Collectors.joining(":")));
    }

    public Item get_item(String item_id) {
        for(Item item : inventory) {
            if(item.id.equals(item_id)){
                return item;
            }
        }
        return null;
    }
    public void remove_item(Item[] items) {
        for(Item item : items) {
            inventory.remove(item);
        }
        database.update_db("meta","start_items",inventory.stream().map(i -> i.id).collect(Collectors.joining(":")));
    }

}
