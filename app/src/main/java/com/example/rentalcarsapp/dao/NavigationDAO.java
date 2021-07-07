package com.example.rentalcarsapp.dao;


import android.content.Intent;
import android.view.View;
import com.example.rentalcarsapp.DashboardActivity;

public class NavigationDAO {
    public void callDashboard(View v) {
        Intent intent = new Intent(v.getContext(), DashboardActivity.class);
        v.getContext().startActivity(intent);
    }

}
