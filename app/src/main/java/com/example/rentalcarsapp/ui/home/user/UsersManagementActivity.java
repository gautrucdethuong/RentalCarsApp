package com.example.rentalcarsapp.ui.home.user;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/10/2021.
 * Company: FPT大学.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.rentalcarsapp.DashboardActivity;
import com.example.rentalcarsapp.MainActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.apdapter.UsersListApdapter;
import com.example.rentalcarsapp.model.User;
import com.example.rentalcarsapp.ui.admin.bill.ListBillActivity;
import com.example.rentalcarsapp.ui.admin.booking.ListBookingActivity;
import com.example.rentalcarsapp.ui.admin.car.ListCarActivity;
import com.example.rentalcarsapp.ui.admin.statistical.BarChartActivity;
import com.example.rentalcarsapp.ui.admin.statistical.PieChartActivity;
import com.example.rentalcarsapp.ui.admin.user.CreateUserActivity;
import com.example.rentalcarsapp.ui.admin.user.UpdateUserActivity;
import com.example.rentalcarsapp.ui.login.EditProfileActivity;
import com.example.rentalcarsapp.ui.login.LoginActivity;
import com.example.rentalcarsapp.ui.register.RegisterInforActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class UsersManagementActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // creating a variable for our list view,
    // arraylist and firebase Firestore.
    ListView coursesLV;
    ArrayList<User> dataModalArrayList;
    FirebaseFirestore db;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    MenuBuilder menuBuilder;
    Button buttonNew;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        imgBack = findViewById(R.id.ImageBtnBack);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_closed);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        // below line is use to initialize our variables
        coursesLV = findViewById(R.id.idLVCourses);
        dataModalArrayList = new ArrayList<>();
        buttonNew = findViewById(R.id.new_user);
        backToList();
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // here we are calling a method
        // to load data in our list view.
        loadDatainListview();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    // function back to list
    private void backToList(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void loadDatainListview() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        menuBuilder = new MenuBuilder(this);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.poupup_menu, menuBuilder);
        //db.collection("users").whereEqualTo("roleName","Customer").get()
        db.collection("users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding
                            // our progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                User dataModal = d.toObject(User.class);
                                // below is the updated line of code which we have to
                                // add to pass the document id inside our modal class.
                                // we are setting our document id with d.getId() method
                                //dataModal.setUserId(Integer.parseInt(d.getId()));
                                // after getting data from Firebase we are
                                // storing that data in our array list
                                dataModalArrayList.add(dataModal);
                            }
                            // after that we are passing our array list to our adapter class.
                            UsersListApdapter adapter = new UsersListApdapter(UsersManagementActivity.this, dataModalArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.

                            coursesLV.setAdapter(adapter);
                            coursesLV.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

                                /**
                                 * Callback method to be invoked when an item in this AdapterView has
                                 * been clicked.
                                 * <p>
                                 * Implementers can call getItemAtPosition(position) if they need
                                 * to access the data associated with the selected item.
                                 *
                                 * @param parent   The AdapterView where the click happened.
                                 * @param view     The view within the AdapterView that was clicked (this
                                 *                 will be a view provided by the adapter)
                                 * @param position The position of the view in the adapter.
                                 * @param id       The row id of the item that was clicked.
                                 */
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    MenuPopupHelper optionMenu = new MenuPopupHelper(UsersManagementActivity.this, menuBuilder, view);
                                    final User currentLecture = dataModalArrayList.get(position);
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot d : list) {
                                        currentLecture.setStaffCode(list.get(position).getId());
                                    }
                                    Log.d("TAG", "Mih meo person id: " + currentLecture.getStaffCode());
                                    Log.d("TAG", "Mih meo position: " + position);
                                    final View currentView = view;
                                    menuBuilder.setCallback(new MenuBuilder.Callback() {
                                        @Override
                                        public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                                            switch (item.getItemId()) {
                                                case R.id.one:
                                                    //                                                  call edit activity
                                                    Intent intent = new Intent(currentView.getContext(), UpdateUserActivity.class);
                                                    intent.putExtra("person", currentLecture);
                                                    startActivity(intent);
                                                    finish();

                                                    break;
                                                case R.id.three:
                                                    DocumentReference docRef = db.collection("users").document(currentLecture.getStaffCode());
                                                    // Source can be CACHE, SERVER, or DEFAULT.
                                                    Source source = Source.CACHE;
                                                    // Get the document, forcing the SDK to use the offline cache
                                                    docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                // Document found in the offline cache
                                                                DocumentSnapshot document = task.getResult();
                                                                Map<String, Object> user = document.getData();
                                                                if ((user.get("userStatus").toString()) == "false") {
                                                                    user.put("userStatus", true);
                                                                    docRef.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            Toast.makeText(UsersManagementActivity.this, "Delete User successful", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                } else {
                                                                    user.put("userStatus", false);
                                                                    docRef.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            Toast.makeText(UsersManagementActivity.this, "Un-Delete User successful", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                }
                                                            } else {
                                                                Log.d("TAG", "Cached get failed: ", task.getException());
                                                            }
                                                        }
                                                    });
                                                    break;
                                                //      case R.id.change_role:
//                                                    // function remove lecture
                                                //        db.removeLecture(currentLecture.getId());
                                                //        reloadView();
                                                //         break;
                                                default:
                                                    return false;
                                            }
                                            return false;
                                        }

                                        @Override
                                        public void onMenuModeChange(@NonNull MenuBuilder menu) {

                                        }
                                    });
                                    optionMenu.show();
//            Log.e("E",currentLecture.getName());

                                }
                            });
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(UsersManagementActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message
                // when we get any error from Firebase.
                Toast.makeText(UsersManagementActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent intentDashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intentDashboard);
                finish();
                break;
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
}
