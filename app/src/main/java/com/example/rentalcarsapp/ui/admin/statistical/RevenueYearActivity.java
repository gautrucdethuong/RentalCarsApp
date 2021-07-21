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
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.ui.admin.bill.ListBillActivity;
import com.example.rentalcarsapp.ui.admin.booking.ListBookingActivity;
import com.example.rentalcarsapp.ui.admin.car.ListCarActivity;
import com.example.rentalcarsapp.ui.home.user.UsersManagementActivity;
import com.example.rentalcarsapp.ui.login.EditProfileActivity;
import com.example.rentalcarsapp.ui.login.LoginActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class RevenueYearActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // initialize variable list
    private ImageView imgBack;
    private BarChart barChart;
    private FirebaseFirestore fireStore;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private View header;
    private TextView textViewName, textViewEmail;
    FirebaseAuth fAuth;
    FirebaseUser user;
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
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
//                finish();
//            }
//        });
    }

    double sum20 = 0;

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
