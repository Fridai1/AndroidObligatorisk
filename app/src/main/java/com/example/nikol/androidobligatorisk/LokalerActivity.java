package com.example.nikol.androidobligatorisk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LokalerActivity extends AppCompatActivity {

    public static Building building;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokaler);
        building = (com.example.nikol.androidobligatorisk.Building) getIntent().getExtras().get("BUILDING");
        GetLokaleTask task = new GetLokaleTask();
        task.execute("https://anbo-roomreservation.azurewebsites.net/api/rooms");

        }

    private class GetLokaleTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            Request.Builder builder = new Request.Builder();
            String uri = strings[0];
            builder.url(uri);
            Request request = builder.build();
            try {
                OkHttpClient client = new OkHttpClient.Builder().build();
                Response response = client.newCall(request).execute();
                String JsonString = response.body().string();
                Log.d("BUILDINGS", JsonString);
                return JsonString;
            } catch (IOException ex) {
                Log.e("BUILDINGS", ex.getMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String JsonString) {

            Gson gson = new GsonBuilder().create();
            final Room[] rooms = gson.fromJson(JsonString, Room[].class);

            ListView listView = findViewById(R.id.LokalerListView);

            List<Room> SpecificRooms = new ArrayList<>();
            for (Room a:rooms) {
                if (a.getBuildingId() == building.getId())
                {
                    SpecificRooms.add(a);
                }

            }
            final ArrayAdapter<Room> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, SpecificRooms);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), RoomActivity.class);
                    Room room = (Room) parent.getItemAtPosition(position);
                    intent.putExtra("ROOM",room);
                    startActivity(intent);
                }
            });
        }
    }
}
