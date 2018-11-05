package com.example.nikol.androidobligatorisk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.text.MessageFormat;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReservationActivity extends AppCompatActivity {

    private Reservation thisReservation;
    FirebaseUser user;
    DeleteResTask task;
    String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        thisReservation = (Reservation) getIntent().getExtras().get("RESERVATION");
        user = (FirebaseUser) getIntent().getExtras().get("USER");
        Button deleteBTN = findViewById(R.id.DeleteBtn);




        TextView userId = findViewById(R.id.UserIdRes);
        TextView fromTime = findViewById(R.id.FromTimeRes);
        TextView toTime = findViewById(R.id.ToTimeRes);
        TextView purpose = findViewById(R.id.PurposeRes);

        userId.setText(thisReservation.getUserId());
        fromTime.setText(thisReservation.getFromTimeString());
        toTime.setText(thisReservation.getToTimeString());
        purpose.setText(thisReservation.getPurpose());
        String loggedInUserId = "";

        if (user != null)
        {
            loggedInUserId = user.getUid();
        }

        String reservationUserId = thisReservation.getUserId();

        // den er jo HELT gal her, hvis jeg sætte den til at gå ind hvis de er lig med hinanden går den ikke ind på trods af at de er
        // hvis jeg siger du skal gå hvis i ikke er lig hinanden gå den ind på trods af at de er lig med hinande.. så jeg bytter om på
        // skjul og hvis metoden.. det giver ingen mening..
        deleteBTN.setVisibility(View.GONE);
        if (loggedInUserId == reservationUserId )
        {
            deleteBTN.setVisibility(View.VISIBLE);
        }

         task = new DeleteResTask();
         uri = "https://anbo-roomreservation.azurewebsites.net/api/reservations/";

    }


    public void DeleteRes(View view) {
    task.execute("uri");
    }


    private class DeleteResTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            String code = "";
            HttpUrl route = HttpUrl.parse(uri)
                    .newBuilder()
                    .addPathSegment(thisReservation.getId())
                    .build();
            Request request = new Request.Builder().url(route).delete().build();

                try{
                    OkHttpClient client = new OkHttpClient.Builder().build();
                    Response response = client.newCall(request).execute();
                    if (response.code() == 204) code = "complete";
                   else if (response.code() == 404) code = "not found";
                   else code = String.valueOf(response.code());
                   return code;
                }catch (IOException ex) {
                    Log.e("BUILDINGS", ex.getMessage());
                }
                return null;

        }

        @Override
        protected void onPostExecute(String JsonString) {

            if (JsonString == "complete")
            {
                Toast.makeText(ReservationActivity.this, "Delete completed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), MyReservationActivity.class);
                intent.putExtra("USER",user);
            }
            else if (JsonString == "not found")
            {
                Toast.makeText(ReservationActivity.this, "Delete Failed, Nothing found", Toast.LENGTH_SHORT).show();
            }

            else{
                Toast.makeText(ReservationActivity.this, JsonString, Toast.LENGTH_SHORT).show();
            }

        }
    }

}
