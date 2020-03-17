package com.yalematta.battleship.ui.setup

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yalematta.battleship.R
import com.yalematta.battleship.data.Board
import com.yalematta.battleship.data.Player
import kotlinx.android.synthetic.main.activity_setup.*
import kotlin.math.floor

class SetupActivity : AppCompatActivity() {

    private lateinit var board: Board
    private lateinit var boardAdapter: BoardGridAdapter

    private val player = Player("Layale", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        initBoard()

        randomButton.setOnClickListener {
            generateRandomShips()
        }
    }

    private fun initBoard() {
        board = Board()

        boardAdapter = BoardGridAdapter(this, board.getFieldStatus())
        { view: View, position: Int -> handleBoardClick(view, position) }

        boardGridView.adapter = boardAdapter
    }

    private fun handleBoardClick(view: View, position: Int) {

        val x: Int = floor((position / board.boardX).toDouble()).toInt()
        val y: Int = position % board.boardX

        // TODO Implement placing the ships manually
    }

    private fun generateRandomShips() {
        board = Board()
        player.generateShips(board)
        boardAdapter.refresh(board.getFieldStatus())
    }
}
