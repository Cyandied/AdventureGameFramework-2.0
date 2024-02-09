package UIelems;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

public class OutputField extends TextArea {

    public OutputField() {
        this.setEditable(false);
        this.setWrapText(true);

    }
    public void set_font(String font) {
        this.setFont(new Font(font,20));
    }
    public void draw(int width, int height, int pos_w, int pos_h) {
        this.setPrefSize(width,height);
        this.relocate(pos_w, pos_h);
    }
    public void append_text(String text) {
        String new_text = text.replace("(n)","\n");
        this.appendText(new_text + "\n");
    }

}
