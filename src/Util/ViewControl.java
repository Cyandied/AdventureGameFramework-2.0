package Util;

import Main.GameMaster;
import UIelems.Overlays;
import UIelems.ViewField;

public class ViewControl {
    public ViewField view_f;
    public Overlays overlay;
    public InputControl inc;
    GameMaster gm;

    public ViewControl(GameMaster gm) {
        this.gm = gm;
        this.inc = gm.inc;
        view_f = new ViewField();
        overlay = new Overlays(this);
    }

    public void handle_click(String type,String id, String content) {
        inc.handle_click_input(type,id,content);
    }

    public void change_scene(Map map,SQLiteJDBC database) {
        inc.out_field.clear();
        view_f.set_scene(map.background_file, gm.game);
        overlay.void_arrows();
        overlay.void_labels();
        for(String direction : map.connections_ids.keySet()) {
            boolean place = true;
            if(!map.connections_ids.get(direction).isEmpty()){
                if(!database.get_row_from_db("maps",map.connections_ids.get(direction)).get_bool("unlocked")){
                    place = false;
                }
            }
            if(place){
                overlay.put_arrow(direction,map.connections_ids.get(direction));
            }
            else {
                overlay.put_arrow(direction,"");
            }
        }
        if(map.locations != null){
            for(Location location : map.locations) {
                if (location.shown && !location.completed) {
                    overlay.put_label(location);
                }
            }
            overlay.place_labels();
        }
        gm.redraw();
    }
}
