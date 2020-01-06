package com.jacknkiarie.couplebet.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jacknkiarie.couplebet.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


    private void declareWinnerDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_bet_details_cancel_bet_title)
                .setItems(R.array.bet_participants_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        if (position == 0) {
                            Toast.makeText(getActivity(),
                                    "The winner is Jack", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(),
                                    "The winner is Mercy", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();

//                .setItems(R.array.bet_participants_array, DialogInterface.OnClickListener {dialog, position ->
//            if (position == 0 ) {
//                bet.betWinner = "jack"
//            }
//            else {
//                bet.betWinner = "mercy"
//            }
//            bet.status = Bet.STATUS_COMPLETED
//            bet_details_status.text = bet.status
//
//            viewModel?.insert(bet)
//
//            Toast.makeText(this@BetDetailsActivity, "This bet has been completed successfully", Toast.LENGTH_SHORT).show()
//
//        }).show()
    }
}