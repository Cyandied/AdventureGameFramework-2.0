package UIelems;

import Util.ViewControl;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

import java.io.FileInputStream;

public class Arrow extends StackPane {

    ImageView arrow = new ImageView();
    Text label = new Text("Test");

    public Arrow(int width, int height, String direction, ViewControl viewc) {
        this.resize(width,height);
        arrow.setFitWidth(width);
        arrow.setFitHeight(height);
        this.setCursor(Cursor.HAND);

        try {
            String arrow_src = "src/res/Global/";
            FileInputStream in_stream = new FileInputStream(arrow_src + direction + ".png");
            Image img = new Image(in_stream);

            arrow.setImage(img);
        } catch (Exception e) {System.err.println(e);}

        label.setFont(new Font("Corbel bold",30));
        label.setBoundsType(TextBoundsType.VISUAL);
        label.setTextAlignment(TextAlignment.CENTER);

        this.getChildren().addAll(arrow,label);

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
                    viewc.handle_click("arrow",label.getText());
                }
            }
        });

    }

    public void set_label(String label) {
        this.label.setText(label);
    }

    public void set_visible(boolean visible) {
        arrow.setVisible(visible);
        label.setVisible(visible);
    }

    public void place(int pos_w, int pos_h) {
        this.relocate(pos_w,pos_h);
    }

}
