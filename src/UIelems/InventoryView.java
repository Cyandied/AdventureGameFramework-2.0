package UIelems;

import Main.GameMaster;
import Util.InputControl;
import Util.Item;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class InventoryView extends StackPane {

    Pane scroll;
    InputControl inc;
    public InventoryView(GameMaster gm) {
        this.inc = gm.inc;
        scroll = new Pane();
        this.getChildren().add(scroll);
        this.setAlignment(Pos.TOP_CENTER);
    }

    public void update_inv_view(Item[] items) {
        scroll.getChildren().removeAll(scroll.getChildren());
        for(int i = 0; i < items.length; i++) {
            scroll.getChildren().add(items[i].get_item_object(inc,this.getWidth() - 20, (int) ((this.getWidth() - 20)*i + 10*(i+1))));
        }
    }

    public void draw(int width, int height, int pos_w, int pos_h) {
        this.setPrefSize(width,height);
        this.relocate(pos_w, pos_h);
    }
}
