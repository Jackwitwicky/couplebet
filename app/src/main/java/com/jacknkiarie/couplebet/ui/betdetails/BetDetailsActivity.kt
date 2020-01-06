package com.jacknkiarie.couplebet.ui.betdetails

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.jacknkiarie.couplebet.R
import com.jacknkiarie.couplebet.models.Bet
import com.jacknkiarie.couplebet.ui.editbet.EditBetActivity
import com.jacknkiarie.couplebet.ui.history.HistoryViewModel
import com.jacknkiarie.couplebet.ui.history.HistoryViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_bet_details.*
import java.util.*


class BetDetailsActivity : AppCompatActivity() {

    private var viewModel: NewBetDetailsViewModel? = null

    // views
    private var titleView: TextView? = null
    private var betuid: Int = 0
    private lateinit var bet : Bet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bet_details)
        bet = intent.getSerializableExtra("BET") as Bet

        val factory = BetDetailsViewModelFactory(this.getApplication())
        viewModel = ViewModelProviders.of(this, factory).get(NewBetDetailsViewModel::class.java)

        details_bet.text = bet.title
        bet_details_initiator_winnings.text = bet.initiatorReward
        participant_winnings.text = bet.participantReward
        the_bet_details_creation_date.text = bet.creationDate
        bet_details_status.text = "- ${bet.status.toUpperCase()}"

        bet_details_declare_winner_button.setOnClickListener {
            declareWinnerDialog()
        }

        bet_details_cancel_button.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_bet_details_cancel_bet_title)
                    .setMessage(R.string.dialog_bet_details_cancel_bet_content)
                    .setPositiveButton(android.R.string.yes) { _, _ ->
                        bet.status = Bet.STATUS_CANCELLED
                        viewModel!!.insert(bet)
                        Toast.makeText(this,
                                R.string.dialog_bet_details_cancel_bet_confirmed, Toast.LENGTH_SHORT).show()

                    }.setNegativeButton(android.R.string.no) { _, _ ->
                        // dismiss the dialog
                    }.show()
        }

        bet_details_edit_button.setOnClickListener {
            // go to edit bet screen
            var editBetIntent = Intent(this@BetDetailsActivity, EditBetActivity::class.java)
            editBetIntent.putExtra(Bet.BET_EXTRA, bet)
            startActivity(editBetIntent)
        }


        println("The bet title is: " + bet.title)

        println("The bet in full contains the following info: " + bet.toString())

        var appBar = supportActionBar
        appBar?.title = "Bet Details"
        appBar?.setDisplayHomeAsUpEnabled(true)

        setupUI()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        if (bet.status == Bet.STATUS_CANCELLED || bet.status ==  Bet.STATUS_EXPIRED || bet.status == Bet.STATUS_COMPLETED) {
            bet_details_cancel_button.visibility = View.INVISIBLE
        }

        if (bet.status != Bet.STATUS_ONGOING) {
            bet_details_declare_winner_button.visibility = View.INVISIBLE
            bet_details_edit_button.visibility = View.INVISIBLE
        }

        if(bet.betWinner != null && bet.betWinner.isNotEmpty()) {
            bet_details_bet_winner.setText(bet.betWinner.capitalize())
        }

        if(bet.expiryDate != null || !bet.status.equals(Bet.STATUS_EXPIRED)) {
            setUpExpiryTimer()
        }

//        bet.status = Bet.STATUS_ONGOING
//        viewModel?.insert(bet)
//        Toast.makeText(this@BetDetailsActivity, "Saaved", Toast.LENGTH_SHORT).show()
    }

    private fun setUpExpiryTimer() {
        var currentCalendar =  Calendar.getInstance()

        var expiryCalendar = Calendar.getInstance()
        var expiryDateElements = bet.expiryDate.split("-")

        expiryCalendar.set(Calendar.DAY_OF_MONTH, expiryDateElements[0].toInt())
        expiryCalendar.set(Calendar.MONTH, expiryDateElements[1].toInt())
        expiryCalendar.set(Calendar.YEAR, expiryDateElements[2].toInt())

        println("The expiry date is: $expiryCalendar")

        if(expiryCalendar.before(currentCalendar) && (bet.status != Bet.STATUS_COMPLETED || bet.status != Bet.STATUS_CANCELLED) ) {
            bet_details_status.text = Bet.STATUS_EXPIRED
            bet.status = Bet.STATUS_EXPIRED.toUpperCase()
            viewModel?.insert(bet)
        } else {
            // display the expiry date timer countdown
            // in the format hh::mm::ss

//            var timerString = "${expiryCalendar.get(Calendar.HOUR)}"

            val difference = expiryCalendar.getTimeInMillis() - currentCalendar.getTimeInMillis()
            val days = (difference / (1000 * 60 * 60 * 24)).toInt()
            bet_details_expiry_time.text = days.toString()

//            val seconds = (difference / (1000)).toInt()

        }
    }

    private fun declareWinnerDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.dialog_bet_details_cancel_bet_title)
                .setItems(R.array.bet_participants_array, DialogInterface.OnClickListener {dialog, position ->
                    println("The selected position is: $position")
                    if (position == 0 ) {
                        bet.betWinner = "jack"
                    }
                    else {
                        bet.betWinner = "mercy"
                    }
                    bet.status = Bet.STATUS_COMPLETED
                    bet_details_status.text = bet.status

                    viewModel?.insert(bet)

                    Toast.makeText(this@BetDetailsActivity, "This bet has been completed successfully", Toast.LENGTH_SHORT).show()

                }).show()
    }
}
