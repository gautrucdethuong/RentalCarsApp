package com.example.rentalcarsapp.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.dao.AuthenticationDAO;
import com.example.rentalcarsapp.helper.RegexValidate;
import com.example.rentalcarsapp.model.User;
import com.example.rentalcarsapp.ui.home.UsersManagementActivity;
import com.example.rentalcarsapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class RegisterInforActivity extends AppCompatActivity {


    public static final String TAG = "TAG";

    Button mRegisterBtn;
    TextView mWelcome, mSlogan, mStep;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    AuthenticationDAO authDao;
    String userID;
    ImageView imgBack;
    private Intent intent;
    RadioGroup mRadioGroupGender;
    RadioButton selectAge;
    DatePicker mDatePicker;
    Date startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_infor);
        //mRadioButton = fi
       // mRadioGroupGender = findViewById(mRadioGroupGender.getCheckedRadioButtonId());

        mDatePicker = findViewById(R.id.datePicker);
        mRegisterBtn = findViewById(R.id.signup_next_button);

        authDao = new AuthenticationDAO();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        imgBack   = findViewById(R.id.logoImage);

        intent = getIntent();

        String emailAddress = String.valueOf(intent.getStringExtra("email"));
        String fullName = String.valueOf(intent.getStringExtra("fullName"));
        String phoneNumber = String.valueOf(intent.getStringExtra("phoneNumber"));
        String passWord = String.valueOf(intent.getStringExtra("passWord"));

        //String gender =

 /*       if(!validateAge() | !validateGender()){
            return;
        }*/

        //selectAge = findViewById(mRadioGroupGender.getCheckedRadioButtonId());
        //selectAge.getText();

 /*       int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth();
        int year = mDatePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        String birthday = day+"/"+month+"/"+year;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate;
        try {
            startDate = df.parse(birthday);
            String newDateString = df.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        progressBar = findViewById(R.id.progressBar);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);

                    fAuth.createUserWithEmailAndPassword(emailAddress,passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                LocalDateTime userCreatedDate = LocalDateTime.now();

                                Log.e("time", userCreatedDate.toString());
                                userID = fAuth.getCurrentUser().getUid();

                                User userInfo = new User(emailAddress, fullName, phoneNumber, "Customer", 1);
                                Toast.makeText(RegisterInforActivity.this, "User Created.", Toast.LENGTH_SHORT).show();

                            //    userID = fAuth.getCurrentUser().getUid();
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

                                startActivity(new Intent(getApplicationContext(), UsersManagementActivity.class));

                            }else {
                                Log.e("massage",task.getException().toString());
                                Toast.makeText(RegisterInforActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                // }
            }
        });
        // Back login
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pairs=new Pair[4];
                pairs[0]=new Pair<View,String>(imgBack,"logo_image");
                pairs[1]=new Pair<View,String>(mWelcome,"logo_text");
                pairs[2]=new Pair<View,String>(mSlogan,"logo_signup");
                pairs[3]=new Pair<View,String>(mStep,"txt_transaction");
                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(RegisterInforActivity.this, pairs);
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class),options.toBundle());
            }
        });

    }


    private boolean validateGender(){
        if(mRadioGroupGender.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Please choose gender", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = mDatePicker.getYear();
        int isAgeValid = currentYear - userAge;
        if(isAgeValid < 18 ){
            return false;
        }else{
            return true;
        }
    }
}