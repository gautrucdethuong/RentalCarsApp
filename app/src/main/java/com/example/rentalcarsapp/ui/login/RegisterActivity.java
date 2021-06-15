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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.MainActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.dao.AuthenticationDAO;
import com.example.rentalcarsapp.helper.RegexValidate;
import com.example.rentalcarsapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextInputLayout mFullName,mEmail,mPassword,mPhone,mConfirmPassword;
    Button mRegisterBtn,mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    AuthenticationDAO authDao;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName   = findViewById(R.id.fullName);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.re_confirm_password);
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

                final String email = mEmail.getEditText().toString().trim();
                final String password = mPassword.getEditText().toString().trim();
                final String fullName = mFullName.getEditText().toString();
                final String phone    = mPhone.getEditText().toString();
                final String confirm_password    = mConfirmPassword.getEditText().toString();

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
                } else if(!password.equals(confirm_password)) {

                    mConfirmPassword.requestFocus();
                    mConfirmPassword.setError(RegexValidate.MESSAGE_ERROR_CONFIRM_PASSWORD);
                    //check = false;
                    return;
                }

                else if(confirm_password.length() == 0){
                    mEmail.requestFocus();
                    mEmail.setError("Please enter confirm password.");
                // Check validation regex
                } else if (!email.matches(RegexValidate.VALID_EMAIL)) {
                    mEmail.requestFocus();
                    mEmail.setError(RegexValidate.MESSAGE_ERROR_EMAIL);
                } else if (!password.matches(RegexValidate.VALID_PASSWORD)) {
                    mPassword.requestFocus();
                    mPassword.setError(RegexValidate.MESSAGE_ERROR_PASSWORD);
                }else if(!phone.matches(RegexValidate.VALID_PHONE_NUMBER)){
                    mPhone.requestFocus();
                    mPhone.setError(RegexValidate.MESSAGE_ERROR_PHONE_NUMBER);
                } else if(!fullName.matches(RegexValidate.VALID_FULL_NAME)){
                    mFullName.requestFocus();
                    mFullName.setError(RegexValidate.MESSAGE_ERROR_FULL_NAME);
                }else{
                    progressBar.setVisibility(View.VISIBLE);

                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                // send verification link

//                                FirebaseUser fuser = fAuth.getCurrentUser();
//                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(Register.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
//                                    }
//                                });
                                userID = fAuth.getCurrentUser().getUid();
                                User userInfo = new User(email, fullName, phone, "Customer");
                                Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                documentReference.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                            }else {
                                Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
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

    private boolean comparePassword(){
        final String password = mPassword.getEditText().toString().trim();
        final String confirm_password    = mConfirmPassword.getEditText().toString();
        boolean check = true;
        if(!password.equals(confirm_password)){
            mConfirmPassword.requestFocus();
            mConfirmPassword.setError(RegexValidate.MESSAGE_ERROR_CONFIRM_PASSWORD);
            check = false;
        }
        return check;
    }
}
