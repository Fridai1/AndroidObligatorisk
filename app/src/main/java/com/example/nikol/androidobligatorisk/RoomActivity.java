package com.example.nikol.androidobligatorisk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RoomActivity extends AppCompatActivity {
public static Room room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
       room = (Room) getIntent().getExtras().get("ROOM");

        TextView TextName = findViewById(R.id.RoomName);
        TextName.setText(room.getName());

        GetReservationTask task = new GetReservationTask();
        task.execute("https://anbo-roomreservation.azurewebsites.net/api/reservations");


    }

    private class GetReservationTask extends AsyncTask<String, Void, String> {
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
            final Reservation[] reservations = gson.fromJson(JsonString, Reservation[].class);

            ListView alistView = findViewById(R.id.RoomListView);

            List<Reservation> roomReservations = new ArrayList<>();

            for (Reservation a:reservations) {
                if (a.getRoomId() == room.getId())
                {
                    roomReservations.add(a);
                }

            }
            final ArrayAdapter<Reservation> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, roomReservations);

            alistView.setAdapter(adapter);
            alistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), ReservationActivity.class);
                    Reservation reservation = (Reservation) parent.getItemAtPosition(position);
                    intent.putExtra("RESERVATION",reservation);
                    startActivity(intent);
                }
            });
        }
    }
}
