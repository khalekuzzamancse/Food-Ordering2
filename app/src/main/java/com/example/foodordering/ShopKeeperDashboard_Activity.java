package com.example.foodordering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.foodordering.Recyler.Domain_FoodList;
import com.example.foodordering.Recyler.FoodAdapter;
import com.example.foodordering.activity.FoodItem_Activity;
import com.example.foodordering.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShopKeeperDashboard_Activity extends AppCompatActivity {
    TextView shopName, location, name, email, phoneNo;
    List<Domain_FoodList> list;
    FoodAdapter adapter;
    SearchView search;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Food Delivery App");

        list = new ArrayList<>();

        OnCompleteListener<QuerySnapshot> callback = new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.i("Getted", document.getId() + " => " + document.getData());
                        String itemName = (String) document.get("Name");
                        String shopName = (String) document.get("Shop Name");
                        String price = (String) document.get("Price");
                        String url = (String) document.get("URL");
                        list.add(new Domain_FoodList(itemName, Integer.parseInt(price), shopName, url));
                    }

                    adapter = new FoodAdapter(ShopKeeperDashboard_Activity.this, list);
                    //
                    adapter.isShopkeeperActivity = true;
                    RecyclerView r = findViewById(R.id.recycler);
                    GridLayoutManager manager = new GridLayoutManager(ShopKeeperDashboard_Activity.this, 2);
                    r.setLayoutManager(manager);
                    r.setAdapter(adapter);
                }
            }

        };

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ref = db.collection("FoodItem");
        Task<QuerySnapshot> snapshotTask = ref.get();
        snapshotTask.addOnCompleteListener(callback);


    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addItem) {
            startActivity(new Intent(this, UploadImage.class));

        }
        return super.onOptionsItemSelected(item);
    }


}