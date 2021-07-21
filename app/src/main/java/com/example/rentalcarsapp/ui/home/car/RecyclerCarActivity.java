package com.example.rentalcarsapp.ui.home.car;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.apdapter.admin.CarListAdapter;
import com.example.rentalcarsapp.model.Car;
import com.example.rentalcarsapp.ui.admin.booking.ListBookingActivity;
import com.example.rentalcarsapp.ui.home.booking.ListBookingUserActivity;
import com.example.rentalcarsapp.ui.login.EditProfileActivity;
import com.example.rentalcarsapp.ui.login.LoginActivity;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/4/2021.
 * Company: FPT大学.
 */

public class RecyclerCarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // variable initialization
    private RecyclerView recyclerView;
    private FirestoreRecyclerOptions<Car> options;
    private FirebaseFirestore fireStore;

    private View header;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView textViewName, textViewEmail;
    private FirebaseUser user;

    private CarListAdapter adapter;

    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_car);

        fireStore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        init();
        getInfoUserByDrawer();
        loadListViewCar("");
        searchByName();
    }


    /**
     * Function get information when user login
     * Set data in UI
     */
    private void getInfoUserByDrawer() {
        if (user != null) {
            String email = user.getEmail();
            String phone = user.getPhoneNumber();

            textViewName.setText(email);
            textViewEmail.setText(phone);
        }
    }


    /**
     * // Function initializing our UI components of list view item.
     */
    private void init() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        header = navigationView.getHeaderView(0);
        textViewName = header.findViewById(R.id.textViewName);
        textViewEmail = header.findViewById(R.id.textViewEmail);
        toolbar = findViewById(R.id.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_closed);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    /**
     * Event addTextChangedListener
     * Function search car by name car
     *  Search when data change
     */
    private void searchByName() {
        EditText inputSearch = findViewById(R.id.inputSearch);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null) {
                    loadListViewCar(s.toString());

                } else {
                    loadListViewCar("");
                }
            }
        });
    }

    /**
     * Function load list car
     * Query filter car list by condition
     * @param text
     */
    private void loadListViewCar(String text){
        Query query = fireStore.collection("cars").limit(8).orderBy("carName").startAt(text.toUpperCase()).endAt(text+"\uf8ff");
        options = new FirestoreRecyclerOptions.Builder<Car>().setQuery(query, Car.class).build();
        adapter = new CarListAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    /**
     * onStart
     */
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    /**
     * onStop
     */
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }



    /**
     * List onNavigationItemSelected
     * // Drawer function user
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent intentHome = new Intent(getApplicationContext(), RecyclerCarActivity.class);
                startActivity(intentHome);
                finish();

                break;
            case R.id.nav_profile:
                Intent profile = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(profile);
                finish();
                break;
            case R.id.nav_history_rental_car:
                Intent intentHistory = new Intent(getApplicationContext(), ListBookingUserActivity.class);
                startActivity(intentHistory);
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
}
