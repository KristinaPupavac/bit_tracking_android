package ba.bitcamp.bittracking.bittrackingapplication.models;

/**
 * Created by Mladen13 on 25.10.2015.
 */
public class PostOffice {

    private Long id;

    private String name;

    private String address;

    public PostOffice(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
