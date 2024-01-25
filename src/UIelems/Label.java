package UIelems;

import Util.ViewControl;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.*;

public class Label extends Text {

    public int x,y;
    private String text;

    public Label(String text, int x, int y, ViewControl viewc) {
        this.setText(text);
        this.text = text;
        this.x = x;
        this.y = y;

        this.setTextAlignment(TextAlignment.CENTER);
        this.setTextOrigin(VPos.CENTER);
        this.setFont(new Font("Verdana bold",30));
        this.setFill(Paint.valueOf("black"));
        this.setStroke(Paint.valueOf("white"));
        this.setStrokeWidth(3);
        this.setStrokeType(StrokeType.OUTSIDE);
        this.setCursor(Cursor.HAND);

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
                    viewc.handle_click("location",text);
                }
            }
        });
    }

    public void place(int pos_w, int pos_h) {
        this.relocate(pos_w,pos_h);
    }

}
