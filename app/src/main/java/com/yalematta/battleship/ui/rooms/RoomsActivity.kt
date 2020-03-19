package com.yalematta.battleship.ui.rooms

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.yalematta.battleship.R
import com.yalematta.battleship.data.firebase.FirebaseSource
import com.yalematta.battleship.data.models.Player
import com.yalematta.battleship.ui.main.MainActivity.Companion.ME_PLAYER
import com.yalematta.battleship.ui.main.MainActivity.Companion.ROLE_NAME
import com.yalematta.battleship.ui.main.MainActivity.Companion.ROOM_NAME
import com.yalematta.battleship.ui.main.MainActivity.Companion.VS_PLAYER
import com.yalematta.battleship.ui.setup.SetupActivity
import kotlinx.android.synthetic.main.activity_rooms.*

class RoomsActivity : AppCompatActivity() {

    lateinit var myPlayer: Player

    var roomName: String = ""
    var roleName: String = ""

    private lateinit var roomsListener: ValueEventListener

    private lateinit var database: FirebaseDatabase
    private lateinit var roomsRef: DatabaseReference
    private lateinit var roomRef: DatabaseReference

    var roomsList: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)
        title = getString(R.string.choose_opponent)

        database = FirebaseSource().database
        myPlayer = intent.getParcelableExtra(ME_PLAYER)

        createRoomButton.setOnClickListener {

            createRoomButton.text = getString(R.string.creating_room)
            createRoomButton.isEnabled = false
            roomName = myPlayer.playerName
            addNewRoom(myPlayer.playerName)
        }

        roomsView.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
            roomName = roomsList[position]
            addToExistingRoom(roomName, myPlayer.playerName)
        }

        showRooms()
    }

    private fun showRooms() {

        roomsRef = database.getReference(FirebaseSource.ROOMS_TABLE)

        roomsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                roomsList.clear()

                val rooms: Iterable<DataSnapshot> = dataSnapshot.children
                for (snapShot: DataSnapshot in rooms) {
                    roomsList.add(snapShot.key!!)
                    val adapter: ArrayAdapter<String> = ArrayAdapter(
                        this@RoomsActivity,android.R.layout.simple_list_item_1,
                        roomsList
                    )
                    roomsView.adapter = adapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        roomsRef.addValueEventListener(roomsListener)

    }

    private fun addToExistingRoom(roomName: String, username: String) {

        if (roomName == username) {
            roomRef = database.getReference(FirebaseSource.ROOMS_TABLE)
                .child(roomName + "/" + FirebaseSource.HOST)
            roomRef.setValue(username)
            roleName = FirebaseSource.HOST
        } else {
            roomRef = database.getReference(FirebaseSource.ROOMS_TABLE)
                .child(roomName + "/" + FirebaseSource.GUEST)
            roomRef.setValue(username)
            roleName = FirebaseSource.GUEST
        }

        setRoomValue(roomRef, getString(R.string.success_join_room))
    }

    private fun addNewRoom(username: String) {

        roomRef = database.getReference(FirebaseSource.ROOMS_TABLE)
            .child(username + "/" + FirebaseSource.HOST)

        roomRef.setValue(username)
        roleName = FirebaseSource.HOST

        setRoomValue(roomRef, getString(R.string.success_create_room))
    }

    private fun setRoomValue(roomRef: DatabaseReference, message: String) {

        val roomListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                roomsList.clear()

                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                createRoomButton.text = getString(R.string.create_room)
                createRoomButton.isEnabled = true

                val intent = Intent(this@RoomsActivity, SetupActivity::class.java)

                intent.apply {
                    putExtra(VS_PLAYER, Player("Player", 0))
                    putExtra(ME_PLAYER, myPlayer)
                    putExtra(ROOM_NAME, roomName)
                    putExtra(ROLE_NAME, roleName)
                }

                startActivity(intent)
                finish()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, getString(R.string.error), Toast.LENGTH_SHORT).show()
                createRoomButton.text = getString(R.string.create_room)
                createRoomButton.isEnabled = true
            }
        }
        roomRef.addValueEventListener(roomListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        roomsRef.removeEventListener(roomsListener)
    }
}
