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
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.DashboardActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.dao.AuthenticationDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class UpdateInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "TAG";
    Button mUpdateBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;
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
    RadioButton rGender;
    DatePicker mDatePicker;
    int gender = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermanagement_update_infor);
        mDatePicker = findViewById(R.id.age_picker);
        mUpdateBtn = findViewById(R.id.next2Btn);
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
        user= fAuth.getCurrentUser();
        imgBack = findViewById(R.id.logoImage);
        progressBar = findViewById(R.id.progressBar);
        mRadioGroupGender = findViewById(R.id.radioGender);
        fetchdata();
        intent = getIntent();
        String emailAddress = String.valueOf(intent.getStringExtra("email"));
        String fullName = String.valueOf(intent.getStringExtra("fullName"));
        String phoneNumber = String.valueOf(intent.getStringExtra("phone"));
        String role = intent.getStringExtra("roleName");
        int radioId = mRadioGroupGender.getCheckedRadioButtonId();
        rGender = findViewById(radioId);
        if (rGender.getText().equals("Male")) {
            gender = 1;
        } else if (rGender.getText().equals("Other")) {
            gender = 2;
        }
        fStore = FirebaseFirestore.getInstance();
        String userId = fAuth.getCurrentUser().getUid();
        // [START get_document_options]
        DocumentReference docRef = fStore.collection("users").document(userId);

        // Source can be CACHE, SERVER, or DEFAULT.
        Source source = Source.CACHE;
        // Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot document = task.getResult();
                    Log.d("TAG", "Cached document data: " + document.getData());
                    Map<String, Object> user = document.getData();
                    Log.d("TAG", "Mih Meo: " + user.get("userGender").toString());
                    if (Integer.parseInt(user.get("userGender").toString()) == 1) {
                        rGender = findViewById(R.id.male);
                        rGender.setChecked(true);
                    } else if (Integer.parseInt(user.get("userGender").toString()) == 2) {
                        rGender = findViewById(R.id.others);
                        rGender.setChecked(true);
                    } else {
                        rGender = findViewById(R.id.female);
                        rGender.setChecked(true);
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    Timestamp dunghivong = (Timestamp) user.get("userBirthday");
                    Date birthday = dunghivong.toDate();
                    int day = Integer.parseInt((String) DateFormat.format("dd", birthday));
                    int month = Integer.parseInt((String) DateFormat.format("MM", birthday));
                    int year = Integer.parseInt((String) DateFormat.format("yyyy", birthday));
                    Log.d("TAG", "Mih Meo test: " + day + month + year);
                    mDatePicker.init(year, month, day, null);
                    textView.setText(String.valueOf(user.get("roleName")));
                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Mih Meo email: " +emailAddress);
                progressBar.setVisibility(View.VISIBLE);
                String email = emailAddress;
                int day = mDatePicker.getDayOfMonth();
                int month = mDatePicker.getMonth();
                int year = mDatePicker.getYear();
                String birthday = day+"/"+month+"/"+year;
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String str=simpleDateFormat1.format(date);
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("userEmail", email);
                        edited.put("fullName", fullName);
                        edited.put("userPhoneNumber", phoneNumber);
                        edited.put("roleName",textView.getText().toString());
                        edited.put("userGender",gender);
                        try {
                            edited.put("userBirthday",simpleDateFormat.parse(birthday));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateInfoActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                                finish();
                            }
                        });
                        Toast.makeText(UpdateInfoActivity.this, "Email is changed.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
//        mEmail.getEditText().setText(emailAddress);
//        mFullName.getEditText().setText(fullName);
//        mPhone.getEditText().setText(phoneNumber);
        textView.setText(role);
        Log.d(TAG, "onCreate: " + fullName + " " + emailAddress + " " + phoneNumber + " "+ role + " " +gender);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = spinner.getSelectedItem().toString();
        textView.setText(item);
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
}