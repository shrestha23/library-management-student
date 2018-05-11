package shrestha.shaw.librarymanagementstudent;

public class User{

    private String acesslevel;
    private long id;
    private String stuname;

    public User() {
    }

    public User(String acesslevel, long id, String stuname) {
        this.acesslevel = acesslevel;
        this.id = id;
        this.stuname = stuname;
    }

    public String getAcesslevel() {
        return acesslevel;
    }

    public long getId() {
        return id;
    }

    public String getStuname() {
        return stuname;
    }
}

/*
public class User{

    private String acesslevel;
    private  long id;
    private String name;

    public User() {
    }

    public User(String acesslevel, long id, String name) {
        this.acesslevel = acesslevel;
        this.id = id;
        this.name = name;
    }

    public String getAcesslevel() {
        return acesslevel;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
*/
