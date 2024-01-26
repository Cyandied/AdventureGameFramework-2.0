package UIelems;

import Util.Location;
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

    public Label(Location loc, ViewControl viewc) {
        this.setText("?");
        this.text = loc.id;
        this.x = loc.x_y[0];
        this.y = loc.x_y[1];

        this.setTextAlignment(TextAlignment.CENTER);
        this.setTextOrigin(VPos.CENTER);
        this.setFont(new Font("Verdana bold",40));
        this.setFill(Paint.valueOf("black"));
        this.setStroke(Paint.valueOf("white"));
        this.setStrokeWidth(3);
        this.setStrokeType(StrokeType.OUTSIDE);
        this.setCursor(Cursor.HAND);
        if(loc.secret) {
            this.setStyle("-fx-opacity: 0;");
        }

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
