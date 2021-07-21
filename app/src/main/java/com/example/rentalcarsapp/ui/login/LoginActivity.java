package com.example.rentalcarsapp.ui.login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.DashboardActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.dao.AuthenticationDAO;
import com.example.rentalcarsapp.dao.Callback;
import com.example.rentalcarsapp.ui.admin.car.CreateCarActivity;
import com.example.rentalcarsapp.ui.admin.car.ListCarActivity;
import com.example.rentalcarsapp.ui.forgot.ForgotPasswordActivity;

import com.example.rentalcarsapp.ui.home.car.RecyclerCarActivity;
import com.example.rentalcarsapp.ui.register.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout mEmail, mPassword;
    TextView mWelcome, mSlogan;
    Button mLoginBtn;
    Button mCreateBtn, forgotTextLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    AuthenticationDAO authDao;
    ImageView imglogo;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authDao = new AuthenticationDAO();
        mEmail = findViewById(R.id.txtEmail);
        mPassword = findViewById(R.id.txtPassword);
        progressBar = findViewById(R.id.progressBar);
        mLoginBtn = findViewById(R.id.loginBtn);
        mCreateBtn = findViewById(R.id.createText);
        forgotTextLink = findViewById(R.id.forgotPassword);
        imglogo = findViewById(R.id.logoImage);
        mWelcome = findViewById(R.id.logo_name);
        mSlogan = findViewById(R.id.slogan_name);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null) {
            String userId = fAuth.getCurrentUser().getUid();

            DocumentReference docRef = fStore.collection("users").document(userId);
            Source source = Source.SERVER;

            docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        // Document found in the offline cache
                        DocumentSnapshot document = task.getResult();

                        Map<String, Object> user = document.getData();
                        String role = String.valueOf(user.get("roleName"));
                        boolean statusUser = Boolean.parseBoolean(String.valueOf(user.get("userStatus")));
                        if (!statusUser) {
                            Toast.makeText(LoginActivity.this, "Your account is deactive now !", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();//logout
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }
                        Log.e("roleName", role);
                        switch (role) {
                            case "Admin":
                                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                                finish();
                                break;
                            case "Customer":
                                Toast.makeText(LoginActivity.this, "Customer", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), RecyclerCarActivity.class));
                                finish();
                                break;
                            default:
                                break;
                        }
                    } else {
                        Log.d("TAG", "Cached get failed: ", task.getException());
                    }
                }
            });
            finish();
        }
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(imglogo, "logo_image");
                pairs[1] = new Pair<View, String>(mWelcome, "logo_text");
                pairs[2] = new Pair<View, String>(mSlogan, "logo_signup");
                pairs[3] = new Pair<View, String>(forgotTextLink, "txt_transaction");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(imglogo, "logo_image");
                pairs[1] = new Pair<View, String>(mWelcome, "logo_text");
                pairs[2] = new Pair<View, String>(mSlogan, "logo_signup");
                pairs[3] = new Pair<View, String>(forgotTextLink, "txt_transaction");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(mEmail.getEditText().getText());
                String password = String.valueOf(mPassword.getEditText().getText());

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                authDao.loginFirebaseAuthentication(email, password, new Callback() {
                    @Override
                    public void isLogin(String roleName) {

                        if (!roleName.isEmpty()) {
                            switch (roleName) {
                                case "Admin":
                                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                                    finish();
                                    break;
                                case "Customer":
                                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), RecyclerCarActivity.class));
                                    finish();
                                    break;
                                case "deactive":
                                    Toast.makeText(LoginActivity.this, "Your account is deactive now !", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();//logout
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    finish();
                                    break;
                                default:
                                    break;
                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "Logged in Failed ! ", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void isRegister(boolean status) {

                    }

                });
            }
        });


    }
}