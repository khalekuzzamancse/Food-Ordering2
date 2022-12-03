package com.example.foodordering;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.example.foodordering.firebasetemplate.CallbackUploadImage;
import com.example.foodordering.firebasetemplate.DatabaseFetch;
import com.example.foodordering.firebasetemplate.FirebaseCustom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UploadImage extends AppCompatActivity {
    AppCompatButton upload;
    ImageView img;
    Uri imageUri;
    ProgressBar progressBar;
    EditText name, price, quantity, type;
    String docId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image_activity);
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
                    setData(link);
                    Log.i("UploadedImage", String.valueOf(link));
                    progressBar.setVisibility(View.INVISIBLE);
                    img.setImageResource(R.drawable.vector);
                    showSnakbar("Uploaded Successfully");


                } else
                    Log.i("UploadedImage", "fail");
            }
        };


        upload.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            docId = "khalekuzzaman91@gmail.com" + name.getText();
            custom.uploadImage("FoodImages", docId, imageUri, callback);

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
        data.put("Name", name.getText().toString());
        data.put("Price", price.getText().toString());
        data.put("Type", type.getText().toString());
        data.put("Available Quantity", quantity.getText().toString());
        data.put("URL", uri.toString());
        ///
        data.put("OwnerID","khalekuzzaman@gmail.com");
        //shopname
        data.put("Shop Name","Azad Store");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        //  data.put("Owner", );
        FirebaseCustom db = new DatabaseFetch();
        //  String docId = auth.getCurrentUser().getEmail();
        db.addDocument("FoodItem", docId, data);
      //  clearView();
    }

    private void initializeView() {
        img = findViewById(R.id.image);
        progressBar = findViewById(R.id.progressbar);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);
        type = findViewById(R.id.type);
    }

    private void clearView() {

        name.setText("");
        price.setText("");
        quantity.setText("");
        type.setText("");
    }
}