package com.example.foodordering.activity;


import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;

public class VH_OrderActivity extends RecyclerView.ViewHolder {
    public TextView TextView_ViewHolder_DragName;
    public EditText DropDown;
    public ImageButton delete;

    public VH_OrderActivity(@NonNull View itemView) {
        super(itemView);
        TextView_ViewHolder_DragName = itemView.findViewById(R.id.DragNameOrder);
        DropDown=itemView.findViewById(R.id.AmountRecyler);
        delete=itemView.findViewById(R.id.deleteItemOrder);

    }


}
