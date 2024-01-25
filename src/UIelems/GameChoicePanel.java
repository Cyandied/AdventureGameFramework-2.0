package UIelems;

import Main.GameMaster;
import Util.SQLiteJDBC;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameChoicePanel extends Pane {

    String games_dir = "src/Res/Games";
    String[] games;
    ChoiceBox games_choices;
    Button refresh;
    Button load_game;

    public GameChoicePanel(GameMaster gm, Stage prim, Stage start) {
        games = get_available_games();
        games_choices = new ChoiceBox();
        refresh = new Button("Refresh");
        load_game = new Button("Load game");

        for(String game : games) {
            games_choices.getItems().add(game);
        }

        games_choices.setValue(games[0]);

        Text text = new Text("Select a game:");
        text.setFont(new Font("Cambria",20));
        text.setTextAlignment(TextAlignment.CENTER);
        games_choices.setPrefWidth(150);
        games_choices.setPrefHeight(20);
        refresh.setPrefWidth(150);
        refresh.setPrefHeight(20);
        load_game.setPrefWidth(150);
        load_game.setPrefHeight(20);

        text.relocate((200/2 - 150/2), 20);
        games_choices.relocate((200/2 - 150/2), 20*3);
        refresh.relocate((200/2 - 150/2), 20*5);
        load_game.relocate((200/2 - 150/2), 20*7);

        this.getChildren().addAll(text,games_choices,refresh,load_game);
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                games_choices.getItems().removeAll(games);
                games = get_available_games();
                for(String game : games) {
                    games_choices.getItems().add(game);
                }
                games_choices.setValue(games[0]);
            }
        });

        load_game.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                String game = games_choices.getValue().toString();
                if(!game.isEmpty()) {
                    gm.init_game(games_choices.getValue().toString());
                    start.close();
                }
            }
        });

    }

    private String[] get_available_games() {
        String[] games_unchecked = find_folder_in_dir(games_dir);
        List<String> games_validated = new ArrayList<String>(0);
        for(String game : games_unchecked) {
            boolean SQL_connection = test_SQL_connection(game);
            boolean backgrounds_existence = check_if_background_folder(game);
            if (SQL_connection && backgrounds_existence) {
                games_validated.add(game);
            }
            else if (!SQL_connection) {
                System.err.println("Could not connect to database for " + game + "!");
            }
            else {
                System.err.println("Could not find a backgrounds folder for " + game + "!");
            }
        }
        return games_validated.toArray(new String[games_validated.size()]);
    }

    private boolean test_SQL_connection(String game) {
        SQLiteJDBC sql = new SQLiteJDBC(game,game);
        return sql.connection_status;
    }

    private boolean check_if_background_folder(String game) {
        String[] dir_list = find_folder_in_dir(games_dir + "/" + game);
        return Arrays.stream(dir_list).allMatch(i -> i.equals("Backgrounds"));
    }

    private String[] find_folder_in_dir(String from_dir) {
        try {
            File dir = new File(from_dir);

            FileFilter dir_file_filter = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            };

            File[] dir_list_as_file = dir.listFiles(dir_file_filter);
            String[] games_in_dir = new String[dir_list_as_file.length];
            for (int i = 0; i < dir_list_as_file.length; i++) {
                games_in_dir[i] = dir_list_as_file[i].getName();
            }
            return games_in_dir;
        } catch (Exception e) {
            System.err.println(e);
            return new String[0];
        }
    }

}
