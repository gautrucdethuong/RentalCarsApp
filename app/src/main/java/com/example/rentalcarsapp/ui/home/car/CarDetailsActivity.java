package com.example.rentalcarsapp.ui.home.car;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.model.Car;
import com.example.rentalcarsapp.ui.home.booking.BookingDetails;
import com.example.rentalcarsapp.ui.home.payment.ChoosePaymentActivity;
import com.example.rentalcarsapp.ui.home.payment.ChooseTimeActivity;
import com.example.rentalcarsapp.ui.home.payment.CreditCard;
import com.example.rentalcarsapp.ui.register.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.squareup.picasso.Picasso;

import java.util.Map;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/4/2021.
 * Company: FPT大学.
 */

public class CarDetailsActivity extends AppCompatActivity {
    // variable intilized
    private ImageView imgBack;
    private ImageView imageView;
    private TextView textViewCarName,textViewPriceCar,
            textViewDescriptionCar,textViewSeatCar;
    private RatingBar ratingBarCar;
    private FirebaseFirestore fireStore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        init();
        getDetailsCar();
        booking();
        backToList();
    }
    // initializing our UI components of list view item.
    private void init(){
        imageView = findViewById(R.id.imageViewCarDetails);
        textViewCarName = findViewById(R.id.txtCarNameDetails);
        textViewPriceCar = findViewById(R.id.txtPriceCarDetails);
        ratingBarCar = findViewById(R.id.ratingBar_all);
        textViewDescriptionCar = findViewById(R.id.txtDescriptionDetails);
        textViewSeatCar = findViewById(R.id.txtSeatCarDetails);
        imgBack = findViewById(R.id.ImageBtnBack);
        // Access a Cloud FireStore instance from your Activity
        fireStore = FirebaseFirestore.getInstance();
    }

    // get car details by id car
    private void getDetailsCar(){
        DocumentReference docRef = fireStore.collection("cars").document("car-id");

        String carName = getIntent().getStringExtra("carName");
        float carPrice = getIntent().getFloatExtra("carPrice", -1);
        String carImage = getIntent().getStringExtra("carImage");
        String carSeat= getIntent().getStringExtra("carSeat");
        float carRating = getIntent().getFloatExtra("carRating", -1);
        String carDescription = getIntent().getStringExtra("carDescription");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document != null){
                        Picasso.get().load(carImage).into(imageView);
                        textViewCarName.setText(carName);
                        textViewPriceCar.setText("$ " + carPrice +"/ Daily");
                        ratingBarCar.setRating(carRating);
                        textViewSeatCar.setText("Car seat: " + carSeat);
                        textViewDescriptionCar.setText(carDescription);
                    }else{
                        Log.e("Response", "No such document");
                    }
                }else{
                    Log.e("Response", String.valueOf(task.getException()));
                }
            }
        });
    }

    // function add booking
    private void booking(){
        FloatingActionButton buttonBooking = findViewById(R.id.floatingBtn_Booking);
        buttonBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChooseTimeActivity.class));
                finish();

            }
        });
    }

    // function back to list
    private void backToList(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RecyclerCarActivity.class));
                finish();
            }
        });
    }

}
