package com.yalematta.battleship.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.yalematta.battleship.R
import com.yalematta.battleship.data.firebase.FirebaseSource
import com.yalematta.battleship.internal.getViewModel
import com.yalematta.battleship.ui.login.LoginActivity
import com.yalematta.battleship.ui.setup.SetupActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        getViewModel { MainViewModel() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logoutButton.setOnClickListener {

            AuthUI.getInstance().signOut(this@MainActivity).addOnCompleteListener {
                // user is now signed out
                Toast.makeText(applicationContext, getString(R.string.user_signed_out), Toast.LENGTH_SHORT).show()
                launchLoginActivity()
            }
        }

        continueButton.setOnClickListener {
            val intent = Intent(this, SetupActivity::class.java)
            startActivity(intent)
        }

        getStartedButton.setOnClickListener {
            launchLoginActivity()
        }
    }

    private fun launchLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        this.startActivity(intent)
        finish()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        updateUI(FirebaseSource().currentUser())
    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {

            welcomeView.text = String.format("Welcome - %s", currentUser.displayName)
            getStartedButton.visibility = View.GONE
            logoutButton.visibility = View.VISIBLE
            continueButton.visibility = View.VISIBLE

        } else {

            getStartedButton.visibility = View.VISIBLE
            logoutButton.visibility = View.GONE
            continueButton.visibility = View.GONE
        }
    }
}




