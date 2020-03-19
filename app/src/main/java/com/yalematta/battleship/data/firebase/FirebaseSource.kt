package com.yalematta.battleship.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseSource {

    companion object{
        const val PLAYERS_TABLE: String = "players"
    }

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    fun currentUser() = firebaseAuth.currentUser

}