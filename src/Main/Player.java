package Main;

import Util.Item;
import Util.SQLiteJDBC;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    SQLiteJDBC database;
    public List<Item> inventory = new ArrayList<Item>();

    public Player(SQLiteJDBC database) {
        this.database = database;
    }

    public void add_item (Item item) {
        inventory.add(item);
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
    public void remove_item(Item item) {
        List<String> temp = inventory.stream().map(i -> i.id).toList();
        int i = temp.indexOf(item.id);
        inventory.remove(i);
        database.update_db("meta","start_items",inventory.stream().map(i_2 -> i_2.id).collect(Collectors.joining(":")));
    }
    public void remove_item(Item[] items) {
        List<String> temp = inventory.stream().map(i -> i.id).toList();
        for(Item item : items) {
            int i = temp.indexOf(item.id);
            inventory.remove(i);
        }
        database.update_db("meta","start_items",inventory.stream().map(i -> i.id).collect(Collectors.joining(":")));
    }

    public Item[] give_list_inv(){
        return inventory.toArray(new Item[inventory.size()]);
    }

}
