package com.example.rentalcarsapp.ui.home;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/10/2021.
 * Company: FPT大学.
 */
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.dao.TestAdapterDAO;
import com.example.rentalcarsapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersManagementActivity extends AppCompatActivity{
    // creating a variable for our list view,
    // arraylist and firebase Firestore.
    ListView coursesLV;
    ArrayList<User> dataModalArrayList;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        // below line is use to initialize our variables
        coursesLV = findViewById(R.id.idLVCourses);
        dataModalArrayList = new ArrayList<>();

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // here we are calling a method
        // to load data in our list view.
        loadDatainListview();

    }


    private void loadDatainListview() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        db.collection("users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding
                            // our progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                User dataModal = d.toObject(User.class);

                                // after getting data from Firebase we are
                                // storing that data in our array list
                                dataModalArrayList.add(dataModal);
                            }
                            // after that we are passing our array list to our adapter class.
                            TestAdapterDAO adapter = new TestAdapterDAO(UsersManagementActivity.this, dataModalArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            coursesLV.setAdapter(adapter);

                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(UsersManagementActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message
                // when we get any error from Firebase.
                Toast.makeText(UsersManagementActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
