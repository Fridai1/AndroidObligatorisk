package com.example.nikol.androidobligatorisk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class LoggedInActivity extends AppCompatActivity {

    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        user =(FirebaseUser) getIntent().getExtras().get("USER");
        TextView LoggedInUser = findViewById(R.id.LoggedInAs);
        LoggedInUser.setText(user.getEmail());

    }

    public void ToBuildings(View view) {
        Intent intent = new Intent(getBaseContext(),BuildingActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void ToMyReservations(View view) {
        Intent intent = new Intent(getBaseContext(),MyReservationActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }


}
