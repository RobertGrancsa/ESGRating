package com.redrevoraise.esgrating;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SearchView searchBar;
    private ProgressBar progressIndicator;
    private List<CompanyFront> comp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerViewCompanies);
        progressIndicator = findViewById(R.id.progressBar);
        searchBar = findViewById(R.id.searchBarHome);

        progressIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setNestedScrollingEnabled(false);

        initRecyclerViewComp();

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.toLowerCase(Locale.ROOT);
                List<CompanyFront> companiestemp = new ArrayList<CompanyFront>();

                for (int i = 0; i < comp.size(); i++) {
                    String ticker = comp.get(i).getStock_symbol().toLowerCase(Locale.ROOT);
                    String name =  comp.get(i).getCompany_name().toLowerCase(Locale.ROOT);
                    if (ticker.contains(query) || name.contains(query)) {
                        companiestemp.add(comp.get(i));
                    }
                }

                if (companiestemp.isEmpty()) {
                    Snackbar snackbar = Snackbar
                            .make(mRecyclerView, "No firm named " + query + " found", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    new CompanyRecyclerView().setConfig(mRecyclerView, MainActivity.this, companiestemp);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty())
                    new CompanyRecyclerView().setConfig(mRecyclerView, MainActivity.this, comp);
                return false;
            }
        });

        initTopData();
    }

    private void initTopData() {
        new FirebaseDatabaseHelper().readTop(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoadedComp(List<CompanyFront> companies, List<String> keys) {

            }

            @Override
            public void DataIsLoadedCompany(Company company, String key) {

            }

            @Override
            public void DataIsLoadedTop(List<CompanyFront> companies, List<String> keys) {
                initTopComp(companies, keys);
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

    private void initRecyclerViewComp(){
        progressIndicator.setVisibility(View.VISIBLE);

        new FirebaseDatabaseHelper().readCompanies(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoadedComp(List<CompanyFront> companies, List<String> keys) {
                comp = companies;
                sortListBy(comp);
                new CompanyRecyclerView().setConfig(mRecyclerView, MainActivity.this, comp);
                progressIndicator.setVisibility(View.GONE);
            }

            @Override
            public void DataIsLoadedCompany(Company company, String key) {

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

    private void sortListBy(List<CompanyFront> companies){
        Spinner spinnerSort = findViewById(R.id.sortBy);
        Spinner spinnerWay = findViewById(R.id.upDown);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortListBy(companies);
                new CompanyRecyclerView().setConfig(mRecyclerView, MainActivity.this, companies);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        spinnerWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortListBy(companies);
                new CompanyRecyclerView().setConfig(mRecyclerView, MainActivity.this, companies);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        Collections.sort(companies, new Comparator<CompanyFront>(){
            public int compare(CompanyFront obj1, CompanyFront obj2) {
                // ## Ascending order
                Object selectedItem = spinnerSort.getSelectedItem();
                Object selectedItem1 = spinnerWay.getSelectedItem();
                Log.d(ContentValues.TAG, "compare: " + selectedItem1);
                Log.d(ContentValues.TAG, "compare: " + spinnerWay.getItemIdAtPosition(1));
                if (spinnerWay.getItemAtPosition(0).equals(selectedItem1)) {
                    if (spinnerSort.getItemAtPosition(0).equals(selectedItem)) {
                        return obj1.getCompany_name().compareToIgnoreCase(obj2.getCompany_name());
                    } else if (spinnerSort.getItemAtPosition(2).equals(selectedItem))  {
                        return Float.valueOf(obj1.getTotal()).compareTo(Float.valueOf(obj2.getTotal()));
                    } else {
                        return obj1.getStock_symbol().compareToIgnoreCase(obj2.getStock_symbol());
                    }
                } else {
                    if (spinnerSort.getItemAtPosition(0).equals(selectedItem)) {
                        return obj2.getCompany_name().compareToIgnoreCase(obj1.getCompany_name());
                    } else if (spinnerSort.getItemAtPosition(2).equals(selectedItem)) {
                        return Float.valueOf(obj2.getTotal()).compareTo(Float.valueOf(obj1.getTotal()));
                    } else {
                        return obj2.getStock_symbol().compareToIgnoreCase(obj1.getStock_symbol());
                    }
                }

            }
        });
    }

    private void initTopComp(List<CompanyFront> companies, List<String> keys) {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

        TextView topESGComp = findViewById(R.id.topEsgComp);
        TextView topEComp = findViewById(R.id.topEComp);
        TextView topSComp = findViewById(R.id.topSComp);
        TextView topGComp = findViewById(R.id.topGComp);

        LinearLayout topESG = findViewById(R.id.topESG);
        LinearLayout topE = findViewById(R.id.topE);
        LinearLayout topS = findViewById(R.id.topS);
        LinearLayout topG = findViewById(R.id.topG);

        for (String key : keys) {
            if (key.equals("TOP_ESG")) {
                topESGComp.setText(companies.get(keys.indexOf(key)).getCompany_name());
                topESG.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callNewIntent(companies.get(keys.indexOf(key)).getStock_symbol());
                    }
                });
            } else if (key.equals("TOP_E")) {
                topEComp.setText(companies.get(keys.indexOf(key)).getCompany_name());
                topE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callNewIntent(companies.get(keys.indexOf(key)).getStock_symbol());
                    }
                });
            } else if (key.equals("TOP_S")) {
                topSComp.setText(companies.get(keys.indexOf(key)).getCompany_name());
                topS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callNewIntent(companies.get(keys.indexOf(key)).getStock_symbol());
                    }
                });
            } else if (key.equals("TOP_G")) {
                topGComp.setText(companies.get(keys.indexOf(key)).getCompany_name());
                topG.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callNewIntent(companies.get(keys.indexOf(key)).getStock_symbol());
                    }
                });
            }
        }
        topESG.setVisibility(View.VISIBLE);
        topE.setVisibility(View.VISIBLE);
        topS.setVisibility(View.VISIBLE);
        topG.setVisibility(View.VISIBLE);
    }

    private void callNewIntent(String key) {
        Intent intent = new Intent(MainActivity.this, CompanyActivity.class);
        intent.putExtra("ticker", key);
        startActivity(intent);
    }
}