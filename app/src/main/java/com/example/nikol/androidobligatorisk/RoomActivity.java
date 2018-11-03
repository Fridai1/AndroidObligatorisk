package com.example.nikol.androidobligatorisk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RoomActivity extends AppCompatActivity {
public static Room room;
int day;
int month;
int year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        room = (Room) getIntent().getExtras().get("ROOM");
        FirebaseUser user =(FirebaseUser) getIntent().getExtras().get("USER");
        day =(int) getIntent().getExtras().get("DAY");
        month =(int) getIntent().getExtras().get("MONTH");
        year =(int) getIntent().getExtras().get("YEAR");

        Toolbar myToolbar = findViewById(R.id.StartToolBar);
        setSupportActionBar(myToolbar);

        String dayS = String.valueOf(day);
        String MonthS = String.valueOf(month);
        String YearsS = String.valueOf(year);


        String dateSelected = YearsS+"-"+dayS+"-"+MonthS;

        TextView TextName = findViewById(R.id.RoomName);
        TextName.setText(room.getName());
        String roomSelected = String.valueOf(room.getId());

        GetReservationTask task = new GetReservationTask();
        String URI = MessageFormat.format("https://anbo-roomreservation.azurewebsites.net/api/reservations/room/{0}/date/{1}",roomSelected,dateSelected);
        task.execute(URI);


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
           /* String dayS = String.valueOf(day);
            String MonthS = String.valueOf(month);
            String YearsS = String.valueOf(year);



            List<Reservation> roomReservations = new ArrayList<>();

            String dateSelected = dayS+"-"+MonthS+"-"+YearsS;
            int selectedRoom = room.getId();

            for (Reservation a:reservations) {
                String date = a.getDate();
                int aRoomId = a.getRoomId();
                if (aRoomId == selectedRoom && date == dateSelected)
                {
                    roomReservations.add(a);
                }


            }*/
            final ArrayAdapter<Reservation> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, reservations);

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
