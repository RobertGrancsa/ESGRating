package com.redrevoraise.esgrating;

import static android.content.ContentValues.TAG;

import static com.github.mikephil.charting.animation.Easing.EaseOutBack;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.chip.ChipGroup;
import com.redrevoraise.esgrating.databinding.ActivityScrollingBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Delayed;

public class CompanyActivity extends AppCompatActivity {
    private TextView companyName;
    private TextView companyGrade;
    private TextView companyESG;
    private TextView companyTicker;
    private LinearLayout data;
    private TextView companySector;
    private ChipGroup chipGroup;

    private TextView eScore;
    private TextView eGrade;
    private TextView sScore;
    private TextView sGrade;
    private TextView gScore;
    private TextView gGrade;

    private PieChart esgChart;
    private LineChart lineChart;

    private Company comp;

    private ActivityScrollingBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

//        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        Toolbar toolbar = binding.toolbar;
//        setSupportActionBar(toolbar);
//        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
//        toolBarLayout.setTitle("null");

        companyName = findViewById(R.id.companyNameDash);
        companyGrade = findViewById(R.id.companyGrade);
        companyESG = findViewById(R.id.currentESG);
        companyTicker = findViewById(R.id.companyTickerScreen);
        companySector = findViewById(R.id.sector);
        data = findViewById(R.id.data);
        chipGroup = findViewById(R.id.chipGroup);

        eScore = findViewById(R.id.eScore);
        eGrade = findViewById(R.id.eGrade);
        sScore = findViewById(R.id.sScore);
        sGrade = findViewById(R.id.sGrade);
        gScore = findViewById(R.id.gScore);
        gGrade = findViewById(R.id.gGrade);

        lineChart = findViewById(R.id.lineChart);
        esgChart = findViewById(R.id.piechart);

        String key = getIntent().getStringExtra("ticker");

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                int tag = chipGroup.getCheckedChipId();
                int graphType = 0;
                switch (tag){
                    case R.id.esg:
                        graphType = 0;
                        break;
                    case R.id.environmental:
                        graphType = 1;
                        break;
                    case R.id.social:
                        graphType = 2;
                        break;
                    case R.id.governance:
                        graphType = 3;
                        break;
                    default:
                        break;
                }
                addData(graphType);
            }
        });

        new FirebaseDatabaseHelper().readCompany(key, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoadedComp(List<CompanyFront> companies, List<String> keys) {

            }

            @Override
            public void DataIsLoadedCompany(Company company, String key) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }, 1000);
                comp = company;
                updateView(company);
                makePieChart(company);
                addData(0);
                setBackground();
