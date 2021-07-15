package com.example.rentalcarsapp.ui.admin.statistical;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.DashboardActivity;
import com.example.rentalcarsapp.MainActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.ui.home.car.RecyclerCarActivity;
import com.example.rentalcarsapp.ui.home.payment.ChooseTimeActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/13/2021.
 * Company: FPT大学.
 */

public class BarChartActivity extends AppCompatActivity {

    private ImageView imgBack;
    private BarChart barChart;
    private FirebaseFirestore fireStore;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_BAR_CHART = "myPrefsBarChart";
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);

        init();
        drawerBarChart();
        backToList();

    }

    private void init() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_BAR_CHART, Context.MODE_PRIVATE);
        fireStore = FirebaseFirestore.getInstance();
        imgBack = findViewById(R.id.ImageBtnBack);
        barChart = findViewById(R.id.barChart);
    }

    private double monthlyRevenue(){
        editor = sharedPreferences.edit();
        fireStore.collection("bookings")
                //.whereEqualTo("bookingPickUpDate",1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                double totalMonth = 0;
                                Date monthFromDateBooking = document.getTimestamp("bookingPickUpDate").toDate();
                                if (monthFromDateBooking.getMonth() == 6) {
                                    totalMonth += (Double) document.getData().get("bookingTotal");
                                }
                                System.out.println("sum "+totalMonth);
                            }
                            //return totalMonth;
                        } else {
                            Log.d("Result", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return 0;
    }


    private void drawerBarChart() {

        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(2015, 3200));
        visitors.add(new BarEntry(2016, 2001));
        visitors.add(new BarEntry(2017, 9807));
        visitors.add(new BarEntry(2018, 2809));
        visitors.add(new BarEntry(2019, 4205));
        visitors.add(new BarEntry(2020, 6707));
        visitors.add(new BarEntry(2021, 4200));

        BarDataSet barDataSet = new BarDataSet(visitors, "YEAR");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.getValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Revenue Year");
        barChart.animateY(2000);

    }

    private void backToList() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
            }
        });
    }
}
