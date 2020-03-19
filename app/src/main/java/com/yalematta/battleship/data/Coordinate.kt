package com.yalematta.battleship.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Coordinate (val x: Int, val y: Int) : Parcelable {

    fun isSameCoordinate (coord: Coordinate): Boolean {
        return this.x == coord.x && this.y == coord.y
    }
}