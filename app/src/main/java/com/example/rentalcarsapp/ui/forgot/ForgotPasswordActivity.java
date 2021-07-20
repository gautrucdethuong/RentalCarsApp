package com.example.rentalcarsapp.ui.forgot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.helper.RegexValidate;
import com.example.rentalcarsapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
/**
 * Author by Tieu Ha Anh Khoi
 * Email: khoithace140252@fpt.edu.vn.
 * Date on 13/6/2021.
 * Company: FPT大学.
 */
public class ForgotPasswordActivity extends AppCompatActivity {
    ImageView imgBack,imgLock;//declare variable imgBack and imgLock
    TextInputLayout mEmail;//declare variable mEmail
    TextView txtLogo,txtSlogan;//declare variable txtLogo and txtSlogan
    Button btnForgot;//declare variable btnForgot
    ProgressBar progressBar;//declare variable progressBar
    FirebaseAuth fAuth;//declare variable fAuth
    /**
     * Method to interface initialization
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        progressBar = findViewById(R.id.progressBar);
        imgBack=findViewById(R.id.imgBack);
        mEmail=findViewById(R.id.txtEmail);
        btnForgot=findViewById(R.id.btnForgot);
        imgLock=findViewById(R.id.logoLock);
        txtLogo=findViewById(R.id.logo_name);
        txtSlogan=findViewById(R.id.slogan_name);
        fAuth=FirebaseAuth.getInstance();
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ValidateEmail()){//call function to check condition of email
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                    fAuth.sendPasswordResetEmail(String.valueOf(mEmail.getEditText().getText())).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ForgotPasswordActivity.this,"Password reset link sent to your email",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }else{
                            Toast.makeText(ForgotPasswordActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                Pair[] pairs=new Pair[4];
                pairs[0]=new Pair<View,String>(imgBack,"logo_image");
                pairs[1]=new Pair<View,String>(txtLogo,"logo_text");
                pairs[2]=new Pair<View,String>(txtSlogan,"logo_signup");
                pairs[3]=new Pair<View,String>(imgLock,"txt_transaction");
                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(ForgotPasswordActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }

    /**
     * Function to validate email
     * @return true if no error, false if there's an error
     */
    private boolean ValidateEmail(){
        String email = String.valueOf(mEmail.getEditText().getText());
        if(email.isEmpty()){
            mEmail.setError("Please enter email");
            return false;
        }else if(!email.matches(RegexValidate.VALID_EMAIL)){
            mEmail.setError(RegexValidate.MESSAGE_ERROR_EMAIL);
            return false;
        }else{
            mEmail.setError(null);
            mEmail.setErrorEnabled(false);
            return true;
        }

    }
}