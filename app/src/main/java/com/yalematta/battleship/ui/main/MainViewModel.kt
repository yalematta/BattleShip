package com.yalematta.battleship.ui.main

import androidx.lifecycle.ViewModel
import com.yalematta.battleship.data.firebase.FirebaseSource
import com.yalematta.battleship.data.models.Player

class MainViewModel : ViewModel() {

    fun getMyPlayer(): Player {
        val currentUser = FirebaseSource().currentUser()
        return Player(currentUser?.displayName.toString(), 0)
    }
}