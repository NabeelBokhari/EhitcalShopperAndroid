package com.example.nabeel.myapplication;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchableActivity extends AppCompatActivity {

    ArrayAdapter<String> mResultsAdapter;
    private ListView mResultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }

        mResultsList = (ListView)findViewById(R.id.resultsList);
        addSearchResults();
    }

    public void doMySearch(String query){
        //perform the actual search operation
    }

    private void addSearchResults() {
        ArrayList<String> myStringArray1 = new ArrayList<String>();
        myStringArray1.add("Demo Product");

        mResultsAdapter = new ArrayAdapter<String>(this, R.layout.result_view, myStringArray1);
        mResultsList.setAdapter(mResultsAdapter);
    }

    public void launchDemoOverview (View view) {
        Intent intent = new Intent(this, ProductOverview.class);
        startActivity(intent);
    }
}
