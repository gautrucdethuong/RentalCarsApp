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

import java.util.ArrayList;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/13/2021.
 * Company: FPT大学.
 */

public class PieChartActivity extends AppCompatActivity {

    private PieChart pieChart;
    private ImageView imgBack;
    private CollectionReference collectionReference;
    private FirebaseFirestore fireStore;
    private static final String TAG = "RESULT";
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_PIE_CHART= "myPrefsPieChart";
    private SharedPreferences.Editor editor;
    private TextView textViewTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        init();
        backToList();
        loadPieChartData();
        setupPieChart();

    }

    private void init(){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_PIE_CHART, Context.MODE_PRIVATE);
        pieChart = findViewById(R.id.pieChart);
        imgBack = findViewById(R.id.ImageBtnBack);
        fireStore = FirebaseFirestore.getInstance();
        textViewTitle = findViewById(R.id.textViewTitle);
    }

    private void loadPieChartData(){
        collectionReference = fireStore.collection("cars");
        editor = sharedPreferences.edit();

        collectionReference.whereEqualTo("carBrand", "Audi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        editor.putFloat("countAudi", task.getResult().size());
                        editor.apply();
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        collectionReference.whereEqualTo("carBrand", "Toyota").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        editor.putFloat("countToyota", task.getResult().size());
                        editor.apply();
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        collectionReference.whereEqualTo("carBrand", "Ford").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        editor.putFloat("countFord", task.getResult().size());
                        editor.apply();
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        collectionReference.whereEqualTo("carBrand", "Honda").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        editor.putFloat("countHonda", task.getResult().size());
                        editor.apply();
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        collectionReference.whereEqualTo("carBrand", "Hyundai").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        editor.putFloat("countHyundai", task.getResult().size());
                        editor.apply();
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });


        float countAudi = sharedPreferences.getFloat("countAudi", -1);
        System.out.println("audi"+countAudi);
        float countToyota = sharedPreferences.getFloat("countToyota", -1);
        System.out.println("Toyota"+countToyota);
        float countFord = sharedPreferences.getFloat("countFord", -1);
        System.out.println("ford"+countFord);
        float countHonda = sharedPreferences.getFloat("countHonda", -1);
        System.out.println("honda"+countHonda);
        float countHyundai = sharedPreferences.getFloat("countHyundai", -1);
        System.out.println("huyadai"+countHyundai);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(countAudi, "Audi"));
        entries.add(new PieEntry(countToyota, "Toyota"));
        entries.add(new PieEntry(countFord, "Ford"));
        entries.add(new PieEntry(countHonda, "Honda"));
        entries.add(new PieEntry(countHyundai, "Hyundai"));

        ArrayList<Integer> colors = new ArrayList<>();
        for(int color : ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }

        for(int color : ColorTemplate.VORDIPLOM_COLORS){
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
    }

    private void setupPieChart(){
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

    private void backToList(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
            }
        });
    }
}
