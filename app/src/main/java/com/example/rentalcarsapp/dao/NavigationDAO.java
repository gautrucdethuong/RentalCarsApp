package com.example.rentalcarsapp.dao;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.rentalcarsapp.DashboardActivity;
import com.example.rentalcarsapp.ui.login.EditProfileActivity;

public class NavigationDAO {
    public void callDashboard(View v) {
        Intent intent = new Intent(v.getContext(), DashboardActivity.class);
        v.getContext().startActivity(intent);
    }

}
