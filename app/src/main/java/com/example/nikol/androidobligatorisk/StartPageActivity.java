package com.example.nikol.androidobligatorisk;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

public class StartPageActivity extends AppCompatActivity {
FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        Toolbar myToolbar = findViewById(R.id.StartToolBar);
        setSupportActionBar(myToolbar);


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {


        super.onSaveInstanceState(outState, outPersistentState);

    }

    public void ToLogin(View view) {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void ToBuildings(View view) {
        Intent intent = new Intent(getBaseContext(),BuildingActivity.class);
        intent.putExtra("USER",user);
        startActivity(intent);
    }


}
