package com.example.rentalcarsapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.dao.AdapterUserDAO;
import com.example.rentalcarsapp.model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/9/2021.
 * Company: FPT大学.
 */

public class HomePageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterUserDAO adapterUserDAO;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Search here..");

        recyclerView = (RecyclerView) findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), User.class)
                        .build();

        adapterUserDAO = new AdapterUserDAO(options);
        recyclerView.setAdapter(adapterUserDAO);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fadd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddUserActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterUserDAO.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterUserDAO.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s) {
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("fullName").startAt(s).endAt(s + "\uf8ff"), User.class)
                        .build();

        adapterUserDAO = new AdapterUserDAO(options);
        adapterUserDAO.startListening();
        recyclerView.setAdapter(adapterUserDAO);

    }
}
