package com.example.rentalcarsapp.apdapter.admin;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.model.Bill;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;
/**
 * Author by Tieu Ha Anh Khoi
 * Email: khoithace140252@fpt.edu.vn.
 * Date on 7/4/2021.
 * Company: FPT大学.
 */
public class BillListAdapter extends FirestoreRecyclerAdapter<Bill, BillListAdapter.Billviewholder> {
    /**
     * Create constructor with parameter
     * @param options choose how to display data
     */
    public BillListAdapter(@NonNull FirestoreRecyclerOptions<Bill> options)
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
    protected void onBindViewHolder(@NonNull @NotNull BillListAdapter.Billviewholder holder, int position, @NonNull @NotNull Bill model) {
        holder.textViewBillId.setText("Bill ID:\n"+model.getBillId());
        holder.textViewBookingId.setText("Price: $" +model.getBookingTotal());
        holder.textViewBillCreate.setText("Created Date:\n"+String.valueOf(model.getBillCreatedDate()));
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
    public Billviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_bill,parent,false);
        return new BillListAdapter.Billviewholder(view);
    }

    public class Billviewholder extends RecyclerView.ViewHolder {
        public TextView textViewBillId;
        public TextView textViewBookingId;
        public TextView textViewBillCreate;

        /**
         * Create view holder of bill
         * @param itemView id of components in layout file file
         */
        public Billviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            // initializing our UI components of list view item.
            textViewBillId = itemView.findViewById(R.id.txtBillId);
            textViewBookingId= itemView.findViewById(R.id.txtIdbooking);
            textViewBillCreate= itemView.findViewById(R.id.txtBill_create);
        }
    }
}
