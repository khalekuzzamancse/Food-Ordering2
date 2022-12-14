package com.example.foodordering.activity;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.foodordering.R;
import com.example.foodordering.UserData;
import com.example.foodordering.firebasetemplate.CallbackUploadImage;
import com.example.foodordering.firebasetemplate.DatabaseFetch;
import com.example.foodordering.firebasetemplate.FirebaseCustom;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class EditItemActivity extends AppCompatActivity {

    EditText namE, pricE, quantityET, subTypeET, descriptionET; //ET=Edit Text
    ProgressBar progressBar;
    ImageView img;
    Uri imageUri;
    AppCompatButton update, deleteBT;
    String docId = "", name = "", price = "", link = "", subType = "", description = "", categoryStr = "", quantity = "";
    String categories[]={"Normal","Fast Food","Drink"};
    AutoCompleteTextView dropdownTv;
    ArrayAdapter<String> dropDownAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item_activity);
        initializeView();
        //setting the category
        dropDownAdapter=new ArrayAdapter<String>(this,R.layout.drop_down_item_layout,categories);
        dropdownTv.setAdapter(dropDownAdapter);

        //

        //getting the data from intent
        getIntentData();
        //setting the data after the getting the from intent
        setIntentData();

// crating a result launcher for picking image form gallery
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

        ///if the user click on the image to pick a new image
        img.setOnClickListener(view -> {
            resultLauncher.launch("image/*");

        });
        //creating the custom firebase class object to use it method
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
                    showSnackbar("Uploaded Successfully");


                } else
                    Log.i("UploadedImage", "fail");
            }
        };
        //when the user click on the update button
        update.setOnClickListener(view -> {
            // progressBar.setVisibility(View.VISIBLE);
            docId = UserData.email + name;
            if (imageUri != null)
                //if the customer do not want to change the item image
                custom.uploadImage("FoodImages", docId, imageUri, callback);
            else {
                setData(Uri.parse(link));
                showSnackbar("Updated Successfully");

            }

        });

        ///when the use click on the spinner
        dropdownTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                categoryStr = (String) parent.getItemAtPosition(position);
                Log.i("SelectedCategoty",categoryStr);
            }
        });
        deleteBT.setOnClickListener(view -> {
            String docId = UserData.email + name;
            new DatabaseFetch().deleteDocument("FoodItem", docId);
            onNavigateUp();
        });


    }


    // <-----------methods start--------------->
    // <-----------methods start--------------->

    void setData(Uri uri) {
        HashMap<String, Object> data = new HashMap<>();
        //updating the string,if use made changes
        name = namE.getText().toString();
        data.put("Name", name);
        //
        price = pricE.getText().toString();
        data.put("Price", price);
        //

        data.put("Category", categoryStr);
        //
        subType = subTypeET.getText().toString();
        data.put("SubType", subType);
        //
        quantity = quantityET.getText().toString();
        data.put("Available Quantity", quantity);
        //
        description = descriptionET.getText().toString();
        data.put("Description", description);
        ///fetching the user info form the UserData class
        data.put("OwnerID", UserData.email);
        data.put("Shop Name", UserData.shopName);

        //if uri is null,means shop keeper do not want to update the item image
        if (uri != null)
            data.put("URL", uri.toString());
        else
            data.put("URL", link);


        ///
        FirebaseCustom db = new DatabaseFetch();
        //  String docId = auth.getCurrentUser().getEmail();
        db.addDocument("FoodItem", docId, data);
        //refresh the activity

        //after refreshing setting the updated data
        setUpdate();

    }


    void showSnackbar(String msg) {
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
        quantityET = findViewById(R.id.quantity);
        subTypeET = findViewById(R.id.itemSubType);
        descriptionET = findViewById(R.id.description);
        dropdownTv=findViewById(R.id.dropdown);
        deleteBT = findViewById(R.id.delete);

    }

    private void getIntentData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        link = intent.getStringExtra("link");
        subType = intent.getStringExtra("subType");
        description = intent.getStringExtra("description");
    }

    private void setIntentData() {
        namE.setText(name);
        pricE.setText(price);
        subTypeET.setText(subType);
        descriptionET.setText(description);
        Glide.with(img.getContext()).load(Uri.parse(link)).into(img);

    }


    private void setUpdate() {
        namE.setText(name);
        pricE.setText(price);
        subTypeET.setText(subType);
        quantityET.setText(quantity);
        descriptionET.setText(description);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //replacing the back button functionality in this activity,because
        // 1:going the previous activity by back button will not reload the previous activity
        //2:we need the updated data on the previous activity
        //3:so we replace the back button
        ///<---- caution---->
        //1: if we have a idea to refresh the previous activity recycler
        //2:then you can skip this technique
        onNavigateUp();
    }
}