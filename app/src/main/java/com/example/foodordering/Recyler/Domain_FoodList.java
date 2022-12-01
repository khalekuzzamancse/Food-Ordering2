package com.example.foodordering.Recyler;

public class Domain_FoodList {
    public String itmName;
    public Integer imgId;
    public Integer price;
    public Integer quantity;
    public boolean isCartActivity = false;

    public Domain_FoodList(String itmName, Integer imgId, Integer price) {
        this.itmName = itmName;
        this.imgId = imgId;
        this.price = price;
    }

    public Domain_FoodList(String itmName, Integer imgId, Integer price, boolean isCartActivity) {
        this.itmName = itmName;
        this.imgId = imgId;
        this.price = price;
        this.isCartActivity = isCartActivity;
    }
}
