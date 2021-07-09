package com.example.rentalcarsapp.ui.home.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/1/2021.
 * Company: FPT大学.
 */

public class MainCarActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE = 101;
    private ImageView imageView;
    private EditText inputImageName;
    private TextView textViewProgressBar;
    private ProgressBar progressBar;
    private Button buttonUpload;

    Uri imageUrl;
    boolean isImageAdded = false;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image_car);

        imageView = findViewById(R.id.imageViewCarDetails);
        inputImageName = findViewById(R.id.inputImageName);
        textViewProgressBar = findViewById(R.id.textViewProgress);
        progressBar = findViewById(R.id.progressBar);
        buttonUpload = findViewById(R.id.btnUpload);

        textViewProgressBar.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Car");
        storageReference = FirebaseStorage.getInstance().getReference().child("CarImage");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String imageName = inputImageName.getText().toString();
                if (isImageAdded != false && imageName != null){
                    uploadImage(imageName);
                }
            }
        });



    }

    private void uploadImage(String imageName) {
            textViewProgressBar.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);


            String key = databaseReference.push().getKey();
            storageReference.child(key+ ".jpg").putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.child(key +".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            HashMap hashMap = new HashMap();
                            hashMap.put("CarName",imageName);
                            hashMap.put("ImageUrl",uri.toString());

                            databaseReference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainCarActivity.this, "Data Success", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                    progressBar.setProgress((int) progress);
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && data!= null){
            imageUrl = data.getData();
            isImageAdded = true;
            imageView.setImageURI(imageUrl);
        }
    }
}
