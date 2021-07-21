package com.example.rentalcarsapp.ui.admin.booking;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.apdapter.admin.BookingListAdapter;
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
public class ListBookingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;//declare variable recyclerView
    private FirestoreRecyclerOptions<Booking> options;//declare variable options
    private FirebaseFirestore fireStore;//declare variable fireStore
    private BookingListAdapter adapter;//declare variable adapter
    /**
     * Method to interface initialization
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_booking_admin);
        fireStore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView_booking);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Query querybooking = fireStore.collection("bookings");
        options = new FirestoreRecyclerOptions.Builder<Booking>().setQuery(querybooking, Booking.class).build();
        adapter=new BookingListAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
    /**
     * Method to star to connect adapter
     */
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    /**
     * Method to stop to connect adapter
     */
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
