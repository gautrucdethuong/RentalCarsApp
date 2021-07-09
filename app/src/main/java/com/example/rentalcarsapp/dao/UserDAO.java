package com.example.rentalcarsapp.dao;

import androidx.annotation.NonNull;

import com.example.rentalcarsapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/9/2021.
 * Company: FPT大学.
 */

public class UserDAO {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    boolean response;

    public boolean isStatusLogin() {
        return response;
    }

    public void setStatusLogin(boolean statusLogin) {
        this.response = statusLogin;
    }
}
