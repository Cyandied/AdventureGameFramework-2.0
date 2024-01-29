package Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Map {

    public String id;
    public String flavour;
    public String refer_special;
    public boolean use_special;
    public Location[] locations;
    public HashMap<String, String> connections_ids;
    public String background_file;

    private final String[] directions = {"up","down","left","right"};

    public Map(SQLiteJDBC database, String id) {
        SQLResult map = database.get_row_from_db("maps",id);
        this.id = map.get_string("id");
        flavour = map.get_string("flavour");
        if(map.get_string("refer_special") != null) {
            refer_special = map.get_string("refer_special");
        }
        else refer_special = null;
        use_special = map.get_bool("use_special");
        locations = init_locations(map.get_string("locations"),database);
        connections_ids = init_connections(map,database);
        background_file = map.get_string("background_file");

    }

    public Location find_location(String location_id) {
        List<String> res_arr = Arrays.stream(locations).map(n -> n.id).toList();
        if(res_arr.contains(location_id)){
            return locations[res_arr.indexOf(location_id)];
        }
        return null;
    }

    private HashMap<String, String> init_connections(SQLResult map, SQLiteJDBC database) {
        HashMap<String, String> connections = new HashMap<String, String>();
        for( String direction : directions) {
            String label = map.get_string("connection_" + direction);
            if(label == null){
                label = "";
            }
            connections.put(direction, label);
        }
        return connections;
    }

    private Location[] init_locations(String location_ids, SQLiteJDBC database) {
        if (location_ids == null) {
            return null;
        }
        String[] location_ids_arr = location_ids.split(":");
        Location[] locations = new Location[location_ids_arr.length];
        for (int i = 0; i < location_ids_arr.length; i++) {
            locations[i] = new Location(database,location_ids_arr[i]);
        }
        return locations;
    }

}
