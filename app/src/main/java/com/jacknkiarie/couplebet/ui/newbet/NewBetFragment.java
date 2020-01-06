package com.jacknkiarie.couplebet.ui.newbet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jacknkiarie.couplebet.R;
import com.jacknkiarie.couplebet.models.Bet;

import java.util.Calendar;

public class NewBetFragment extends Fragment {

    // views
    private TextView betInitiatorView;
    private TextView betParticipantView;
    private EditText betTitleEdit;
    private EditText betPropositionEdit;
    private EditText betInitiatorWinningsEdit;
    private EditText betParticipantWinningsEdit;
    private Button saveBetButton;
    private TextView betExpiryDateView;
    private ImageView betSwitchView;



    // string variables
    private String betInitiator;
    private String betParticipant;
    private String betTitle;
    private String betProposition;
    private String betInitiatorWinnings;
    private String betParticipantWinnings;
    private String betExpiryDate;


    private NewBetViewModel newBetViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewBetViewModelProviderFactory factory = new NewBetViewModelProviderFactory(getActivity().getApplication());
        newBetViewModel =
                ViewModelProviders.of(this, factory).get(NewBetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_new_bet, container, false);

        betInitiatorView = root.findViewById(R.id.text_bet_initiator);
        betParticipantView = root.findViewById(R.id.text_bet_participant);
        betTitleEdit = root.findViewById(R.id.new_bet_title);
        betPropositionEdit = root.findViewById(R.id.new_bet_proposition);
        betInitiatorWinningsEdit = root.findViewById(R.id.new_bet_initiator_winning);
        betParticipantWinningsEdit = root.findViewById(R.id.new_bet_participant_winning);
        saveBetButton = root.findViewById(R.id.save_bet_button);
        betExpiryDateView = root.findViewById(R.id.new_bet_expiry);

        saveBetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    Bet bet = new Bet(betInitiator, betParticipant,
                            betTitle, betProposition, Bet.STATUS_ONGOING,
                            null, null, getCurrentDate());
                    bet.setInitiatorReward(betInitiatorWinnings);
                    bet.setParticipantReward(betParticipantWinnings);
                    bet.setExpiryDate(betExpiryDate);


                    newBetViewModel.insert(bet);
                    Toast.makeText(getActivity(),
                            "The bet has been saved successfully", Toast.LENGTH_SHORT).show();
                }


            }
        });
        final TextView textView = root.findViewById(R.id.text_dashboard);
        newBetViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button expiryDateButton = root.findViewById(R.id.set_expiry_date_button);
        expiryDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(getActivity(), myDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        betSwitchView = root.findViewById(R.id.icon_bet_switch);
        betSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String betInitiatorString = betInitiatorView.getText().toString();
                String betParticipantString = betParticipantView.getText().toString();
                betInitiatorView.setText(betParticipantString);
                betParticipantView.setText(betInitiatorString);
            }
        });

        return root;
    }

    private boolean validateFields() {
        boolean isValidated = true;

        betInitiator = betInitiatorView.getText().toString().trim().toLowerCase();
        betParticipant = betParticipantView.getText().toString().trim().toLowerCase();
        betTitle = betTitleEdit.getText().toString().trim();
        betProposition = betPropositionEdit.getText().toString().trim();
        betInitiatorWinnings = betInitiatorWinningsEdit.getText().toString().trim();
        betParticipantWinnings = betParticipantWinningsEdit.getText().toString().trim();
//        betExpiryDate;

        // validate bet title
        if (betTitle.isEmpty()) {
            betTitleEdit.setError("Bet title cannot be blank");
            isValidated = false;
        }

        // validate bet proposition
        if (betProposition.isEmpty()) {
            betPropositionEdit.setError("Bet proposition cannot be blank");
            isValidated = false;
        }

        // validate initiator winnings
        if (betInitiatorWinnings.isEmpty()) {
            betInitiatorWinningsEdit.setError("Initiator winnings cannot be blank");
            isValidated = false;
        }

        // validate participant winnings
        if (betParticipantWinnings.isEmpty()) {
            betParticipantWinningsEdit.setError("Participant winnings cannot be blank");
            isValidated = false;
        }

        // validate the expiry date
        if (betExpiryDate == null || betExpiryDate.isEmpty()) {
            isValidated = false;
            Toast.makeText(getActivity(),
                    "Please select the bet expiry date", Toast.LENGTH_SHORT).show();
        }

        return isValidated;
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH) + "-"
                + calendar.get(Calendar.MONTH) + "-"
                + calendar.get(Calendar.YEAR);
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int year, int month, int day) {

                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(Calendar.DAY_OF_MONTH, day);
                    selectedCalendar.set(Calendar.MONTH, month);
                    selectedCalendar.set(Calendar.DAY_OF_MONTH, day);

                    if (selectedCalendar.before(Calendar.getInstance())) {
                        Toast.makeText(getActivity(),
                                "The expiry date cannot be in the past", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        betExpiryDate = day + "-" + month + "-" + year;
                        betExpiryDateView.setText(betExpiryDate);
                    }
                }
            };

}