package com.example.rentalcarsapp.ui.admin.statistical;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.DashboardActivity;
import com.example.rentalcarsapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/13/2021.
 * Company: FPT大学.
 */

public class RevenueYearActivity extends AppCompatActivity {
    // initialize variable list
    private ImageView imgBack;
    private BarChart barChart;
    private FirebaseFirestore fireStore;

    /**
     * onCreate
     * Call function active
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);

        init();
        drawerBarChart();
        backToList();

    }

    /**
     * initializing our UI components of list view item.
     */
    private void init() {
        fireStore = FirebaseFirestore.getInstance();
        imgBack = findViewById(R.id.ImageBtnBack);
        barChart = findViewById(R.id.barChart);
    }

    /**
     * Function drawer bar chart
     */
    private void drawerBarChart() {
        fireStore.collection("bookings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            /**
             * Access data from firebase
             * Plot the graph of the total amount of rent by year
             * Get data in firebase
             * @param task
             */
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    double sum17 = 0, sum18 = 0, sum19 = 0, sum20 = 0, sum21 = 0;

                    /**
                     * Loop get list booking in firebase
                     * Parse and get date from Timestamp
                     */
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Date yearFromDateBooking = document.getTimestamp("bookingDropOffDate").toDate();
                        double statusPay = Double.valueOf(String.valueOf(document.getData().get("bookingStatus")));
                        double totalBookingInDB = Double.valueOf(String.valueOf(document.getData().get("bookingTotal")));

                        if (yearFromDateBooking.getYear() == 117 && statusPay == 3) {
                            double revenueYear2017 = totalBookingInDB;
                            sum17 = sum17 + revenueYear2017;
                        }

                        if (yearFromDateBooking.getYear() == 118 && statusPay == 3) {
                            double revenueYear2018 = totalBookingInDB;
                            sum18 = sum18 + revenueYear2018;
                        }

                        if (yearFromDateBooking.getYear() == 119 && statusPay == 3) {
                            double revenueYear2019 = totalBookingInDB;
                            sum19 = sum19 + revenueYear2019;
                        }

                        if (yearFromDateBooking.getYear() == 120 && statusPay == 3) {
                            double revenueYear2020 = totalBookingInDB;
                            sum20 = sum20 + revenueYear2020;
                        }

                        if (yearFromDateBooking.getYear() == 121 && statusPay == 3) {
                            double revenueYear2021 = totalBookingInDB;
                            sum21 = sum21 + revenueYear2021;
                        }
                    }
                    /**
                     * Set data to BarChart
                     */
                     if (sum17 != 0 || sum18 != 0 || sum19 != 0 || sum20 != 0 || sum21 != 0) {
                    ArrayList<BarEntry> visitors = new ArrayList<>();

                    visitors.add(new BarEntry(2017, (float) sum17));
                    visitors.add(new BarEntry(2018, (float) sum18));
                    visitors.add(new BarEntry(2019, (float) sum19));
                    visitors.add(new BarEntry(2020, (float) sum20));
                    visitors.add(new BarEntry(2021, (float) sum21));

                    BarDataSet barDataSet = new BarDataSet(visitors, "YEAR");
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    barDataSet.getValueTextColor(Color.BLACK);

                    barDataSet.setValueTextSize(14f);

                    BarData barData = new BarData(barDataSet);
                    barChart.setFitBars(true);
                    barChart.setData(barData);
                    barChart.getDescription().setText("Revenue Year");
                    barChart.animateY(2000);
                }else{
                        Toast.makeText(RevenueYearActivity.this, "No revenue for the year", Toast.LENGTH_SHORT).show();
                        finish();
                    }
            }
             }
        });
    }

    /**
     * Back to DashboardActivity
     */
    private void backToList() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
            }
        });
    }

    double sum20 = 0;

    //Log.e("SUM", String.valueOf(sum));


    /*//long statusPay = (long) document.getData().get("bookingStatus");

                        if (yearFromDateBooking.getYear() == 117 *//*&& statusPay == 3*//*) {
        revenueYear17 = (double) document.getData().get("bookingTotal");
        revenueYear17++;
    } else if (yearFromDateBooking.getYear() == 118 *//*&& statusPay == 3*//*) {
        revenueYear18 = (double) document.getData().get("bookingTotal");
        revenueYear18++;
    } else if (yearFromDateBooking.getYear() == 119 *//*&& statusPay == 3*//*) {
        revenueYear19 = (double) document.getData().get("bookingTotal");
        revenueYear19++;
    } else if (yearFromDateBooking.getYear() == 120 *//*&& statusPay == 3*//*) {
        revenueYear20 = (double) document.getData().get("bookingTotal");
        //revenueYear20++;
        sum20 = revenueYear20 + revenueYear20;
    } else {
        revenueYear21 = (double) document.getData().get("bookingTotal");
        sum21 = revenueYear21 + revenueYear21;
    }*/
}
