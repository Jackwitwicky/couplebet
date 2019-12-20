package com.jacknkiarie.couplebet.ui.betdetails

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.jacknkiarie.couplebet.R
import com.jacknkiarie.couplebet.models.Bet
import com.jacknkiarie.couplebet.ui.history.HistoryViewModel
import com.jacknkiarie.couplebet.ui.history.HistoryViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_bet_details.*

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



        println("The bet title is: " + bet.title)

        println("The bet in full contains the following info: " + bet.toString())

        var appBar = supportActionBar
        appBar?.title = "Bet Details"
        appBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
