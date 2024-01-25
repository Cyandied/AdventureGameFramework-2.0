package Util;

import Main.Player;

public class Happening {

    public String id;
    public String flavour;
    public Item[] give_items;
    public String[] change_specials;
    public String[] change_maps;
    public Item[] parent_items;
    public boolean destroy_parents;
    public boolean repeatable;
    public String[] accepted_tags;

    public Happening(SQLiteJDBC database, String id) {
        SQLResult happening = database.get_row_from_db("happening",id);
        this.id = happening.get_string("id");
        flavour = happening.get_string("flavour");
        give_items = init_items(happening.get_string("give_items"),database);
        change_specials = happening.get_string("change_specials").split(":");
        change_maps = happening.get_string("change_maps").split(":");
        parent_items = init_items(happening.get_string("parent_items"),database);
        destroy_parents = happening.get_bool("destroy_parents");
        repeatable = happening.get_bool("repeatable");
        accepted_tags = happening.get_string("accepted_tags").split(":");
    }

    private Item[] init_items(String item_ids, SQLiteJDBC database) {
        if (item_ids == null) {
            return null;
        }
        String[] item_ids_arr = item_ids.split(":");
        Item[] items = new Item[item_ids_arr.length];
        for (int i = 0; i < item_ids_arr.length; i++) {
            items[i] = new Item(database.get_row_from_db("items",item_ids_arr[i]));
        }
        return items;
    }

    public String activate_happening(Player p, SQLiteJDBC database) {
        return "";
    }

}
