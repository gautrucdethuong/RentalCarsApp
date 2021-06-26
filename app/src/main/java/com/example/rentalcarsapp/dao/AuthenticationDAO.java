package com.example.rentalcarsapp.dao;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/9/2021.
 * Company: FPT大学.
 */

public class AuthenticationDAO {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    public void loginFirebaseAuthentication(String email, String password, Callback callback) {
        fAuth = FirebaseAuth.getInstance();

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                callback.isLogin(task.isSuccessful());
            }
        });

    }

    /*public void registerFirebaseAuthentication(String emailAddress, String password, String fullName, String phoneNumber, Callback callback) {

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        @SuppressLint("RestrictedApi") User userInfo = new User(emailAddress, fullName, phoneNumber, "Customer", 1);

        fAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String userID = fAuth.getCurrentUser().getUid();

                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    documentReference.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            callback.isRegister(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.isRegister(false);
                        }
                    });
                }
            }
        });
    }*/
}
