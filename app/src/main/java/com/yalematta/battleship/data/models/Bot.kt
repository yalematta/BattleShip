package com.yalematta.battleship.data.models

import java.util.*

class Bot {

    private fun chooseRandomCell(theBoard: Board): Coordinate {

        val x = (0..theBoard.boardX).random()
        val y = (0..theBoard.boardY).random()

        return Coordinate(x, y)
    }

    fun returnNextMove(theBoard: Board) : Coordinate {

        if (theBoard.isGameOver())
            throw Exception("Game is already over!")

        var coordinate = chooseRandomCell(theBoard)

        while (!theBoard.canPlaceShot(coordinate)) {
            coordinate =  chooseRandomCell(theBoard)
        }
        return coordinate
    }
}