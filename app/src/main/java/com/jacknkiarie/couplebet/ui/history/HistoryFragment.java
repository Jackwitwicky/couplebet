package com.jacknkiarie.couplebet.ui.history;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jacknkiarie.couplebet.R;
import com.jacknkiarie.couplebet.adapters.BetHistoryAdapter;
import com.jacknkiarie.couplebet.models.Bet;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private RecyclerView historyRecyclerView;
    private BetHistoryAdapter betHistoryAdapter;
    private ArrayList<Bet> betHistoryList;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        // initialize variables
        betHistoryList = new ArrayList<>();


        historyRecyclerView = root.findViewById(R.id.recycler_view_bet_history);
        betHistoryAdapter = new BetHistoryAdapter(getActivity(), betHistoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        historyRecyclerView.setLayoutManager(layoutManager);
        historyRecyclerView.setAdapter(betHistoryAdapter);

//        historyViewModel =
//                new ViewModelProvider(this).get(HistoryViewModel.class);
        HistoryViewModelProviderFactory factory = new HistoryViewModelProviderFactory(getActivity().getApplication());
        historyViewModel = ViewModelProviders.of(this, factory).get(HistoryViewModel.class);
        final TextView textView = root.findViewById(R.id.text_history);
        historyViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        historyViewModel.getAllBets().observe(this, new Observer<List<Bet>>() {
            @Override
            public void onChanged(List<Bet> bets) {
                // update the caced copy of the bets in the adapter
                betHistoryAdapter.setBets(bets);
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        // TODO: Use the ViewModel
    }

//    public ArrayList<Bet> generateFakeBets() {
//        Bet bet1 = new Bet("Twerk contest", "This is a twerk contest bet", "Completed", "Mercy Won", "02/11/19");
//        betHistoryList.add(bet1);
//        betHistoryList.add(bet1);
//        betHistoryList.add(bet1);
//        betHistoryList.add(bet1);
//        betHistoryList.add(bet1);
//        betHistoryList.add(bet1);
//
//        return betHistoryList;
//    }

}
