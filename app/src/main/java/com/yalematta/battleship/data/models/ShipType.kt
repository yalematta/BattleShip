package com.yalematta.battleship.data.models

enum class ShipType(val size: Int, val shipName: String, val points: Int) {
    CARRIER(5, "Carrier", 50),
    CRUISER(3, "Cruiser", 30),
    SUBMARINE(2, "Submarine", 20),
    DESTROYER(3, "Destroyer", 30),
    BATTLESHIP(4,"BattleShip", 40)
}