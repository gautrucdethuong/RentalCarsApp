package com.example.rentalcarsapp.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.MainActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.dao.AuthenticationDAO;
import com.example.rentalcarsapp.helper.RegexValidate;
import com.example.rentalcarsapp.model.User;
import com.example.rentalcarsapp.ui.login.LoginActivity;
import com.example.rentalcarsapp.ui.register.RegisterActivity;
import com.example.rentalcarsapp.ui.register.RegisterInforActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CreateUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "TAG";
    TextInputLayout mFullName, mEmail, mPassword, mPhone, mConfirmPassword;
    Button next2Btn, mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    AuthenticationDAO authDao;
    String userID;
    TextView textView;
    Spinner spinner;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    String[] roles = {"Choose role","Customer","Admin","Sale Staff"};
    String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermanagement_create_infor);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.re_confirm_password);
        mPhone = findViewById(R.id.phone);
        next2Btn = findViewById(R.id.next2Btn);
        mLoginBtn = findViewById(R.id.createText);
        textView = findViewById(R.id.create_role);
        spinner = findViewById(R.id.spinner);
        authDao = new AuthenticationDAO();
        fAuth = FirebaseAuth.getInstance();
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        fetchdata();
        adapter.notifyDataSetChanged();
        next2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectValue(item);
                final String email = mEmail.getEditText().toString().trim();
                final String password = mPassword.getEditText().toString().trim();
                final String fullName = mFullName.getEditText().toString();
                final String phone = mPhone.getEditText().toString();
                final String confirm_password = mConfirmPassword.getEditText().toString();
                if (fullName.length() == 0) {
                    mFullName.requestFocus();
                    mFullName.setError("Please enter full name");
                } else if (email.length() == 0) {
                    mEmail.requestFocus();
                    mEmail.setError("Please enter email address");
                } else if (password.length() == 0) {
                    mPassword.requestFocus();
                    mPassword.setError("Please enter password");
                } else if (phone.length() == 0) {
                    mPhone.requestFocus();
                    mPhone.setError("Please enter phone number");
                } else if (!password.equals(confirm_password)) {

                    mConfirmPassword.requestFocus();
                    mConfirmPassword.setError(RegexValidate.MESSAGE_ERROR_CONFIRM_PASSWORD);
                    //check = false;
                    return;
                } else if (confirm_password.length() == 0) {
                    mEmail.requestFocus();
                    mEmail.setError("Please enter confirm password.");
                    // Check validation regex
                } else if (!email.matches(RegexValidate.VALID_EMAIL)) {
                    mEmail.requestFocus();
                    mEmail.setError(RegexValidate.MESSAGE_ERROR_EMAIL);
                } else if (!password.matches(RegexValidate.VALID_PASSWORD)) {
                    mPassword.requestFocus();
                    mPassword.setError(RegexValidate.MESSAGE_ERROR_PASSWORD);
                } else if (!phone.matches(RegexValidate.VALID_PHONE_NUMBER)) {
                    mPhone.requestFocus();
                    mPhone.setError(RegexValidate.MESSAGE_ERROR_PHONE_NUMBER);
                } else if (!fullName.matches(RegexValidate.VALID_FULL_NAME)) {
                    mFullName.requestFocus();
                    mFullName.setError(RegexValidate.MESSAGE_ERROR_FULL_NAME);
                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userID = fAuth.getCurrentUser().getUid();
                                User userInfo = new User(email, fullName, phone, "Customer");
                                Toast.makeText(CreateUserActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                documentReference.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), RegisterInforActivity.class));

                            } else {
                                Toast.makeText(CreateUserActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean comparePassword() {
        final String password = mPassword.getEditText().toString().trim();
        final String confirm_password = mConfirmPassword.getEditText().toString();
        boolean check = true;
        if (!password.equals(confirm_password)) {
            mConfirmPassword.requestFocus();
            mConfirmPassword.setError(RegexValidate.MESSAGE_ERROR_CONFIRM_PASSWORD);
            check = false;
        }
        return check;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e("ERRRRRRR", parent.getSelectedItem().toString());
        String choice = spinner.getSelectedItem().toString();
        textView.setText(choice);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void fetchdata() {
        fStore = FirebaseFirestore.getInstance();

        fStore.collection("roles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String role_name = document.get("roleName").toString();
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                list.add(role_name);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }
    void SelectValue(String item){
        if(item == "Choose role"){
            Toast.makeText(this,"please select a role",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Not done",Toast.LENGTH_SHORT).show();
        }
    }
}