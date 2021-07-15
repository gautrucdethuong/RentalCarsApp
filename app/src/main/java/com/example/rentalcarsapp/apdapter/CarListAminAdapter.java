package com.example.rentalcarsapp.apdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.model.Car;
import com.example.rentalcarsapp.ui.home.car.CarDetailsActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/4/2021.
 * Company: FPT大学.
 */

public class CarListAminAdapter extends FirestoreRecyclerAdapter<Car, CarListAminAdapter.CarViewHolder>{

    private SharedPreferences sharedpreferences;
    private static final String SHARED_PREFERENCE_PRICE = "myPrefs";
    //public Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CarListAminAdapter(@NonNull FirestoreRecyclerOptions<Car> options) {

        super(options);
    }

    @NonNull
    @NotNull
    @Override
    public CarListAminAdapter.CarViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_car_homepage,parent,false);

        return new CarListAminAdapter.CarViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull CarListAminAdapter.CarViewHolder holder, int position, @NonNull Car model) {
/*        ArrayList<String> listCar = new ArrayList<>();

        FirebaseFirestore.getInstance().collection("cars").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot snapshot:snapshotList){

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                System.out.println("Failed get value");
            }
        });*/
        holder.textViewNameCar.setText(model.getCarName());
        holder.textViewPrice.setText("$ " + model.getCarPrice() + " / Daily");
        holder.ratingBar.setRating(model.getCarRating());
        Picasso.get().load(model.getCarImage())
                .error(R.drawable.user)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = holder.itemView.getContext().getSharedPreferences(SHARED_PREFERENCE_PRICE, Context.MODE_PRIVATE);
                Intent intent = new Intent(holder.itemView.getContext(), CarDetailsActivity.class);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putFloat("CAR_PRICE",model.getCarPrice());
                editor.apply();
                intent.putExtra("carName", model.getCarName());
                intent.putExtra("carPrice", model.getCarPrice());
                intent.putExtra("carRating", model.getCarRating());
                intent.putExtra("carImage", model.getCarImage());
                intent.putExtra("carSeat", model.getCarSeat());
                intent.putExtra("carDescription", model.getCarDescription());
                //v.startActivity(intent);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }



    class CarViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewNameCar;
        public TextView textViewPrice;
        public ImageView imageView;
        public RatingBar ratingBar;
        public CarViewHolder(@NonNull View itemView)
        {
            super(itemView);
            // initializing our UI components of list view item.
            textViewNameCar = itemView.findViewById(R.id.textViewNameCar);
            textViewPrice = itemView.findViewById(R.id.txtPriceTotal);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imageView = itemView.findViewById(R.id.imageButtonCar);
        }
    }
}
