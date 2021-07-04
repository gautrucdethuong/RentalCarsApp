package com.example.rentalcarsapp.ui.home.car;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.apdapter.CarListAdapter;
import com.example.rentalcarsapp.model.Car;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/4/2021.
 * Company: FPT大学.
 */

public class RecyclerCarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirestoreRecyclerOptions<Car> options;
    private FirestoreRecyclerAdapter<Car, CarListAdapter> adapter;
    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_car);

        fireStore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        loadListViewCar("");
        searchByName();

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

    private void loadListViewCar(String searchName){
        // Query
        Query query = fireStore.collection("cars").limit(5).orderBy("carName").startAt(searchName).endAt(searchName+"\uf8ff");

        options = new FirestoreRecyclerOptions.Builder<Car>().setQuery(query, Car.class).build();
        adapter = new FirestoreRecyclerAdapter<Car, CarListAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CarListAdapter holder, int position, @NonNull Car model) {
                holder.textViewNameCar.setText(model.getCarName());
                holder.textViewPrice.setText("$ "+model.getCarPrice().toString() +" / Daily");
                holder.textViewRating.setText("Rating: "+ "🌟🌟🌟🌟🌟");
                // load image from URL in our Image VIew.
                Picasso.get().load(model.getCarImage())
                        .error(R.drawable.user)
                        .into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RecyclerCarActivity.this, CarDetailsActivity.class);
                        intent.putExtra("carName", model.getCarName());
                        intent.putExtra("carPrice", model.getCarPrice());
                        intent.putExtra("carRating", "Rating: "+ "🌟🌟🌟🌟🌟");
                        intent.putExtra("carImage", model.getCarImage());
                        intent.putExtra("carSeat", model.getCarSeat());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public CarListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_car_homepage,parent,false);
                return new CarListAdapter(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
