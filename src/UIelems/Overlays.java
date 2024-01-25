package UIelems;

import Util.ViewControl;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class Overlays extends Pane {

    HashMap<String,String> arrow_labels = new HashMap<String,String>();
    HashMap<String,Arrow> arrows = new HashMap<String,Arrow>();
    int divisions = 11;
    public HashMap<String,Label> labels = new HashMap<String,Label>();
    int width,height;
    int arrow_w = 200;
    int arrow_h = 60;

    ViewControl viewc;

    public Overlays(ViewControl viewc) {
        this.viewc = viewc;
        init_arrows();
    }

    public void draw(int width, int height, int pos_w, int pos_h) {
        this.width = width;
        this.height = height;
        this.resize(width, height);
        this.relocate(pos_w, pos_h);
        draw_arrows(width,height);
        draw_labels(width,height);
    }

    public void void_labels() {
        for(String key : labels.keySet()) {
            this.getChildren().remove(labels.get(key));
            labels.remove(key);
        }
    }

    public void put_label(String text, int[] xy) {
        labels.put(text, new Label(text, xy[0],xy[1], viewc));
    }

    public void void_arrows() {
        arrow_labels.replaceAll((k, v) -> "");
    }

    public void put_arrow(String direction, String label) {
        arrow_labels.put(direction, label);
    }

    public void place_labels() {
        for(String key : labels.keySet()) {
            this.getChildren().add(labels.get(key));
        }
    }

    private void init_arrows() {
        arrows.put("up",new Arrow(arrow_w,arrow_h,"up",viewc));
        arrow_labels.put("up","A");
        arrows.put("down",new Arrow(arrow_w,arrow_h,"down",viewc));
        arrow_labels.put("down","A");
        arrows.put("left",new Arrow(arrow_h,arrow_w,"left",viewc));
        arrow_labels.put("left","A");
        arrows.put("right",new Arrow(arrow_h,arrow_w,"right",viewc));
        arrow_labels.put("right","A");
        this.getChildren().addAll(arrows.get("up"),arrows.get("down"),arrows.get("left"),arrows.get("right"));
    }

    public void draw_labels(int width, int height) {
        double x_scale = (double) width /divisions;
        double y_scale = (double) height /divisions;
        for(String key : labels.keySet()) {
            Label label = labels.get(key);
            label.relocate(x_scale*label.x,y_scale*label.y);
        }
    }

    public void draw_arrows(int width, int height) {
        for(String direction : arrow_labels.keySet()) {
            Arrow arrow = arrows.get(direction);
            String label = arrow_labels.get(direction);
            arrow.set_visible(!label.isEmpty());
            arrow.set_label(label);
            switch (direction) {
                case "up":
                    arrow.place(width/2 - arrow_w/2,0);
                    break;
                case "down":
                    arrow.place(width/2 - arrow_w/2,height - arrow_h);
                    break;
                case "left":
                    arrow.place(0,height/2 - arrow_w/2);
                    break;
                case "right":
                    arrow.place(width - arrow_h,height/2 - arrow_w/2);
                    break;
            }
        }
    }


}
