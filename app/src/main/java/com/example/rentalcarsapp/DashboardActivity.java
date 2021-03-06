package com.example.rentalcarsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.rentalcarsapp.ui.admin.bill.ListBillActivity;
import com.example.rentalcarsapp.ui.admin.booking.ListBookingActivity;
import com.example.rentalcarsapp.ui.admin.car.ListCarActivity;
import com.example.rentalcarsapp.ui.admin.statistical.RevenueYearActivity;
import com.example.rentalcarsapp.ui.admin.statistical.RevenueBrandActivity;
import com.example.rentalcarsapp.ui.home.user.UsersManagementActivity;
import com.example.rentalcarsapp.ui.login.EditProfileActivity;
import com.example.rentalcarsapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private View header;
    private TextView textViewName, textViewEmail;
    private TextView personCount, adminCount, saleStaffCount, carCount;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private StorageReference storageReference;
    private FirebaseUser user;
    /**
     * oncreate when click on it will active it
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fAuth = FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();

        storageReference = storage.getReference();
        personCount = findViewById(R.id.textView3);
        adminCount = findViewById(R.id.textView4);
        saleStaffCount = findViewById(R.id.textView6);
        carCount = findViewById(R.id.textView7);

        fStore = FirebaseFirestore.getInstance();
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
        fStore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int perCount = 0;
                    int adCount = 0;
                    int sataffCount = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.get("roleName").equals("Customer")) {
                            perCount++;
                        } else if (document.get("roleName").equals("Admin")) {
                            adCount++;
                        } else {
                            sataffCount++;
                        }
                    }
                    if (perCount != 0 || adCount != 0 || sataffCount != 0) {
                        personCount.setText(perCount + " Person");
                        adminCount.setText(adCount + " Admin");
                        saleStaffCount.setText(sataffCount + " Sale Staff");
                    } else {
                        personCount.setText("Person");
                        adminCount.setText("Admin");
                        saleStaffCount.setText("Sale Staff");
                    }
                }
            }
        });
        fStore.collection("cars").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int cCount = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            cCount++;
                        }
                    }
                    if (cCount != 0) {
                        carCount.setText(cCount + " Car");
                    } else {
                        carCount.setText("Car");
                    }
                }
            }
        });
    }


    /**
     * navigation (Tong or Minh Huynh)=))
     * @param menuItem
     * @return
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
