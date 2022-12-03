package com.example.foodordering.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.foodordering.R;
import com.example.foodordering.Recyler.FoodAdapter;
import com.example.foodordering.Recyler.Domain_FoodList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DrinkFood_Activity extends AppCompatActivity {
    // Recycler_FoodItemActivity adapter;
    List<Domain_FoodList> list;
    FoodAdapter adapter;
    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_food);

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

                    adapter = new FoodAdapter(DrinkFood_Activity.this, list);
                    RecyclerView r = findViewById(R.id.recylerView_FoodItemActivity);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DrinkFood_Activity.this);
                    r.setLayoutManager(linearLayoutManager);
                    r.setAdapter(adapter);
                }
            }

        };

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ref = db.collection("FoodItem");
        Task<QuerySnapshot> snapshotTask = ref.get();
        snapshotTask.addOnCompleteListener(callback);

//        //initializing list
//        list = new ArrayList<>();
//        list.add( new Domain_FoodList("Water", R.drawable.water, 20));
//        list.add( new Domain_FoodList("Juice", R.drawable.juice, 15));
//        list.add( new Domain_FoodList("Power", R.drawable.power, 35));
//        list.add( new Domain_FoodList("Cola Cola", R.drawable.coca_cola, 25));
//        list.add( new Domain_FoodList("Cola Cola", R.drawable.sprite, 15));
//
//        adapter = new Recycler_FoodItemActivity(DrinkFood_Activity.this, list);
//        RecyclerView r = findViewById(R.id.recylerView_FoodItemActivity);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
////        linearLayoutManager.setReverseLayout(true);
////        linearLayoutManager.setStackFromEnd(true);
//        r.setLayoutManager(linearLayoutManager);
//        r.setAdapter(adapter);
//        ImageView button = findViewById(R.id.cartB);
//        button.setOnClickListener(view -> {
//            startActivity(new Intent(this, Cart_Activity.class));
//        });
//
//        search = findViewById(R.id.searchView);
//        search.clearFocus();
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterList(newText);
//                return true;
//            }
//        });
//    }
//
//    private void filterList(String newText) {
//        List<Domain_FoodList> L = new ArrayList<>();
//        //L = list;
//        for (Domain_FoodList item : list) {
//            if (item.itmName.toLowerCase().contains(newText.toLowerCase())) {
//                L.add(item);
//            }
//        }
//        if (L.isEmpty()) {
//            Snackbar snackbar = Snackbar
//                    .make(search, "Not Found", Snackbar.LENGTH_LONG);
//            snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
//            snackbar.show();
//        }
//        else {
//            adapter.setFilterList(L);
//        }
//
    }
}