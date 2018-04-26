package shrestha.shaw.librarymanagementstudent;

public class User {
    private  String accesslevel;
    private int id;
    private String name;

    public User() {
    }

    public User(String accesslevel, int id, String name) {
        this.accesslevel = accesslevel;
        this.id = id;
        this.name = name;
    }

    public String getAccesslevel() { return accesslevel; }

    public int getId() { return id; }

    public String getName() { return name; }
}
