package com.example.rentalcarsapp.ui.admin;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.rentalcarsapp.MainActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.helper.RegexValidate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextInputLayout mFullName, mEmail, mPhone;
    Button mUpdateBtn;
    TextView mStep;
    ProgressBar progressBar;
    ImageView imgBack;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("saveFullName", String.valueOf(mFullName.getEditText().getText()));
        outState.putString("saveEmail", String.valueOf(mEmail.getEditText().getText()));
        outState.putString("savePhoneNumber", String.valueOf(mPhone.getEditText().getText()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermanagement_update);
        Intent data = getIntent();
        final String fullName = data.getStringExtra("fullName");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPhone = findViewById(R.id.phone);
        mUpdateBtn = findViewById(R.id.signup_next_button);
        imgBack = findViewById(R.id.logoImage);
        progressBar = findViewById(R.id.progressBar);
        mStep = findViewById(R.id.txtStep);
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
                    mFullName.getEditText().setText(String.valueOf(user.get("fullName")));
                    mEmail.getEditText().setText(String.valueOf(user.get("userEmail")));
                    mPhone.getEditText().setText(String.valueOf(user.get("userPhoneNumber")));
                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ValidateFullName() | !ValidateEmail()  | !ValidatePhoneNumber()){
                    Toast.makeText(UpdateActivity.this, "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String email = mEmail.getEditText().getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("email",email);
                        edited.put("fullName",mFullName.getEditText().getText().toString());
                        edited.put("phone",mPhone.getEditText().getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        });
                        Toast.makeText(UpdateActivity.this, "Email is changed.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateActivity.this,   e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        mEmail.getEditText().setText(email);
        mFullName.getEditText().setText(fullName);
        mPhone.getEditText().setText(phone);

        Log.d(TAG, "onCreate: " + fullName + " " + email + " " + phone);
//                Intent intent = new Intent(getApplicationContext(), CreateInforActivity.class);
//
//                intent.putExtra("fullName", String.valueOf(mFullName.getEditText().getText()));
//                intent.putExtra("email", String.valueOf(mEmail.getEditText().getText()));
//                intent.putExtra("phoneNumber", String.valueOf(mPhone.getEditText().getText()));
//
//                startActivity(intent);
//            }
//        });
        // Back login
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(imgBack, "logo_image");
                pairs[1] = new Pair<View, String>(mStep, "txt_transaction");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UpdateActivity.this, pairs);
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class), options.toBundle());
            }
        });
    }

    private boolean ValidateFullName() {
        String fullName = String.valueOf(mFullName.getEditText().getText());

        if (fullName.isEmpty()) {
            mFullName.setError("Please enter full name.");
            return false;
        } else if (!fullName.matches(RegexValidate.VALID_FULL_NAME)) {
            mFullName.setError(RegexValidate.MESSAGE_ERROR_FULL_NAME);
            return false;
        } else {
            mFullName.setError(null);
            mFullName.setErrorEnabled(false);
            return true;
        }

    }

    private boolean ValidatePhoneNumber() {
        String phoneNumber = String.valueOf(mPhone.getEditText().getText());
        if (phoneNumber.isEmpty()) {
            mPhone.setError("Please enter phone number.");
            return false;
        } else if (!phoneNumber.matches(RegexValidate.VALID_PHONE_NUMBER)) {
            mPhone.setError(RegexValidate.MESSAGE_ERROR_PHONE_NUMBER);
            return false;
        } else {
            mPhone.setError(null);
            mPhone.setErrorEnabled(false);
            return true;
        }

    }

    private boolean ValidateEmail() {
        String email = String.valueOf(mEmail.getEditText().getText());
        if (email.isEmpty()) {
            mEmail.setError("Please enter email.");
            return false;
        } else if (!email.matches(RegexValidate.VALID_EMAIL)) {
            mEmail.setError(RegexValidate.MESSAGE_ERROR_EMAIL);
            return false;
        } else {
            mEmail.setError(null);
            mEmail.setErrorEnabled(false);
            return true;
        }

    }
}