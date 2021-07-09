package com.example.rentalcarsapp.ui.admin.car;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CreateCarActivity extends AppCompatActivity {
    public static final int PICK_IMAGE_REQUEST=1;
    private Button btnCreate;
    private TextInputLayout txtCarName,txtCarPrice,txtColor,txtSeat,txtDescription;
    private ImageView imgCar,imgBack;
    private Uri imgUri=null;
    private FirebaseFirestore firestore;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private String currentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carmanagement_create);
        btnCreate=findViewById(R.id.btnCreateCar);
        txtCarName=findViewById(R.id.txtCarname);
        txtCarPrice=findViewById(R.id.txtCarprice);
        txtColor=findViewById(R.id.txtCarColor);
        txtSeat=findViewById(R.id.txtCarSeat);
        txtDescription=findViewById(R.id.txtCarDescription);
        imgCar=findViewById(R.id.imgCar);
        imgBack=findViewById(R.id.logoImage);
        storageReference= FirebaseStorage.getInstance().getReference();
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        currentId=auth.getCurrentUser().getUid();
        imgCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListCarActivity.class));
                finish();
            }
        });

    }
    private void openFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imgUri = data.getData();
            imgCar.setBackground(null);
            Picasso.get().load(imgUri).into(imgCar);

        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(){
        if(imgUri!=null){
            StorageReference postRef=storageReference.child("car_images").child(FieldValue.serverTimestamp().toString()+".jpg");
            postRef.putFile(imgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        postRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap<String,Object> postMap=new HashMap<>();
                                postMap.put("carColor","Khoi");
                                postMap.put("carCreatedDate",FieldValue.serverTimestamp());
                                postMap.put("carDescription","Khoi");
                                postMap.put("carImage",uri.toString());
                                postMap.put("carLicensePlates","Khoi");
                                postMap.put("carName","Khoi");
                                postMap.put("carPrice",150000);
                                postMap.put("carSeat","4");
                                firestore.collection("cars").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(CreateCarActivity.this,"Create Car Successful",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), ListCarActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(CreateCarActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }else {
                        Toast.makeText(CreateCarActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }

    }
}
