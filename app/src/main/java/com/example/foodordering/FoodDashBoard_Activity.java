package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class FoodDashBoard_Activity extends AppCompatActivity {
    AppCompatButton food, drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_dash_board);
        food = findViewById(R.id.food);
        drink = findViewById(R.id.drink);
        CartActivityData.createData();
        Log.i("HHHHHHH", "started");
        food.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, new FoodList_1_Fragment());
            ft.commit();
        });
        drink.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, new DrinkFoodFragment());
            ft.commit();
        });
        ImageView view = findViewById(R.id.food_cart);
        view.setOnClickListener(view1 -> {
            startActivity(new Intent(this,Cart_Activity.class));
        });

    }
}