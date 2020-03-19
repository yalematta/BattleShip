package com.yalematta.battleship.data

class Ship(val shipType: ShipType, var orientation: Orientation = Orientation.VERTICAL) {

    // An array list to store this ships coordinates
    var coords: ArrayList<Coordinate> = arrayListOf()

    // Registers that this ship has been hit
    fun shipHit(coord: Coordinate?): Unit {
        val iterator = coords.iterator()
        for(i in iterator){
            if(i.isSameCoordinate(coord!!)){
                println("Removed a coordinate from ships own array")
                iterator.remove()
            }
        }
    }

    // Checks if there are any parts left of this ship
    fun noMoreShip(): Boolean {
        return coords.isEmpty()
    }

    // Tests whether this ship is placed on these coordinates
    fun hasCoordinates(theCoord: Coordinate?): Boolean {
        for (coord in coords) {
            if (coord.isSameCoordinate(theCoord!!)) {
                println("Ship.hasCoordinates(): true")
                return true
            }
        }
        return false
    }

}