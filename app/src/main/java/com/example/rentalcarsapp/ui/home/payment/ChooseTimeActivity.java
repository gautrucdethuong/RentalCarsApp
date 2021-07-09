package com.example.rentalcarsapp.ui.home.payment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rentalcarsapp.R;
import com.example.rentalcarsapp.ui.home.booking.BookingDetails;
import com.example.rentalcarsapp.ui.home.car.CarDetailsActivity;
import com.example.rentalcarsapp.ui.home.car.RecyclerCarActivity;
import com.example.rentalcarsapp.ui.register.RegisterInforActivity;

import java.util.Calendar;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 7/8/2021.
 * Company: FPT大学.
 */

public class ChooseTimeActivity extends AppCompatActivity {
    // variable intilized
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private EditText editTextDateFrom, editTextTimeFrom, editTextDateTo, editTextTimeTo;
    private Button buttonSaveTime;
    private ImageView imgBack;
    private int dayFrom, monthFrom, yearFrom, hourFrom, minuteFrom;
    private int dayTo, monthTo, yearTo, hourTo, minuteTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);

        init();
        getDateTimeFrom();
        getDateTimeTo();
        saveChange();
        backToList();
    }

    // function save change date and time
    private void saveChange(){
        buttonSaveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dayTo - dayFrom >= 0){
                    Intent intent = new Intent(ChooseTimeActivity.this, BookingDetails.class);
                    intent.putExtra("dateFrom", dayFrom+"/"+monthFrom+"/"+yearFrom);
                    intent.putExtra("dateTo", dayTo+"/"+monthTo+"/"+yearTo);
                    intent.putExtra("timeFrom", hourFrom+":"+minuteFrom);
                    intent.putExtra("timeTo", hourTo+":"+minuteTo);
                    intent.putExtra("TotalDay", dayTo - dayFrom);
                    startActivity(intent);
                }else{
                    Toast.makeText(ChooseTimeActivity.this, "The pay period must be greater than the rental period !!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    // get date From
    private void getDateTimeFrom(){
        editTextDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                 dayFrom = calendar.get(Calendar.DAY_OF_MONTH);
                 monthFrom = calendar.get(Calendar.MONTH);
                 yearFrom = calendar.get(Calendar.YEAR);
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ChooseTimeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editTextDateFrom.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                yearFrom = year;
                                monthFrom = monthOfYear + 1;
                                dayFrom = dayOfMonth;
                            }
                        }, yearFrom, monthFrom, dayFrom);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        editTextTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                 hourFrom = calendar.get(Calendar.HOUR_OF_DAY);
                 minuteFrom = calendar.get(Calendar.MINUTE);
                // time picker dialog
                timePickerDialog = new TimePickerDialog(ChooseTimeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                editTextTimeFrom.setText(hourOfDay  + ":" + minute);
                                hourFrom = hourOfDay;
                                minuteFrom = minute;
                            }
                        }, hourFrom, minuteFrom,false);
                timePickerDialog.show();
            }
        });
    }

    // function get date To
    private void getDateTimeTo(){
        editTextDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                 dayTo = calendar.get(Calendar.DAY_OF_MONTH);
                 monthTo = calendar.get(Calendar.MONTH);
                 yearTo = calendar.get(Calendar.YEAR);
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ChooseTimeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editTextDateTo.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                yearTo = year;
                                monthTo = monthOfYear + 1;
                                dayTo = dayOfMonth;
                            }
                        }, yearTo, monthTo, dayTo);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        editTextTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                 hourTo = calendar.get(Calendar.HOUR_OF_DAY);
                 minuteTo = calendar.get(Calendar.MINUTE);
                // time picker dialog
                timePickerDialog = new TimePickerDialog(ChooseTimeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                editTextTimeTo.setText(hourOfDay  + ":" + minute);
                                hourTo = hourOfDay;
                                minuteTo = minute;
                            }
                        }, hourTo, minuteTo,false);
                timePickerDialog.show();
            }
        });
    }

    private void backToList(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RecyclerCarActivity.class));
                finish();
            }
        });
    }
    // initializing our UI components of list view item.
    private void init(){
        editTextDateFrom = findViewById(R.id.editTextDateFrom);
        editTextDateFrom.setInputType(InputType.TYPE_NULL);
        editTextDateTo = findViewById(R.id.editTextDateTo);
        editTextDateTo.setInputType(InputType.TYPE_NULL);
        editTextTimeFrom = findViewById(R.id.editTextTimeTo);
        editTextTimeFrom.setInputType(InputType.TYPE_NULL);
        editTextTimeTo = findViewById(R.id.editTextTimeFrom);
        editTextTimeTo.setInputType(InputType.TYPE_NULL);
        imgBack = findViewById(R.id.ImageBtnBack);
        buttonSaveTime=(Button)findViewById(R.id.buttonAddBooking);
    }
}
