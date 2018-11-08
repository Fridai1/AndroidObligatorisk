package com.example.nikol.androidobligatorisk;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RoomsActivity extends AppCompatActivity {

    FirebaseUser user;
    public static Building building;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        Toolbar myToolbar = findViewById(R.id.StartToolBar);
        setSupportActionBar(myToolbar);
        user = (FirebaseUser) getIntent().getExtras().get("USER");
        building = (com.example.nikol.androidobligatorisk.Building) getIntent().getExtras().get("BUILDING");
        GetRoomTask task = new GetRoomTask();
        task.execute("https://anbo-roomreservation.azurewebsites.net/api/rooms");

        }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if (user == null)
        {
            MenuItem item = menu.findItem(R.id.Signout);
            item.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Signout:
                Intent intent = new Intent(getBaseContext(), SignOutActivity.class);
                intent.putExtra("USER",user);
                startActivity(intent);
            case R.id.ToStart:
                if (user != null)
                {
                    Intent intent1 = new Intent(getBaseContext(), LoggedInActivity.class);
                    intent1.putExtra("USER", user);
                    startActivity(intent1);
                }
                else{
                    Intent intent2 = new Intent(getBaseContext(),StartPageActivity.class);
                    intent2.putExtra("USER", user);
                    startActivity(intent2);
                }

        }
        return super.onOptionsItemSelected(item);
    }

    private class GetRoomTask extends AsyncTask<String, Void, String> {


        int day;
        int month;
        int _year;
        Room room;

        private void ActivityGo(){
            Intent intent = new Intent(getBaseContext(), RoomActivity.class);
            intent.putExtra("ROOM",room);
            intent.putExtra("DAY", day);
            intent.putExtra("MONTH", month + 1);
            intent.putExtra("YEAR", _year);
            intent.putExtra("USER",user);
            startActivity(intent);

        }

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

        @RequiresApi(api = Build.VERSION_CODES.N)
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


            final Calendar myCalendar = Calendar.getInstance();


            // testing Datepicker
           final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                      myCalendar.set(Calendar.YEAR, year);
                      myCalendar.set(Calendar.MONTH, monthOfYear);
                      myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                      day = dayOfMonth;
                      month = monthOfYear;
                      _year = year;

                      ActivityGo();


                }

            };




            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    new DatePickerDialog(RoomsActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    room = (Room) parent.getItemAtPosition(position);




                }
            });


        }
    }
}
