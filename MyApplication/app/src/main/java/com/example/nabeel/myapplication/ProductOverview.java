package com.example.nabeel.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
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

public class ProductOverview extends AppCompatActivity {
    final String demoText = "This product is a demo product created by Company X. It was released in 2016.";

    TextView productDescription;
    ImageView productImage;

    //ScrollView environmentScroll;
    LinearLayout environmentSourceLayout;

    //ScrollView humanRightsScroll;
    LinearLayout humanRightsSourceLayout;

    //ScrollView animalWelfareScroll;
    LinearLayout animalWelfareSourceLayout;

    //TextView environmentSources;
    //TextView humanRightsSources;
    //TextView animalWelfareSources;

    String environmentSourceInfo;
    String hrSourceInfo;
    String animalWelfareSourceInfo;

    Button addSourceEnv;
    Button addSourceHr;
    Button addSourceAw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_overview);

        productDescription = (TextView)findViewById(R.id.productOverviewDescription);

        //environmentScroll = (ScrollView)findViewById(R.id.environmentSourcesScrollView);
        environmentSourceLayout = (LinearLayout)findViewById(R.id.environmentSourcesLayout);
        //environmentSources = (TextView)findViewById(R.id.environmentSources);

        //humanRightsScroll = (ScrollView)findViewById(R.id.humanRightsSourcesScrollView);
        humanRightsSourceLayout = (LinearLayout)findViewById(R.id.humanRightsSourcesLayout);
        //humanRightsSources = (TextView)findViewById(R.id.humanRightsSources);

        //animalWelfareScroll = (ScrollView)findViewById(R.id.animalWelfareSourcesScrollView);
        animalWelfareSourceLayout = (LinearLayout)findViewById(R.id.animalWelfareSourcesLayout);
        //animalWelfareSources = (TextView)findViewById(R.id.animalWelfareSources);

        init();
    }

    private void init() {
        Intent intent = getIntent();
        if(intent != null && intent.getStringExtra("description") != null) {
            String desc = intent.getStringExtra("description");
            productDescription.setText(desc);
        } else {
            productDescription.setText(demoText);
        }

        setTitle("Demo Product");

        /*environmentSourceInfo = "+ Demo Product recognized for good environmental practices\n\n" +
                "+ Demo product manufacturers advocate for environmental preservation";*/

        ProductSourceView environmentSourceOne = createProductSource(
                "Demo Product recognized for good environmental practices", "cnn.com", true);
        ProductSourceView environmentSourceTwo = createProductSource(
                "Demo product manufacturers advocate for environmental preservation",
                "time.com", true);

        environmentSourceLayout.addView(environmentSourceOne);
        environmentSourceLayout.addView(environmentSourceTwo);

        environmentSourceLayout.addView(createAddSourceButton("environment"));
        //environmentSources.setText(environmentSourceInfo);
        //environmentSources.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        /*hrSourceInfo = "+ Demo Product manufacturers outline new working condition policies\n\n" +
                "- Demo Product manufacturers raise concerns about labor rights";*/

        ProductSourceView hrSourceOne = createProductSource(
                "Demo Product manufacturers outline new working condition policies", "salon.com", true);
        ProductSourceView hrSourceTwo = createProductSource(
                "Demo Product manufacturers raise concerns about labor rights",
                "bbc.co.uk", false);

        humanRightsSourceLayout.addView(hrSourceOne);
        humanRightsSourceLayout.addView(hrSourceTwo);
        humanRightsSourceLayout.addView(createAddSourceButton("humanRights"));

        //humanRightsSources.setText(hrSourceInfo);
        //humanRightsSources.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        //animalWelfareSourceInfo = "- Demo Product known to be tested on animals\n\n" + "- Evidence that Demo Product is tested on animals";

        ProductSourceView awSourceOne = createProductSource(
                "Demo Product known to be tested on animals", "nyt.com", false);
        ProductSourceView awSourceTwo = createProductSource(
                "Evidence that Demo Product is tested on animals",
                "dawn.com", false);

        animalWelfareSourceLayout.addView(awSourceOne);
        animalWelfareSourceLayout.addView(awSourceTwo);
        animalWelfareSourceLayout.addView(createAddSourceButton("animalWelfare"));
        //animalWelfareSources.setText(animalWelfareSourceInfo);
        //animalWelfareSources.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


    }

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
        //humanRightsSources.setVisibility(View.GONE);
        //animalWelfareSources.setVisibility(View.GONE);
        //environmentSources.setVisibility(View.VISIBLE);

        environmentSourceLayout.setVisibility(environmentSourceLayout.getVisibility() == View.VISIBLE ?
                View.GONE : View.VISIBLE);

//        humanRightsSourceLayout.setVisibility(View.GONE);
//        animalWelfareSourceLayout.setVisibility(View.GONE);
//        environmentSourceLayout.setVisibility(View.VISIBLE);
    }

    public void expandHumanRights (View view) {
        //humanRightsSources.setVisibility(View.VISIBLE);
        //animalWelfareSources.setVisibility(View.GONE);
        //environmentSources.setVisibility(View.GONE);

//        humanRightsSourceLayout.setVisibility(View.VISIBLE);
//        animalWelfareSourceLayout.setVisibility(View.GONE);
//        environmentSourceLayout.setVisibility(View.GONE);

        humanRightsSourceLayout.setVisibility(humanRightsSourceLayout.getVisibility() == View.VISIBLE ?
                View.GONE : View.VISIBLE);
    }

    public void expandAnimalWelfare (View view) {
        //humanRightsSources.setVisibility(View.GONE);
        //animalWelfareSources.setVisibility(View.VISIBLE);
        //environmentSources.setVisibility(View.GONE);

//        humanRightsSourceLayout.setVisibility(View.GONE);
//        animalWelfareSourceLayout.setVisibility(View.VISIBLE);
//        environmentSourceLayout.setVisibility(View.GONE);

        animalWelfareSourceLayout.setVisibility(animalWelfareSourceLayout.getVisibility() == View.VISIBLE ?
                View.GONE : View.VISIBLE);
    }
}
