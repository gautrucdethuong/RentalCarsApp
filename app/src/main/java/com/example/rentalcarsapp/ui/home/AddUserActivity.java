package com.example.rentalcarsapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.DashboardActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.dao.UserDAO;
import com.example.rentalcarsapp.ui.login.LoginActivity;



/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/9/2021.
 * Company: FPT大学.
 */

public class AddUserActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText mFullName,mEmail,mImage,mPhone;
    Button mRegisterBtn;
    Button mBackBtn;
    ProgressBar progressBar;
    UserDAO userDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mFullName=(EditText)findViewById(R.id.editFullName);
        mEmail=(EditText)findViewById(R.id.editEmail);
        mPhone=(EditText)findViewById(R.id.editPhoneNumber);
        mImage=(EditText)findViewById(R.id.editImage);

        mBackBtn=(Button)findViewById(R.id.add_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
            }
        });

        mRegisterBtn=(Button)findViewById(R.id.add_submit);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*                final String email = mEmail.getText().toString().trim();
                final String image = mImage.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String phone    = mPhone.getText().toString();

                progressBar.setVisibility(View.VISIBLE);

                boolean status = userDAO.addUser(email, image, fullName, phone);
                // register the user in firebase
                if(status){
                    Toast.makeText(AddUserActivity.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                }else {
                    Toast.makeText(AddUserActivity.this, "Add failed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }*/
            }
        });
    }


}
