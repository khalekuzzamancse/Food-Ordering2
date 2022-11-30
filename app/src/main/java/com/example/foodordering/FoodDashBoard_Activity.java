package com.example.foodordering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class FoodDashBoard_Activity extends AppCompatActivity {
    AppCompatButton food, drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_dash_board);
        food = findViewById(R.id.food);
        drink = findViewById(R.id.drink);
        CartActivityData.createData();
        ///
        Toolbar toolbar =findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Food Delivery App");
        //
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
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_item_for_non_home_activity_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            Log.i("MEnyHHH","clicked");
            startActivity(new Intent(this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}