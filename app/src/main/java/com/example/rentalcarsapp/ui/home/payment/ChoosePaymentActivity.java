package com.example.rentalcarsapp.ui.home.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.ui.home.car.RecyclerCarActivity;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/5/2021.
 * Company: FPT大学.
 */

public class ChoosePaymentActivity extends AppCompatActivity {

    private Button btnVisa, btnDirect;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_method_payment);

        btnVisa = findViewById(R.id.btnMethodVisa);
        btnDirect = findViewById(R.id.btnMethodNormal);

        paymentDirect();
        paymentVisa();
        backToList();

    }
    private void paymentVisa(){
        btnVisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreditCard.class));
                finish();
            }
        });
    }

    private void paymentDirect(){
        btnDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RecyclerCarActivity.class));
                finish();
            }
        });
    }

    private void backToList(){

        imgBack = findViewById(R.id.ImageBtnBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RecyclerCarActivity.class));
                finish();
            }
        });
    }
}
