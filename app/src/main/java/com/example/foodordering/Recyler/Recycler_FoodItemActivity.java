package com.example.foodordering.Recyler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;

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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

class View_Holder extends RecyclerView.ViewHolder {
    public TextView ItemName;
    public TextView ItemPrice;
    public ImageButton delete;
    public ImageView img;

    public View_Holder(@NonNull View view) {
        super(view);
        img = view.findViewById(R.id.FoodItemList_RecylerLayout_ImagVw);
        ItemName=view.findViewById(R.id.FoodItemList_RecylerLayout_ItemName);
        ItemPrice=view.findViewById(R.id.FoodItemList_RecylerLayout_Price);
    }
}

