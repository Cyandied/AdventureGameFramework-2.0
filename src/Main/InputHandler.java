package Main;

import Util.*;

import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class InputHandler {

    private SQLiteJDBC database;
    private InputControl inc;
    private ViewControl viewc;
    public Player player;

    private Map active_map;

    public InputHandler(GameMaster gm) {
        database = gm.database;
        inc = gm.inc;
        viewc = gm.viewc;
        player = new Player();
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
        inc.print_out(meta.get_string("introduction"));
    }

    public void handle(String type, String content){
        switch (type) {
            case "arrow":
                active_map = new Map(database, content);
                viewc.change_scene(active_map);
                inc.print_out(active_map.flavour);
                break;
            case "location":

        }
        inc.print_out(type + " , " + content);
    }

    public void handle(String type1, String content1, String type2, String content2) {
        inc.print_out(content1 + " , " + content2);
    }



}
