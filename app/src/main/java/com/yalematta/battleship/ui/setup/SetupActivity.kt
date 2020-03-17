package com.yalematta.battleship.ui.setup

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.yalematta.battleship.R
import com.yalematta.battleship.data.*
import com.yalematta.battleship.internal.FieldOccupiedException
import com.yalematta.battleship.internal.getViewModel
import com.yalematta.battleship.ui.setup.adapter.BoardGridAdapter
import com.yalematta.battleship.ui.setup.adapter.ShipListAdapter
import kotlinx.android.synthetic.main.activity_setup.*
import kotlinx.coroutines.*
import kotlin.math.floor

class SetupActivity : AppCompatActivity(), Animation.AnimationListener {

    private val viewModel by lazy {
        getViewModel { SetupViewModel() }
    }

    private lateinit var board: Board
    private lateinit var shipAdapter: ShipListAdapter
    private lateinit var boardAdapter: BoardGridAdapter

    private var selectedShip: Ship? = null
    private lateinit var shipList: ArrayList<Ship>
    private var shipDirection = Orientation.VERTICAL

    private val player = Player("Layale", 0)

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        initBoard()

        initShips()

        randomButton.setOnClickListener {
            generateRandomShips()
            randomButton.visibility = View.GONE;
            manualButton.visibility = View.GONE;
            startButton.visibility = View.VISIBLE;
        }

        manualButton.setOnClickListener {
            randomButton.visibility = View.GONE;
            manualButton.visibility = View.GONE;
            shipsLayout.visibility = View.VISIBLE;
        }

        rotateButton.setOnClickListener {
            shipDirection = if (shipDirection == Orientation.VERTICAL)
                Orientation.HORIZONTAL
            else
                Orientation.VERTICAL
            selectedShip?.orientation = shipDirection
        }

        startButton.setOnClickListener{
            // TODO Show 2 Boards on Game Screen
        }
    }

    private fun initBoard() {
        board = Board()

        boardAdapter =
            BoardGridAdapter(
                this,
                board.getFieldStatus()
            )
            { view: View, position: Int -> handleBoardClick(view, position) }

        boardGridView.adapter = boardAdapter
    }

    private fun initShips() {
        shipList = arrayListOf()
        shipList.apply {
            add(Ship(ShipType.CARRIER))
            add(Ship(ShipType.CRUISER))
            add(Ship(ShipType.DESTROYER))
            add(Ship(ShipType.SUBMARINE))
            add(Ship(ShipType.BATTLESHIP))
        }

        shipAdapter = ShipListAdapter(this, shipList)

        shipListView.adapter = shipAdapter

        shipListView.setOnItemClickListener { _, view, position, _ ->
            selectedShip = shipAdapter.getItem(position) as Ship
            selectedShip?.orientation = shipDirection
            shipAdapter.selectedPosition = position;
            shipAdapter.notifyDataSetChanged();
        }
    }

    private fun handleBoardClick(view: View, position: Int) {

        val x: Int = floor((position / board.boardX).toDouble()).toInt()
        val y: Int = position % board.boardX

        if (selectedShip != null) {

            val ship = selectedShip

            try {
                player.tryPlaceShip(board, ship!!, Coordinate(x, y))
                boardAdapter.refresh(board.getFieldStatus())

                selectedShip = null
                shipList.remove(ship)
                shipAdapter.selectedPosition = -1
                shipAdapter.notifyDataSetChanged()

            } catch (foe: FieldOccupiedException) {
                setBlinkAnimation(view)
                ship!!.coords.clear()
            }
        }

        if (shipList.isEmpty()){
            rotateButton.visibility = View.GONE
            startButton.visibility = View.VISIBLE
        }
    }

    private fun generateRandomShips() {
        board = Board()
        player.generateShips(board)
        boardAdapter.refresh(board.getFieldStatus())
    }

    private fun setBlinkAnimation(view: View) {
        val animBlink: Animation = AnimationUtils.loadAnimation(this@SetupActivity, R.anim.blink_in);
        animBlink.setAnimationListener(this@SetupActivity)

        view.startAnimation(animBlink)
        scope.launch {
            delay(500)
            view.clearAnimation()
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    override fun onAnimationRepeat(animation: Animation?) {

    }

    override fun onAnimationEnd(animation: Animation?) {

    }

    override fun onAnimationStart(animation: Animation?) {

    }
}
