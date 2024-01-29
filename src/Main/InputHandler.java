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
        viewc.change_scene(active_map,database);
        inv.update_inv_view(player.give_list_inv());
        inc.print_out(meta.get_string("introduction"));
    }

    public void reload_active_map() {
        Map map = new Map(database, active_map.id);
        if(map.use_special) {
            active_map = new Map(database,map.refer_special);
        }
        else active_map = map;
        viewc.change_scene(active_map,database);
    }

    public void handle(String type, String content){
        switch (type) {
            case "arrow":
                Map map = new Map(database, content);
                if(map.use_special) {
                    active_map = new Map(database,map.refer_special);
                }
                else active_map = map;
                database.update_db("meta","start_stage",active_map.id);
                database.update_db("meta","introduction",active_map.flavour);
                viewc.change_scene(active_map,database);
                inc.print_out(active_map.flavour);
                break;
            case "location":
                Location location = active_map.find_location(content);
                if(location == null) {
                    inc.print_out("Something went wrong...");
                    System.err.println("Location: " + content + ", not present on map: " + active_map.id);
                    break;
                }
                String text = location.activate_location(player,database);
                inv.update_inv_view(player.give_list_inv());
                reload_active_map();
                inc.print_out(text);
                break;
            case "item":
                Item item = player.get_item(content);
                if(item == null) {
                    inc.print_out("Something went wrong...");
                    System.err.println("Item: " + content + ", not present in player inventory");
                    break;
                }
                inc.print_out(item.name);
                inc.print_out(item.flavour);
                break;
        }
        gm.redraw();
    }

    public void handle(String type1, String content1, String type2, String content2) {
        if(type1.equals("item") && type2.equals("item")){
            item_item(content1,content2);
        }
        else if (type1.equals("item") && type2.equals("location")) {
            item_location(content1,content2);
        }
        else if(type2.equals("item") && type1.equals("location")) {
            item_location(content2,content1);
        }
        else {
            inc.print_out("Something went wrong...");
            System.err.println("Combination was neither item-item or item-location, but was: " + type1 +"-"+type2 + " which is invalid.");
        }
        gm.redraw();
    }

    private void item_item(String item_1, String item_2) {
        Happening happ;
        String happ_id = null;
        if(item_1.length() == item_2.length()){
            if(str_to_int(item_1) < str_to_int(item_2)){
                happ = new Happening(database,item_1+item_2);
                happ_id = happ.id;
            }
            else {
                happ = new Happening(database,item_2+item_1);
                happ_id = happ.id;
            }
        }
        else if(item_1.length() < item_2.length()){
            happ = new Happening(database,item_1+item_2);
            happ_id = happ.id;
        }
        else {
            happ = new Happening(database,item_2+item_1);
            happ_id = happ.id;
        }
        if(happ_id != null){
            String text = happ.activate_happening(player,database);
            reload_active_map();
            inv.update_inv_view(player.give_list_inv());
            inc.print_out(text);
        }
        else {
            inc.print_out("No entry");
        }
    }

    private int str_to_int(String s){
        int val = Integer.parseInt(s.substring(0,2));
        if(s.length() > 2){
            char[] c_arr = s.substring(2).toCharArray();
            for(char c : c_arr){
                val += c;
            }
        }
        return val;
    }

    private void item_location(String item, String location) {
        Happening happ = new Happening(database,location+item);
        if(happ.id != null){
            String text = happ.activate_happening(player,database);
            reload_active_map();
            inv.update_inv_view(player.give_list_inv());
            inc.print_out(text);
        }
        else {
            inc.print_out("No entry");
        }
    }



}
