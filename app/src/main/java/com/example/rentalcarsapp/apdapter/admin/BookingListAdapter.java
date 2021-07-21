package com.example.rentalcarsapp.apdapter.admin;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.model.Booking;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * Author by Tieu Ha Anh Khoi
 * Email: khoithace140252@fpt.edu.vn.
 * Date on 7/4/2021.
 * Company: FPT大学.
 */
public class BookingListAdapter extends FirestoreRecyclerAdapter<Booking, BookingListAdapter.Bookingviewholder>{
    /**
     * Create constructor with parameter
     * @param options choose how to display data
     */
    public BookingListAdapter(@NonNull FirestoreRecyclerOptions<Booking> options)
    {
        super(options);
    }
    /**
     * Method to bind data into recyclerview
     * @param holder Names of variables inside the layout file that you want to be displayed
     * @param position Location of items
     * @param model Object of a table
     */
    @Override
    protected void onBindViewHolder(@NonNull @NotNull BookingListAdapter.Bookingviewholder holder, int position, @NonNull @NotNull Booking model) {
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
                    map.put("bookingTotal",model.getBookingTotal());
                    map.put("billUpdateddate", null);
                    map.put("bookingId", model.getBookingId());
                    FirebaseFirestore.getInstance().collection("bills").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            map.put("billId",String.valueOf(documentReference.getId()));
                            FirebaseFirestore.getInstance().collection("bills").document(String.valueOf(documentReference.getId())).set(map);
                            Map<String,Object> bookingMap = new HashMap<>();
                            bookingMap.put("bookingStatus",2);
                            FirebaseFirestore.getInstance().collection("bookings").document(model.getBookingId()).update(bookingMap);
                            Map<String,Object> carMap = new HashMap<>();
                            carMap.put("carStatus",0);
                            FirebaseFirestore.getInstance().collection("cars").document(model.getCarId()).update(carMap);
                            holder.linearLayout.setBackgroundColor(Color.GREEN);
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
                    FirebaseFirestore.getInstance().collection("bookings").document(model.getBookingId()).update(bookingMap);
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
            holder.imageCancel.setVisibility(View.INVISIBLE);
            holder.imageView.setBackgroundColor(Color.GREEN);
            holder.textViewFullname.setTextColor(Color.WHITE);
            holder.textViewNameCar.setTextColor(Color.WHITE);
            holder.textViewPrice.setTextColor(Color.WHITE);
            holder.textViewPick.setTextColor(Color.WHITE);
            holder.textViewReturn.setTextColor(Color.WHITE);
            holder.imageAccpet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,Object> bookingMap = new HashMap<>();
                    bookingMap.put("bookingStatus",3);
                    FirebaseFirestore.getInstance().collection("bookings").document(model.getBookingId()).update(bookingMap);
                    Map<String,Object> carMap = new HashMap<>();
                    carMap.put("carStatus",1);
                    FirebaseFirestore.getInstance().collection("cars").document(model.getCarId()).update(carMap);
                    holder.imageAccpet.setVisibility(View.INVISIBLE);
                }
            });
        }
        //if booking status is 3 cancel
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
        //if booking status is 3 returned the car
        else if(model.getBookingStatus()==3){
            holder.linearLayout.setBackgroundColor(Color.YELLOW);
            holder.imageAccpet.setVisibility(View.INVISIBLE);
            holder.imageCancel.setVisibility(View.INVISIBLE);
            holder.imageView.setBackgroundColor(Color.YELLOW);
            holder.textViewFullname.setTextColor(Color.WHITE);
            holder.textViewNameCar.setTextColor(Color.WHITE);
            holder.textViewPrice.setTextColor(Color.WHITE);
            holder.textViewPick.setTextColor(Color.WHITE);
            holder.textViewReturn.setTextColor(Color.WHITE);
        }
        holder.textViewPrice.setText("$ " + String.valueOf(model.getBookingTotal())+ " / Daily");
        holder.textViewPick.setText(String.valueOf(model.getBookingPickUpDate()));
        holder.textViewReturn.setText(String.valueOf(model.getBookingDropOffDate()));
        holder.textViewIdBooking.setText(model.getBookingId());

        FirebaseFirestore.getInstance().collection("cars").document(model.getCarId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
             Picasso.get().load(String.valueOf(documentSnapshot.getData().get("carImage")))
                      .error(R.drawable.user)
                    .into(holder.imageView);
             holder.textViewNameCar.setText(String.valueOf(documentSnapshot.getData().get("carName")));
            }
        });
        FirebaseFirestore.getInstance().collection("users").document(model.getUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                holder.textViewFullname.setText(String.valueOf(documentSnapshot.getData().get("fullName")));
            }
        });
    }
    /**
     * Method to get layout in layout file
     * @param parent group view
     * @param viewType view type want to be displayed
     * @return class get components inside layout file
     */
    @NonNull
    @NotNull
    @Override
    public Bookingviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_booking_admin,parent,false);
        return new Bookingviewholder(view);
    }
    class Bookingviewholder extends RecyclerView.ViewHolder
    {
        public TextView textViewNameCar;
        public TextView textViewPrice;
        public TextView textViewFullname;
        public TextView textViewPick;
        public TextView textViewReturn;
        public TextView textViewIdBooking;
        public ImageView imageView,imageAccpet,imageCancel;
        public LinearLayout linearLayout;
        /**
         * Create view holder of bill
         * @param itemView id of components in layout file file
         */
        public Bookingviewholder(@NonNull View itemView)
        {
            super(itemView);
            // initializing our UI components of list view item.
            textViewNameCar = itemView.findViewById(R.id.txtCar_name);
            textViewPrice = itemView.findViewById(R.id.txtCar_price);
            textViewFullname = itemView.findViewById(R.id.txtUser);
            textViewPick= itemView.findViewById(R.id.txtCar_pick);
            textViewReturn= itemView.findViewById(R.id.txtCar_return);
            textViewIdBooking= itemView.findViewById(R.id.txtIdbooking);
            imageView = itemView.findViewById(R.id.image_car);
            imageAccpet=itemView.findViewById(R.id.check_icon);
            imageCancel=itemView.findViewById(R.id.cancel_icon);
            linearLayout=itemView.findViewById(R.id.layout_booking);
        }
    }

}
