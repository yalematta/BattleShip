package com.yalematta.battleship.data.models

enum class Orientation {

    HORIZONTAL {
        override fun translate(startPoint: Coordinate?, stepsToMove: Int): Coordinate {
            return Coordinate(
                startPoint!!.x + stepsToMove,
                startPoint.y
            )
        }
    },
    VERTICAL {
        override fun translate(startPoint: Coordinate?, stepsToMove: Int): Coordinate {
            return Coordinate(
                startPoint!!.x,
                startPoint.y + stepsToMove
            )
        }
    };

    abstract fun translate(startPoint: Coordinate?, stepsToMove: Int): Coordinate?
}