package com.example.rentalcarsapp.apdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalcarsapp.R;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/4/2021.
 * Company: FPT大学.
 */

public class CarListAdapter extends RecyclerView.ViewHolder {
    // initializing our UI components of list view item.
    public TextView textViewNameCar;
    public TextView textViewPrice;
    public ImageView imageView;
    public RatingBar ratingBar;

    public CarListAdapter(@NonNull View itemView) {
        super(itemView);
        // initializing our UI components of list view item.
         textViewNameCar = itemView.findViewById(R.id.textViewNameCar);
         textViewPrice = itemView.findViewById(R.id.txtPriceTotal);
         ratingBar = itemView.findViewById(R.id.ratingBar);
         imageView = itemView.findViewById(R.id.imageButtonCar);

    }
}
