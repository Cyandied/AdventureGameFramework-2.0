package UIelems;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GamePanel extends Pane {
    InputField in;
    OutputField out;
    ViewField view;
    Overlays overlay;
    final int padding = 10;

    public Pane init(int width, int height, InputField in, OutputField out, ViewField view, Overlays overlay) {
        this.resize(width,height);
        this.in = in;
        this.out = out;
        this.view = view;
        this.overlay = overlay;

        draw(width,height);

        this.getChildren().addAll(in,out,view,overlay);

        return this;
    }


    public void draw(int width, int height) {

        this.resize(width,height);

        int elem_width = width - padding*2;

        int in_h = 30;
        int out_h = height/3 - in_h - padding*2;
        int view_h = height/3 * 2 - in_h - padding*3;

        in.draw(elem_width,in_h,padding,height - (in_h + padding));
        out.draw(elem_width, out_h,padding,height - (in_h + out_h + padding*2));
        view.draw(elem_width, view_h,padding,height - (in_h + out_h + view_h + padding*3));
        overlay.draw(elem_width, view_h,padding,height - (in_h + out_h + view_h + padding*3));
    }

}
