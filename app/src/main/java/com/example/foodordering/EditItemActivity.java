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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.foodordering.firebasetemplate.CallbackUploadImage;
import com.example.foodordering.firebasetemplate.DatabaseFetch;
import com.example.foodordering.firebasetemplate.FirebaseCustom;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class EditItemActivity extends AppCompatActivity {

    EditText namE, pricE, quantity, subTypeET, descriptionET; //ET=Edit Text
    ProgressBar progressBar;
    ImageView img;
    Uri imageUri;
    AppCompatButton update;
    String docId = "", name, price, link, subType = "", description, categoryStr = "";
    Spinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item_activity);
        initializeView();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        link = intent.getStringExtra("link");
        subType = intent.getStringExtra("subType");
        description = intent.getStringExtra("description");

        Log.i("DataRecieved", name + " " + price + " " + link);
        namE.setText(name);
        pricE.setText(price);
        subTypeET.setText(subType);
        descriptionET.setText(description);
        Glide.with(img.getContext()).load(Uri.parse(link)).into(img);


        ActivityResultLauncher<String> resultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        imageUri = result;
                        if (imageUri != null)
                            // we checked the condition,if user not  select a image from gallery
                            //user do not want to change the photo
                            //so we have to use the sent link  form shop keeper dashboard
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
            if (imageUri != null)
                //if the customer do not want to change the item image
                custom.uploadImage("FoodImages", docId, imageUri, callback);
            else {
                setData(Uri.parse(link));

                showSnakbar("Updated Successfully");

            }


        });

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                categoryStr = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    // <-----------methods start--------------->
    // <-----------methods start--------------->

    void setData(Uri uri) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("Name", namE.getText().toString());
        data.put("Price", pricE.getText().toString());
        data.put("Category", categoryStr);
        data.put("SubType", subTypeET.getText().toString());
        data.put("Available Quantity", quantity.getText().toString());
        data.put("Description", descriptionET.getText().toString());
        data.put("OwnerID", UserData.email);
        data.put("Shop Name", UserData.shopName);

        //if uri is null,means shop keeper do not want to update the item image
        if (uri != null)
            data.put("URL", uri.toString());
        else
            data.put("URL", link);

        //shopname

        ///
        FirebaseCustom db = new DatabaseFetch();
        //  String docId = auth.getCurrentUser().getEmail();
        db.addDocument("FoodItem", docId, data);
        //refresh the activity
         finish();
        startActivity(getIntent());

    }

    void showSnakbar(String msg) {
        Snackbar snackbar = Snackbar
                .make(img, msg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
        snackbar.show();
    }

    void initializeView() {
        progressBar = findViewById(R.id.progressbar);
        namE = findViewById(R.id.name);
        pricE = findViewById(R.id.price);
        img = findViewById(R.id.image);
        update = findViewById(R.id.upload);
        quantity = findViewById(R.id.quantity);
        subTypeET = findViewById(R.id.itemSubType);
        descriptionET = findViewById(R.id.description);
        category = findViewById(R.id.snipper);
    }

}