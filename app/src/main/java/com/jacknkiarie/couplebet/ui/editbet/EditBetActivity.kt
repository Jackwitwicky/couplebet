package com.jacknkiarie.couplebet.ui.editbet

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.jacknkiarie.couplebet.MainActivity
import com.jacknkiarie.couplebet.R
import com.jacknkiarie.couplebet.models.Bet
import kotlinx.android.synthetic.main.activity_edit_bet.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class EditBetActivity : AppCompatActivity() {

    lateinit var bet : Bet

    var betExpiryDate : String = ""

    // variables
    private var betInitiator = ""
    private var betParticipant  = ""
    private var betTitle = ""
    private var betProposition = ""
    private var betInitiatorWinnings = ""
    private var betParticipantWinnings = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bet)

        // modify the app bar
        supportActionBar?.title = "Edit Bet"

        bet = intent.getSerializableExtra(Bet.BET_EXTRA) as Bet
        betExpiryDate = bet.expiryDate ?: ""

        val factory = EditBetViewModelProviderFactory(this@EditBetActivity.getApplication())
        var editBetViewModel = ViewModelProviders.of(this, factory).get(EditBetViewModel::class.java!!)

        setupUI()
        edit_expiry_date_button.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this@EditBetActivity, myDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        })

        save_edit_bet_button.setOnClickListener {
            if (validateFields()) {
                bet.initiator = betInitiator
                bet.participant = betParticipant
                bet.title = betTitle
                bet.proposition = betProposition
                bet.initiatorReward = betInitiatorWinnings
                bet.participantReward = betParticipantWinnings

                editBetViewModel.insert(bet)

                Toast.makeText(this@EditBetActivity, "Your bet has been updated successfully", Toast.LENGTH_SHORT).show()
                val mainIntent = Intent(this@EditBetActivity, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }

    }

    private fun setupUI() {
        edit_bet_title.setText(bet.title)
        edit_bet_proposition.setText(bet.proposition)
        edit_bet_initiator_winning.setText(bet.initiatorReward)
        edit_bet_participant_winning.setText(bet.participantReward)
        edit_bet_expiry.setText(bet.expiryDate ?: getString(R.string.pending_date_expiry))
    }

    private fun validateFields(): Boolean {
        var isValidated = true

        betInitiator = text_edit_bet_initiator.getText().toString().trim({ it <= ' ' })
        betParticipant = text_bet_participant.getText().toString().trim({ it <= ' ' })
        betTitle = edit_bet_title.getText().toString().trim({ it <= ' ' })
        betProposition = edit_bet_proposition.getText().toString().trim({ it <= ' ' })
        betInitiatorWinnings = edit_bet_initiator_winning.getText().toString().trim({ it <= ' ' })
        betParticipantWinnings = edit_bet_participant_winning.getText().toString().trim({ it <= ' ' })
        //        betExpiryDate;

        // validate bet title
        if (betTitle.isEmpty()) {
            edit_bet_title.setError("Bet title cannot be blank")
            isValidated = false
        }

        // validate bet proposition
        if (betProposition.isEmpty()) {
            edit_bet_proposition.setError("Bet proposition cannot be blank")
            isValidated = false
        }

        // validate initiator winnings
        if (betInitiatorWinnings.isEmpty()) {
            edit_bet_initiator_winning.setError("Initiator winnings cannot be blank")
            isValidated = false
        }

        // validate participant winnings
        if (betParticipantWinnings.isEmpty()) {
            edit_bet_participant_winning.setError("Participant winnings cannot be blank")
            isValidated = false
        }

        // validate the expiry date
        if (betExpiryDate.isEmpty()) {
            isValidated = false
            Toast.makeText(this@EditBetActivity,
                    "Please select the bet expiry date", Toast.LENGTH_SHORT).show()
        }

        return isValidated
    }


    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        return (calendar.get(Calendar.DAY_OF_MONTH).toString() + "-"
                + calendar.get(Calendar.MONTH) + "-"
                + calendar.get(Calendar.YEAR))
    }

    private val myDateListener = DatePickerDialog.OnDateSetListener { arg0, year, month, day ->
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.set(Calendar.DAY_OF_MONTH, day)
        selectedCalendar.set(Calendar.MONTH, month)
        selectedCalendar.set(Calendar.DAY_OF_MONTH, day)

        if (selectedCalendar.before(Calendar.getInstance())) {
            Toast.makeText(this@EditBetActivity,
                    "The expiry date cannot be in the past", Toast.LENGTH_SHORT).show()
            betExpiryDate = ""
        } else {
            betExpiryDate = "$year-$month-$day"
            edit_bet_expiry.setText(betExpiryDate)
        }
    }
}
