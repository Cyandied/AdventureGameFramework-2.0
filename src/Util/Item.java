package Util;

import UIelems.ErrorBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class Item {
    public String id;
    public String name;
    public String flavour;
    public String[] tags;
    public Item(SQLResult item,String id) {
        if(item.get_string("id") == null){
            String error = "Tried to load item that does not exist!\nError encountered with item id: " + id;
            new ErrorBox(error);
        }
        this.id = item.get_string("id");
        name = item.get_string("name");
        flavour = item.get_string("flavour");
        if(item.get_string("tags") != null) {
            tags = item.get_string("tags").split(":");
        } else tags = new String[0];
    }

    public Button get_item_object(InputControl inc, double width,int pos_h,String font) {
        String label = name.substring(0, Math.min(name.length(), 4));
        Button button = new Button(label);
        button.setPrefWidth(width);
        button.setPrefHeight(width);
        button.relocate(10,pos_h);
        button.setFont(new Font(font,width/4));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                inc.handle_click_input("item",id,name);
            }
        });
        return button;
    }
}