//                        toolBarLayout.setTitle(company.getCompany_name());
                Log.d(TAG, "DataIsLoadedCompany: DOne");
            }

            @Override
            public void DataIsLoadedTop(List<CompanyFront> companies, List<String> keys) {

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    private void setBackground() {
        int size = comp.getESG_score_peers().size();
        if (comp.getESG_score_peers().get(size - 1) > comp.getESG_score_peers().get(size - 4)) {
            data.setBackground(getDrawable(R.drawable.background_grad_good));
//            toolBarLayout.setContentScrim(getDrawable(R.color.bg_good));
        } else {
            data.setBackground(getDrawable(R.drawable.background_grad_bad));
//            toolBarLayout.setContentScrim(getDrawable(R.color.bg_bad));
        }

    }

    private LineData switchData(int graphType) {
        ArrayList<Entry> testData = new ArrayList<>();
        ArrayList<Entry> predictData = new ArrayList<>();
        String dataType = "";

        switch (graphType) {
            case 0:
                dataType = getString(R.string.esg);
                for (int i = 0; i < comp.getESG_score_peers().size(); i++)
                    testData.add(new Entry(i, comp.getESG_score_peers().get(i)));
                for (int i = 192; i < comp.getESG_score_predictions().size() + 192; i++)
                    predictData.add(new Entry(i, comp.getESG_score_predictions().get(i - 192)));
                break;
            case 1:
                dataType = getString(R.string.environmental);
                for (int i = 0; i < comp.getE_score_peers().size(); i++)
                    testData.add(new Entry(i, comp.getE_score_peers().get(i)));
                for (int i = 192; i < comp.getE_score_predictions().size() + 192; i++)
                    predictData.add(new Entry(i, comp.getE_score_predictions().get(i - 192)));
                break;
            case 2:
                dataType = getString(R.string.social);
                for (int i = 0; i < comp.getS_score_peers().size(); i++)
                    testData.add(new Entry(i, comp.getS_score_peers().get(i)));
                for (int i = 192; i < comp.getS_score_predictions().size() + 192; i++)
                    predictData.add(new Entry(i, comp.getS_score_predictions().get(i - 192)));
                break;
            case 3:
                dataType = getString(R.string.governance);
                for (int i = 0; i < comp.getG_score_peers().size(); i++)
                    testData.add(new Entry(i, comp.getG_score_peers().get(i)));
                for (int i = 192; i < comp.getG_score_predictions().size() + 192; i++)
                    predictData.add(new Entry(i, comp.getG_score_predictions().get(i - 192)));
                break;
        }

        LineDataSet setComp1 = new LineDataSet(testData, dataType);
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet setComp2 = new LineDataSet(predictData, "Prediction");
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        LineData data = new LineData(dataSets);

//        LineDataSet dataSet = new LineDataSet(testData, dataType);
//        LineData lineData = new LineData(data);

        Drawable drawable = null;
        Drawable predictDraw = ContextCompat.getDrawable(this, R.drawable.predict_gradient);

        setComp1.setDrawValues(false);
        setComp1.setDrawHorizontalHighlightIndicator(false);
        setComp1.setDrawCircles(false);
        setComp1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        setComp1.setDrawFilled(true);

        setComp2.setDrawValues(false);
        setComp2.setDrawHorizontalHighlightIndicator(false);
        setComp2.setDrawCircles(false);
        setComp2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        setComp2.setDrawFilled(true);
        setComp2.setFillDrawable(predictDraw);
        setComp2.setColor(getColor(R.color.predict));
        setComp1.setHighLightColor(getColor(R.color.predict));

        switch (graphType) {
            case 0:
                setComp1.setColor(getColor(R.color.main_500));
                setComp1.setHighLightColor(getColor(R.color.main_500));
                drawable = ContextCompat.getDrawable(this, R.drawable.esg_gradient);
                break;
            case 1:
                setComp1.setColor(getColor(R.color.environment));
                setComp1.setHighLightColor(getColor(R.color.environment));
                drawable = ContextCompat.getDrawable(this, R.drawable.environment_gradient);
                break;
            case 2:
                setComp1.setColor(getColor(R.color.social));
                setComp1.setHighLightColor(getColor(R.color.social));
                drawable = ContextCompat.getDrawable(this, R.drawable.social_gradient);
                break;
            case 3:
                setComp1.setColor(getColor(R.color.governance));
                setComp1.setHighLightColor(getColor(R.color.governance));
                drawable = ContextCompat.getDrawable(this, R.drawable.governance_gradient);
                break;
        }

        setComp1.setFillDrawable(drawable);

        return data;
    }

    private ArrayList<Integer> getColors() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getColor(R.color.environment));
        colors.add(getColor(R.color.social));
        colors.add(getColor(R.color.governance));

        return colors;
    }

    private void updateView(Company company) {
        Log.d(TAG, "updateView: updated text");
        companyName.setText(company.getCompany_name());
        companyESG.setText(company.getTotal().toString());
        companyGrade.setText(company.getTotal_grade());
        companyTicker.setText(company.getStock_symbol());
        companySector.setText(String.format("%s\n%s", company.getSector(), company.getIndustry()));

        eScore.setText(company.getEnvironment_score().toString());
        eGrade.setText(company.getEnvironment_grade());
        sScore.setText(company.getSocial_score().toString());
        sGrade.setText(company.getSocial_grade());
        gScore.setText(company.getGovernance_score().toString());
        gGrade.setText(company.getGovernance_grade());
    }

    private void makePieChart(Company company) {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(company.getEnvironment_score(), getString(R.string.environmental)));
        pieEntries.add(new PieEntry(company.getSocial_score(), getString(R.string.social)));
        pieEntries.add(new PieEntry(company.getGovernance_score(), getString(R.string.governance)));

//        pieEntries.get(0).setIcon(getDrawable(R.drawable.ic_baseline_eco_24));
//        pieEntries.get(0)


        PieDataSet dataSet = new PieDataSet(pieEntries, getString(R.string.esg_values));
        PieData pieData = new PieData(dataSet);

        dataSet.setColors(getColors());
        dataSet.setValueTextColor(getColor(R.color.black_bg));
        dataSet.setValueLineColor(getColor(R.color.black_bg));

        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);
//        pieData.getDataSet().set

        esgChart.setCenterTextColor(getColor(R.color.black_bg));
        esgChart.setData(pieData);
        esgChart.setCenterText(getString(R.string.esg_values));
        esgChart.animateX(1000);
        esgChart.setCenterTextColor(Color.WHITE);
        esgChart.setHoleColor(Color.parseColor("#00000000"));

        esgChart.getLabelFor();
        esgChart.getLegend().setTextColor(Color.WHITE);
        esgChart.getLegend().setTextSize(10f);

        esgChart.getDescription().setEnabled(false);

        esgChart.invalidate();
    }

    private void addData(int graphType) {
        lineChart.clear();

//        IMarker iMarker = new MarkerView(this, R.id.label);
//        lineChart.setMarker(iMarker);

        lineChart.setData(switchData(graphType));

        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.animateX(1000, Easing.EaseOutSine);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
//        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
//        lineChart.getAxisRight().setTextColor(Color.WHITE);
        lineChart.setBorderColor(Color.WHITE);
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);

        lineChart.invalidate();
    }

}
