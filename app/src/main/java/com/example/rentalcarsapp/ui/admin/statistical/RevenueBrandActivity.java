package com.example.rentalcarsapp.ui.admin.statistical;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.rentalcarsapp.DashboardActivity;
import com.example.rentalcarsapp.MainActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.model.Car;
import com.example.rentalcarsapp.ui.admin.bill.ListBillActivity;
import com.example.rentalcarsapp.ui.admin.booking.ListBookingActivity;
import com.example.rentalcarsapp.ui.admin.car.ListCarActivity;
import com.example.rentalcarsapp.ui.home.car.RecyclerCarActivity;
import com.example.rentalcarsapp.ui.home.payment.ChooseTimeActivity;
import com.example.rentalcarsapp.ui.home.user.UsersManagementActivity;
import com.example.rentalcarsapp.ui.login.EditProfileActivity;
import com.example.rentalcarsapp.ui.login.LoginActivity;
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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class RevenueBrandActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // initialize variable list
    private PieChart pieChart;
    private ImageView imgBack;
    private FirebaseFirestore fireStore;
    private static final String TAG = "RESULT";
    private TextView textViewTitle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private View header;
    private TextView textViewName, textViewEmail;
    FirebaseAuth fAuth;
    FirebaseUser user;
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
        fAuth = FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_closed);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);
        textViewName = header.findViewById(R.id.textViewName);
        textViewEmail = header.findViewById(R.id.textViewEmail);
        getInfoUserByDrawer();
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
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
//                finish();
//            }
//        });
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent intentDashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intentDashboard);
                finish();
                break;
            case R.id.nav_home_month:
                Intent intentHome = new Intent(getApplicationContext(), RevenueYearActivity.class);
                startActivity(intentHome);
                finish();

                break;
            case R.id.nav_home_brand:
                Intent intentProfile = new Intent(getApplicationContext(), RevenueBrandActivity.class);
                startActivity(intentProfile);
                finish();

                break;
            case R.id.nav_users_management:
                Intent intentUsersManagement = new Intent(getApplicationContext(), UsersManagementActivity.class);
                startActivity(intentUsersManagement);
                finish();
                break;
            case R.id.nav_profile:
                Intent profile = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(profile);
                finish();
                break;

            case R.id.nav_booking_management:
                Intent booking = new Intent(getApplicationContext(), ListBookingActivity.class);
                startActivity(booking);
                finish();
                break;
            case R.id.nav_car_management:
                Intent car = new Intent(getApplicationContext(), ListCarActivity.class);
                startActivity(car);
                finish();
                break;
            case R.id.nav_bill_management:
                Intent bill = new Intent(getApplicationContext(), ListBillActivity.class);
                startActivity(bill);
                finish();
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // get information user login
    private void getInfoUserByDrawer() {
        if (user != null) {
            String email = user.getEmail();
            String name = user.getDisplayName();
            textViewName.setText(name);
            textViewEmail.setText(email);

        }
    }
}
