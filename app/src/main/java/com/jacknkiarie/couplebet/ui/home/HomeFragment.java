package com.jacknkiarie.couplebet.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jacknkiarie.couplebet.R;
import com.jacknkiarie.couplebet.models.Bet;
import com.jacknkiarie.couplebet.util.Util;

import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView noBetsView;
    private RelativeLayout currentBetLayout;

    // views
    private TextView betExpiryView;
    private TextView betPropositionView;
    private TextView betCreationDateView;
    private TextView betInitiatorTitleView;
    private TextView betInitiatorWinningsView;
    private TextView betParticipantTitleView;
    private TextView betParticipantWinningsView;
    private Button declareWinnerButton;

    Bet bet;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModelProviderFactory factory = new HomeViewModelProviderFactory(getActivity().getApplication());
        homeViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        noBetsView = root.findViewById(R.id.current_bet_no_bet);
        currentBetLayout = root.findViewById(R.id.current_bet_layout);
        betExpiryView = root.findViewById(R.id.current_bet_expiry_time);
        betPropositionView = root.findViewById(R.id.the_bet);
        betCreationDateView = root.findViewById(R.id.current_bet_details_creation_date);
        betInitiatorTitleView = root.findViewById(R.id.initiator_winnings_title);
        betInitiatorWinningsView = root.findViewById(R.id.initiator_winnings);
        betParticipantTitleView = root.findViewById(R.id.participant_winnings_title);
        betParticipantWinningsView = root.findViewById(R.id.participant_winnings);
        declareWinnerButton = root.findViewById(R.id.current_bet_declare_winner);
        declareWinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                declareWinnerDialog();
            }
        });


        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        homeViewModel.getOngoingBets().observe(this, new Observer<List<Bet>>() {
            @Override
            public void onChanged(List<Bet> bets) {
                // get the most recent bet and check which is ongoing
                if (bets.isEmpty()) {
                    noBetsView.setVisibility(View.VISIBLE);
                    currentBetLayout.setVisibility(View.INVISIBLE);
                }
                else {
                    noBetsView.setVisibility(View.INVISIBLE);
                    Bet currentBet = bets.get(bets.size() - 1);
                    bet = currentBet;
                    setupUI(currentBet);
                }
            }
        });
        return root;
    }


    private void declareWinnerDialog() {
        if (bet == null) {
            return;
        }
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_bet_details_cancel_bet_title)
                .setItems(R.array.bet_participants_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        if (position == 0) {
                            Toast.makeText(getActivity(),
                                    "The winner is Jack", Toast.LENGTH_SHORT).show();
                            bet.setBetWinner("jack");
                        }
                        else {
                            Toast.makeText(getActivity(),
                                    "The winner is Mercy", Toast.LENGTH_SHORT).show();
                            bet.setBetWinner("mercy");
                        }

                        bet.setStatus(Bet.STATUS_COMPLETED);
                        homeViewModel.insert(bet);
                    }
                })
                .show();
    }

    private void setupUI(Bet bet) {
        betPropositionView.setText(bet.getProposition());
        betInitiatorWinningsView.setText(bet.getInitiatorReward());
        betParticipantWinningsView.setText(bet.getParticipantReward());
        betCreationDateView.setText(bet.getCreationDate());

        if(bet.getExpiryDate() != null) {
            setUpExpiryTimer(bet);
        }

        betInitiatorTitleView.setText(String.format(getString(R.string.initiator_wins), Util.capitalizeWord(bet.getInitiator())));
        betParticipantTitleView.setText(String.format(getString(R.string.participant_wins), Util.capitalizeWord(bet.getParticipant())));

//        bet.status = Bet.STATUS_ONGOING
//        viewModel?.insert(bet)
//        Toast.makeText(this@BetDetailsActivity, "Saaved", Toast.LENGTH_SHORT).show()
    }

    private void setUpExpiryTimer(Bet bet) {
        Calendar currentCalendar =  Calendar.getInstance();

        Calendar expiryCalendar = Calendar.getInstance();
        String[] expiryDateElements = bet.getExpiryDate().split("-");

        expiryCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(expiryDateElements[0]));
        expiryCalendar.set(Calendar.MONTH, Integer.parseInt(expiryDateElements[1]));
        expiryCalendar.set(Calendar.YEAR, Integer.parseInt(expiryDateElements[2]));

        // display the expiry date timer countdown
        long difference = expiryCalendar.getTimeInMillis() - currentCalendar.getTimeInMillis();
        int days = (int) (difference / (1000 * 60 * 60 * 24));
        betExpiryView.setText(Integer.toString(days));
    }
}