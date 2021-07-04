package com.example.rentalcarsapp.ui.home.car;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Map;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/4/2021.
 * Company: FPT大学.
 */

public class CarDetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textViewCarName;
    private TextView textViewPriceName;
    private TextView textViewRatingName;
    private TextView textViewDescriptionName;
    private FirebaseFirestore fireStore;


    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_car_details);
        imageView = findViewById(R.id.imageViewCarDetails);
        textViewCarName = findViewById(R.id.txtCarNameDetails);
        textViewPriceName = findViewById(R.id.txtPriceCarDetails);
        textViewRatingName = findViewById(R.id.txtRatingDetails);
        textViewDescriptionName = findViewById(R.id.txtDescriptionDetails);

        //fireStore.collection("cars");
        fireStore = FirebaseFirestore.getInstance();

        String carName = getIntent().getStringExtra("carName");
        String carPrice = getIntent().getStringExtra("carPrice");
        String carImage = getIntent().getStringExtra("carImage");
        //String carSeat= getIntent().getStringExtra("carSeat");
        String carRating = getIntent().getStringExtra("carRating");


        databaseReference.child(carImage).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
/*                    String carName = snapshot.child("carName").getValue().toString();
                    String carPrice = snapshot.child("carPrice").getValue().toString();
                    String carSeat = snapshot.child("carRating").getValue().toString();
                    String carImage = snapshot.child("")*/

                    Picasso.get().load(carImage).into(imageView);
                    textViewCarName.setText(carName);
                    textViewPriceName.setText(carPrice);
                    textViewRatingName.setText(carRating);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
