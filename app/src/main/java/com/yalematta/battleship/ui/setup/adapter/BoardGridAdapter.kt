package com.yalematta.battleship.ui.setup.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.yalematta.battleship.R

class BoardGridAdapter(private val context: Context, private val fieldStatus: Array<Array<Int>>,
                       private val clickListener: (View, Int) -> Unit): BaseAdapter() {

    var fields = fieldStatus.flatten()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        // Inflate the custom view
        val inflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.board_cell,null)

        val grid: GridView = parent as GridView
        val size = grid.columnWidth

        // Get the custom view widgets reference
        val cell = view.findViewById<TextView>(R.id.cellView)

        // Display color on view
        cell.setBackgroundColor(ContextCompat.getColor(context, getStatusColor(fields[position])))

        cell.width = size
        cell.height = size

        cell.setOnClickListener { clickListener(cell, position) }

        // Finally, return the view
        return view
    }

    private fun getStatusColor(status: Int): Int {
        return when (status) {
            0 -> R.color.colorWater
            1 -> R.color.colorMissed
            2 -> R.color.colorSelected
            else -> {
                R.color.colorHit
            }
        }
    }

    override fun getItem(position: Int): Any? {
        return fields[position]
    }

    fun getItem(columnNum: Int, rowNum: Int): Any {
        return fieldStatus[columnNum][rowNum];
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return fields.size
    }

    override fun isEnabled(position: Int): Boolean {
        return super.isEnabled(position)
    }

    fun refresh(fieldStatus: Array<Array<Int>>) {
        fields = fieldStatus.flatten()
        notifyDataSetChanged()
    }
}