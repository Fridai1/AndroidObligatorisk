package com.example.nikol.androidobligatorisk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BuildingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        GetBuildingsTask task = new GetBuildingsTask();
        task.execute("https://anbo-roomreservation.azurewebsites.net/api/buildings");

    }


private class GetBuildingsTask extends AsyncTask<String, Void, String> {
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
        final Building[] Buildings = gson.fromJson(JsonString, Building[].class);

        ListView listView = findViewById(R.id.BuildingListView);

        final ArrayAdapter<Building> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, Buildings);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), RoomsActivity.class);
                Building building = (Building) parent.getItemAtPosition(position);
                intent.putExtra("BUILDING",building);
                startActivity(intent);
            }
        });
    }
    }
}
