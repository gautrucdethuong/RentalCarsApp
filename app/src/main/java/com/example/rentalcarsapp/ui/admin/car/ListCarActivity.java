package com.example.rentalcarsapp.ui.admin.car;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalcarsapp.DashboardActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.apdapter.CarListAdapter;
import com.example.rentalcarsapp.apdapter.CarListAminAdapter;
import com.example.rentalcarsapp.model.Car;
import com.example.rentalcarsapp.ui.admin.user.UpdateUserActivity;
import com.example.rentalcarsapp.ui.home.car.CarDetailsActivity;
import com.example.rentalcarsapp.ui.home.user.UsersManagementActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ListCarActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirestoreRecyclerOptions<Car> options;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore fireStore;
    private FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_car_admin);
        fireStore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        loadListViewCar("");
        searchByName();
        fb=(FloatingActionButton)findViewById(R.id.f_add_car);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateCarActivity.class));
                finish();
            }
        });
    }
    private void searchByName(){
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
                if(s.toString() != null){
                    loadListViewCar(s.toString());

                }else{
                    loadListViewCar("");
                }
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void loadListViewCar(String searchName){
        // Query
        // below line is use to get data from Firebase
        // firestore using collection in android.
//        Query query = fireStore.collection("cars").limit(7).orderBy("carName").startAt(text.toUpperCase()).endAt(text+"\uf8ff");
//        options = new FirestoreRecyclerOptions.Builder<Car>().setQuery(query, Car.class).build();
//        adapter = new CarListAminAdapter(options);




        Query query = fireStore.collection("cars").orderBy("carName").startAt(searchName).endAt(searchName+"\uf8ff");
        options = new FirestoreRecyclerOptions.Builder<Car>().setQuery(query, Car.class).build();
        adapter = new CarListAminAdapter(options,ListCarActivity.this);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
