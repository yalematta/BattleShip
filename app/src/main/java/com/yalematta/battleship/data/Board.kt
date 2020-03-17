package com.yalematta.battleship.data

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
}