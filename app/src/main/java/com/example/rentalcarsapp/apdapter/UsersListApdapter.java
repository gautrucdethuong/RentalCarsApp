package com.example.rentalcarsapp.apdapter;

import android.widget.ArrayAdapter;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.model.User;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/10/2021.
 * Company: FPT大学.
 */

public class UsersListApdapter extends ArrayAdapter<User> {
    public UsersListApdapter(@NonNull Context context, ArrayList<User> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.user_item, parent, false);
        }

        // our modal class.
        User dataModal = getItem(position);

        // initializing our UI components of list view item.
        TextView textFullName = listitemView.findViewById(R.id.textFullName);
        TextView textRoleName = listitemView.findViewById(R.id.textRoleName);

        textFullName.setText(dataModal.getFullName());
        textRoleName.setText(dataModal.getRoleName());

        return listitemView;
    }
}
