package com.example.foodordering;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.foodordering.firebasetemplate.CallbackUploadImage;
import com.example.foodordering.firebasetemplate.DatabaseFetch;
import com.example.foodordering.firebasetemplate.FirebaseCustom;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class EditItemActivity extends AppCompatActivity {
    String name, price, link;
    EditText namE, pricE,quantity;
    ImageView img;
    Uri imageUri;
    AppCompatButton update;
    String docId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item_activity);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        link = intent.getStringExtra("link");
        Log.i("DataRecieved", name + " " + price + " " + link);
        namE = findViewById(R.id.name);
        pricE = findViewById(R.id.price);
        namE.setText(name);
        pricE.setText(price);
        img = findViewById(R.id.image);
        update=findViewById(R.id.upload);
        quantity=findViewById(R.id.quantity);
        Glide.with(img.getContext()).load(Uri.parse(link)).into(img);


        ActivityResultLauncher<String> resultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        imageUri = result;
                        img.setImageURI(result);
                    }
                });
        img.setOnClickListener(view -> {
            resultLauncher.launch("image/*");

        });
        FirebaseCustom custom = new DatabaseFetch();
        ///callback when upload complete
        CallbackUploadImage callback = new CallbackUploadImage() {
            @Override
            public void downloadLink(Uri link) {
                if (link != null) {
                    setData(link);
                    Log.i("UploadedImage", String.valueOf(link));
                  //  progressBar.setVisibility(View.INVISIBLE);
                    img.setImageResource(R.drawable.vector);
                    showSnakbar("Uploaded Successfully");


                } else
                    Log.i("UploadedImage", "fail");
            }
        };
        update.setOnClickListener(view -> {
           // progressBar.setVisibility(View.VISIBLE);
            docId = UserData.email + name;
            custom.uploadImage("FoodImages", docId, imageUri, callback);

        });



    }


    void setData(Uri uri) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("Name", namE.getText().toString());
        data.put("Price", pricE.getText().toString());
        data.put("Type","Normal");
        data.put("Available Quantity",quantity.getText().toString());
        data.put("URL", uri.toString());
        ///
        data.put("OwnerID",UserData.email);
        //shopname
        data.put("Shop Name",UserData.shopName);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        //  data.put("Owner", );
        FirebaseCustom db = new DatabaseFetch();
        //  String docId = auth.getCurrentUser().getEmail();
        db.addDocument("FoodItem", docId, data);
        //  clearView();
    }
    void showSnakbar(String msg) {
        Snackbar snackbar = Snackbar
                .make(img, msg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
        snackbar.show();
    }

}