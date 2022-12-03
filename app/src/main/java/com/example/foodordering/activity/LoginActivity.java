package com.example.foodordering.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.foodordering.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_page_scrollable);
        Button register = findViewById(R.id.ActivityLogin_Button_Register);
        register.setOnClickListener((view) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
        Button login = findViewById(R.id.ActivityLogin_Button_Login);
        login.setOnClickListener(view -> {
            ProgressBar p = findViewById(R.id.ActivityLogin_ProgessBar);
            p.setVisibility(View.VISIBLE);

            EditText Email = findViewById(R.id.ActivityLogin_EditText_Email);
            EditText PassWord = findViewById(R.id.ActivityLogin_TextInputLayout_EditText_Password);
            String email = Email.getText().toString().trim();
            String password = PassWord.getText().toString().trim();
            Login(email, password);
            //  startActivity(new Intent(this, MainActivity.class));
        });
    }

    private void Login(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Task<AuthResult> task) -> {
                    if (!task.isSuccessful()) {
                        //Log.i("Curr Logined","Failed");
                        ;
                    } else {

                        // Log.i("Curr Logined","Successfully");

                        Intent i = new Intent(this, FoodDashBoard_Activity.class);
                        startActivity(i);


                    }
                });
    }
}