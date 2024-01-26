package Main;

import UIelems.InventoryView;
import Util.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class InputHandler {

    private SQLiteJDBC database;
    private InputControl inc;
    private ViewControl viewc;
    public Player player;

    InventoryView inv;
    private Map active_map;
    GameMaster gm;

    public InputHandler(GameMaster gm) {
        database = gm.database;
        inc = gm.inc;
        viewc = gm.viewc;
        inv = gm.inv;
        this.gm = gm;
        player = new Player(database);
        init_game();
    }

    private void init_game() {
        SQLResult meta = database.get_database("meta");
        database.update_db("meta","fresh",false);
        active_map = new Map(database, meta.get_string("start_stage"));
        if(meta.get_string("start_items") != null) {
            for (String item_id : meta.get_string("start_items").split(":")){
                player.add_item(new Item(database.get_row_from_db("items",item_id)));
            }
        }
        viewc.change_scene(active_map);
        inv.update_inv_view(player.inventory.toArray(new Item[player.inventory.size()]));
        inc.print_out(meta.get_string("introduction"));
    }

    public void reload_active_map() {
        System.out.println("Reloaded map");
        Map map = new Map(database, active_map.id);
        if(map.use_special) {
            active_map = map.refer_special;
        }
        else active_map = map;
        viewc.change_scene(active_map);
    }

    public void handle(String type, String content){
        switch (type) {
            case "arrow":
                Map map = new Map(database, content);
                if(map.use_special) {
                    active_map = map.refer_special;
                }
                else active_map = map;
                viewc.change_scene(active_map);
                inc.print_out(active_map.flavour);
                break;
            case "location":
                Location location = active_map.find_location(content);
                if(location == null) {
                    inc.print_out("Something went wrong...");
                    System.out.println("Location: " + content + ", not present on map: " + active_map.id);
                    break;
                }
                String text = location.activate_location(player,database);
                inv.update_inv_view(player.inventory.toArray(new Item[player.inventory.size()]));
                reload_active_map();
                inc.print_out(text);
                break;
            case "item":
                Item item = player.get_item(content);
                if(item == null) {
                    inc.print_out("Something went wrong...");
                    System.out.println("Item: " + content + ", not present in player inventory");
                    break;
                }
                inc.print_out(item.name);
                inc.print_out(item.flavour);
                break;
        }
    }

    public void handle(String type1, String content1, String type2, String content2) {
        inc.print_out(content1 + " , " + content2);
    }



}
