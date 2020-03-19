package com.yalematta.battleship.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.yalematta.battleship.R
import com.yalematta.battleship.data.firebase.FirebaseSource
import com.yalematta.battleship.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        const val id = "id"
        const val username = "username"
        const val RC_SIGN_IN: Int = 1234
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonSignIn.setOnClickListener {

            if (FirebaseSource.currentUser() != null) {
                Toast.makeText(applicationContext, getString(R.string.user_already_in), Toast.LENGTH_SHORT).show()
                // already signed in

            } else {
                // Choose authentication providers
                val providers = listOf(AuthUI.IdpConfig.EmailBuilder().build())

                // Create and launch sign-in intent
                startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(), RC_SIGN_IN
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // RC_SIGN_IN is the request code you passed into
        if (requestCode == RC_SIGN_IN) {
            // Successfully signed in
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseSource.currentUser()

                addPlayer(user)
            }
        } else {
            // Sign in failed. If response is null the user canceled the sign-in flow using the back button.
            // Otherwise check response.getError().getErrorCode() and handle the error.
            Toast.makeText(applicationContext, getString(R.string.error_sign_in), Toast.LENGTH_SHORT).show()
        }
    }

    private fun addPlayer(user: FirebaseUser?) {

        val userId: String = user!!.uid
        val reference = FirebaseDatabase.getInstance().getReference(FirebaseSource.PLAYERS_TABLE).child(userId)

        val hashMap: HashMap<String, String> = hashMapOf()
        hashMap[id] = userId
        hashMap[username] = user.displayName.toString()

        reference.setValue(hashMap).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Toast.makeText(applicationContext, getString(R.string.success_sign_in), Toast.LENGTH_SHORT).show()
                launchMainActivity(user)
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(applicationContext, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun launchMainActivity(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            finish();
        }
    }
}

