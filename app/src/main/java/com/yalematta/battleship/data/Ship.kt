package com.yalematta.battleship.data

class Ship(val shipType: ShipType, var orientation: Orientation = Orientation.VERTICAL) {

    // An array list to store this ships coordinates
    var coords: ArrayList<Coordinate> = arrayListOf()

}