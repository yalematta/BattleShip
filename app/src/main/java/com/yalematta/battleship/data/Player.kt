package com.yalematta.battleship.data

import com.yalematta.battleship.internal.FieldOccupiedException
import java.util.*

data class Player(var playerName: String, private var score: Int = 0) {

    // Adds points to the players score
    fun addScore(points: Int) {
        score += points
    }

    // Initialize ship field to be used for creating ships
    private var ship: Ship? = null

    // Method that generates random ships
    fun generateShips(theBoard: Board) {

        // Used to generate pseudo-random coordinates
        val random = Random()

        // The highest number to generate
        val max: Int = theBoard.boardX;

        // List of the possible orientations
        val directions: Array<Orientation> = Orientation.values()

        val ships: Array<ShipType> = ShipType.values()

        val shipMap: EnumMap<ShipType, Boolean> = EnumMap(
            ShipType::class.java)

        for (i in 0..4) {
            shipMap[ships[i]] = true
        }

        for (entry in shipMap.entries) {

            while (entry.value) {

                // Direction of vessel
                val dir: Orientation = directions[random.nextInt(directions.size)]

                // Generate random offset coordinates
                val x: Int = random.nextInt(max);
                val y: Int = random.nextInt(max);

                // Offset coordinate
                var coord: Coordinate

                ship = Ship(entry.key)

                for (i in 0 until ship?.shipType!!.size) {
                    coord = if (dir == Orientation.VERTICAL) {
                        Coordinate(x + i, y)
                    } else {
                        Coordinate(x, y + i)
                    }
                    ship?.coords?.add(coord)
                }

                //Test if ship can be placed, if true, place ship
                try {
                    if (theBoard.canPlaceShip(this.ship!!)) {
                        theBoard.placeShip(ship!!)
                        // We're done placing the Ship
                        entry.setValue(false)
                    }
                } catch (foe: FieldOccupiedException) {
                    println("An error occurred: $foe")
                    foe.printStackTrace()
                }
            }
        }
    }
}