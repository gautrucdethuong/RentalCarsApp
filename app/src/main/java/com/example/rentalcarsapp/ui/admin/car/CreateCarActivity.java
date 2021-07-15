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
import com.example.rentalcarsapp.dao.CarDAO;
import com.example.rentalcarsapp.helper.RegexValidate;
import com.example.rentalcarsapp.model.Car;
import com.example.rentalcarsapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class CreateCarActivity extends AppCompatActivity {
    public static final int PICK_IMAGE_REQUEST = 1;
    private Button btnCreate;
    private TextInputLayout txtCarName, txtCarPrice, txtColor, txtSeat, txtDescription, txtCarLicensePlates;
    private ImageView imgCar;
    private Uri imgUri;
    FirebaseFirestore fStore;
    CarDAO carDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carmanagement_create);
        btnCreate = findViewById(R.id.btnCreateCar);
        txtCarName = findViewById(R.id.txtCarname);
        txtCarPrice = findViewById(R.id.txtCarprice);
        txtColor = findViewById(R.id.txtCarColor);
        txtSeat = findViewById(R.id.txtCarSeat);
        txtDescription = findViewById(R.id.txtCarDescription);
        txtCarLicensePlates = findViewById(R.id.txtLicensePlates);
        carDAO = new CarDAO();
        imgCar = findViewById(R.id.imgCar);
        imgCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });
        fStore = FirebaseFirestore.getInstance();
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imgName = System.currentTimeMillis()
                        + "." + getFileExtension(imgUri);
                String carName = String.valueOf(txtCarName.getEditText().getText());
                float carPrice = Float.parseFloat(String.valueOf(txtCarPrice.getEditText().getText()));
                String carColor = String.valueOf(txtColor.getEditText().getText());
                String carSet = String.valueOf(txtSeat.getEditText().getText());
                String carDescription = String.valueOf(txtDescription.getEditText().getText());
                String carLicensePlates = String.valueOf(txtCarLicensePlates.getEditText().getText());
                Car newCar = new Car();
                newCar.setCarImage(imgName);
                newCar.setCarName(carName);
                newCar.setCarPrice(carPrice);
                newCar.setCarColor(carColor);
                newCar.setCarImage(imgName);
                newCar.setCarSeat(carSet);
                newCar.setCarDescription(carDescription);
                newCar.setCarLicensePlates(carLicensePlates);
                DocumentReference documentReference = fStore.collection("cars").document();
                documentReference.set(newCar).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CreateCarActivity.this, "Car created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ListCarActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
                if (imgUri != null) {
                    carDAO.uploadFile(imgUri, imgName, CreateCarActivity.this, "car_images");
                } else {
                    Toast.makeText(CreateCarActivity.this, "No file selected", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
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

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
