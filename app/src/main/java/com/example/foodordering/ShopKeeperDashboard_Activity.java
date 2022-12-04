package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShopKeeperDashboard_Activity extends AppCompatActivity {
    TextView shopName, location, name, email, phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_keeper_dashboard);
        shopName = findViewById(R.id.shopName);
        location = findViewById(R.id.location);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneNo = findViewById(R.id.phoneNo);
        shopName.setText(UserData.shopName);
        location.setText(" " + UserData.shopLocation);
        name.setText("Pro: " + UserData.name);
        email.setText("Email : " + UserData.email);
        phoneNo.setText("Phone : " + UserData.phone);

    }
}