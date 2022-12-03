package com.example.foodordering.activity;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.foodordering.CartActivityData;
import com.example.foodordering.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodList_1_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodList_1_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FoodList_1_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodList_1_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodList_1_Fragment newInstance(String param1, String param2) {
        FoodList_1_Fragment fragment = new FoodList_1_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_food_list_1_, container, false);
        AppCompatButton f1, f2, f3, f4, f5;
        EditText e1, e2, e3, e4, e5;
        f1 = view.findViewById(R.id.btn_foodItem_1);
        f2 = view.findViewById(R.id.btn_foodItem_2);
        f3 = view.findViewById(R.id.btn_foodItem_3);
        f4 = view.findViewById(R.id.btn_foodItem_4);
        f5 = view.findViewById(R.id.btn_foodItem_5);
        //
        e1 = view.findViewById(R.id.edtTxt_foodItem_1);
        e2 = view.findViewById(R.id.edtTxt_foodItem_2);
        e3 = view.findViewById(R.id.edtTxt_foodItem_3);
        e4 = view.findViewById(R.id.edtTxt_foodItem_4);
        e5 = view.findViewById(R.id.edtTxt_foodItem_5);
        f1.setOnClickListener(view1 -> {
            int quantity = 0;
            String s = e1.getText().toString().trim();
            if (!s.isEmpty())
                quantity = Integer.parseInt(s);
            CartActivityData.selectedItem.set(0, quantity);
            showUpdate();
        });
        f2.setOnClickListener(view1 -> {
            int quantity = 0;
            String s = e2.getText().toString().trim();
            if (!s.isEmpty())
                quantity = Integer.parseInt(s);
            CartActivityData.selectedItem.set(1, quantity);
            showUpdate();
        });
        f3.setOnClickListener(view1 -> {
            int quantity = 0;
            String s = e3.getText().toString().trim();
            if (!s.isEmpty())
                quantity = Integer.parseInt(s);
            CartActivityData.selectedItem.set(2, quantity);
            showUpdate();

        });
        f4.setOnClickListener(view1 -> {
            int quantity = 0;
            String s = e4.getText().toString().trim();
            if (!s.isEmpty())
                quantity = Integer.parseInt(s);
            CartActivityData.selectedItem.set(3, quantity);
            showUpdate();
        });
        f5.setOnClickListener(view1 -> {
            int quantity = 0;
            String s = e5.getText().toString().trim();
            if (!s.isEmpty())
                quantity = Integer.parseInt(s);
            CartActivityData.selectedItem.set(4, quantity);
            showUpdate();
        });


        return view;
    }

    private void showUpdate() {
        Log.i("DataList", String.valueOf(CartActivityData.foodList));
        Log.i("DataList", String.valueOf(CartActivityData.priceList));
        Log.i("DataList", String.valueOf(CartActivityData.selectedItem));
    }
}