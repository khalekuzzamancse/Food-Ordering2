package com.example.foodordering;

import java.util.ArrayList;
import java.util.List;

public class CartActivityData {
    public static List<String> foodList;
    public static List<Integer> priceList;
    public static List<Integer> selectedItem;
    private static CartActivityData dataObject;
    public static int totalCost=0;


    private CartActivityData() {
        foodList = new ArrayList<>();
        priceList = new ArrayList<>();
        selectedItem = new ArrayList<>();
        foodList.add(0, "Burger");
        foodList.add(1, "Pizza");
        foodList.add(2, "Bread");
        foodList.add(3, "Food");
        foodList.add(4, "Food");
        foodList.add(5, "Coca Cola");
        foodList.add(6, "Power");
        foodList.add(7, "Sprite");
        foodList.add(8, "Juice");
        foodList.add(9, "Water");
        //initializing price list
        priceList.add(0, 60);
        priceList.add(1, 350);
        priceList.add(2, 50);
        priceList.add(3, 100);
        priceList.add(4, 120);
        priceList.add(5, 55);
        priceList.add(6, 45);
        priceList.add(7, 25);
        priceList.add(8, 15);
        priceList.add(9, 20);

        //initializing selectedItems
        for (int i = 0; i < 9; i++)
            selectedItem.add(i, 0);
    }

    public static void createData() {
        if (dataObject == null)
            new CartActivityData();

    }

}
