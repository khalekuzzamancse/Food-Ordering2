package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodordering.Recyler.Domain_FoodList;
import com.example.foodordering.Recyler.Recycler_FoodItemActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Cart_Activity extends AppCompatActivity {
    AdapteRecyler_OrderActivity adapter2;
    List<DataType_OrderList> orderList;
    AppCompatButton button;
    List<Domain_FoodList> list;
    Recycler_FoodItemActivity adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
//        orderList = new ArrayList<>();
//        for (int i = 0; i < 9; i++) {
//            if (CartActivityData.selectedItem.get(i) > 0) {
//                DataType_OrderList l = new DataType_OrderList();
//                l.itemName = CartActivityData.foodList.get(i);
//                l.quantity = String.valueOf(CartActivityData.selectedItem.get(i));
//                l.totalPrice = String.valueOf(CartActivityData.priceList.get(i) * CartActivityData.selectedItem.get(i));
//                orderList.add(l);
//            }
//
//        }
//        adapter2 = new AdapteRecyler_OrderActivity(Cart_Activity.this, orderList);
//        RecyclerView r = findViewById(R.id.recycler);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
//        r.setLayoutManager(linearLayoutManager);
//        r.setAdapter(adapter2);
        //
        list = new ArrayList<>();
        list.add(0, new Domain_FoodList("Burger", R.drawable.burger, 60,true));
        list.add(1, new Domain_FoodList("Pizza", R.drawable.pizza, 350,true));

        adapter = new Recycler_FoodItemActivity(Cart_Activity.this, list);
        RecyclerView r = findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        r.setLayoutManager(linearLayoutManager);
        r.setAdapter(adapter);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialouge);
        button = findViewById(R.id.orderNow);
        button.setOnClickListener(view -> {
//            TextView tv=findViewById(R.id.needToPay);
//            tv.setText("H");

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum = sum + (CartActivityData.priceList.get(i) * CartActivityData.selectedItem.get(i));
                // Log.i("TotalCost", String.valueOf(sum)+"\n");
                String s = String.valueOf(sum);
                //  button.setText(s);

            }
            TextView tv = dialog.findViewById(R.id.needToPay);
            String s = String.valueOf(sum);
            tv.setText("Need To Pay : " + s + " Tk.");

            // Log.i("TotalPrice", String.valueOf(CartActivityData.totalCost)+"->");
        });
        Button submit = dialog.findViewById(R.id.dialoge_btn_1);
        Button cancel = dialog.findViewById(R.id.dialoge_btn_2);
        submit.setOnClickListener(view -> {
            dialog.dismiss();
        });
        cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

}