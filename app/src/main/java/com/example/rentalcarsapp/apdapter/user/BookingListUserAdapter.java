package com.example.rentalcarsapp.apdapter.user;

import android.graphics.Color;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class BookingListUserAdapter extends FirestoreRecyclerAdapter<Booking, BookingListUserAdapter.Bookinguserviewholder>{
    public BookingListUserAdapter(@NonNull FirestoreRecyclerOptions<Booking> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull BookingListUserAdapter.Bookinguserviewholder holder, int position, @NonNull @NotNull Booking model) {
        //if booking status is 2 accept
        if(model.getBookingStatus()==2){
            holder.linearLayout.setBackgroundColor(Color.GREEN);
            holder.textViewNameCar.setTextColor(Color.WHITE);
            holder.textViewPrice.setTextColor(Color.WHITE);
            holder.textViewPick.setTextColor(Color.WHITE);
            holder.textViewReturn.setTextColor(Color.WHITE);
        }
        else if (model.getBookingStatus()==0){
            holder.linearLayout.setBackgroundColor(Color.RED);
            holder.textViewNameCar.setTextColor(Color.WHITE);
            holder.textViewPrice.setTextColor(Color.WHITE);
            holder.textViewPick.setTextColor(Color.WHITE);
            holder.textViewReturn.setTextColor(Color.WHITE);
        }
        else if (model.getBookingStatus()==3){
            holder.linearLayout.setBackgroundColor(Color.YELLOW);
            holder.textViewNameCar.setTextColor(Color.WHITE);
            holder.textViewPrice.setTextColor(Color.WHITE);
            holder.textViewPick.setTextColor(Color.WHITE);
            holder.textViewReturn.setTextColor(Color.WHITE);
        }
        holder.textViewPrice.setText("$ " + String.valueOf(model.getBookingTotal())+ " / Daily");
        holder.textViewPick.setText(String.valueOf(model.getBookingPickUpDate()));
        holder.textViewReturn.setText(String.valueOf(model.getBookingDropOffDate()));
        FirebaseFirestore.getInstance().collection("cars").document(model.getCarId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Picasso.get().load(String.valueOf(documentSnapshot.getData().get("carImage")))
                        .error(R.drawable.user)
                        .into(holder.imageView);
                holder.textViewNameCar.setText(String.valueOf(documentSnapshot.getData().get("carName")));
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public Bookinguserviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_booking,parent,false);
        return new Bookinguserviewholder(view);
    }
    class Bookinguserviewholder extends RecyclerView.ViewHolder
    {
        public TextView textViewNameCar;
        public TextView textViewPrice;
        public TextView textViewPick;
        public TextView textViewReturn;
        public LinearLayout linearLayout;
        public ImageView imageView;
        public Bookinguserviewholder(@NonNull View itemView)
        {
            super(itemView);
            // initializing our UI components of list view item.
            textViewNameCar = itemView.findViewById(R.id.txtCar_name);
            textViewPrice = itemView.findViewById(R.id.txtCar_price);
            textViewPick= itemView.findViewById(R.id.txtCar_pick);
            textViewReturn= itemView.findViewById(R.id.txtCar_return);
            linearLayout=itemView.findViewById(R.id.layout_booking);
            imageView = itemView.findViewById(R.id.image_car);
        }
    }

}
