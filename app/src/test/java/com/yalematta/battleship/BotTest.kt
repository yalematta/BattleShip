package com.yalematta.battleship

import com.yalematta.battleship.data.models.*
import org.junit.Test
import org.junit.Assert

internal class BotTest {

    @Test
    fun botPlacesOnUnoccupiedCoordinate() {
        val bot : Bot =
            Bot()
        val board: Board =
            Board()
        val ship: Ship =
            Ship(ShipType.SUBMARINE)
        ship.coords.add(Coordinate(0, 0))
        ship.coords.add(Coordinate(0, 1))
        board.placeShip(ship)

        val coordinate = bot.returnNextMove(board)

        Assert.assertTrue(board.canPlaceShot(coordinate))

    }

    @Test
    fun botPlacesOnUnoccupiedCoordinateOnSecondTry() {
        val bot : Bot =
            Bot()
        val board: Board =
            Board()
        val ship: Ship =
            Ship(ShipType.SUBMARINE)
        ship.coords.add(Coordinate(0, 0))
        ship.coords.add(Coordinate(0, 1))
        board.placeShip(ship)

        val firstCoordinate = bot.returnNextMove(board)

        board.placeShot(firstCoordinate)

        Assert.assertFalse(board.canPlaceShot(firstCoordinate))

        val secondCoordinate = bot.returnNextMove(board)

        Assert.assertFalse(firstCoordinate.isSameCoordinate(secondCoordinate))

        Assert.assertTrue(board.canPlaceShot(secondCoordinate))

    }
}