package Util;

import UIelems.InventoryView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;

public class Item {
    public String id;
    public String name;
    public String flavour;
    public String[] tags;
    public Item(SQLResult item) {
        id = item.get_string("id");
        name = item.get_string("name");
        flavour = item.get_string("flavour");
        if(item.get_string("tags") != null) {
            tags = item.get_string("tags").split(":");
        } else tags = new String[0];
    }

    public Button get_item_object(InputControl inc, double width,int pos_h) {
        String label = name.substring(0, Math.min(name.length(), 4));
        Button button = new Button(label);
        button.setPrefWidth(width);
        button.setPrefHeight(width);
        button.relocate(10,pos_h);
        button.setFont(new Font("Cambria",width/4));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                inc.handle_click_input("item",id);
            }
        });
        return button;
    }
}
