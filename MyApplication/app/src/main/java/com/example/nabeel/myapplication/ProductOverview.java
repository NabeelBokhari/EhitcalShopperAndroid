package com.example.nabeel.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductOverview extends AppCompatActivity {
    final String demoText = "This product is a demo product created by Company X. It was released in 2016.";
    final double GOOD_THRESHOLD = .75;

    TextView productDescription;
    ImageView productImage;

    LinearLayout environmentSourceLayout;
    TextView environmentRating;

    LinearLayout humanRightsSourceLayout;
    TextView hrRating;

    LinearLayout animalWelfareSourceLayout;
    TextView awRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_overview);

        productDescription = (TextView)findViewById(R.id.productOverviewDescription);

        environmentSourceLayout = (LinearLayout)findViewById(R.id.environmentSourcesLayout);
        environmentRating = (TextView)findViewById(R.id.environmentRating);

        humanRightsSourceLayout = (LinearLayout)findViewById(R.id.humanRightsSourcesLayout);
        hrRating = (TextView)findViewById(R.id.humanRightsRating);

        animalWelfareSourceLayout = (LinearLayout)findViewById(R.id.animalWelfareSourcesLayout);
        awRating = (TextView)findViewById(R.id.animalWelfareRating);

        init();
    }

    /**Initializes the product description and sources on the overview. If the intent that launched
     the activity (such as from the search activity included a title, a description, and/or sources,
     then those items are placed in the overview. If any of those items are missing,
     then the default dummy values are used instead.
     */
    private void init() {
        Intent intent = getIntent();
        if(intent != null && intent.getStringExtra("description") != null) {
            String desc = intent.getStringExtra("description");
            productDescription.setText(desc);
        } else {
            productDescription.setText(demoText);
        }

        String prodName = "";

        if(intent != null && intent.getStringExtra("productName") != null) {
            prodName = intent.getStringExtra("productName");
            setTitle(prodName);
        } else {
            setTitle("Demo Product");
        }

        if(intent != null && intent.getSerializableExtra("sources") != null) {
            initFromSources((HashMap)intent.getSerializableExtra("sources"));
            return;
        }

        ProductSourceView environmentSourceOne = createProductSource(
                "Demo Product recognized for good environmental practices", "cnn.com", true);
        ProductSourceView environmentSourceTwo = createProductSource(
                "Demo product manufacturers advocate for environmental preservation",
                "time.com", true);

//        environmentSourceOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "http://www.starbucks.com/responsibility/environment";
//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            }
//        });

        environmentSourceLayout.addView(environmentSourceOne);
        environmentSourceLayout.addView(environmentSourceTwo);

        environmentSourceLayout.addView(createAddSourceButton("environment"));

        ProductSourceView hrSourceOne = createProductSource(
                "Demo Product manufacturers outline new working condition policies", "salon.com", true);
        ProductSourceView hrSourceTwo = createProductSource(
                "Demo Product manufacturers raise concerns about labor rights",
                "bbc.co.uk", false);

        humanRightsSourceLayout.addView(hrSourceOne);
        humanRightsSourceLayout.addView(hrSourceTwo);
        humanRightsSourceLayout.addView(createAddSourceButton("humanRights"));

        ProductSourceView awSourceOne = createProductSource(
                "Demo Product known to be tested on animals", "nyt.com", false);
        ProductSourceView awSourceTwo = createProductSource(
                "Evidence that Demo Product is tested on animals",
                "dawn.com", false);

        animalWelfareSourceLayout.addView(awSourceOne);
        animalWelfareSourceLayout.addView(awSourceTwo);
        animalWelfareSourceLayout.addView(createAddSourceButton("animalWelfare"));

        calculateRatings();
    }

    /** pulls sources information for the three demo categories (environment, human rights, and
     * animal welfare from the intent that launched the overview activity. Then creates
     * ProductSourceViews out of those product sources and adds them to the appropriate layouts
     * in the overview*/
    private void initFromSources(HashMap map) {
        HashMap<String, ArrayList<ProductSourceView.ProductSource>> sources =
                (HashMap<String, ArrayList<ProductSourceView.ProductSource>>)map;

        ProductSourceView psv;
        ArrayList<ProductSourceView.ProductSource> sourceList = sources.get("environment");
        if(sourceList != null) {
            for(ProductSourceView.ProductSource source : sourceList) {
                psv = new ProductSourceView(this);
                psv.setAttributes(source);
                environmentSourceLayout.addView(psv);
            }
        }

        environmentSourceLayout.addView(createAddSourceButton("environment"));

        sourceList = sources.get("humanRights");
        if(sourceList != null) {
            for(ProductSourceView.ProductSource source : sourceList) {
                psv = new ProductSourceView(this);
                psv.setAttributes(source);
                humanRightsSourceLayout.addView(psv);
            }
        }

        humanRightsSourceLayout.addView(createAddSourceButton("humanRights"));

        sourceList = sources.get("animalWelfare");
        if(sourceList != null) {
            for(ProductSourceView.ProductSource source : sourceList) {
                psv = new ProductSourceView(this);
                psv.setAttributes(source);
                animalWelfareSourceLayout.addView(psv);
            }
        }

        animalWelfareSourceLayout.addView(createAddSourceButton("animalWelfare"));

        calculateRatings();
    }

    /** creates a ProductSourceView out of the information entered by the user in the Add Source
     * dialog
     *
     * @param title The title of the source, which will be displayed as the link text in the
     *              source list
     * @param path The url of the source
     * @param good a boolean representing if the source is positive (true) or negative (false)
     * @return a ProductSourceView created from the above parameters ready to be inserted into the
     * overview screen
     */
    private ProductSourceView createProductSource(String title, String path, boolean good) {
        if(title == null) {
            title = "Default Title";
        }

        if(path == null) {
            path = "example.com";
        }
        ProductSourceView psv = new ProductSourceView(this);
        psv.setSourceTitle(title);
        psv.setSourcePath(path);
        if(good) {
            psv.setGoodSource();
        } else {
            psv.setBadSource();
        }
        return psv;
    }

    /** Creates an Add Source button and adds it to the appropriate source layout based on the
     * category passed in.
     *
     * @param category String representing the name of the category that the Add Source button will
     *                 be added to
     */
    private Button createAddSourceButton(final String category) {
        Button add = new Button(this);
        add.setText("+ Add Source");
        final Context context = this;
        final LayoutInflater inflater = this.getLayoutInflater();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(inflater.inflate(R.layout.add_source_layout, null))
                        .setTitle("Add Source")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Dialog d = (Dialog) dialog;
                                EditText title = (EditText) d.findViewById(R.id.sourceTitleEntry);
                                EditText url = (EditText) d.findViewById(R.id.sourceURLEntry);
                                RadioButton positive = (RadioButton) d.findViewById(R.id.sourcePosRadio);

                                ProductSourceView psv = createProductSource(title.getText().toString(),
                                        url.getText().toString(), positive.isChecked());

                                switch (category) {
                                    case "environment":
                                        environmentSourceLayout.addView(psv,
                                                environmentSourceLayout.getChildCount() - 1);
                                        break;
                                    case "humanRights":
                                        humanRightsSourceLayout.addView(psv,
                                                humanRightsSourceLayout.getChildCount() - 1);
                                        break;
                                    case "animalWelfare":
                                        animalWelfareSourceLayout.addView(psv,
                                                animalWelfareSourceLayout.getChildCount() - 1);
                                        break;
                                    default:
                                        break;
                                }

                                Toast.makeText(context,
                                        "Your source submission is under review",
                                        Toast.LENGTH_SHORT).show();
                                calculateRatings();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
        return add;
    }

    /**
     * Calculates the ratings for each category and sets the appropriate color and text on them
     */
    private void calculateRatings() {
        /*using getChildCount() - 1 because all sourceLayouts include at least one child:
        the Add Source button*/
        int environmentSourceCount = environmentSourceLayout.getChildCount() - 1;
        int i;
        int goodCount = 0;
        double goodRatio;
        ProductSourceView psv;
        for(i = 0; i < environmentSourceCount; i++) {
            psv = (ProductSourceView)environmentSourceLayout.getChildAt(i);
            if(psv.isPositive()) {
                goodCount++;
            }

        }
        goodRatio = environmentSourceCount > 0 ? (double)goodCount/environmentSourceCount : -1;
        setRating(environmentRating, goodRatio);

        goodCount = 0;
        int hrSourceCount = humanRightsSourceLayout.getChildCount() - 1;
        for(i = 0; i < hrSourceCount; i++) {
            psv = (ProductSourceView)humanRightsSourceLayout.getChildAt(i);
            if(psv.isPositive()) {
                goodCount++;
            }
        }
        goodRatio = hrSourceCount > 0 ? (double)goodCount/hrSourceCount : -1;
        setRating(hrRating, goodRatio);

        goodCount = 0;
        int awSourceCount = animalWelfareSourceLayout.getChildCount() - 1;
        for(i = 0; i < awSourceCount; i++) {
            psv = (ProductSourceView)animalWelfareSourceLayout.getChildAt(i);
            if(psv.isPositive()) {
                goodCount++;
            }
        }
        goodRatio = awSourceCount > 0 ? (double)goodCount/awSourceCount : -1;
        setRating(awRating, goodRatio);
    }

    /**Sets the appropriate color and text on each category label based on its rating
     *
     * @param ratingView the textView representing the rating for a given category
     * @param ratio the rating calculated. If the ratio passed is -1, then it indicates that the
     *        rating should be "N/A"
     */
    private void setRating (TextView ratingView, double ratio) {
        if(ratio == -1) {
            ratingView.setBackgroundResource(R.drawable.color_rating_none);
            ratingView.setText("N/A");
            return;
        }

        if(ratio >= GOOD_THRESHOLD) {
            ratingView.setBackgroundResource(R.drawable.color_rating_good);
            ratingView.setText("GOOD");
        } else if (ratio >= .5) {
            ratingView.setBackgroundResource(R.drawable.color_rating_neutral);
            ratingView.setText("OK");
        } else {
            ratingView.setBackgroundResource(R.drawable.color_rating_bad);
            ratingView.setText("BAD");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.product_overview_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_bar_share:
                shareButtonClick();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void shareButtonClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Share Product")
                .setView(this.getLayoutInflater().inflate(R.layout.product_share_dialog_layout, null))
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Shared to social media", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }

    public void expandEnvironment (View view) {
        environmentSourceLayout.setVisibility(environmentSourceLayout.getVisibility() == View.VISIBLE ?
                View.GONE : View.VISIBLE);
        ImageView arrow = (ImageView)findViewById(R.id.envArrow);
        arrow.setBackgroundResource(environmentSourceLayout.getVisibility() == View.VISIBLE ?
        R.drawable.ic_keyboard_arrow_down_black_48dp : R.drawable.ic_keyboard_arrow_up_black_48dp);
    }

    public void expandHumanRights (View view) {
        humanRightsSourceLayout.setVisibility(humanRightsSourceLayout.getVisibility() == View.VISIBLE ?
                View.GONE : View.VISIBLE);
        ImageView arrow = (ImageView)findViewById(R.id.hrArrow);
        arrow.setBackgroundResource(humanRightsSourceLayout.getVisibility() == View.VISIBLE ?
                R.drawable.ic_keyboard_arrow_down_black_48dp : R.drawable.ic_keyboard_arrow_up_black_48dp);
    }

    public void expandAnimalWelfare (View view) {
        animalWelfareSourceLayout.setVisibility(animalWelfareSourceLayout.getVisibility() == View.VISIBLE ?
                View.GONE : View.VISIBLE);
        ImageView arrow = (ImageView)findViewById(R.id.awArrow);
        arrow.setBackgroundResource(animalWelfareSourceLayout.getVisibility() == View.VISIBLE ?
                R.drawable.ic_keyboard_arrow_down_black_48dp : R.drawable.ic_keyboard_arrow_up_black_48dp);
    }
}
