package com.example.nikol.androidobligatorisk

import android.content.Intent
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.google.firebase.auth.FirebaseUser

class StartPageActivity : AppCompatActivity() {
    internal var user: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_page)
        val myToolbar = findViewById<Toolbar>(R.id.StartToolBar)
        setSupportActionBar(myToolbar)


    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {


        super.onSaveInstanceState(outState, outPersistentState)

    }

    fun ToLogin(view: View) {
        val intent = Intent(baseContext, LoginActivity::class.java)
        startActivity(intent)
    }

    fun ToBuildings(view: View) {
        val intent = Intent(baseContext, BuildingActivity::class.java)
        intent.putExtra("USER", user)
        startActivity(intent)
    }


}
