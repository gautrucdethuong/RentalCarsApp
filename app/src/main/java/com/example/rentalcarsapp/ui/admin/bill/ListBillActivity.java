package com.example.rentalcarsapp.ui.admin.bill;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.apdapter.BillListAdapter;
import com.example.rentalcarsapp.apdapter.BookingListAdapter;
import com.example.rentalcarsapp.model.Bill;
import com.example.rentalcarsapp.model.Booking;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * Author by Tieu Ha Anh Khoi
 * Email: khoithace140252@fpt.edu.vn.
 * Date on 7/4/2021.
 * Company: FPT大学.
 */
public class ListBillActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirestoreRecyclerOptions<Bill> options;
    private FirebaseFirestore fireStore;
    private BillListAdapter adapter;
    private EditText inputSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_car);
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
}
