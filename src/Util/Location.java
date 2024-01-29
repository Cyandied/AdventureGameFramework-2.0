package Util;

import Main.Player;

public class Location {

    public String id;
    public String name;
    public String flavour;
    public boolean completed;
    public boolean visited;
    public Happening refer_happening;
    public String[] map_locations;
    public boolean shown;
    public boolean secret;
    public boolean mark_complete;
    public int[] x_y = new int[2];

    public Location(SQLiteJDBC database, String id) {
        SQLResult location = database.get_row_from_db("locations",id);
        if(location.get_string("id") == null){
            System.err.println("One or more locations referenced in map/locations do NOT exist!\nError encountered with location id: " + id);
            System.exit(1);
        }
        this.id = location.get_string("id");
        name = location.get_string("name");
        flavour = location.get_string("flavour");
        completed = location.get_bool("completed");
        visited = location.get_bool("visited");
        if(location.get_string("refer_happening") != null){
            refer_happening = new Happening(database, location.get_string("refer_happening"));
        }
        map_locations = location.get_string("map_location").split(":");
        shown = location.get_bool("shown");
        secret = location.get_bool("secret");
        mark_complete = location.get_bool("mark_complete");
        x_y[0] = location.get_int("x");
        x_y[1] = location.get_int("y");
    }

    public String activate_location(Player p, SQLiteJDBC database) {
        database.update_db("locations","visited",this.id,true);
        if(mark_complete){
            database.update_db("locations","completed",this.id,true);
        }
        if(refer_happening == null){
            return flavour;
        }
        String happening_flavour = refer_happening.activate_happening(p,database);
        if(happening_flavour == null){
            return flavour;
        }
        return flavour + "\n" + happening_flavour;
    }

}
