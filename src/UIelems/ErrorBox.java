package UIelems;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ErrorBox {

    public ErrorBox(String msg){
        init(msg);
    }

    private void init(String error){
        Stage window = new Stage();
        Pane content = new Pane();

        content.resize(240,150);
        Text txt_static = new Text("A fetal error occurred!");
        Text txt_dynamic = new Text(error);
        txt_dynamic.setWrappingWidth(220);
        txt_dynamic.setTextAlignment(TextAlignment.CENTER);
        txt_static.setWrappingWidth(220);
        txt_static.setTextAlignment(TextAlignment.CENTER);
        txt_static.setFill(Paint.valueOf("red"));
        txt_static.setStyle("-fx-font-weight: bold;");
        Button btn = new Button("close");
        btn.setPrefWidth(220);

        txt_static.relocate(10,10);
        txt_dynamic.relocate(10,40);
        btn.relocate(10,110);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(1);
            }
        });

        content.getChildren().addAll(txt_static,txt_dynamic,btn);
        window.setScene(new Scene(content,240,150));

        window.setAlwaysOnTop(true);
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(1);
            }
        });

        window.showAndWait();

    }

}
