package com.yalematta.battleship.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object FirebaseSource {

    const val PLAYERS_TABLE: String = "players"
    const val ROOMS_TABLE: String = "rooms"

    const val SHOT_SENT_BACK: String = "shot_sent_back"
    const val SHOT_SENT: String = "shot_sent"
    const val SCORE: String = "score"
    const val GUEST: String = "guest"
    const val HOST: String = "host"

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    fun currentUser() = firebaseAuth.currentUser

}