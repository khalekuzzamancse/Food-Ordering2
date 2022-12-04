package com.example.foodordering.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodordering.activity.EditItemActivity;
import com.example.foodordering.R;
import com.example.foodordering.activity.Cart_Activity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.Viewholder> {
    Context context;
    List<Domain_FoodList> list;
    public boolean isShopkeeperActivity = false;

    public FoodAdapter(Context context, List<Domain_FoodList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.recycler_layout, parent, false);
        Viewholder vh = new Viewholder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int pos) {

        // 1:if the item subtype id available
        // 2:then we want to show the subtype with the name
        String name = list.get(pos).itmName;
        String subtype = list.get(pos).subType;
        if (!subtype.equals(""))
            name = name + " (" + subtype + " )";
        //
        holder.ItemName.setText(name);
        //
        String price = String.valueOf(list.get(pos).price) + " Tk.";
        holder.ItemPrice.setText(price);
        holder.shopName.setText(list.get(pos).shopName);
        Glide.with(holder.img.getContext()).load(Uri.parse(list.get(pos).imgUrl)).into(holder.img);

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
        // if shop keeper want to edit item details so that he click on a item
        //from the dashboard activity
        holder.container.setOnClickListener(view -> {
            if (isShopkeeperActivity) {
                Intent intent = new Intent(context, EditItemActivity.class);
                intent.putExtra("name", list.get(pos).itmName);
                intent.putExtra("price", String.valueOf(list.get(pos).price));
                intent.putExtra("link", list.get(pos).imgUrl);
                intent.putExtra("subType", list.get(pos).subType);
                intent.putExtra("description", list.get(pos).description);
                context.startActivity(intent);
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
        //if the user click on the image,then product details will be shown
        holder.img.setOnClickListener(view -> {
            //if the user is a customer
            //then we show the description dialog
            //a custom never can access the  isShopkeeperActivity
            //if  isShopkeeperActivity==false that means the user is not in  isShopkeeperActivity
            //only the shop keeper can access  isShopkeeperActivity
            if (!isShopkeeperActivity)
                showDialoge(list.get(pos).description);
            else {
                Intent intent = new Intent(context, EditItemActivity.class);
                intent.putExtra("name", list.get(pos).itmName);
                intent.putExtra("price", String.valueOf(list.get(pos).price));
                intent.putExtra("link", list.get(pos).imgUrl);
                intent.putExtra("subType", list.get(pos).subType);
                intent.putExtra("description", list.get(pos).description);
                context.startActivity(intent);
            }


        });

        //

        ///
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
        //if this adapter is used by the shopkeeper dashboard,then hide some unwanted view
        if (isShopkeeperActivity) {
            holder.spinner.setVisibility(View.GONE);
            holder.shopName.setVisibility(View.GONE);
            holder.tot_price.setVisibility(View.GONE);
            holder.add.setVisibility(View.GONE);
        }
    }

    void showSnapbar(Viewholder holder, String msg) {
        Snackbar snackbar = Snackbar
                .make(holder.ItemPrice, msg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.purple_500));
        snackbar.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setFilterList(List<Domain_FoodList> L) {
        this.list = L;
        notifyDataSetChanged();
    }

    void showDialoge(String des) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.diagloug_description);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView tv = dialog.findViewById(R.id.description);
        tv.setText(des);
        Button ok = dialog.findViewById(R.id.ok);
        ok.setOnClickListener(view -> {
            dialog.dismiss();
        });

    }

    ///
    ////
    // <----------ViewHolder class ------------>
    public class Viewholder extends RecyclerView.ViewHolder {

        public TextView ItemName, ItemPrice, tot_price, quantity, shopName;
        public ImageButton delete;
        public ImageButton add;
        public ImageView img;
        public Spinner spinner;
        LinearLayout container;

        public Viewholder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.FoodItemList_RecylerLayout_ImagVw);
            ItemName = view.findViewById(R.id.FoodItemList_RecylerLayout_ItemName);
            ItemPrice = view.findViewById(R.id.FoodItemList_RecylerLayout_Price);
            spinner = view.findViewById(R.id.spinner);
            delete = view.findViewById(R.id.delete);
            add = view.findViewById(R.id.addToCart);
            quantity = view.findViewById(R.id.quantity);
            tot_price = view.findViewById(R.id.tot_price);
            shopName = view.findViewById(R.id.shopName);
            container = view.findViewById(R.id.container);

        }

    }
}
