package com.example.rentalcarsapp.ui.admin.bill;

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
import com.example.rentalcarsapp.apdapter.BillListAdapter;
import com.example.rentalcarsapp.apdapter.BookingListAdapter;
import com.example.rentalcarsapp.model.Bill;
import com.example.rentalcarsapp.model.Booking;
import com.example.rentalcarsapp.ui.admin.booking.ListBookingActivity;
import com.example.rentalcarsapp.ui.admin.car.ListCarActivity;
import com.example.rentalcarsapp.ui.admin.statistical.BarChartActivity;
import com.example.rentalcarsapp.ui.admin.statistical.PieChartActivity;
import com.example.rentalcarsapp.ui.home.user.UsersManagementActivity;
import com.example.rentalcarsapp.ui.login.EditProfileActivity;
import com.example.rentalcarsapp.ui.login.LoginActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * Author by Tieu Ha Anh Khoi
 * Email: khoithace140252@fpt.edu.vn.
 * Date on 7/4/2021.
 * Company: FPT大学.
 */
public class ListBillActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView recyclerView;
    private FirestoreRecyclerOptions<Bill> options;
    private FirebaseFirestore fireStore;
    private BillListAdapter adapter;
    private EditText inputSearch;

    private View header;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView textViewName, textViewEmail;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_car);
        user = FirebaseAuth.getInstance().getCurrentUser();


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

        fireStore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        inputSearch = findViewById(R.id.inputSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Query querybill = fireStore.collection("bills");
        options = new FirestoreRecyclerOptions.Builder<Bill>().setQuery(querybill, Bill.class).build();
        adapter=new BillListAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() != null){
                    processsearch(s.toString());

                }else{
                    processsearch("");
                }
            }
        });
    }
    private void processsearch(String text){
        Query querybill = fireStore.collection("bills").orderBy("billCreateddate").startAt(text).endAt(text+"\uf8ff");
        options = new FirestoreRecyclerOptions.Builder<Bill>().setQuery(querybill, Bill.class).build();
        adapter=new BillListAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    // get information user login
    private void getInfoUserByDrawer() {
        if (user != null) {
            String email = user.getEmail();
            String phone = user.getPhoneNumber();

            textViewName.setText(email);
            textViewEmail.setText(phone);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home_month:
                Intent intentHome = new Intent(getApplicationContext(), BarChartActivity.class);
                startActivity(intentHome);
                finish();

                break;
            case R.id.nav_home_brand:
                Intent intentProfile = new Intent(getApplicationContext(), PieChartActivity.class);
                startActivity(intentProfile);
                finish();

                break;
            case R.id.nav_user_management:
                Intent intentUsersManagement = new Intent(getApplicationContext(), UsersManagementActivity.class);
                startActivity(intentUsersManagement);
                //sao k qia
                finish();
                break;

            case R.id.nav_booking_management:
                Intent booking = new Intent(getApplicationContext(), ListBookingActivity.class);
                startActivity(booking);
                //nay no k chay bro fix đi bấm k dc
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
}
