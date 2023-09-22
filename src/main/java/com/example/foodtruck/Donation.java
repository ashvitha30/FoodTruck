package com.example.foodtruck;

public class Donation {
    private String itemName;
    private String itemType;
    private int itemQuantity;
    private String itemAddress;
    private String ngoName;

    public Donation(String itemName, String itemType, int itemQuantity, String itemAddress, String ngoName) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemQuantity = itemQuantity;
        this.itemAddress = itemAddress;
        this.ngoName = ngoName;
    }

    public String getItemName() {
        return itemName;
    }


    public String getItemType() {
        return itemType;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public String getItemAddress() {
        return itemAddress;
    }
    public String getNgoName() {return ngoName;}
}


