package com.example.rentalcarsapp.dao;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.rentalcarsapp.helper.RegexValidate;
import com.example.rentalcarsapp.model.Car;
import com.example.rentalcarsapp.ui.admin.car.CreateCarActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class CarDAO {

    public void uploadFile(Uri imgUri, String imgName, Context context,String folder) {
        if (imgUri != null) {

        } else {
            Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }

}
