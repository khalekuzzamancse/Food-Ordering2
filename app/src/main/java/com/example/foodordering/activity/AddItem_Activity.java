package com.example.foodordering.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;


import com.example.foodordering.R;
import com.example.foodordering.UserData;
import com.example.foodordering.firebasetemplate.CallbackUploadImage;
import com.example.foodordering.firebasetemplate.DatabaseFetch;
import com.example.foodordering.firebasetemplate.FirebaseCustom;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class AddItem_Activity extends AppCompatActivity {
    AppCompatButton upload;
    ImageView img;
    Uri imageUri;
    ProgressBar progressBar;
    EditText nameET, priceET, quantityET, subTypeET, descriptionET; //ET=Edit Text
    Spinner category;
    String categoryStr = "", docId = "", subType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);
        initializeView();

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
        upload = findViewById(R.id.upload);
        ///callback when upload complete
        CallbackUploadImage callback = new CallbackUploadImage() {
            @Override
            public void downloadLink(Uri link) {
                if (link != null) {
                    //before setting the data,we have to check if the item name
                    // and price field are empty or not
                    //this checking will avoid 2 problems
                    //1: if the name and price fields are empty
                     // 1.1->then the data will not set to the the database
                    //2:sometimes firebase write data more than one time(automatically),
                    //2.1-> this will causes problem such as if we clear the edittext filed
                    //2.2->then because of point 2,our currently pushed data will be replaced with
                    //2.3->empty data
                    if (!check())
                    {
                        setData(link);
                        Log.i("UploadedImage", String.valueOf(link));
                        progressBar.setVisibility(View.INVISIBLE);
                        img.setImageResource(R.drawable.vector);
                        showSnakbar("Uploaded Successfully");
                        clearEditText();
                    }



                } else
                    Log.i("UploadedImage", "fail");
            }
        };


        upload.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            docId = "khalekuzzaman91@gmail.com" + nameET.getText();
            custom.uploadImage("FoodImages", docId, imageUri, callback);

        });
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                categoryStr = (String) parent.getItemAtPosition(position);
                //Log.i("Spinner", s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void showSnakbar(String msg) {
        Snackbar snackbar = Snackbar
                .make(img, msg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
        snackbar.show();
    }


    void setData(Uri uri) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("Name", nameET.getText().toString());
        data.put("Price", priceET.getText().toString());
        data.put("Category", categoryStr);
        data.put("AvailableQuantity", quantityET.getText().toString());
        //checking subtype is available or not..
        subType = subTypeET.getText().toString();
        data.put("SubType", subType);
        //
        data.put("Description", descriptionET.getText().toString());
        data.put("URL", uri.toString());
        ///
        data.put("OwnerID", UserData.email);
        //shop name
        data.put("Shop Name", UserData.shopName);
        Log.i("DataChecked", String.valueOf(data));
        FirebaseCustom db = new DatabaseFetch();
        //  String docId = auth.getCurrentUser().getEmail();
        db.addDocument("FoodItem", docId, data);
        //refresh the activity
        finish();
        startActivity(getIntent());
    }

    private void initializeView() {
        img = findViewById(R.id.image);
        progressBar = findViewById(R.id.progressbar);
        nameET = findViewById(R.id.name);
        priceET = findViewById(R.id.price);
        quantityET = findViewById(R.id.quantity);
        category = findViewById(R.id.snipper);
        subTypeET = findViewById(R.id.itemSubType);
        descriptionET = findViewById(R.id.description);

    }
    private boolean check() {
        //subType can be empty,
        //the name and the price should not be empty
        String name = nameET.getText().toString();
        String price = priceET.getText().toString();
        if (name.equals("") || price.equals(""))
            return true;
        else
            return false;
    }
    private void clearEditText()
    {
        nameET.setText("");
        priceET.setText("");
        quantityET.setText("");
        subTypeET.setText("");
        descriptionET.setText("");

    }
}