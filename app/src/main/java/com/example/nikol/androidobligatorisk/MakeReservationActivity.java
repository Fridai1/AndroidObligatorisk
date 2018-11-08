package com.example.nikol.androidobligatorisk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.MessageFormat;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MakeReservationActivity extends AppCompatActivity {

    FirebaseUser user;
    String day;
    String month;
    String year;
    EditText fromTime;
    EditText toTime;
    EditText Purpose;
    Room room;
    String json;
    String uri;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);
        // APPBAR
        Toolbar myToolbar = findViewById(R.id.MakeResToolBar);
        setSupportActionBar(myToolbar);

        // get extras fra tidligere intent
        user = (FirebaseUser) getIntent().getExtras().get("USER");
        day = String.valueOf(getIntent().getExtras().get("DAY"));
        month = String.valueOf(getIntent().getExtras().get("MONTH"));
        year = String.valueOf(getIntent().getExtras().get("YEAR"));
        room = (Room) getIntent().getExtras().get("ROOM");

        // get view info
        fromTime = findViewById(R.id.MakeResFromTime);
        toTime = findViewById(R.id.MakeResToTime);
        Purpose = findViewById(R.id.MakeResPurpose);

        // set URI
        uri = "https://anbo-roomreservation.azurewebsites.net/api/reservations";

        // konstruer strings


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

    public void PostRes(View view) {
        try {
            JSONObject jsonObject = new JSONObject();
            String fromDateTime = month + "-" + day + "-" + year + " " + fromTime.getText().toString();
            String toDateTime = month + "-" + day + "-" + year + " " + toTime.getText().toString();
            String purpose = Purpose.getText().toString();
            jsonObject.put("fromTimeString", fromDateTime);
            jsonObject.put("toTimeString", toDateTime);
            jsonObject.put("userId", user.getUid());
            jsonObject.put("purpose",purpose);
            jsonObject.put("roomId", room.getId());

            String jsonDocument = jsonObject.toString();

            PostBookTask task = new PostBookTask();
            task.execute("https://anbo-roomreservation.azurewebsites.net/api/reservations", jsonDocument);

        } catch (JSONException ex) {
           Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private class PostBookTask extends AsyncTask<String, Void, CharSequence> {


        @Override
        protected CharSequence doInBackground(String... params) {
            String urlString = params[0];
            String jsonDocument = params[1];
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(jsonDocument);
                osw.flush();
                osw.close();
                int responseCode = connection.getResponseCode();
                if (responseCode / 100 != 2) {
                    String responseMessage = connection.getResponseMessage();
                    throw new IOException("HTTP response code: " + responseCode + " " + responseMessage);
                }
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();
                return line;
            } catch (MalformedURLException ex) {
                cancel(true);
                String message = ex.getMessage() + " " + urlString;
                Log.e("RES", message);
                return message;
            } catch (IOException ex) {
                cancel(true);
                Log.e("RES", ex.getMessage());
                return ex.getMessage();
            }
        }

        @Override
        protected void onPostExecute(CharSequence charSequence) {
            super.onPostExecute(charSequence);
            String charseq = charSequence.toString();
            Toast.makeText(MakeReservationActivity.this, "Post Success", Toast.LENGTH_SHORT).show();
            Log.d("MINE", charSequence.toString());
            finish();
        }

        @Override
        protected void onCancelled(CharSequence charSequence) {
            super.onCancelled(charSequence);

            Toast.makeText(MakeReservationActivity.this, "Post Failed", Toast.LENGTH_SHORT).show();
            Log.d("MINE", charSequence.toString());
            finish();
        }
    }
}

