package Main;

import UIelems.GameChoicePanel;
import UIelems.InventoryView;
import UIelems.OutputField;
import Util.InputControl;
import Util.SQLResult;
import Util.SQLiteJDBC;
import Util.ViewControl;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class GameMaster extends Application {

    public GamePanel gp;
    public InputControl inc;
    public OutputField out_field;
    public ViewControl viewc;

    public InventoryView inv;

    public SQLiteJDBC database;
    public InputHandler inh;
    Stage primary_stage;

    public static String game;
    int start_width = 1000;
    int sys_padding_width = 0;
    int start_height = 700;
    int sys_padding_height = 0;
    boolean first_redraw = true;

    public void init() {
        out_field = new OutputField();
        inc = new InputControl(this);
        viewc = new ViewControl(this);
        inv = new InventoryView(this);
        gp = new GamePanel();
    }

    public void start(Stage primary_stage) {
        this.primary_stage = primary_stage;


        Stage start_screen = new Stage();
        start_screen.setTitle("Select game");
        start_screen.setResizable(false);
        GameChoicePanel content = new GameChoicePanel(this,primary_stage,start_screen);
        start_screen.setScene(new Scene(content,200,180));

        start_screen.show();
    }

    public void init_game(String game) {
        this.game = game;
        make_saved_game(false);
        database = new SQLiteJDBC(game,"saved");

        SQLResult meta = database.get_database("meta");

        primary_stage.setTitle(meta.get_string("game_name"));
        primary_stage.setResizable(true);

        Scene scene = new Scene(gp.init(
                start_width,
                start_height,
                inc.in_field,
                out_field,
                viewc.view_f,
                viewc.overlay,
                inv
        ), start_width, start_height);
        scene.setFill(new Color( 0.2, 0.2, 0.2, 1.0));

        primary_stage.setScene(scene);

        primary_stage.widthProperty().addListener(observable -> redraw());
        primary_stage.heightProperty().addListener(observable -> redraw());

        Stage title_screen = title_screen(game,meta,primary_stage);
        title_screen.show();
    }

    private Stage title_screen(String game, SQLResult meta, Stage primary_stage) {
        Stage stage = new Stage();

        stage.setTitle(meta.get_string("game_name"));
        stage.setResizable(false);

        Pane pane = new Pane();
        ImageView img = new ImageView();
        Button start_game = new Button("New game");
        Button saved_game = new Button("Load game");
        Button exit_game = new Button("Exit");

        if(meta.get_bool("fresh")){
            saved_game.setDisable(true);
        }

        try {
            FileInputStream in_stream = new FileInputStream("src/res/Games/"+game+"/Backgrounds/" + meta.get_string("title_screen_file"));
            Image title_screen = new Image(in_stream);
            img.setImage(title_screen);
        } catch (Exception e) {System.err.println(e);}

        pane.resize(1960*0.5,1050*0.5);

        img.setPreserveRatio(true);
        img.setFitWidth(pane.getWidth());

        start_game.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                database.close();
                make_saved_game(true);
                database = new SQLiteJDBC(game,"saved");
                inh = new InputHandler(gm());
                inc.get_input_handler(inh);
                primary_stage.show();
            }
        });

        saved_game.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                database.close();
                database = new SQLiteJDBC(game,"saved");
                inh = new InputHandler(gm());
                inc.get_input_handler(inh);
                primary_stage.show();
            }
        });

        exit_game.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });

        start_game.setPrefWidth(pane.getWidth()/2);
        start_game.setFont(new Font("Cambria",25));
        start_game.relocate(pane.getWidth()/4, 260);

        saved_game.setPrefWidth(pane.getWidth()/2);
        saved_game.setFont(new Font("Cambria",25));
        saved_game.relocate(pane.getWidth()/4, 330);

        exit_game.setPrefWidth(pane.getWidth()/2);
        exit_game.setFont(new Font("Cambria",25));
        exit_game.relocate(pane.getWidth()/4, 400);

        pane.getChildren().addAll(img,start_game, saved_game,exit_game);

        stage.setScene(new Scene(pane, pane.getWidth(),pane.getHeight()));

        return stage;
    }

    private static void make_saved_game(boolean overwrite){
        Path source = Path.of("src/Res/Games/" + game + "/" + game.toLowerCase() + ".sqlite");
        Path target = Path.of("src/Res/Games/" + game + "/saved.sqlite");
        File f = new File("src/Res/Games/" + game + "/saved.sqlite");
        if(!f.exists() || overwrite){
            try {
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public GameMaster gm() {
        return this;
    }

    public void redraw() {
        if(first_redraw && (int)primary_stage.getHeight() > 0){
            first_redraw = false;
            sys_padding_width = (int)primary_stage.getWidth() - start_width;
            sys_padding_height = (int)primary_stage.getHeight() - start_height;
        }
        int width = (int)primary_stage.getWidth();
        int height = (int)primary_stage.getHeight();
        if((double) width/height < 1.7) {
            width = (int) (height*1.7);
        }
        primary_stage.setWidth(width);

        gp.draw(width - sys_padding_width,height - sys_padding_height);
    }
    public void stop() {

    }

    public void start_game() {
        launch();
    }

}
