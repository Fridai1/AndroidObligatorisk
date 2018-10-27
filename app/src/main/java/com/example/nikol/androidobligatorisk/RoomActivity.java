package com.example.nikol.androidobligatorisk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Room room = (Room) getIntent().getExtras().get("ROOM");

        TextView TextName = findViewById(R.id.RoomName);
        TextName.setText(room.getName());
    }
}
