package com.example.rentalcarsapp.dao;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.rentalcarsapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.auth.User;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

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
        fStore = FirebaseFirestore.getInstance();

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // Get the document, forcing the SDK to use the offline cache
                if (task.isSuccessful()) {
                    String userId = fAuth.getCurrentUser().getUid();
                    DocumentReference docRef = fStore.collection("users").document(userId);

                    // Source can be CACHE, SERVER, or DEFAULT.
                    Source source = Source.SERVER;
                    docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                // Document found in the offline cache
                                DocumentSnapshot document = task.getResult();

                                Log.d("TAG", "Cached document data: " + document.getData());
                                Map<String, Object> user = document.getData();
                                boolean statusUser = Boolean.parseBoolean(String.valueOf(user.get("userStatus")));

                                String role = String.valueOf(user.get("roleName"));
                                Log.e("fStoreID", role);
                                Log.e("statusUser", String.valueOf(statusUser));
                                if (statusUser) {
                                    role = "deactive";
                                }
                                callback.isLogin(role);

                            } else {
                                Log.d("TAG", "Cached get failed: ", task.getException());
                            }
                        }
                    });

                } else {
                    callback.isLogin("");
                }
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
