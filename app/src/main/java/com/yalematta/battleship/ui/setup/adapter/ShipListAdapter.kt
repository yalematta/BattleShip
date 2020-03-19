package com.yalematta.battleship.ui.setup.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.yalematta.battleship.R
import com.yalematta.battleship.data.models.Ship

class ShipListAdapter(private val context: Context): BaseAdapter(){

    var selectedPosition: Int = -1
    private var dataSource = arrayListOf<Ship>()

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun refreshShipList(shipList: ArrayList<Ship>) {
        dataSource.clear()
        dataSource.addAll(shipList)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowView = inflater.inflate(R.layout.ship_item, parent, false)

        val shipView = rowView.findViewById(R.id.shipView) as TextView

        shipView.text = dataSource[position].shipType.shipName

        if (selectedPosition == position) {
            shipView.setTypeface(null, Typeface.BOLD)
        } else {
            shipView.setTypeface(null, Typeface.NORMAL)
        }

        return rowView
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}
