package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.foodordering.Recyler.Domain_FoodList;
import com.example.foodordering.Recyler.Recycler_FoodItemActivity;

import java.util.ArrayList;
import java.util.List;

public class FoodItem_Activity extends AppCompatActivity {
    Recycler_FoodItemActivity adapter;
    List<Domain_FoodList> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);

        //initializing list
        list = new ArrayList<>();
        list.add(0, new Domain_FoodList("Burger", R.drawable.burger, 60));
        adapter = new Recycler_FoodItemActivity(FoodItem_Activity.this, list);
        RecyclerView r = findViewById(R.id.recylerView_FoodItemActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        r.setLayoutManager(linearLayoutManager);
        r.setAdapter(adapter);
    }
}