package com.evan.autoscape.autoscape;

/**
 * Created by EvanOwns on 3/16/2015.
 */
public class Item {

    public int value;
    public int ID;
    public String name;
    public String examine;
    public int quantity;

    public Item(int value, int ID, String name, String examine, int quantity){
        this.value = value;
        this.ID = ID;
        this.name = name;
        this.examine = examine;
        this.quantity = quantity;
    }
}
