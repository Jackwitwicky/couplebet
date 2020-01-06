package com.jacknkiarie.couplebet.ui.statistics;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.jacknkiarie.couplebet.R;
import com.jacknkiarie.couplebet.models.Bet;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel statisticsViewModel;
    private LineChart lineChart;
    private TextView winnerTextView;
//    private List<Bet> allBets;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        StatisticsViewModelProviderFactory factory = new StatisticsViewModelProviderFactory(getActivity().getApplication());
        statisticsViewModel = ViewModelProviders.of(this, factory).get(StatisticsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_statistics, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);

//        allBets = statisticsViewModel.getCompleteBets().getValue();

        winnerTextView = root.findViewById(R.id.bet_statistics_winner);

        lineChart = root.findViewById(R.id.statistics_chart);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawGridBackground(false);
//        lineChart.


        // setup the x axis
        XAxis xAxis;
        xAxis = lineChart.getXAxis();

//        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);

        statisticsViewModel.getCompleteBets().observe(this, new Observer<List<Bet>>() {
            @Override
            public void onChanged(List<Bet> bets) {

                // add data
                setData(bets);

                lineChart.animateX(1500);
            }
        });

        return root;
    }

    private void setData(List<Bet> bets) {

        ArrayList<Entry> jackValues = new ArrayList<>();
        ArrayList<Entry> mercyValues = new ArrayList<>();

        for (int i = 0; i < bets.size(); i++) {
            if (bets.get(i).getBetWinner() != null && bets.get(i).getBetWinner().equals("jack")) {
                jackValues.add(new Entry(i, i));
                if (mercyValues.size() > 1) {
                    mercyValues.add(mercyValues.get(mercyValues.size() - 1));
                    mercyValues.add(new Entry(i, mercyValues.get(mercyValues.size() - 1).getY()));
                }
            }
            else if(bets.get(i).getBetWinner() != null && bets.get(i).getBetWinner().equals("mercy")) {
                mercyValues.add(new Entry(i, i));
                if (jackValues.size() > 1) {
                    jackValues.add(new Entry(i, jackValues.get(jackValues.size() - 1).getY()));
                }
            }
        }

//        for (int i = 0; i < count; i++) {
//
//            float val = (float) (Math.random() * 2);
//            Log.d("Blue", "THe value generate is: " + val);
//            if (val > 1) {
//                jackValues.add(new Entry(i, i));
//                if (mercyValues.size() > 1) {
//                    mercyValues.add(mercyValues.get(mercyValues.size() - 1));
//                    mercyValues.add(new Entry(i, mercyValues.get(mercyValues.size() - 1).getY()));
//                }
//            }
//            else {
//                mercyValues.add(new Entry(i, i));
//                if (jackValues.size() > 1) {
//                    jackValues.add(new Entry(i, jackValues.get(jackValues.size() - 1).getY()));
//                }
//            }
//
//        }

        LineDataSet set1;
        LineDataSet set2;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(jackValues);
            set1.notifyDataSetChanged();
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(jackValues, "Jack");

            set1.setDrawIcons(false);

            // draw dashed line
//            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);


            // create a dataset and give it a type
            set2 = new LineDataSet(mercyValues, "Mercy");

            set1.setDrawIcons(false);

            // draw dashed line
//            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set2.setColor(Color.BLUE);
            set2.setCircleColor(Color.BLUE);

            // line thickness and point size
            set2.setLineWidth(1f);
            set2.setCircleRadius(3f);

            // draw points as solid circles
            set2.setDrawCircleHole(false);

            // customize legend entry
            set2.setFormLineWidth(1f);
            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set2.setFormSize(15.f);

            // text size of values
            set2.setValueTextSize(9f);

            // draw selection line as dashed
//            set2.enableDashedHighlightLine(10f, 5f, 0f);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets
            dataSets.add(set2);

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            lineChart.setData(data);
        }

        // check who is winning
        float highestJackValue = 0;
        float highestMercyValue = 0;
        if (jackValues.size() > 1) {
            highestJackValue = jackValues.get(jackValues.size() - 1).getY();
        }

        if (mercyValues.size() > 1) {
            highestMercyValue = mercyValues.get(mercyValues.size() - 1).getY();
        }

        if (highestJackValue > highestMercyValue) {
            winnerTextView.setText("Jack is winning!");
        }
        else if(highestMercyValue > highestJackValue) {
            winnerTextView.setText("Mercy is winning!");
        }
        else {
            winnerTextView.setText("We are tied!");
        }
    }
}