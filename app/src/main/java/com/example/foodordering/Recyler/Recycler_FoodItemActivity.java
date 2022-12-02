package com.example.foodordering.Recyler;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.CartActivityData;
import com.example.foodordering.Cart_Activity;
import com.example.foodordering.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class Recycler_FoodItemActivity extends RecyclerView.Adapter<View_Holder> {
    Context context;
    List<Domain_FoodList> list;

    public Recycler_FoodItemActivity(Context context, List<Domain_FoodList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.food_item_list_recyler_layout, parent, false);
        View_Holder vh = new View_Holder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int pos) {
        holder.img.setImageResource(list.get(pos).imgId);
        holder.ItemName.setText(list.get(pos).itmName);
        String price = String.valueOf(list.get(pos).price) + " Tk.";
        holder.ItemPrice.setText(price);
        //if is cart activity


        holder.add.setOnClickListener(view -> {
            TextView textView = (TextView) holder.spinner.getSelectedView();
            String s = textView.getText().toString();
            if (s.equals("Quantity")) {
                showSnapbar(holder, "Please,select  Quantity");
            } else {
                Domain_FoodList d = list.get(pos);
                d.isCartActivity = true;
                d.quantity = Integer.parseInt(s);
                Cart_Activity.list.add(d);
                showSnapbar(holder, "Added To Cart");
            }

        });
        holder.delete.setOnClickListener(view -> {
            // int pos = viewHolder.getAdapterPosition();
            list.remove(pos);
            notifyItemRemoved(pos);
            showSnapbar(holder, "Deleted Successfully");
        });
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = new String();
                s = (String) parent.getItemAtPosition(position);
                Log.i("Spinner", s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////
        if (list.get(pos).isCartActivity == true) {
            holder.add.setVisibility(View.GONE);
            holder.spinner.setSelection(list.get(pos).quantity);
            holder.spinner.setVisibility(View.GONE);
            holder.delete.setVisibility(View.VISIBLE);
            String quantity = String.valueOf(list.get(pos).quantity);
            holder.quantity.setText("Quantity : " + quantity);
            String tot = String.valueOf(list.get(pos).price * list.get(pos).quantity);
            holder.tot_price.setText("Total Price : " + tot + " Tk.");

        } else {
            holder.delete.setVisibility(View.GONE);
            holder.tot_price.setVisibility(View.GONE);
            holder.quantity.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void showSnapbar(View_Holder holder, String msg) {
        Snackbar snackbar = Snackbar
                .make(holder.ItemPrice, msg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.purple_500));
        snackbar.show();
    }

    public void setFilterList(List<Domain_FoodList> L) {
        this.list = L;
        notifyDataSetChanged();
    }


}

class View_Holder extends RecyclerView.ViewHolder {
    public TextView ItemName, ItemPrice, tot_price, quantity;
    public ImageButton delete;
    public ImageButton add;
    public ImageView img;
    public Spinner spinner;


    public View_Holder(@NonNull View view) {
        super(view);
        img = view.findViewById(R.id.FoodItemList_RecylerLayout_ImagVw);
        ItemName = view.findViewById(R.id.FoodItemList_RecylerLayout_ItemName);
        ItemPrice = view.findViewById(R.id.FoodItemList_RecylerLayout_Price);
        spinner = view.findViewById(R.id.spinner);
        delete = view.findViewById(R.id.delete);
        add = view.findViewById(R.id.addToCart);
        quantity = view.findViewById(R.id.quantity);
        tot_price = view.findViewById(R.id.tot_price);
    }
}

