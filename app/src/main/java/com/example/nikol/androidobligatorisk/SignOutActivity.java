package com.example.nikol.androidobligatorisk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignOutActivity extends AppCompatActivity {

    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (FirebaseUser) getIntent().getExtras().get("USER");
        setContentView(R.layout.activity_sign_out);

        SharedPreferences sharedPref = getSharedPreferences("MyPref" ,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("EMAIL", null);
        editor.putString("PASSWORD", null);
        editor.apply();

        user = null;
        Intent intent = new Intent(getBaseContext(), StartPageActivity.class);
        intent.putExtra("USER", user);
        Toast.makeText(this, "You have signed out", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
