package Util;

import Main.Player;

public class Location {

    public String id;
    public String flavour;
    public Location refer_special;
    public boolean completed;
    public boolean visited;
    public Happening refer_happening;
    public String map_location;
    public boolean shown;

    public int[] x_y = new int[2];

    public Location(SQLiteJDBC database, String id) {
        SQLResult location = database.get_row_from_db("locations",id);
        this.id = location.get_string("id");
        flavour = location.get_string("flavour");
        if(location.get_string("refer_special") != null) {
            refer_special = new Location(database, location.get_string("refer_special"));
        }
        else refer_special = null;
        completed = location.get_bool("completed");
        visited = location.get_bool("visited");
        if(location.get_string("refer_special") != null) {
            refer_happening = new Happening(database, location.get_string("refer_happening"));
        }
        else refer_happening = null;
        map_location = location.get_string("map_location");
        shown = location.get_bool("shown");
        x_y[0] = location.get_int("x");
        x_y[1] = location.get_int("y");
    }

    public String activate_location(Player p, SQLiteJDBC database) {
        database.update_db("locations","visited",this.id,true);
        return "";
    }

}
