package com.example.nabeel.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductOverview extends AppCompatActivity {
    final String demoText = "This product is a demo product created by Company X. It was released in 2016.";

    TextView productDescription;
    ImageView productImage;

    TextView environmentSources;
    TextView humanRightsSources;
    TextView animalWelfareSources;

    String environmentSourceInfo;
    String hrSourceInfo;
    String animalWelfareSourceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_overview);

        productDescription = (TextView)findViewById(R.id.productOverviewDescription);

        environmentSources = (TextView)findViewById(R.id.environmentSources);
        humanRightsSources = (TextView)findViewById(R.id.humanRightsSources);
        animalWelfareSources = (TextView)findViewById(R.id.animalWelfareSources);

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

        environmentSourceInfo = "+ Demo Product recognized for good environmental practices\n\n" +
                "+ Demo product manufacturers advocate for environmental preservation";
        environmentSources.setText(environmentSourceInfo);
        environmentSources.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        hrSourceInfo = "+ Demo Product manufacturers outline new working condition policies\n\n" +
                "- Demo Product manufacturers raise concerns about labor rights";
        humanRightsSources.setText(hrSourceInfo);
        humanRightsSources.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        animalWelfareSourceInfo = "- Demo Product known to be tested on animals\n\n" +
                "- Evidence that Demo Product is tested on animals";
        animalWelfareSources.setText(animalWelfareSourceInfo);
        animalWelfareSources.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
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
        humanRightsSources.setVisibility(View.GONE);
        animalWelfareSources.setVisibility(View.GONE);
        environmentSources.setVisibility(View.VISIBLE);
    }

    public void expandHumanRights (View view) {
        humanRightsSources.setVisibility(View.VISIBLE);
        animalWelfareSources.setVisibility(View.GONE);
        environmentSources.setVisibility(View.GONE);
    }

    public void expandAnimalWelfare (View view) {
        humanRightsSources.setVisibility(View.GONE);
        animalWelfareSources.setVisibility(View.VISIBLE);
        environmentSources.setVisibility(View.GONE);
    }
}
