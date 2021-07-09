package com.example.rentalcarsapp.ui.home.booking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.ui.home.payment.ChooseTimeActivity;

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
        buttonChangeTime = findViewById(R.id.buttonChangeTime);
        buttonVerifyCode = findViewById(R.id.buttonVerifyCode);
        buttonAddBooking = findViewById(R.id.buttonAddBooking);

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
        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        int totalDay = getIntent().getIntExtra("TotalDay",-1);

        float car_price = sharedPreferences.getFloat("CAR_PRICE", -1);
        float total = car_price * totalDay + 50 - car_price * 10/100;

        Log.e("total", String.valueOf(total));
        textViewPriceCar.setText("$ "+ (int) car_price);
        textViewTotalMoney.setText("$ "+total);
    }

    private void addBooking(){
        buttonAddBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
