package com.example.rentalcarsapp.ui.admin.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.rentalcarsapp.ui.home.user.UsersManagementActivity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateUserInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    String[] roles = {"Choose role", "Customer", "Admin"};
    String item;
    ImageView imgBack;
    private Intent intent;
    RadioGroup mRadioGroupGender;
    RadioButton rGender;
    DatePicker mDatePicker;
    int gender=0;

    /**
     * oncreate when click on it will active it
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermanagement_create_infor);
        mDatePicker = findViewById(R.id.age_picker);
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
        imgBack = findViewById(R.id.ImageBtnBack);
        progressBar = findViewById(R.id.progressBar);
        mRadioGroupGender=findViewById(R.id.radioGender);
        fetchdata();
        intent = getIntent();

        String emailAddress = String.valueOf(intent.getStringExtra("email"));
        String fullName = String.valueOf(intent.getStringExtra("fullName"));
        String phoneNumber = String.valueOf(intent.getStringExtra("phoneNumber"));
        String passWord = String.valueOf(intent.getStringExtra("passWord"));
        int radioId=mRadioGroupGender.getCheckedRadioButtonId();
        rGender=findViewById(radioId);
        if(rGender.getText().equals("Male")){
            gender=1;
        }else if(rGender.getText().equals("Other")){
            gender=2;
        }
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (item == "Choose role") {
                    Toast.makeText(CreateUserInfoActivity.this, "please select a role", Toast.LENGTH_SHORT).show();
                } else {
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
                                    userInfo = new User(emailAddress, fullName, phoneNumber, item, gender, simpleDateFormat.parse(birthday), simpleDateFormat1.parse(str));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(CreateUserInfoActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                documentReference.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                        startActivity(new Intent(getApplicationContext(), UsersManagementActivity.class));
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
                                Log.e("message", task.getException().toString());
                                Toast.makeText(CreateUserInfoActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
        // Back login
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateUserActivity.class));
                finish();
            }
        });

    }

    /**
     *
     * @return true, false for validateAge
     */
    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = mDatePicker.getYear();
        int isAgeValid = currentYear - userAge;
        if(isAgeValid < 18 ){
            Toast.makeText(this,"You not eligible to apply",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    /**
     * get spinner role on select item to bind it into textview
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = spinner.getSelectedItem().toString();
        textView.setText(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * fetchdata from role firebase data to spinner(not use)
     */
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
}