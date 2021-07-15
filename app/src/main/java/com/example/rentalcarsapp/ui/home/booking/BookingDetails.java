package com.example.rentalcarsapp.ui.home.booking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.model.Booking;
import com.example.rentalcarsapp.model.Car;
import com.example.rentalcarsapp.ui.home.car.CarDetailsActivity;
import com.example.rentalcarsapp.ui.home.car.RecyclerCarActivity;
import com.example.rentalcarsapp.ui.home.payment.ChooseTimeActivity;
import com.example.rentalcarsapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/8/2021.
 * Company: FPT大学.
 */

public class BookingDetails extends AppCompatActivity {
    // variable initializing
    private Button buttonChangeTime, buttonVerifyCode, buttonAddBooking;
    private EditText editTextVoucher;
    private TextView textViewTime, textViewPriceCar, textViewDiscount, textViewInsurance, textViewTotalMoney, textViewDate;
    private ImageView imgBack;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_TOTAL_BOOKING= "myPrefsBooking";
    private FirebaseUser user;
    private FirebaseFirestore fStore;
    private SharedPreferences.Editor editor;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        // Call function
        init();
        changeTime();
        verifyCode();
        addBooking();
        backToList();
        totalMoney();
        setDateFromChooseTime();
        setDateFromChooseDate();
    }
    // function set Time Choose
    private void setDateFromChooseTime(){
        String timeFrom = getIntent().getStringExtra("timeFrom");
        String timeTo = getIntent().getStringExtra("timeTo");
        textViewTime.setText("Time: "+timeFrom+ " -> " + timeTo);
    }

    // function set Date Choose
    private void setDateFromChooseDate(){
        String dateFrom = getIntent().getStringExtra("dateFrom");
        String dateTo = getIntent().getStringExtra("dateTo");
        textViewDate.setText(dateFrom+ " -> " + dateTo);
    }
    // // initializing our UI components of list view item.
    private void init(){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_TOTAL_BOOKING, Context.MODE_PRIVATE);

        buttonChangeTime = findViewById(R.id.buttonChangeTime);
        buttonVerifyCode = findViewById(R.id.buttonVerifyCode);
        buttonAddBooking = findViewById(R.id.buttonAddBooking);
        // get authentication in firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        fStore = FirebaseFirestore.getInstance();

        editTextVoucher = findViewById(R.id.editTextVoucher);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);
        textViewPriceCar = findViewById(R.id.textViewPriceCar);
        textViewDiscount = findViewById(R.id.textViewDiscount);
        textViewInsurance = findViewById(R.id.textViewInsurance);
        textViewTotalMoney = findViewById(R.id.textViewTotalMoney);
        imgBack = findViewById(R.id.ImageBtnBack);
    }

    private void changeTime(){
        buttonChangeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChooseTimeActivity.class));
                finish();
            }
        });
    }

    private void verifyCode(){
        buttonVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    // function caculator total money
    private void totalMoney(){
        //editor = sharedPreferences.edit();
        //get data by SharedPreferences
        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        int totalDay = getIntent().getIntExtra("TotalDay",-1);

        float car_price = sharedPreferences.getFloat("CAR_PRICE", -1);

        float total = car_price * totalDay + 50 - car_price * 10/100;
        // save total price by SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("TOTAL_BOOKING", total);
        editor.apply();

        textViewPriceCar.setText("$ "+ (int) car_price);
        textViewTotalMoney.setText("$ "+total);
    }

    private void addBooking(){
        buttonAddBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBooking();

            }
        });
    }

    private void backToList(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChooseTimeActivity.class));
                finish();
            }
        });
    }

    private void createBooking(){
        if (user != null) {

            //sharedPreferences = getSharedPreferences("myPrefsBooking", Context.MODE_PRIVATE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateFrom = getIntent().getStringExtra("dateFrom");
            String dateTo = getIntent().getStringExtra("dateTo");
            float totalBooking = sharedPreferences.getFloat("TOTAL_BOOKING", -1);
            String carId = sharedPreferences.getString("CAR_ID",null);
            String userUid = user.getUid();

            Booking booking = null;
            try {
                booking = new Booking(simpleDateFormat.parse(dateFrom), simpleDateFormat.parse(dateTo), totalBooking, 1,carId, userUid,null);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DocumentReference documentReference =  fStore.collection("bookings").document();
            // insert your data ({ 'group_name'...) instead of data here
            documentReference.set(booking).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    String getId=String.valueOf(documentReference.getId());
                    Booking booking = null;
                    try {
                        booking = new Booking(simpleDateFormat.parse(dateFrom), simpleDateFormat.parse(dateTo), totalBooking, 1,carId, userUid,getId);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.e("ID:", getId);
                    fStore.collection("bookings").document(getId).set(booking);
                }
            });
        }
    }

//    private void showAlertDialog(int layout){
//        dialogBuilder = new AlertDialog.Builder(BookingDetails.this);
//        View layoutView = getLayoutInflater().inflate(layout, null);
//        Button dialogButton = layoutView.findViewById(R.id.btnDialog);
//        dialogBuilder.setView(layoutView);
//        alertDialog = dialogBuilder.create();
//        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog.show();
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//                startActivity(new Intent(getApplicationContext(), RecyclerCarActivity.class));
//                finish();
//            }
//        });
//    }
}
