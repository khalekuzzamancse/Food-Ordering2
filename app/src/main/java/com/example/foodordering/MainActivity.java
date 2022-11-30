package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CartActivityData.createData();
       startActivity(new Intent(this,FoodDashBoard_Activity.class));
       // setContentView(R.layout.activity_food_dash_board);
      //  setContentView(R.layout.fragment_food_list_1_);


    }
}