package com.example.rentalcarsapp.ui.login;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.rentalcarsapp.ui.admin.user.UpdateUserActivity;
import com.example.rentalcarsapp.ui.admin.user.UpdateUserInfoActivity;
import com.example.rentalcarsapp.ui.home.car.RecyclerCarActivity;
import com.example.rentalcarsapp.ui.home.user.UsersManagementActivity;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextInputLayout mFullName, mEmail, mPhone;
    ImageView profileImageView;
    Button mUpdateBtn;
    TextView mStep;
    ProgressBar progressBar;
    ImageView imgBack;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;

    /**
     * oncreate when click on it will active it
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_info);


        Intent data = getIntent();
        final String fullName = data.getStringExtra("fullName");
        final String email = data.getStringExtra("email");
        final String phone = data.getStringExtra("phone");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        profileImageView = findViewById(R.id.profileImageView);
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mEmail.setEnabled(false);
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


        // [END get_document_options]
        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

//        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!ValidateFullName() | !ValidateEmail() | !ValidatePhoneNumber()) {
//                    Toast.makeText(EditProfileActivity.this, "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                final String email = profileEmail.getText().toString();
//                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
//                        Map<String, Object> edited = new HashMap<>();
//                        edited.put("email",email);
//                        edited.put("fullName",profileFullName.getText().toString());
//                        edited.put("userPhoneNumber",profilePhone.getText().toString());
//                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(EditProfileActivity.this, "Update successful !!", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
//                                finish();
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(EditProfileActivity.this,   e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//            }
//        });
//
//        profileEmail.setText(email);
//        profileFullName.setText(fullName);
//        profilePhone.setText(phone);
//
//        Log.d(TAG, "onCreate: " + fullName + " " + email + " " + phone);
//    }

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ValidateFullName() | !ValidateEmail() | !ValidatePhoneNumber()) {
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), EditUserProfileActivity.class);

                intent.putExtra("fullName", String.valueOf(mFullName.getEditText().getText().toString().trim()));
                intent.putExtra("email", String.valueOf(mEmail.getEditText().getText().toString().trim()));
                intent.putExtra("phone", String.valueOf(mPhone.getEditText().getText().toString().trim()));
                startActivity(intent);
            }
        });
        // Back login
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(imgBack, "logo_image");
                pairs[1] = new Pair<View, String>(mStep, "txt_transaction");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(EditProfileActivity.this, pairs);
                startActivity(new Intent(getApplicationContext(), RecyclerCarActivity.class), options.toBundle());
            }
        });
    }

    /**
     * Maybe this code to check image to upload firebase (not use)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();

                uploadImageToFirebase(imageUri);


            }
        }

    }

    /**
     * uploadImageToFirebase
     * @param imageUri
     */
    private void uploadImageToFirebase(Uri imageUri) {
        // uplaod image to firebase storage
        final StorageReference fileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    /**
     *
     * @return true, false for validatefullname
     */
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
    /**
     *
     * @return true, false for validatephonenumber
     */
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
    /**
     *
     * @return true, false for ValidateEmail
     */
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
