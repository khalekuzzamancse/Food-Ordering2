package com.example.foodordering;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.activity.DataType_OrderList;
import com.example.foodordering.activity.VH_OrderActivity;

import java.util.List;

public class AdapteRecyler_OrderActivity extends RecyclerView.Adapter<VH_OrderActivity> {
    Context context;
    List<DataType_OrderList> list;

    public AdapteRecyler_OrderActivity(Context context, List<DataType_OrderList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH_OrderActivity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewOfRecyclerLayout = LayoutInflater.from(context).
                inflate(R.layout.cart_recycler_layout, parent, false);
        VH_OrderActivity viewHolder = new VH_OrderActivity(viewOfRecyclerLayout);

        viewHolder.delete.setOnClickListener(view -> {
            int pos = viewHolder.getAdapterPosition();
            CartActivityData.selectedItem.set(pos, 0);
//            Log.i("TotalCost", String.valueOf(CartActivityData.totalCost));
            list.remove(pos);
            notifyItemRemoved(pos);
            Log.i("ListSize", String.valueOf(list.size()));

        });


        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull VH_OrderActivity holder, int position) {
        holder.TextView_ViewHolder_DragName.setText(list.get(position).itemName);
        holder.DropDown.setText("Quantity = " + list.get(position).quantity + " ,Total Price = " + list.get(position).totalPrice + " Tk.");
        // CartActivityData.totalCost= CartActivityData.totalCost+CartActivityData.priceList.get(position)*CartActivityData.selectedItem.get(position);
        String totalItems = list.get(position).quantity;
        String itemPrice = list.get(position).itemPrice;
        // Log.i("TotalCost", String.valueOf(CartActivityData.totalCost));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
