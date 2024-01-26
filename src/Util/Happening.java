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
    public boolean coded;
    public String code_result;
    public String code_solution;
    public boolean happened;

    public Happening(SQLiteJDBC database, String id) {
        SQLResult happening = database.get_row_from_db("happening",id);
        this.id = happening.get_string("id");
        flavour = happening.get_string("flavour");
        give_items = init_items(happening.get_string("give_items"),database);
        if (happening.get_string("change_specials") != null) {
            change_specials = happening.get_string("change_specials").split(":");
        } else change_specials = new String[0];
        if(happening.get_string("change_maps") != null) {
            change_maps = happening.get_string("change_maps").split(":");
        } else change_maps = new String[0];
        parent_items = init_items(happening.get_string("parent_items"),database);
        destroy_parents = happening.get_bool("destroy_parents");
        repeatable = happening.get_bool("repeatable");
        if (happening.get_string("accepted_tags") != null) {
            accepted_tags = happening.get_string("accepted_tags").split(":");
        } else accepted_tags = new String[0];
        coded = happening.get_bool("coded");
        code_result = happening.get_string("code_result");
        code_solution = happening.get_string("code_solution");
        happened = happening.get_bool("happened");
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
        if(happened && !repeatable){
            return null;
        }
        if(give_items != null) {
            for(Item item : give_items) {
                p.add_item(item);
            }
        }
        for (String id : change_maps) {
            boolean use_special = database.get_row_from_db("maps",id).get_bool("use_special");
            database.update_db("maps","use_special",id,!use_special);
        }
        for (String id : change_specials) {
            SQLResult location = database.get_row_from_db("locations", id);
            String[] map_locations = location.get_string("map_location").split(":");
            String special = location.get_string("refer_special");
            for (String map_location : map_locations) {
                SQLResult map = database.get_row_from_db("maps", map_location);
                String new_location_data = map.get_string("locations").replace(id, special);
                database.update_db("maps", "locations", map_location, new_location_data);
            }
        }
        database.update_db("happening","happened",id,true);
        if(coded) {
            return "coded";
        }
        return flavour;
    }

}
