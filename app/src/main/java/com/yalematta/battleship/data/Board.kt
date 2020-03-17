package com.yalematta.battleship.data

import com.yalematta.battleship.internal.FieldOccupiedException

class Board(var boardX: Int = 10, var boardY: Int = 10) {

    // An ArrayList to store and keep track of ships, their points, names and status (hit or sunk)
    var fleet: ArrayList<Ship> = arrayListOf()

    // 0: empty, not hit / 1: empty, but hit / 2: not empty, not hit / 3: not empty, but hit
    private var fieldStatus = Array(boardX) { _ -> Array(boardY) { _ -> 0} }

    fun getFieldStatusItem(x: Int, y: Int): Int {
        return fieldStatus[x][y]
    }

    fun getFieldStatus(): Array<Array<Int>> {
        return fieldStatus
    }

    // Check if it's OK to place ship
    fun canPlaceShip(theShip: Ship): Boolean {
        val iterate: Iterator<Coordinate> = theShip.coords.iterator()
        while (iterate.hasNext()) {
            val fieldCoord: Coordinate = iterate.next()
            val x: Int = fieldCoord.x
            val y: Int = fieldCoord.y
            if (x >= boardX || y >= boardY) {
                throw FieldOccupiedException(fieldCoord, "Field already occupied")
            }
            // If ship already in this field
            if (fieldStatus[x][y] != 0) {
                throw FieldOccupiedException(fieldCoord, "Field already occupied")
            }
        }
        return true
    }

    // Place the ship on the board
    @Throws(FieldOccupiedException::class)
    fun placeShip(theShip: Ship) {
        val iterate: Iterator<Coordinate> = theShip.coords.iterator()
        while (iterate.hasNext()) {
            val placeCoord = iterate.next()
            val x = placeCoord.x
            val y = placeCoord.y
            // If ship already in this field
            if (fieldStatus[x][y] != 0) {
                throw FieldOccupiedException(placeCoord, "Field already occupied")
            } else {
                // Set fields to not empty, not hit
                fieldStatus[x][y] = 2
            }
        }
        // Add the ship to the fleet
        fleet.add(theShip)
    }

}