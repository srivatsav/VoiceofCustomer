package com.sri.voiceofcustomer.survey.fragment.admin;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sri.voiceofcustomer.R;

import java.util.ArrayList;

/**
 * Created by SRiGorthi on 22-01-2017.
 */

public class StatsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.stats_layout,container,false);
        PieChart piechart =(PieChart)view.findViewById(R.id.stats_chart);
        ArrayList<Entry> entries = new ArrayList<Entry>();
                entries.add(new Entry(4f, 0));
                entries.add(new Entry(8f, 1));
                entries.add(new Entry(6f, 2));
                entries.add(new Entry(12f, 3));
                entries.add(new Entry(18f, 4));
                entries.add(new Entry(9f, 5));
        PieDataSet dataSet=new PieDataSet(entries,"no Of calls");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<String> labels = new ArrayList<String>();
                labels.add("January");
                labels.add("Feb");
                labels.add("Mar");
                labels.add("April");
                labels.add("May");
                labels.add("June");

        PieData data = new PieData(labels, dataSet);
        piechart.setData(data);
        piechart.setDescription("Sample data");
        piechart.animateY(5000);
        return view;
    }


}
