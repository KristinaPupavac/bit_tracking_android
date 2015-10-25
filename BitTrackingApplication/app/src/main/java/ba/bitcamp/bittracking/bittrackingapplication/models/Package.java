package ba.bitcamp.bittracking.bittrackingapplication.models;

import java.util.List;

/**
 * Created by Kristina Pupavac on 10/18/2015.
 */
public class Package {

    public static final int READY_FOR_SHIPPING = 1;
    public static  final int ON_ROUTE = 2;
    public static final int OUT_FOR_DELIVERY = 3;
    public static final int DELIVERED = 4;
    public static final int RECEIVED = 5;

    public Long id;

    public String trackingNum;

    public String recipientAddress;

    public String destination;

    public Double weight;

    public Double price;

    public String packageType;

    public String senderName;

    public String recipientName;

    public Integer status;

    public Package (){

    }

    public String getTrackingNum() {
        return trackingNum;
    }

    public void setTrackingNum(String trackingNum) {
        this.trackingNum = trackingNum;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Package(String recipientAddress, Double weight, String packageType, String recipientName) {
        this.recipientAddress = recipientAddress;
        this.weight = weight;
        this.packageType = packageType;
        this.recipientName = recipientName;
    }

    public String getStatusName(){
        if (status == 1) {
            return "READY FOR SHIPPING";
        } else if (status == 2){
            return "ON ROUTE";
        } else if (status == 3) {
            return "OUT FOR DELIVERY";
        } else if (status == 4) {
            return "DELIVERED";
        } else if (status == 5) {
            return "RECEIVED";
        }
        return null;
    }

}
