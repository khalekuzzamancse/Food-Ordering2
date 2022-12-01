package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    private static final long SPLASH_TIMER = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        CartActivityData.createData();
        //startActivity(new Intent(this,FoodDashBoard_Activity.class));
        // setContentView(R.layout.activity_food_dash_board);
        //  setContentView(R.layout.fragment_food_list_1_);
        startActivity(new Intent(this,FoodItem_Activity.class));

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (user != null)
//                    startActivity(new Intent(MainActivity.this, FoodDashBoard_Activity.class));
//                else
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                finish();
//            }
//        }, SPLASH_TIMER);


    }
}