package com.example.rentalcarsapp.apdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.model.Bill;
import com.example.rentalcarsapp.model.Booking;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class BillListAdapter extends FirestoreRecyclerAdapter<Bill, BillListAdapter.Billviewholder> {
    public BillListAdapter(@NonNull FirestoreRecyclerOptions<Bill> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull BillListAdapter.Billviewholder holder, int position, @NonNull @NotNull Bill model) {
        holder.textViewBillCreate.setText("Create Date:"+String.valueOf(model.getBillCreateDate()));
        holder.textViewBillId.setText("Bill ID: "+model.getBillId());
        holder.textViewBookingId.setText("Price: $" +model.getBookingTotal());
    }

    @NonNull
    @NotNull
    @Override
    public Billviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_bill,parent,false);
        return new BillListAdapter.Billviewholder(view);
    }

    public class Billviewholder extends RecyclerView.ViewHolder {
        public TextView textViewBillId;
        public TextView textViewBookingId;
        public TextView textViewBillCreate;
        public Billviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            // initializing our UI components of list view item.
            textViewBillId = itemView.findViewById(R.id.txtBillId);
            textViewBookingId= itemView.findViewById(R.id.txtIdbooking);
            textViewBillCreate= itemView.findViewById(R.id.txtBill_create);
        }
    }
}
