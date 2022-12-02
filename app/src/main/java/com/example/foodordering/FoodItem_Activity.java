package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.foodordering.Recyler.Domain_FoodList;
import com.example.foodordering.Recyler.Recycler_FoodItemActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodItem_Activity extends AppCompatActivity {
    Recycler_FoodItemActivity adapter;
    List<Domain_FoodList> list;
    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);
        //initializing list
        list = new ArrayList<>();
        list.add(0, new Domain_FoodList("Burger", R.drawable.burger, 60));
        list.add(1, new Domain_FoodList("Pizza", R.drawable.pizza, 350));
        list.add(2, new Domain_FoodList("Bread", R.drawable.bread, 50));
        list.add(3, new Domain_FoodList("Food", R.drawable.food_4, 150));
        list.add(4, new Domain_FoodList("Food", R.drawable.food_5, 100));
        list.add(5, new Domain_FoodList("Water", R.drawable.water, 20));
        list.add(6, new Domain_FoodList("Juice", R.drawable.juice, 15));

        adapter = new Recycler_FoodItemActivity(FoodItem_Activity.this, list);
        RecyclerView r = findViewById(R.id.recylerView_FoodItemActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        r.setLayoutManager(linearLayoutManager);
        r.setAdapter(adapter);
        ImageView button = findViewById(R.id.cartB);
        button.setOnClickListener(view -> {
            startActivity(new Intent(this, Cart_Activity.class));
        });

        search = findViewById(R.id.searchView);
        search.clearFocus();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String newText) {
        List<Domain_FoodList> L = new ArrayList<>();
        //L = list;
        for (Domain_FoodList item : list) {
            if (item.itmName.toLowerCase().contains(newText.toLowerCase())) {
                L.add(item);
            }
        }
        if (L.isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(search, "Not Found", Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
            snackbar.show();
        }
        else {
            adapter.setFilterList(L);
        }
    }
}