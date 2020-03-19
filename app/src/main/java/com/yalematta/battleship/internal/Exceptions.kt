package com.yalematta.battleship.internal

import com.yalematta.battleship.data.models.Coordinate

open class BattleShipException (message: String = ""): Exception(message)

// Exception class for handling situations when a ship is already on the board
class FieldOccupiedException (problemCoord: Coordinate, message: String = ""): BattleShipException()

// Exception-class for handling problematic shots fired
class InvalidShotException (problemCoord: Coordinate, message: String = ""): BattleShipException()