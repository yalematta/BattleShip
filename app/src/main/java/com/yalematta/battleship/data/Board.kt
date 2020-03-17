package com.yalematta.battleship.data

class Board(var boardX: Int = 10, var boardY: Int = 10) {

    var fleet: ArrayList<Ship> = arrayListOf()

    private var fieldStatus = Array(boardX) { _ -> Array(boardY) { _ -> 0} }

    fun getFieldStatusItem(x: Int, y: Int): Int {
        return fieldStatus[x][y]
    }

    fun getFieldStatus(): Array<Array<Int>> {
        return fieldStatus
    }
}