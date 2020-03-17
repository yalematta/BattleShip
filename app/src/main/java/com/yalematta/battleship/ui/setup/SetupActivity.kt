package com.yalematta.battleship.ui.setup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yalematta.battleship.R
import com.yalematta.battleship.data.Board

class SetupActivity : AppCompatActivity() {

    private lateinit var board: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        initBoard()
    }

    private fun initBoard() {
        board = Board()

    }

}
