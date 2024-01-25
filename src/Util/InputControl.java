package Util;

import Main.GameMaster;
import Main.InputHandler;
import UIelems.InputField;
import UIelems.OutputField;

public class InputControl {

    public InputField in_field;
    public OutputField out_field;
    public InputHandler inh;
    String last_clicked_type = "";
    String last_clicked_content = "";
    GameMaster gm;

    public InputControl(GameMaster gm) {
        this.gm = gm;
        in_field = new InputField(this);
        out_field = gm.out_field;
        in_field.setDisable(true);
    }

    public void get_input_handler(InputHandler inh){
        this.inh = inh;
    }

    private void reset_last_clicked() {
        last_clicked_content = "";
        last_clicked_type = "";
    }

    private boolean no_last_click() {
        return last_clicked_content.isEmpty() && last_clicked_type.isEmpty();
    }

    public void handle_click_input(String type, String content){
        boolean same_thing = last_clicked_type.equals(type) && last_clicked_content.equals(content);
        switch (type) {
            case "arrow":
                inh.handle(type, content);
                in_field.clear();
                reset_last_clicked();
                break;
            case "location":
                if(same_thing){
                    inh.handle(type,content);
                    in_field.clear();
                    reset_last_clicked();
                }
                else if(!no_last_click() && !last_clicked_type.equals("location")) {
                    inh.handle(last_clicked_type,last_clicked_content,type,content);
                    in_field.clear();
                    reset_last_clicked();
                }
                else if(last_clicked_type.equals("location")){
                    out_field.append_text("You cannot combine locations!");
                    in_field.clear();
                    reset_last_clicked();
                }
                else {
                    last_clicked_type = type;
                    last_clicked_content = content;
                }
                break;
            case "item":
                if(same_thing){
                    inh.handle(type,content);
                    in_field.clear();
                    reset_last_clicked();
                }
                else if(!no_last_click()) {
                    inh.handle(last_clicked_type,last_clicked_content,type,content);
                    in_field.clear();
                    reset_last_clicked();
                }
                else {
                    last_clicked_type = type;
                    last_clicked_content = content;
                }
                break;
        }
    }

    public void print_out(String text) {
        out_field.append_text(text);
    }

    public void handle_input(String in) {

    }

}
