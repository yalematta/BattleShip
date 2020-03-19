package com.yalematta.battleship.data

import com.yalematta.battleship.internal.FieldOccupiedException
import com.yalematta.battleship.internal.InvalidShotException

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

    // Check if it's OK to place shot
    fun canPlaceShot(coord: Coordinate): Boolean {
        val x: Int = coord.x
        val y: Int = coord.y
        val field = fieldStatus[x][y]
        // If field value 0 or 2, it's OK to shoot
        if (field == 0 || field == 2) {
            return true;
        }
        return false;
    }

    // Places shot on the board
    @Throws(InvalidShotException::class)
    fun placeShot(coord: Coordinate): Int {
        val x = coord.x
        val y = coord.y

        // If this field has been hit before, throw exception
        if (fieldStatus[x][y] == 1 || fieldStatus[x][y] == 3) {
            throw InvalidShotException(coord, "This field has already been hit");
        } else if (fieldStatus[x][y] == 0) {
            fieldStatus[x][y] = 1
            return fieldStatus[x][y];
        } else {
            fieldStatus[x][y] = 3
            // We have a successful shot, and the ship must remember that it has been hit
            for (ship: Ship in this.fleet) {
                if (ship.hasCoordinates(coord)) {
                    ship.shipHit(coord)
                }
            }
            return fieldStatus[x][y];
        }
    }

    // Gets the name of a ship if it was destroyed. This method must be called after every shot.
    fun getShipNameIfKill(theCoord: Coordinate): String {
        for (ship: Ship in this.fleet) {
            if (ship.noMoreShip()) {
                return ship.shipType.shipName;
            }
        }
        return "";
    }

    // Gets the points for the ship if it was destroyed, and deletes the ship from the fleet.
    // This method must be called after every shot, and after the shipNameIfKill() method.
    fun getShipPointsIfKill(theCoord: Coordinate?): Int {
        for (i in fleet.indices) {
            if (fleet[i].noMoreShip()) {
                val retval: Int = fleet[i].shipType.points
                fleet.removeAt(i)
                return retval
            }
        }
        return 0
    }

    // Game is over if fleet ArrayList is empty
    fun isGameOver(): Boolean {
        return fleet.isEmpty()
    }

}