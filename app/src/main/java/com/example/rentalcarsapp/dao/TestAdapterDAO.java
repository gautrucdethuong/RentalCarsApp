package com.example.rentalcarsapp.dao;


import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.model.User;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rentalcarsapp.ui.home.UsersManagementActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/10/2021.
 * Company: FPT大学.
 */

public class TestAdapterDAO extends ArrayAdapter<User> {
    // constructor for our list view adapter.
    public TestAdapterDAO(@NonNull Context context, ArrayList<User> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.test_item, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        User dataModal = getItem(position);

        // initializing our UI components of list view item.
        TextView textFullName = listitemView.findViewById(R.id.textFullName);
        TextView textRoleName = listitemView.findViewById(R.id.textRoleName);
        ImageView courseIV = listitemView.findViewById(R.id.imageViewUrl);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.

        textFullName.setText(dataModal.getFullName());

        //Log.e("rolename",dataModal.getRoleName());
        textRoleName.setText(dataModal.getUserEmail());

        //String.valueOf(nameTV.setText(dataModal.getFullName()));
        //String.valueOf(mPassword.getEditText().getText());
        // in below line we are using Picasso to
        // load image from URL in our Image VIew.
        Picasso.get().load(R.drawable.user)
                .error(R.drawable.add)
                .into(courseIV);

        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Toast.makeText(getContext(), "Item clicked is : " + dataModal.getFullName(), Toast.LENGTH_SHORT).show();

            }
        });
        return listitemView;
    }
}
