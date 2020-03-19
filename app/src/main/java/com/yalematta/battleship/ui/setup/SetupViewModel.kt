package com.yalematta.battleship.ui.setup

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yalematta.battleship.data.models.*
import com.yalematta.battleship.internal.FieldOccupiedException

class SetupViewModel: ViewModel() {

    var board: Board =
        Board()
    var shipList: ArrayList<Ship> = arrayListOf()

    private val player = Player("Layale", 0)

    private var selectedShip: Ship? = null
    private var shipDirection = Orientation.VERTICAL

    var shipListVisibility = false
    var startGameVisibility = false

    val refreshBoardLiveData = MutableLiveData<Board>()
    val refreshShipsLiveData = MutableLiveData<ArrayList<Ship>>()
    val shipsLiveData = MutableLiveData<ArrayList<Ship>>()
    val blinkLiveData = MutableLiveData<View>()

    fun generateRandomShips() {
        player.generateShips(board)
        refreshBoardLiveData.value = board
    }

    fun rotateShip() {
        shipDirection = if (shipDirection == Orientation.VERTICAL)
            Orientation.HORIZONTAL
        else
            Orientation.VERTICAL
        selectedShip?.orientation = shipDirection
    }

    fun initShips() {
        shipList = arrayListOf()
        shipList.apply {
            add(Ship(ShipType.CARRIER))
            add(Ship(ShipType.CRUISER))
            add(Ship(ShipType.DESTROYER))
            add(Ship(ShipType.SUBMARINE))
            add(Ship(ShipType.BATTLESHIP))
        }
        shipsLiveData.value = shipList
    }

    fun selectedShip(ship: Ship) {
        selectedShip = ship
        selectedShip?.orientation = shipDirection
    }

    // Note: this view might leak if activity is destroyed
    fun handleBoardClick(view: View, position: Int) {
        val x: Int = kotlin.math.floor((position / board.boardX).toDouble()).toInt()
        val y: Int = position % board.boardX

        if (selectedShip != null) {

            val ship = selectedShip

            try {
                player.tryPlaceShip(board, ship!!,
                    Coordinate(x, y)
                )

                shipList.remove(ship)
                selectedShip = null

                refreshBoardLiveData.value = board
                refreshShipsLiveData.value = shipList

            } catch (foe: FieldOccupiedException) {
                blinkLiveData.value = view
                ship!!.coords.clear()
            }
        }
    }

    fun isShipListEmpty(): Boolean {
        return shipList.isEmpty()
    }
}
