package com.example.rentalcarsapp.ui.register;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.MainActivity;
import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.dao.AuthenticationDAO;
import com.example.rentalcarsapp.dao.Callback;
import com.example.rentalcarsapp.helper.RegexValidate;
import com.example.rentalcarsapp.model.User;
import com.example.rentalcarsapp.ui.home.UsersManagementActivity;
import com.example.rentalcarsapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private SharedPreferences sharedPref;
    TextInputLayout mFullName,mEmail,mPassword,mPhone,mConfirmPassword;
    Button mRegisterBtn,mLoginBtn;
    TextView mWelcome,mSlogan,mStep;
    ImageView imgBack;

    //String fullName;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("saveFullName", String.valueOf(mFullName.getEditText().getText()));
        outState.putString("saveEmail", String.valueOf(mEmail.getEditText().getText()));
        outState.putString("savePassWord", String.valueOf(mPassword.getEditText().getText()));
        outState.putString("savePhoneNumber", String.valueOf(mPhone.getEditText().getText()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        mFullName   = findViewById(R.id.fullName);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.re_confirm_password);
        mPhone      = findViewById(R.id.phone);
        mRegisterBtn= findViewById(R.id.signup_next_button);
        mLoginBtn   = findViewById(R.id.createText);
        imgBack   = findViewById(R.id.logoImage);

        mWelcome=findViewById(R.id.logo_name);
        mSlogan=findViewById(R.id.slogan_name);

        mStep = findViewById(R.id.txtStep);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                if(!ValidateFullName() | !ValidateEmail() | !ValidatePassword() | !ValidatePhoneNumber() | !ComparePassword() | !ValidateConfirmPassword()){
                    return;
                }*/

/*
                RegexValidate obj = new RegexValidate();

                obj.checkValidationFormInput(fullName,obj.VALID_FULL_NAME);
                int check = sharedPref.getInt("RESULT_CHECK",0);

                //Log.e("Ketqua", String.valueOf(check));

                if(check == 1){
                    mFullName.setError(RegexValidate.MESSAGE_ERROR_FULL_NAME);
                    return;
                }else{
                    mFullName.setError(null);
                    mFullName.setErrorEnabled(false);
                }
*/

                Intent intent = new Intent(getApplicationContext(), RegisterInforActivity.class);
                intent.putExtra("fullName", String.valueOf(mFullName.getEditText().getText()));
                intent.putExtra("email", String.valueOf(mEmail.getEditText().getText()));
                intent.putExtra("passWord", String.valueOf(mPassword.getEditText().getText()));
                intent.putExtra("phoneNumber", String.valueOf(mPhone.getEditText().getText()));

                startActivity(intent);
            }
        });


        // Back login
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pairs=new Pair[4];
                pairs[0]=new Pair<View,String>(imgBack,"logo_image");
                pairs[1]=new Pair<View,String>(mWelcome,"logo_text");
                pairs[2]=new Pair<View,String>(mSlogan,"logo_signup");
                pairs[3]=new Pair<View,String>(mStep,"txt_transaction");
                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class),options.toBundle());
            }
        });


        // Back login page
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pairs=new Pair[4];
                pairs[0]=new Pair<View,String>(imgBack,"logo_image");
                pairs[1]=new Pair<View,String>(mWelcome,"logo_text");
                pairs[2]=new Pair<View,String>(mSlogan,"logo_signup");
                pairs[3]=new Pair<View,String>(mStep,"txt_transaction");
                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class),options.toBundle());
            }
        });




    }

    private boolean ValidateFullName(){
        String fullName = String.valueOf(mFullName.getEditText().getText());

        if(fullName.isEmpty()){
            mFullName.setError("Please enter full name.");
            return false;
        }else if(!fullName.matches(RegexValidate.VALID_FULL_NAME)){
            mFullName.setError(RegexValidate.MESSAGE_ERROR_FULL_NAME);
            return false;
        }else{
            mFullName.setError(null);
            mFullName.setErrorEnabled(false);
            return true;
        }

    }

    private boolean ValidatePhoneNumber(){
        String phoneNumber = String.valueOf(mPhone.getEditText().getText());
        if(phoneNumber.isEmpty()){
            mPhone.setError("Please enter phone number.");
            return false;
        }else if(!phoneNumber.matches(RegexValidate.VALID_PHONE_NUMBER)){
            mPhone.setError(RegexValidate.MESSAGE_ERROR_PHONE_NUMBER);
            return false;
        }else{
            mPhone.setError(null);
            mPhone.setErrorEnabled(false);
            return true;
        }

    }

    private boolean ValidatePassword(){
        String passWord = String.valueOf(mPassword.getEditText().getText());
        if(passWord.isEmpty()){
            mPassword.setError("Please enter password.");
            return false;
        }else if(!passWord.matches(RegexValidate.VALID_PASSWORD)){
            mPassword.setError(RegexValidate.MESSAGE_ERROR_PASSWORD);
            return false;
        }else{
            mPassword.setError(null);
            mPassword.setErrorEnabled(false);
            return true;
        }

    }

    private boolean ValidateConfirmPassword(){
        String passWord = String.valueOf(mConfirmPassword.getEditText().getText());
        if(passWord.isEmpty()){
            mConfirmPassword.setError("Please enter confirm password.");
            return false;
        }else{
            mConfirmPassword.setError(null);
            mConfirmPassword.setErrorEnabled(false);
            return true;
        }

    }

    private boolean ComparePassword(){
        String passWord = String.valueOf(mPassword.getEditText().getText());
        String re_passWord = String.valueOf(mConfirmPassword.getEditText().getText());
        if(!passWord.equals(re_passWord)){
            mConfirmPassword.setError(RegexValidate.MESSAGE_ERROR_CONFIRM_PASSWORD);
            return false;
        }else{
            mConfirmPassword.setError(null);
            mConfirmPassword.setErrorEnabled(false);
            return true;
        }

    }

    private boolean ValidateEmail(){
        String email = String.valueOf(mEmail.getEditText().getText());
        if(email.isEmpty()){
            mEmail.setError("Please enter email.");
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
