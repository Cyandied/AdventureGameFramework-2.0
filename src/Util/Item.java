package Util;

public class Item {
    public String id;
    public String name;
    public String flavour;
    public String[] tags;
    public Item(SQLResult item) {
        id = item.get_string("id");
        name = item.get_string("name");
        flavour = item.get_string("flavour");
        tags = item.get_string("tags").split(":");
    }
}
