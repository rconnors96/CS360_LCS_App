package com.ryanconnors.cs360;

public class MenuItem {

    private String MENU_ID;
    private String TYPE;
    private String ITEM_NAME;
    private String SIZE;
    private double PRICE;

    public MenuItem(String menu_id, String type, String item_name, String size, double price) {
        MENU_ID = menu_id;
        TYPE = type;
        ITEM_NAME = item_name;
        SIZE = size;
        PRICE = price;
    }

    public void setMENU_ID(String MENU_ID) {
        this.MENU_ID = MENU_ID;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public void setSIZE(String SIZE) {
        this.SIZE = SIZE;
    }

    public void setPRICE(double PRICE) {
        this.PRICE = PRICE;
    }

    public String getMENU_ID() {
        return MENU_ID;
    }

    public String getTYPE() {
        return TYPE;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public String getSIZE() {
        return SIZE;
    }

    public double getPRICE() {
        return PRICE;
    }

}
