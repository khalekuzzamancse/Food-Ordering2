package com.example.foodordering.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.foodordering.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    Spinner spinner;
    EditText shopName, shopLocation;
    LinearLayout shopContainer;
    String userType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        spinner = findViewById(R.id.snipper);
        shopName = findViewById(R.id.shopName);
        shopLocation = findViewById(R.id.shopLocation);
        shopContainer = findViewById(R.id.shopContainer);

        //when submit button will be clicked
        AppCompatButton submit = findViewById(R.id.Activity_Register_Button_Submit);
        submit.setOnClickListener(view -> {
            ProgressBar p = findViewById(R.id.ActivityRegister_ProgessBar);
            p.setVisibility(View.VISIBLE);
            SetUserInfo();
            Log.i("Submit", "clicked");
        });

        //when a spinner item will be selected,by default the first item is selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userType = (String) parent.getItemAtPosition(position);
                if (!userType.equals("Shop Keeper")) {
                    shopContainer.setVisibility(View.GONE);
                } else {
                    shopContainer.setVisibility(View.VISIBLE);
                }
                Log.i("Spinner", userType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void SetUserInfo() {
        EditText Name = findViewById(R.id.Activity_Register_TextInputLayout_EditText_Name);
        String name = Name.getText().toString().trim();
        if (name.isEmpty()) {
            Name.setError("Name can not be empty!");
            return;
        }
        EditText Email = findViewById(R.id.Activity_Register_TextInputLayout_EditText_Email);
        String email = Email.getText().toString().trim();
        if (email.isEmpty()) {
            Email.setError("Email can not be empty!");
            return;
        } else if (!email.contains("@")) {
            Email.setError("Invalid Email!");
            return;
        }
        EditText PhoneNumber = findViewById(R.id.Activity_Register_TextInputLayout_EditText_PhoneNumber);
        String phoneNumber = PhoneNumber.getText().toString().trim();

        EditText PassWord = findViewById(R.id.Activity_Register_TextInputLayout_EditText_Password);
        String password = PassWord.getText().toString().trim();
        if (password.isEmpty()) {
            PassWord.setError("Password can not be empty!");
            return;
        } else if (password.length() < 6) {
            PassWord.setError("Password length is less than 6!");
            return;
        }

        EditText ConfirmPassWord = findViewById(R.id.Activity_Register_TextInputLayout_EditText_ConfirmPassword);
        String confirmPassword = ConfirmPassWord.getText().toString().trim();
        if (confirmPassword.isEmpty()) {
            ConfirmPassWord.setError("Re-Enter the password again!");
            return;
        }
        if (!password.equals(confirmPassword)) {
            ConfirmPassWord.setError("Password does not match");
            return;
        }


        Register(email, password);

        HashMap<String, Object> Data = new HashMap<>();
        Data.put("Name", name);
        Data.put("Email", email);
        Data.put("Password", password);
        Data.put("PhoneNumber", phoneNumber);
        String shop_name = shopName.getText().toString().trim();
        String shopLoc = shopLocation.getText().toString().trim();
        Data.put("Shop Name", shop_name);
        Data.put("Shop Location", shopLoc);
        Data.put("UserType", userType);
        setDataToDatabase(Data);
        Log.i("DataRead", String.valueOf(Data));

    }

    private void Register(String email, String password) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Task<AuthResult> task) -> {
                    if (!task.isSuccessful()) {
                        Log.i("Registraion Failed", "Failed");
                    } else {
                        Log.i("Registered", "Sucessfull");
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

    }

    private void setDataToDatabase(HashMap<String, Object> Data) {
        String email = (String) Data.get("Email");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("UserInfo")
                .document(email)
                .set(Data)
                .addOnCompleteListener((Task<Void> task) -> {
                    if (!task.isSuccessful()) {
                        ;
                    } else {
                        Log.i("Added to Database", "Sucessfull");
                    }
                });

    }


}