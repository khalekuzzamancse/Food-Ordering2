package com.example.foodordering.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.foodordering.CartActivityData;
import com.example.foodordering.R;
import com.google.firebase.auth.FirebaseAuth;

public class FoodDashBoard_Activity extends AppCompatActivity {
    AppCompatButton food, drink;
    ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_dash_board);
        food = findViewById(R.id.food);
        drink = findViewById(R.id.drink);
        CartActivityData.createData();
        ///

        Toolbar toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Food Delivery App");
        flipper = findViewById(R.id.container);
        int imgArr[] = {R.drawable.coca_cola, R.drawable.pizza,
                R.drawable.juice, R.drawable.fast_food_2, R.drawable.water, R.drawable.power,
                R.drawable.fast_food_1
                , R.drawable.fast_food_3};
        for (int i = 0; i < imgArr.length; i++) {
            showImage(imgArr[i], i);

        }


        Log.i("HHHHHHH", "started");
        food.setOnClickListener(view -> {
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.replace(R.id.container, new FoodList_1_Fragment());
//            ft.commit();
            startActivity(new Intent(this, FoodItem_Activity.class));
        });
        drink.setOnClickListener(view -> {
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.replace(R.id.container, new DrinkFoodFragment());
//            ft.commit();
            startActivity(new Intent(this, DrinkFood_Activity.class));
        });

        ImageView view = findViewById(R.id.food_cart);
        view.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Cart_Activity.class));
        });

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_item_for_non_home_activity_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Log.i("MEnyHHH", "clicked");
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    void showImage(int img, int i) {

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(img);
        flipper.addView(imageView);
        flipper.setFlipInterval(2000);
        flipper.setAutoStart(true);
        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);


    }
}