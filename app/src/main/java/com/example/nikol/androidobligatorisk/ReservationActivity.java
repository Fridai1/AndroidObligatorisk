package com.example.nikol.androidobligatorisk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReservationActivity extends AppCompatActivity {

    private Reservation thisReservation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        thisReservation = (Reservation) getIntent().getExtras().get("RESERVATION");


        TextView userId = findViewById(R.id.UserIdRes);
        TextView fromTime = findViewById(R.id.FromTimeRes);
        TextView toTime = findViewById(R.id.ToTimeRes);
        TextView purpose = findViewById(R.id.PurposeRes);

        userId.setText(thisReservation.getUserId());
        fromTime.setText(thisReservation.getFromTimeString());
        toTime.setText(thisReservation.getToTimeString());
        purpose.setText(thisReservation.getPurpose());

    }


}
