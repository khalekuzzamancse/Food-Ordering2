package com.example.foodordering;

import android.util.Log;

import com.example.foodordering.firebasetemplate.CallbackDocument;
import com.example.foodordering.firebasetemplate.DatabaseFetch;
import com.example.foodordering.firebasetemplate.FirebaseCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class UserData {
    public static String name = "";
    public static String email = "";
    public static String phone = "";
    public static String userType = "";
    public static String shopName = "";
    public static String shopLocation = "";
    public boolean isFetch = false;

    public void readData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String docId = user.getEmail();
            email = docId;
            new DatabaseFetch().getDocument("UserInfo", docId, new CallbackDocument() {
                @Override
                public void getDoc(HashMap<String, Object> dataMap) {
                    name = (String) dataMap.get("Name");
                    phone = (String) dataMap.get("PhoneNumber");
                    userType = (String) dataMap.get("UserType");
                    shopName = (String) dataMap.get("Shop Name");
                    shopLocation = (String) dataMap.get("Shop Location");
                    isFetch = true;
                   // Log.i("DataFetch", String.valueOf(dataMap));
                    if (userType.equals("Customer"))
                        Log.i("DataFetch", name + "," + phone + "->" + email);
                    else if (userType.equals("Shop Keeper"))
                        Log.i("DataFetch", name + "," + phone + "->" + "->" + email + "->" + "->" + shopName + "->" + shopLocation);
                }
            });
        }


//        if (user != null) {
//
//            FirebaseCustom db = new DatabaseFetch();
//            db.getDocument("UseInfo", docId, dataMap -> {
//                name = (String) dataMap.get("Name");
//                phone = (String) dataMap.get("PhoneNumber");
//                userType = (String) dataMap.get("UserType");
//                shopName = (String) dataMap.get("Shop Name");
//                shopLocation = (String) dataMap.get("Shop Location");
//                isFetch = true;
//              //  if (userType.equals("Customer")) {
//                    Log.i("DataFetch", name + "," + phone + "->" + "->" + email + "->");
//
//                //else  {
//                    Log.i("DataFetch", name + "," + phone + "->" + "->" + email + "->" + "->" + shopName + "->" + shopLocation);
//
//            });
//// (userType.equals("Shop Keeper"))
//        }


    }
}
