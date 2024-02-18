package Util;

import Main.Player;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Happening {

    public String id;
    public String flavour;
    public Item[] give_items;
    public String[] change_specials;
    public String[] change_maps;
    public Item[] parent_items;
    public boolean destroy_parents;
    public boolean repeatable;
    public String[] accepted_tags;
    public boolean coded;
    public String code_result;
    public String code_solution;
    public String code_type;
    public boolean happened;
    public String[] unlock_maps;
    public String code_params;
    public String font;

    public Happening(SQLiteJDBC database, String id) {
        SQLResult happening = database.get_row_from_db("happening",id);
        if(happening.get_string("id") == null){
            System.err.println("Tried to load happening that does not exist!\nError encountered with happening id: " + id);
            System.exit(1);
        }
        this.id = happening.get_string("id");
        flavour = happening.get_string("flavour");
        give_items = init_items(happening.get_string("give_items"),database);
        if (happening.get_string("change_specials") != null) {
            change_specials = happening.get_string("change_specials").split(":");
        } else change_specials = new String[0];
        if(happening.get_string("change_maps") != null) {
            change_maps = happening.get_string("change_maps").split(":");
        } else change_maps = new String[0];
        parent_items = init_items(happening.get_string("parent_items"),database);
        destroy_parents = happening.get_bool("destroy_parents");
        repeatable = happening.get_bool("repeatable");
        if (happening.get_string("accepted_tags") != null) {
            accepted_tags = happening.get_string("accepted_tags").split(":");
        } else accepted_tags = new String[0];
        coded = happening.get_bool("coded");
        code_result = happening.get_string("code_result");
        code_solution = happening.get_string("code_solution");
        code_type = happening.get_string("code_type");
        happened = happening.get_bool("happened");
        if(happening.get_string("unlock_maps") != null){
            unlock_maps = happening.get_string("unlock_maps").split(":");
        } else unlock_maps = new String[0];
        code_params = happening.get_string("code_params");
        if(code_params != null){
            code_params = code_params.replace("(n)","\n");
        }
        font = database.get_database("meta").get_string("font");
    }

    private Item[] init_items(String item_ids, SQLiteJDBC database) {
        if (item_ids == null) {
            return null;
        }
        String[] item_ids_arr = item_ids.split(":");
        Item[] items = new Item[item_ids_arr.length];
        for (int i = 0; i < item_ids_arr.length; i++) {
            items[i] = new Item(database.get_row_from_db("items",item_ids_arr[i]),item_ids_arr[i]);
        }
        return items;
    }

    public String activate_happening(Player p, SQLiteJDBC database) {
        if(happened && !repeatable && !coded){
            return null;
        }
        if(happened && coded && !repeatable){
            if(take_code()){
                Happening happ = new Happening(database,code_result);
                return happ.activate_happening(p,database);
            }
            return flavour;
        }
        if(give_items != null) {
            for(Item item : give_items) {
                p.add_item(item);
            }
        }
        if(destroy_parents && parent_items != null){
            p.remove_item(parent_items);
        }
        for (String id : change_maps) {
            boolean use_special = database.get_row_from_db("maps",id).get_bool("use_special");
            database.update_db("maps","use_special",id,!use_special);
        }
        for (String id : change_specials) {
            SQLResult location = database.get_row_from_db("locations", id);
            String[] map_locations = location.get_string("map_location").split(":");
            String special = location.get_string("refer_special");
            for (String map_location : map_locations) {
                SQLResult map = database.get_row_from_db("maps", map_location);
                String new_location_data = map.get_string("locations").replace(id, special);
                database.update_db("maps", "locations", map_location, new_location_data);
            }
        }
        for (String id : unlock_maps) {
            database.update_db("maps","unlocked",id,true);
        }
        database.update_db("happening","happened",id,true);
        if(coded) {
            if(take_code()){
                Happening happ = new Happening(database,code_result);
                return happ.activate_happening(p,database);
            }
        }
        return flavour;
    }

    private boolean take_code(){
        final boolean[] res = {false};

        Stage code_input = new Stage();
        code_input.setTitle("Input code");
        ImageView imgv = new ImageView();
        TextField txt_field = new TextField();
        Text txt = new Text(code_params);
        Pane txt_pane = new Pane();
        Pane pane = new Pane();
        try {
            FileInputStream in_stream = new FileInputStream("Res/Global/"+code_type+".png");
            Image img = new Image(in_stream);
            imgv.setImage(img);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        imgv.setFitWidth(300);
        imgv.setFitHeight(300);
        txt.setWrappingWidth(280);
        txt.setFill(Paint.valueOf("white"));
        txt.setFont(new Font(font,20));
        txt_pane.resize(300,300);
        txt_pane.getChildren().add(txt);
        txt_pane.relocate(10,10);
        txt.relocate(10,10);
        txt_pane.setStyle("-fx-background-color: rgba(0,0,0,0.75);-fx-border-radius: 40px;-fx-padding: 20px");
        txt_field.resize(300,30);
        imgv.relocate(10,10);
        txt_field.relocate(10,300+20);
        pane.resize(320,300+30+40);
        pane.getChildren().addAll(imgv,txt_field,txt_pane);
        code_input.setScene(new Scene(pane,320,300+30+40));

        txt_field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                    String text = txt_field.getText().toLowerCase();
                    if(text.equals(code_solution)){
                        res[0] = true;
                    }
                    code_input.close();
                }
            }
        });

        code_input.showAndWait();
        return res[0];
    }

}
