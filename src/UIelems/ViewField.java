package UIelems;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class ViewField extends ImageView {
    public void set_scene(String background, String game){
        try {
            String bg_src = "Res/Games/"+game+"/Backgrounds/";
            FileInputStream in_stream = new FileInputStream(bg_src + background);
            Image img = new Image(in_stream);

            this.setImage(img);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void draw(int width, int height, int pos_w, int pos_h) {
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.relocate(pos_w, pos_h);
    }
}
