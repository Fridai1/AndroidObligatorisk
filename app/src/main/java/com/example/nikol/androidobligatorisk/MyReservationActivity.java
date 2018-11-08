package com.example.nikol.androidobligatorisk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.MessageFormat;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyReservationActivity extends AppCompatActivity {

    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);

       user =(FirebaseUser) getIntent().getExtras().get("USER");
       String userId = user.getUid();
       GetMyResTask task = new GetMyResTask();
       String uri = MessageFormat.format("https://anbo-roomreservation.azurewebsites.net/api/reservations/user/{0}",userId);
       task.execute(uri);


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

    private class GetMyResTask extends AsyncTask<String, Void, String> {
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
            ListView alistView = findViewById(R.id.MyResListView);



            final ArrayAdapter<Reservation> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, reservations);

            alistView.setAdapter(adapter);
            alistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), ReservationActivity.class);
                    Reservation reservation = (Reservation) parent.getItemAtPosition(position);
                    intent.putExtra("RESERVATION",reservation);
                    intent.putExtra("USER", user);
                    startActivity(intent);;
                }
            });
        }
    }
}
