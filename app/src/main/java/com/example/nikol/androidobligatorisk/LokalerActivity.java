package com.example.nikol.androidobligatorisk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class LokalerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokaler);
        // creates the Listener
        AdapterView.OnItemClickListener ItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                if (position == 0){
                    Intent intent = new Intent(LokalerActivity.this, BuildingActivity.class);
                    startActivity(intent);
                }
            }
        };

        //add the listener
        ListView listview = (ListView) findViewById(R.id.LokalerListView);
        listview.setOnItemClickListener(ItemClickListener);

    }
}
