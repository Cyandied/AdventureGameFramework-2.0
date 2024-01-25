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

    public void handle_click(String type, String content) {
        inc.handle_click_input(type,content);
    }

    public void change_scene(Map map) {
        inc.out_field.clear();
        view_f.set_scene(map.background_file);
        overlay.void_arrows();
        overlay.void_labels();
        for(String direction : map.connections_ids.keySet()) {
            overlay.put_arrow(direction,map.connections_ids.get(direction));
        }
        if(map.locations != null){
            for(Location location : map.locations) {
                if (location.shown) {
                    overlay.put_label(location.id, location.x_y);
                }
            }
            overlay.place_labels();
        }
        gm.redraw();
    }
}
