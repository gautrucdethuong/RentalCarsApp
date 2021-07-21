package com.example.rentalcarsapp.ui.admin.statistical;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.DashboardActivity;
import com.example.rentalcarsapp.MainActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.model.Car;
import com.example.rentalcarsapp.ui.home.car.RecyclerCarActivity;
import com.example.rentalcarsapp.ui.home.payment.ChooseTimeActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/13/2021.
 * Company: FPT大学.
 */

public class RevenueBrandActivity extends AppCompatActivity {
    // initialize variable list
    private PieChart pieChart;
    private ImageView imgBack;
    private FirebaseFirestore fireStore;
    private static final String TAG = "RESULT";
    private TextView textViewTitle;

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        init();
        backToList();
        loadPieChartData();
        setupPieChart();

    }

    /**
     * initializing our UI components of list view item.
     */
    private void init() {

        pieChart = findViewById(R.id.pieChart);
        imgBack = findViewById(R.id.ImageBtnBack);
        fireStore = FirebaseFirestore.getInstance();
        textViewTitle = findViewById(R.id.textViewTitle);
    }

    /**
     * Access data from firebase
     * Count brand in firebase and drawer in PieChart
     */
    private void loadPieChartData() {
        fireStore.collection("cars").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            /**
             * onComplete
             * @param task
             */
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int countAudi = 0;
                    int countToyota = 0;
                    int countFord = 0;
                    int countHonda = 0;
                    int countHyundai = 0;
                    int countBMW = 0;
                    int countVinfast = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.get("carBrand").equals("Audi")) {
                            countAudi++;
                        } else if (document.get("carBrand").equals("Toyota")) {
                            countToyota++;
                        } else if(document.get("carBrand").equals("Ford")){
                            countFord++;
                        } else if(document.get("carBrand").equals("Honda")){
                            countHonda++;
                        }else if(document.get("carBrand").equals("Hyundai")){
                            countHyundai++;
                        }else if(document.get("carBrand").equals("BMW")){
                            countBMW++;
                        }else{
                            countVinfast++;
                        }
                    }
                    if (countAudi != 0 || countToyota != 0 || countFord != 0 || countHonda != 0 || countHyundai != 0 || countBMW != 0 || countVinfast != 0) {
                        ArrayList<PieEntry> entries = new ArrayList<>();
                        Log.e("Audi", String.valueOf(countAudi));
                        Log.e("Toyota", String.valueOf(countToyota));
                        Log.e("Ford", String.valueOf(countFord));
                        Log.e("Honda", String.valueOf(countHonda));
                        Log.e("Hyundai", String.valueOf(countHyundai));
                        entries.add(new PieEntry(countAudi, "Audi"));
                        entries.add(new PieEntry(countToyota, "Toyota"));
                        entries.add(new PieEntry(countFord, "Ford"));
                        entries.add(new PieEntry(countHonda, "Honda"));
                        entries.add(new PieEntry(countHyundai, "Hyundai"));
                        entries.add(new PieEntry(countBMW, "BMW"));
                        entries.add(new PieEntry(countVinfast, "Vinfast"));


                        /**
                         * Set up UI Pie Chart
                         * Set style color and animation
                         */
                        ArrayList<Integer> colors = new ArrayList<>();
                        for (int color : ColorTemplate.MATERIAL_COLORS) {
                            colors.add(color);
                        }

                        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
                            colors.add(color);
                        }

                        PieDataSet pieDataSet = new PieDataSet(entries, "Car Brand");
                        pieDataSet.setColors(colors);

                        PieData pieData = new PieData(pieDataSet);

                        pieData.setDrawValues(true);
                        pieData.setValueFormatter(new PercentFormatter(pieChart));
                        pieData.setValueTextSize(12f);
                        pieData.setValueTextColor(Color.BLACK);

                        pieChart.animate();
                        pieChart.setData(pieData);
                        pieChart.invalidate();

                        pieChart.animateY(1400, Easing.EaseInQuad);
                    } else {
                        Toast.makeText(RevenueBrandActivity.this, "No revenue for the year", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

    }

    /**
     * Set up UI Pie Chart
     */
    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Brand Top");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);

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
}
