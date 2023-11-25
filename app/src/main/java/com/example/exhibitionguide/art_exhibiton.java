package com.example.exhibitionguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class art_exhibiton extends AppCompatActivity {

    public static final String SELECT_SHOW = "";
    Button dateButton;
    TextView totalVisitor;
    EditText visitor;
    Button confirm_button;
    Calendar currentDate;
    int year;
    int month;
    int total;
    int day;
    private ArrayList<String> timeSlots;
    private RecyclerView recyclerView;
    private TimeSlotAdapter mTimeSlotAdapter;
    TextView save;
    TextView saveDate;
    String name;
    int dayofWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibiton_art);

        //setting caption name to selected show
        TextView title = (TextView) findViewById(R.id.titleLabel);
        name = getIntent().getStringExtra(SELECT_SHOW);
        title.setText(name);
        total = 0;

        //creating calendar instance and setting its values
        currentDate = Calendar.getInstance();

        recyclerView = findViewById(R.id.time_slots_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        if (name.equals("Visual Show")) {
            ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.conf);
            layout.setBackgroundResource(R.drawable.vis);
        }

        //ID finders
        totalVisitor = (TextView) findViewById(R.id.totalVistor);
        saveDate = (TextView)findViewById(R.id.saveDate);
        visitor = (EditText) findViewById(R.id.visitors);
        dateButton = (Button) findViewById(R.id.dateButton);
        confirm_button = (Button) findViewById(R.id.confirm_booking);
        save = findViewById(R.id.saveTotal);

        //onClick listeners
        dateButton.setOnClickListener(onListener);
        confirm_button.setOnClickListener(onListener);

        //setting default date to current date
        dateButton.setText(currentDate.get(Calendar.DAY_OF_MONTH) + "/" + (currentDate.get(Calendar.MONTH) + 1) + "/" + currentDate.get(Calendar.YEAR));
        visitor.addTextChangedListener(editAmountWatcher);

    }

    public void setTexts(String s) {
    save.setText(s);
    }

    private ArrayList<String> createTimeSlotsVisual(Calendar calendar) {

        Calendar c = Calendar.getInstance(); //create current day calendar instance for reference

       if(day == c.get(Calendar.DAY_OF_MONTH) && month == c.get(Calendar.MONTH) && c.get(Calendar.HOUR_OF_DAY)>=15 && c.get(Calendar.HOUR_OF_DAY) < 17){
            calendar.set(Calendar.HOUR_OF_DAY, 17);
        }
        else if(day == c.get(Calendar.DAY_OF_MONTH) && month == c.get(Calendar.MONTH) && c.get(Calendar.HOUR_OF_DAY)>=17 && c.get(Calendar.HOUR_OF_DAY)<19){
            calendar.set(Calendar.HOUR_OF_DAY, 19);
        }
       else if(day == c.get(Calendar.DAY_OF_MONTH) && month == c.get(Calendar.MONTH) && c.get(Calendar.HOUR_OF_DAY)>= 19){
           calendar.set(Calendar.HOUR_OF_DAY, 22);
       }
        else{calendar.set(Calendar.HOUR_OF_DAY, 15);}

        calendar.set(Calendar.MINUTE, 0);       // set start Minute to -:00

        ArrayList<String> timeSlots = new ArrayList<>();
        while (calendar.get(Calendar.HOUR_OF_DAY) < 21) { // loop until 9:00 PM
        String startTime =calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE));
        calendar.add(Calendar.HOUR, 2); // add 2 hours to get the end time
        String endTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE));
        timeSlots.add(startTime +"-"+ endTime); // add time slot to Arraylist
         }
        return timeSlots;
    }

    private ArrayList<String> createTimeSlots(Calendar calendar) {

        Calendar c = Calendar.getInstance(); //create current day calendar instance for reference

        if (day == c.get(Calendar.DAY_OF_MONTH) && month == c.get(Calendar.MONTH) && c.get(Calendar.MINUTE) >30 && c.get(Calendar.HOUR_OF_DAY)>=9){
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+1);//sets start time at Current hour of day + 1
        }
        else if(day == c.get(Calendar.DAY_OF_MONTH) && month == c.get(Calendar.MONTH) && c.get(Calendar.MINUTE) <30 && c.get(Calendar.HOUR_OF_DAY)>=9){
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));//sets start time at Current hour of day
        }
        else {
            calendar.set(Calendar.HOUR_OF_DAY, 9);  //sets start time at 9:-- AM
        }

        if(day == c.get(Calendar.DAY_OF_MONTH) && month == c.get(Calendar.MONTH) && c.get(Calendar.MINUTE) < 30 && c.get(Calendar.HOUR_OF_DAY)> 9) {
            calendar.set(Calendar.MINUTE, 30);  //sets start minute to --:30 minute
        }
        else{
            calendar.set(Calendar.MINUTE, 0);   //sets start minute to --:00 minute
        }

        ArrayList<String> timeSlots = new ArrayList<>();
        while (calendar.get(Calendar.HOUR_OF_DAY) < 20) { // loop until 7:00 PM
            String startTime =calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE));
            calendar.add(Calendar.MINUTE, 30); // add 30 minutes to get the end time
            String endTime = calendar.get(Calendar.HOUR_OF_DAY)+1 + ":" + String.format("%02d", calendar.get(Calendar.MINUTE));
            timeSlots.add(startTime +"-"+ endTime); // add time slot to Arraylist
        }
        return timeSlots;
    }

    private final View.OnClickListener onListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch(view.getId()){
                        case R.id.dateButton:
                            setDate();
                            break;

                        case R.id.confirm_booking:
                            confirmButton();
                            break;
                    }
                }
            };

        private final TextWatcher editAmountWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {
                    total = Integer.parseInt(charSequence.toString());
                    totalVisitor.setText("" + total);
                }catch (NumberFormatException ex){}


            }
            @Override
            public void afterTextChanged(Editable editable) {}
        };

        public void confirmButton(){

            if(saveDate.getText().toString().equals("") || save.getText().toString().equals("") || total == 0){
                Toast.makeText(this, "Please make sure everything has been selected" , Toast.LENGTH_SHORT).show();
            }
           else {
                exhibition booking = new exhibition(name, saveDate.getText().toString(), save.getText().toString(), total, dayofWeek);
                Intent intent = new Intent(art_exhibiton.this, finalBooking.class);
                intent.putExtra("show", booking);
                startActivity(intent);
            }
        }

        public void setDate(){

            DatePickerDialog mDatePicker=new DatePickerDialog(art_exhibiton.this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {

                    dateButton.setText(selectedDay + "/" + (selectedMonth+1) + "/" + selectedYear);
                    saveDate.setText(selectedDay + "/" + (selectedMonth+1) + "/" + selectedYear);
                    day = selectedDay;
                    month = selectedMonth;
                    year = selectedYear;
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(year, month,day);
                    dayofWeek = selectedCalendar.get(Calendar.DAY_OF_WEEK);

                    if (name.equals("Visual Show")) {
                        mTimeSlotAdapter = new TimeSlotAdapter(createTimeSlotsVisual(selectedCalendar));
                    }
                    else {
                        mTimeSlotAdapter = new TimeSlotAdapter(createTimeSlots(selectedCalendar));
                    }
                    recyclerView.setAdapter(mTimeSlotAdapter);
                }

            },year, month, day);

            //To show current date in the datepicker
            mDatePicker.setTitle("Select date");
            mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            mDatePicker.show();

        }


    }
