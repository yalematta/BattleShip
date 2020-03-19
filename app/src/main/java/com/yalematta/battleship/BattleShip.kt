package com.yalematta.battleship

import android.app.Application
import android.util.Log
import com.google.firebase.database.FirebaseDatabase

open class BattleShip: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Log.d("BattleShip", "application created");
    }
}