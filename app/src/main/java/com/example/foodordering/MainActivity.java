package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CartActivityData.createData();
       //startActivity(new Intent(this,FoodDashBoard_Activity.class));
       // setContentView(R.layout.activity_food_dash_board);
      //  setContentView(R.layout.fragment_food_list_1_);
        if (user != null)
            startActivity(new Intent(this,FoodDashBoard_Activity.class));
       else
           startActivity(new Intent(this,LoginActivity.class));

    }
}