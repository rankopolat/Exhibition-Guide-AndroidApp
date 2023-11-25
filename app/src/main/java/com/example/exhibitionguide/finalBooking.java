package com.example.exhibitionguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class finalBooking extends AppCompatActivity {
    TextView date;
    Button edit;
    exhibition ex;
    TextView totalAmount;
    TextView totalVisitor;
    TextView timeAmount;
    TextView dateAmount;
    TextView exhName;
    TextView discAmount;
    TextView subTotal;
    int visCost;
    int dayofWeek;
    int total;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_booking);

        Intent i = getIntent();
        ex = (exhibition) i.getSerializableExtra("show");
        dayofWeek = ex.getDayOfWeek();
        total = ex.getVisitor();
        name = ex.getName();

        //Grouped up id finders
        date = (TextView)findViewById(R.id.date);
        totalAmount = findViewById(R.id.totalAmount);
        totalVisitor = findViewById(R.id.visitorAmount);
        timeAmount = findViewById(R.id.timeAmount);
        dateAmount = findViewById(R.id.dateAmount);
        exhName = findViewById(R.id.exhName);
        discAmount = findViewById(R.id.discAmount);
        subTotal = findViewById(R.id.subTotal);

        String[] timeParts = ex.getTime().split("-");
        String startTime = timeParts[0].trim();
        String endTime = timeParts[1].trim();

        exhName.setText(ex.getName());
        timeAmount.setText("Start Time: " +startTime + "\nEndTime: "+endTime);
        dateAmount.setText(ex.getDate());
        totalVisitor.setText("" +ex.getVisitor());

        calculate();
        finalise();

        edit = (Button)findViewById(R.id.editBooking);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(finalBooking.this, art_exhibiton.class);
                startActivity(intent);
            }
        });
    }


    private void finalise(){

        double subtotal = visCost*total;
        double discount = 0;
        double subtotaltemp;
        totalAmount.setText(total+"*"+"$" + visCost +"=$" + subtotal);
        if(total >= 4){
            discount = subtotal/10;
            subtotaltemp = subtotal - discount;
            discAmount.setText("10% discount of \n$" +subtotal +" = $" +subtotaltemp);
        }
        else{
            discAmount.setText("0");
            subtotaltemp = subtotal;
        }
        subTotal.setText("$" + subtotaltemp);

    }

    private void calculate(){

        if (dayofWeek >= 2 && dayofWeek <= 6){
            switch (name){
                case "Visual Show":
                    visCost = 40;
                    break;
                case "WWII Exhibiton":
                    visCost = 20;
                    break;
                case "Exploring The Space":
                    visCost = 30;
                    break;
                case "Art Exhibiton":
                    visCost = 25;
                    break;

            }
        }
        else {
            switch (name) {
                case "Visual Show":
                    visCost = 40;
                    break;
                case "WWII Exhibiton":
                    visCost = 25;
                    break;
                case "Exploring The Space":
                    visCost = 35;
                    break;
                case "Art Exhibiton":
                    visCost = 30;
                    break;
            }
        }

    }

}