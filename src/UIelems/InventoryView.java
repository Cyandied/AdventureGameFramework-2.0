package UIelems;

import Main.GameMaster;
import Util.InputControl;
import Util.Item;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class InventoryView extends Pane {

    Pane scroll;
    InputControl inc;

    double width;
    double height;

    boolean first_draw = true;

    String font;
    public InventoryView(GameMaster gm) {
        this.inc = gm.inc;
        scroll = new Pane();
        this.getChildren().add(scroll);
    }

    public void update_inv_view(Item[] items) {
        scroll.getChildren().removeAll(scroll.getChildren());
        for(int i = 0; i < items.length; i++) {
            scroll.getChildren().add(items[i].get_item_object(inc,this.getWidth() - 20, (int) ((this.getWidth() - 20)*i + 10*(i+1)) + 40,font));
        }
    }

    public void scroll_view(int direction) {
        for(Node item : scroll.getChildren()) {
            double pos_height = item.getLayoutY();
            double height = item.getLayoutBounds().getHeight();
            item.relocate(10,pos_height + (direction * height/2));
        }
    }

    public void draw(int width, int height, int pos_w, int pos_h) {
        if(first_draw){
            Button up = scroll_buttons(width,0,1,"↑");
            Button down = scroll_buttons(width,height - 40,-1,"↓");
            this.getChildren().addAll(up,down);
            first_draw = false;
        }
        this.setPrefSize(width,height);
        this.relocate(pos_w, pos_h);
    }

    private Button scroll_buttons(double width,double pos_h, int direction, String content){
        Button button = new Button(content);
        button.setFont(new Font(font, 25));
        button.setPrefWidth(width +20);
        button.relocate(0,pos_h);

        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                scroll_view(direction);
            }
        });
        return button;
    }

    public void set_font(String font){
        this.font = font;
    }
}
