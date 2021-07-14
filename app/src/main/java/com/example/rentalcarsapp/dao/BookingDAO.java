package com.example.rentalcarsapp.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.rentalcarsapp.model.Booking;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    FirebaseFirestore fStore;
    ArrayList<String> lstbooking=new ArrayList<>();
    Booking booking=new Booking();
    public void getBookingId(IdCallback idCallback){
        fStore.getInstance().collection("bookings").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot snapshot:snapshotList){
                    String bookingid= snapshot.getId();
                    booking.setBookingId(bookingid);
//                    lstbooking.add(bookingid);
                }
                idCallback.isIdExist(booking);
            }
        });
    }
}
