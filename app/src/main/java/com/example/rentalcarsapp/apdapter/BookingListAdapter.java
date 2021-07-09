package com.example.rentalcarsapp.apdapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.model.Booking;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;
import com.orhanobut.dialogplus.DialogPlus;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingListAdapter extends FirestoreRecyclerAdapter<Booking, BookingListAdapter.Bookingviewholder>{
    public BookingListAdapter(@NonNull FirestoreRecyclerOptions<Booking> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull BookingListAdapter.Bookingviewholder holder, int position, @NonNull @NotNull Booking model) {
        ArrayList<String> lstbooking=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("bookings").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot snapshot:snapshotList){
                        String bookingid= snapshot.getId();
                        lstbooking.add(bookingid);
                    }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                System.out.println("Failed get value");
            }
        });
        //if booking status is 1 normal
        if(model.getBookingStatus()==1) {
            holder.imageAccpet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    String str = simpleDateFormat1.format(date);
                    Map<String, Object> map = new HashMap<>();
                    try {
                        map.put("billCreateddate", simpleDateFormat1.parse(str));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    map.put("billStatus", 1);
                    map.put("billUpdateddate", null);
                    map.put("bookingId", "ciOEJTKLBWwq3VQDeo1g");
                    FirebaseFirestore.getInstance().collection("bills").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Map<String,Object> bookingMap = new HashMap<>();
                            bookingMap.put("bookingStatus",2);
                            FirebaseFirestore.getInstance().collection("bookings").document(model.getCarId()).update(bookingMap);
                            holder.linearLayout.setBackgroundColor(Color.GREEN);
                            holder.imageAccpet.setVisibility(View.INVISIBLE);
                            holder.imageCancel.setVisibility(View.INVISIBLE);
                            holder.imageView.setBackgroundColor(Color.GREEN);
                            holder.textViewFullname.setTextColor(Color.WHITE);
                            holder.textViewNameCar.setTextColor(Color.WHITE);
                            holder.textViewPrice.setTextColor(Color.WHITE);
                            holder.textViewPick.setTextColor(Color.WHITE);
                            holder.textViewReturn.setTextColor(Color.WHITE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Log.w("TAG", "Error adding document", e);
                        }
                    });
                }
            });
            holder.imageCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,Object> bookingMap = new HashMap<>();
                    bookingMap.put("bookingStatus",0);
                    FirebaseFirestore.getInstance().collection("bookings").document().update(bookingMap);
                    holder.linearLayout.setBackgroundColor(Color.RED);
                    holder.imageAccpet.setVisibility(View.INVISIBLE);
                    holder.imageCancel.setVisibility(View.INVISIBLE);
                    holder.imageView.setBackgroundColor(Color.RED);
                    holder.textViewFullname.setTextColor(Color.WHITE);
                    holder.textViewNameCar.setTextColor(Color.WHITE);
                    holder.textViewPrice.setTextColor(Color.WHITE);
                    holder.textViewPick.setTextColor(Color.WHITE);
                    holder.textViewReturn.setTextColor(Color.WHITE);
                }
            });
        }
        //if booking status is 2 accept
        else if(model.getBookingStatus()==2){
            holder.linearLayout.setBackgroundColor(Color.GREEN);
            holder.imageAccpet.setVisibility(View.INVISIBLE);
            holder.imageCancel.setVisibility(View.INVISIBLE);
            holder.imageView.setBackgroundColor(Color.GREEN);
            holder.textViewFullname.setTextColor(Color.WHITE);
            holder.textViewNameCar.setTextColor(Color.WHITE);
            holder.textViewPrice.setTextColor(Color.WHITE);
            holder.textViewPick.setTextColor(Color.WHITE);
            holder.textViewReturn.setTextColor(Color.WHITE);
        }
        else if (model.getBookingStatus()==0){
            holder.linearLayout.setBackgroundColor(Color.RED);
            holder.imageAccpet.setVisibility(View.INVISIBLE);
            holder.imageCancel.setVisibility(View.INVISIBLE);
            holder.imageView.setBackgroundColor(Color.RED);
            holder.textViewFullname.setTextColor(Color.WHITE);
            holder.textViewNameCar.setTextColor(Color.WHITE);
            holder.textViewPrice.setTextColor(Color.WHITE);
            holder.textViewPick.setTextColor(Color.WHITE);
            holder.textViewReturn.setTextColor(Color.WHITE);
        }
        holder.textViewPrice.setText("$ " + model.getBookingTotal().toString() + " / Daily");
        holder.textViewPick.setText(String.valueOf(model.getBookingPickUpDate()));
        holder.textViewReturn.setText(String.valueOf(model.getBookingDropOffDate()));
        ////                holder.textViewNameCar.setText(model.getCarId());
////                holder.textViewFullname.setText(model.getUserId());
////                Picasso.get().load(model.get())
////                        .error(R.drawable.user)
////                        .into(holder.imageView);
    }

    @NonNull
    @NotNull
    @Override
    public Bookingviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_booking,parent,false);
        return new Bookingviewholder(view);
    }
    class Bookingviewholder extends RecyclerView.ViewHolder
    {
        public TextView textViewNameCar;
        public TextView textViewPrice;
        public TextView textViewFullname;
        public TextView textViewPick;
        public TextView textViewReturn;
        public ImageView imageView,imageAccpet,imageCancel;
        public LinearLayout linearLayout;
        public Bookingviewholder(@NonNull View itemView)
        {
            super(itemView);
            // initializing our UI components of list view item.
            textViewNameCar = itemView.findViewById(R.id.txtCar_name);
            textViewPrice = itemView.findViewById(R.id.txtCar_price);
            textViewFullname = itemView.findViewById(R.id.txtUser);
            textViewPick= itemView.findViewById(R.id.txtCar_pick);
            textViewReturn= itemView.findViewById(R.id.txtCar_return);
            imageView = itemView.findViewById(R.id.image_car);
            imageAccpet=itemView.findViewById(R.id.check_icon);
            imageCancel=itemView.findViewById(R.id.cancel_icon);
            linearLayout=itemView.findViewById(R.id.layout_booking);
        }
    }

}
