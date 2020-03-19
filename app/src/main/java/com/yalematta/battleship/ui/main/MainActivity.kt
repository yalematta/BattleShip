package com.yalematta.battleship.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yalematta.battleship.R
import com.yalematta.battleship.internal.getViewModel
import com.yalematta.battleship.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        getViewModel { MainViewModel() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getStartedButton.setOnClickListener {
            launchLoginActivity()
        }
    }

    private fun launchLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        this.startActivity(intent)
        finish()
    }
}