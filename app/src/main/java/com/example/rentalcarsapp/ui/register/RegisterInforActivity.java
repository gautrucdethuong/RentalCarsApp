package com.example.rentalcarsapp.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.dao.AuthenticationDAO;
import com.example.rentalcarsapp.model.User;
import com.example.rentalcarsapp.ui.home.user.UsersManagementActivity;
import com.example.rentalcarsapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterInforActivity extends AppCompatActivity {


    public static final String TAG = "TAG";

    Button mRegisterBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    AuthenticationDAO authDao;
    String userID;

    private Intent intent;
    RadioGroup mRadioGroupGender;
    RadioButton rGender;
    DatePicker mDatePicker;
    int gender = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_infor);
        mDatePicker = findViewById(R.id.datePicker);
        mRegisterBtn = findViewById(R.id.signup);
        authDao = new AuthenticationDAO();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        progressBar = findViewById(R.id.progressBar);
        mRadioGroupGender = findViewById(R.id.radioGender);
        intent = getIntent();

        String emailAddress = String.valueOf(intent.getStringExtra("email"));
        String fullName = String.valueOf(intent.getStringExtra("fullName"));
        String phoneNumber = String.valueOf(intent.getStringExtra("phoneNumber"));
        String passWord = String.valueOf(intent.getStringExtra("passWord"));
        int radioId = mRadioGroupGender.getCheckedRadioButtonId();
        rGender = findViewById(radioId);
        if (rGender.getText().equals("Male")) {
            gender = 1;
        } else if (rGender.getText().equals("Other")) {
            gender = 2;
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(emailAddress, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            int day = mDatePicker.getDayOfMonth();
                            int month = mDatePicker.getMonth();
                            int year = mDatePicker.getYear();
                            String birthday = day + "/" + month + "/" + year;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = new Date();
                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                            String str = simpleDateFormat1.format(date);
                            userID = fAuth.getCurrentUser().getUid();
                            User userInfo = null;
                            try {
                                userInfo = new User(emailAddress, fullName, phoneNumber, "Customer", gender, simpleDateFormat.parse(birthday), simpleDateFormat1.parse(str));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(RegisterInforActivity.this, "User Created.", Toast.LENGTH_SHORT).show();

                            //    userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            documentReference.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), UsersManagementActivity.class));

                        } else {
                            Log.e("massage", task.getException().toString());
                            Toast.makeText(RegisterInforActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }


                });
                back();

            }


        });
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = mDatePicker.getYear();
        int isAgeValid = currentYear - userAge;
        if (isAgeValid < 18) {
            Toast.makeText(this, "You not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    void register(){

    }

    void back(){
        ImageView imgBack;
        imgBack = findViewById(R.id.logoImage);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });
    }
}
