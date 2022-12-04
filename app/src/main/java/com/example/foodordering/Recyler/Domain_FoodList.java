package com.example.foodordering.Recyler;

public class Domain_FoodList {
    public String itmName, shopName, imgUrl,subType,description;
    public Integer imgId;
    public Integer price;
    public Integer quantity;
    public boolean isCartActivity = false;



    public Domain_FoodList(String itmName, Integer price, String shopName, String imgUrl,String subType,String description) {
        this.itmName = itmName;
        this.price = price;
        this.shopName = shopName;
        this.imgUrl = imgUrl;
        this.subType=subType;
        this.description=description;
    }

}
