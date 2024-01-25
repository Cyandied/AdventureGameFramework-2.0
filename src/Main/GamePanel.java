package Main;

import UIelems.InputField;
import UIelems.OutputField;
import UIelems.Overlays;
import UIelems.ViewField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class GamePanel extends Pane {
    InputField in;
    OutputField out;
    ViewField view;
    Overlays overlay;
    final int padding = 10;

    public Pane init(int scene_width, int height, InputField in, OutputField out, ViewField view, Overlays overlay) {
        double width = 1.2*height;

        this.relocate(scene_width/2 - width/2,0);
        this.resize(width,height);
        this.setBackground(Background.fill(new Color( 0.2, 0.2, 0.2, 1.0)));

        this.in = in;
        this.out = out;
        this.view = view;
        this.overlay = overlay;

        draw(scene_width,height);

        this.getChildren().addAll(in,out,view,overlay);

        return this;
    }


    public void draw(int scene_width, int height) {
        double width = 1.2*height;

        this.relocate(scene_width/2 - width/2,0);
        this.resize(1.2*height,height);

        int elem_width = (int) width - padding*2;

        int in_h = 30;
        int out_h = height/7 * 2 - in_h - padding*2;
        int view_h = height/7 * 5 - in_h - padding*3;

        in.draw(elem_width,in_h,padding,height - (in_h + padding));
        out.draw(elem_width, out_h,padding,height - (in_h + out_h + padding*2));
        view.draw(elem_width, view_h,padding,height - (in_h + out_h + view_h + padding*3));
        overlay.draw(elem_width, view_h,padding,height - (in_h + out_h + view_h + padding*3));
    }

}
