package com.example.rentalcarsapp.ui.home.payment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.cardform.view.CardForm;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.ui.home.car.RecyclerCarActivity;


/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/4/2021.
 * Company: FPT大学.
 */

public class CreditCard extends AppCompatActivity {

    private CardForm cardForm;
    private Button buttonPayment;
    private AlertDialog.Builder alertBuilder;
    private TextView textViewPriceTotal;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_form);

        cardForm = findViewById(R.id.card_form);
        textViewPriceTotal = findViewById(R.id.txtPriceTotal);
        buttonPayment = findViewById(R.id.btnBuy);

        validateCardForm();
        payment();
        backToChoosePayment();

    }

    private void validateCardForm(){
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                //.mobileNumberExplanation("SMS is required on this number")
                .setup(CreditCard.this);

        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
    }

    private void payment(){

        buttonPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardForm.isValid()) {
                    Toast.makeText(CreditCard.this, "Thank you for rental car.", Toast.LENGTH_LONG).show();
                    alertBuilder = new AlertDialog.Builder(CreditCard.this);
                    alertBuilder.setTitle("Payment Confirmation");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Toast.makeText(CreditCard.this, "Thank you for rental car.", Toast.LENGTH_LONG).show();
                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(CreditCard.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
    private void backToChoosePayment(){

        imgBack = findViewById(R.id.ImageBtnBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChoosePaymentActivity.class));
                finish();
            }
        });
    }
}
