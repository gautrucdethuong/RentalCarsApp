package com.example.rentalcarsapp.ui.admin;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.dao.AuthenticationDAO;
import com.example.rentalcarsapp.model.User;
import com.example.rentalcarsapp.ui.home.TestActivity;
import com.example.rentalcarsapp.ui.register.RegisterActivity;
import com.example.rentalcarsapp.ui.register.RegisterInforActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateInforActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    public static final String TAG = "TAG";

    Button mCreateBtn;
    TextView mWelcome, mSlogan, mStep;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    AuthenticationDAO authDao;
    String userID;
    TextView textView;
    Spinner spinner;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    String[] roles = {"Choose role", "Customer", "Admin", "Sale Staff"};
    String item;
    ImageView imgBack;
    private Intent intent;
    RadioGroup mRadioGroupGender;
    RadioButton selectAge;
    DatePicker mDatePicker;
    Date startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermanagement_create_infor);

        mDatePicker = findViewById(R.id.datePicker);
        mCreateBtn = findViewById(R.id.next2Btn);
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
        imgBack = findViewById(R.id.logoImage);
        fetchdata();
        intent = getIntent();

        String emailAddress = String.valueOf(intent.getStringExtra("email"));
        String fullName = String.valueOf(intent.getStringExtra("fullName"));
        String phoneNumber = String.valueOf(intent.getStringExtra("phoneNumber"));
        String passWord = String.valueOf(intent.getStringExtra("passWord"));
        progressBar = findViewById(R.id.progressBar);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(emailAddress, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            LocalDateTime userCreatedDate = LocalDateTime.now();

                            Log.e("time", userCreatedDate.toString());
                            userID = fAuth.getCurrentUser().getUid();

                            User userInfo = new User(emailAddress, fullName, phoneNumber, "Customer", 1, userCreatedDate, userCreatedDate);
                            Toast.makeText(CreateInforActivity.this, "User Created.", Toast.LENGTH_SHORT).show();

                            //    userID = fAuth.getCurrentUser().getUid();
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

                            startActivity(new Intent(getApplicationContext(), TestActivity.class));

                        } else {
                            Log.e("massage", task.getException().toString());
                            Toast.makeText(CreateInforActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(imgBack, "logo_image");
                pairs[1] = new Pair<View, String>(mWelcome, "logo_text");
                pairs[2] = new Pair<View, String>(mSlogan, "logo_signup");
                pairs[3] = new Pair<View, String>(mStep, "txt_transaction");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CreateInforActivity.this, pairs);
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class), options.toBundle());
            }
        });

    }


    private boolean validateGender() {
        if (mRadioGroupGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please choose gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = mDatePicker.getYear();
        int isAgeValid = currentYear - userAge;
        if (isAgeValid < 18) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

    void SelectValue(String item) {
        if (item == "Choose role") {
            Toast.makeText(this, "please select a role", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not done", Toast.LENGTH_SHORT).show();
        }
    }
}