package UIelems;

import Util.InputControl;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

public class InputField extends TextField {

    InputControl in_con;

    public InputField(InputControl in_con) {
        this.in_con = in_con;

        this.setFont(new Font("Cambria",20));
        this.setText(" > ");
        this.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-opacity: 1");
        this.setAlignment(Pos.CENTER_LEFT);

        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.ENTER) && !get_text().isEmpty()) {
                    in_con.handle_input(get_text());
                    clear_text();
                }
            }
        });
    }

    public void draw(int width, int height, int pos_w, int pos_h) {
        this.setPrefSize(width,height);
        this.relocate(pos_w, pos_h);
    }

    public void clear(){
        this.setText(" > ");
    }

    public boolean is_empty() {
        return this.get_text().isEmpty();
    }

    private String get_text() {
        return this.getText();
    }

    private void clear_text() {
        this.setText("");
    }

}
