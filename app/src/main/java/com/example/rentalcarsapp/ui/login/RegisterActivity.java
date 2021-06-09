package com.example.rentalcarsapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.MainActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.dao.AuthenticationDAO;
import com.example.rentalcarsapp.helper.RegexValidate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    AuthenticationDAO authDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName   = findViewById(R.id.fullName);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.password);
        mPhone      = findViewById(R.id.phone);
        mRegisterBtn= findViewById(R.id.registerBtn);
        mLoginBtn   = findViewById(R.id.createText);

        authDao = new AuthenticationDAO();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String phone    = mPhone.getText().toString();

                // Validation data fields when user input
                if (fullName.length() == 0) {
                    mFullName.requestFocus();
                    mFullName.setError("Please enter full name");
                } else if (email.length() == 0) {
                    mEmail.requestFocus();
                    mEmail.setError("Please enter email address");
                } else if (password.length() == 0) {
                    mPassword.requestFocus();
                    mPassword.setError("Please enter password");
                } else if (phone.length() == 0 ) {
                    mPhone.requestFocus();
                    mPhone.setError("Please enter phone number");

                // Check validation regex
                } else if (!email.matches(RegexValidate.VALID_EMAIL)) {
                    mEmail.requestFocus();
                    mEmail.setError("Email incorrect format.");
                } else if (!password.matches(RegexValidate.VALID_PASSWORD)) {
                    mPassword.requestFocus();
                    mPassword.setError("Password very bad.");
                }else if(!phone.matches(RegexValidate.VALID_PHONE_NUMBER)){
                    mPhone.requestFocus();
                    mPhone.setError("Phone number incorrect format.");
                } else if(!fullName.matches(RegexValidate.VALID_FULL_NAME)){
                    mFullName.requestFocus();
                    mFullName.setError("Full name at least two words. !!!");
                }else{
                    progressBar.setVisibility(View.VISIBLE);

                    boolean status = authDao.registerFirebaseAuthentication(email, password, fullName, phone);
                    // register the user in firebase
                    if(status){
                        Toast.makeText(RegisterActivity.this, "Sign up in Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                    }else {
                        Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}
