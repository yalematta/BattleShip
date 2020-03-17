package com.yalematta.battleship.data

class Ship(val shipType: ShipType, var orientation: Orientation = Orientation.VERTICAL) {

    var coords: ArrayList<Coordinate> = arrayListOf()

}