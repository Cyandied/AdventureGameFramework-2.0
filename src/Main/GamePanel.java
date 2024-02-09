package Main;

import UIelems.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GamePanel extends Pane {
    InputField in;
    OutputField out;
    ViewField view;
    Overlays overlay;
    InventoryView inv;
    final int padding = 10;

    public Pane init(int scene_width, int height, InputField in, OutputField out, ViewField view, Overlays overlay, InventoryView inv) {
        double width = 1.2*height;

        this.relocate(scene_width/2 - width/2,0);
        this.resize(width,height);
        this.setBackground(Background.fill(new Color( 0, 0, 0, 1.0)));

        this.in = in;
        this.out = out;
        this.view = view;
        this.overlay = overlay;
        this.inv = inv;

        draw(scene_width,height);

        this.getChildren().addAll(in,out,view,overlay,inv);

        return this;
    }


    public void draw(int scene_width, int height) {
        double width = scene_width - padding*2;
        if((double) width/height > 1.7) {
            width = 1.7*height;
        }

        int elem_width = (int) ((int) width * 0.9 - padding*3);

        int inv_w = (int) (width * 0.1);
        int in_h = 30;
        int out_h = height/7 * 2 - in_h - padding*2;
        int view_h = height/7 * 5 - in_h - padding;

        int view_pos_h = ((height - (in_h + out_h + padding*2)) - padding)/2 - view_h/2;

        this.relocate(scene_width/2 - width/2,0);
        this.resize(width,height);

        out.draw(elem_width, out_h,padding,height - (out_h + padding));
        in.draw(elem_width,in_h,padding,height - (in_h + out_h + padding*3));
        view.draw(elem_width, view_h,padding,view_pos_h);
        overlay.draw(elem_width, view_h,padding,view_pos_h);
        inv.draw(inv_w,height - padding*2,(int)elem_width + padding*2, padding);
    }

}
