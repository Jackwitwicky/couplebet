package com.jacknkiarie.couplebet.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.jacknkiarie.couplebet.R;
import com.jacknkiarie.couplebet.models.Bet;
import com.jacknkiarie.couplebet.ui.betdetails.BetDetailsActivity;
import com.jacknkiarie.couplebet.util.Util;

import java.util.ArrayList;
import java.util.List;

public class BetHistoryAdapter extends RecyclerView.Adapter<BetHistoryAdapter.MyViewHolder> {
    private List<Bet> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item
        // is just a string in this case

        TextView titleView;
        TextView statusView;
        TextView winnerView;
        TextView creationDateView;
        RelativeLayout layoutView;

        public MyViewHolder(View v) {
            super(v);

            titleView = v.findViewById(R.id.bet_history_bet_title);
            statusView = v.findViewById(R.id.bet_history_bet_status);
            winnerView = v.findViewById(R.id.bet_history_bet_winner);
            creationDateView = v.findViewById(R.id.bet_history_bet_time);
            layoutView = v.findViewById(R.id.bet_history_item_layout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BetHistoryAdapter(Context context, ArrayList<Bet> myDataset) {
        this.context = context;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BetHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bet_history, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        "Test content", Toast.LENGTH_SHORT).show();

                Intent detailsIntent = new Intent(context, BetDetailsActivity.class);
                detailsIntent.putExtra("BET", mDataset.get(position));
                context.startActivity(detailsIntent);
            }
        });

        holder.titleView.setText(mDataset.get(position).getTitle());
        holder.statusView.setText(
                Util.capitalizeWord(mDataset.get(position).getStatus()));
        if (mDataset.get(position).getStatus().equals(Bet.STATUS_COMPLETED)) {
            holder.winnerView.setText(mDataset.get(position).getBetWinner());
        }
        else {
            holder.winnerView.setText("");
        }
        holder.creationDateView.setText(mDataset.get(position).getCreationDate());
    }

    public void setBets(List<Bet> bets){
        mDataset = bets;
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
