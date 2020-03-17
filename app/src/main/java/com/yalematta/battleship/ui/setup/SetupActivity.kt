package com.yalematta.battleship.ui.setup

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.yalematta.battleship.R
import com.yalematta.battleship.data.Board
import com.yalematta.battleship.data.Ship
import com.yalematta.battleship.internal.getViewModel
import com.yalematta.battleship.ui.setup.adapter.BoardGridAdapter
import com.yalematta.battleship.ui.setup.adapter.ShipListAdapter
import kotlinx.android.synthetic.main.activity_setup.*
import kotlinx.coroutines.*

class SetupActivity : AppCompatActivity(), Animation.AnimationListener {

    private lateinit var shipAdapter: ShipListAdapter
    private lateinit var boardAdapter: BoardGridAdapter

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private val viewModel by lazy {
        getViewModel { SetupViewModel() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        initObservers()
        initBoardAdapter()
        initShipAdapter()

        randomButton.setOnClickListener {
            viewModel.generateRandomShips()
            startButton.visibility = View.VISIBLE
        }

        manualButton.setOnClickListener {
            viewModel.initShips()
            randomButton.visibility = View.GONE;
            manualButton.visibility = View.GONE;
            viewModel.shipListVisibility = true
            updateVisibility()
        }

        rotateButton.setOnClickListener {
            viewModel.rotateShip()
        }

        startButton.setOnClickListener {
            // TODO Show 2 Boards on Game Screen
        }

    }

    private fun initObservers() {
        viewModel.apply {
            refreshBoardLiveData.observe(this@SetupActivity,
                Observer { board -> refreshBoard(board) })
            shipsLiveData.observe(this@SetupActivity,
                Observer { ships -> addDataToShipAdapter(ships) })
            blinkLiveData.observe(this@SetupActivity,
                Observer { view -> setBlinkAnimation(view) })
            refreshShipsLiveData.observe(this@SetupActivity,
                Observer { shipList -> refreshShips(shipList) })
        }
    }

    override fun onResume() {
        super.onResume()
        updateVisibility()
    }

    private fun updateVisibility() {
        shipsLayout.visibility = if (viewModel.shipListVisibility) View.VISIBLE else View.GONE
    }

    private fun refreshBoard(board: Board) {
        boardAdapter.refresh(board.getFieldStatus())
        randomButton.visibility = View.GONE;
        manualButton.visibility = View.GONE;
    }

    private fun initBoardAdapter() {
        boardAdapter =
            BoardGridAdapter(
                this,
                viewModel.board.getFieldStatus()
            )
            { view: View, position: Int -> handleBoardClick(view, position) }

        boardGridView.adapter = boardAdapter
    }


    private fun addDataToShipAdapter(ships: ArrayList<Ship>) {
        shipAdapter.refreshShipList(ships)
    }

    private fun refreshShips(shipList: ArrayList<Ship>) {
        shipAdapter.selectedPosition = -1
        shipAdapter.refreshShipList(shipList)

        if (viewModel.isShipListEmpty()) {
            rotateButton.visibility = View.GONE
            startButton.visibility = View.VISIBLE
        }
    }

    private fun initShipAdapter() {
        shipAdapter = ShipListAdapter(this)
        shipListView.adapter = shipAdapter

        shipListView.setOnItemClickListener { _, _, position, _ ->
            val selectedShip = shipAdapter.getItem(position) as Ship
            viewModel.selectedShip(selectedShip)
            shipAdapter.selectedPosition = position;
            shipAdapter.notifyDataSetChanged();
        }
    }

    private fun handleBoardClick(view: View, position: Int) {
        viewModel.handleBoardClick(view, position)
    }

    private fun setBlinkAnimation(view: View) {
        val animBlink: Animation =
            AnimationUtils.loadAnimation(this@SetupActivity, R.anim.blink_in);
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
