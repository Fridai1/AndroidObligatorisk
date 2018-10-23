package com.example.nikol.androidobligatorisk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Lokaler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokaler);
    }

    public void GetLokaler()
    {
       ListView listview = findViewById(R.id.LokalerListView);
       //aa
    }
}
