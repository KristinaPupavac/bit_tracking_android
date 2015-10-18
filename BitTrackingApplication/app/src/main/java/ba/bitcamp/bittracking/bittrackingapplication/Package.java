package ba.bitcamp.bittracking.bittrackingapplication;

import java.util.UUID;

/**
 * Created by Kristina Pupavac on 10/18/2015.
 */
public class Package {
    public static final int READY_FOR_SHIPPING = 1;
    public static  final int ON_ROUTE = 2;
    public static final int OUT_FOR_DELIVERY = 3;
    public static final int DELIVERED = 4;
    public static final int RECEIVED = 5;

    private UUID token;
    private int status;

    public Package (){

    }

    public Package(int status){
        this.token =  UUID.randomUUID();
        this.status = status;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
