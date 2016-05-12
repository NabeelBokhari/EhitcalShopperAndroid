package com.example.nabeel.myapplication;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SearchableActivity extends AppCompatActivity {

    ArrayAdapter<String> mResultsAdapter;
    private ListView mResultsList;
    private EditText filterText;

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
        filterText = (EditText)findViewById(R.id.searchFilter);
        addSearchResults();
        setFilterListener();
    }

    public void doMySearch(String query){
        //perform the actual search operation
    }

    private void addSearchResults() {
        ArrayList<String> myStringArray1 = new ArrayList<String>();
        myStringArray1.add("Apple iPhone");
        myStringArray1.add("Coca-Cola");
        myStringArray1.add("Demo Product");
        myStringArray1.add("Lipton Iced Tea");
        myStringArray1.add("Macbook Air");
        myStringArray1.add("MD Dairy Ice Cream");
        myStringArray1.add("Mountain Dew");
        myStringArray1.add("Skinny Cow Bars");
        myStringArray1.add("Starbucks Coffee");
        myStringArray1.add("Under Armour");

        mResultsAdapter = new ArrayAdapter<String>(this, R.layout.result_view, myStringArray1);
        mResultsList.setAdapter(mResultsAdapter);
    }

    private void setFilterListener() {
        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*This line will filter the contents of the adapter based on the character sequence
                * currently present in the editText at the top of the activity*/
                mResultsAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void launchDemoOverview (View view) {
        Intent intent = new Intent(this, ProductOverview.class);
        startActivity(intent);
    }

    public void launchOverview (View view) {
        TextView textView = (TextView)(view);

        String text = textView.getText().toString();
        Intent intent = new Intent(this, ProductOverview.class);
        intent.putExtra("productName", text);

        HashMap<String, ArrayList<ProductSourceView.ProductSource>> sources = getSources(text);
        if(sources != null) {
            intent.putExtra("sources", sources);
        }

        startActivity(intent);
    }

    /**
     * Retrieves a HashMap representing the sources associated with a product.
     *
     * @param productName the name of the product to retrieve sources for
     * @return a HashMap representing the sources or null if there are no sources associated with
     * the given product. The keys of the map are the category names "environment", "animalWelfare",
     * "humanRights", etc.
     * */
    public HashMap getSources(String productName) {
        switch(productName) {
            case "Starbucks Coffee":
                HashMap<String, ArrayList<ProductSourceView.ProductSource>> sources =
                        new HashMap<String, ArrayList<ProductSourceView.ProductSource>>();
                String title = "Starbucks advocates for environmental preservation";
                ProductSourceView.ProductSource environment =
                        new ProductSourceView.ProductSource(title, "http://www.starbucks.com/responsibility/environment",
                                "starbucks.com", true);
                ArrayList<ProductSourceView.ProductSource> envSources = new ArrayList<ProductSourceView.ProductSource>();
                envSources.add(environment);

                sources.put("environment", envSources);
                return sources;
            default:
                return null;
        }
    }
}
