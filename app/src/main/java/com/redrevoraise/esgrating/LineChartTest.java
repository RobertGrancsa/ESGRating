package com.redrevoraise.esgrating;

import static com.github.mikephil.charting.animation.Easing.EaseOutBack;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class LineChartTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        LineChart lineChart = findViewById(R.id.lineChart);

        ArrayList<Entry> testData = new ArrayList<>();
        testData.add(new Entry(2014, 400));
        testData.add(new Entry(2015, 500));
        testData.add(new Entry(2016, 300));
        testData.add(new Entry(2017, 700));
        testData.add(new Entry(2018, 800));
        testData.add(new Entry(2019, 900));
        testData.add(new Entry(2020, 400));
        testData.add(new Entry(2021, 400));
        testData.add(new Entry(2022, 700));
        testData.add(new Entry(2023, 500));
        testData.add(new Entry(2024, 900));
        testData.add(new Entry(2025, 1000));

        LineDataSet dataSet = new LineDataSet(testData, "Ceva"); // add entries to dataset
        LineData lineData = new LineData(dataSet);

        dataSet.setDrawValues(false);
        dataSet.setColor(Color.MAGENTA);
        dataSet.setDrawHorizontalHighlightIndicator(false);
        dataSet.setDrawCircles(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        lineChart.setGridBackgroundColor(Color.WHITE);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.animateX(2000, EaseOutBack);
        lineChart.setBorderWidth(0);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.setData(lineData);

        lineChart.invalidate();
    }

}