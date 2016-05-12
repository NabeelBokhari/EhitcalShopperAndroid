package com.example.nabeel.myapplication;

import android.content.Intent;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Camera mCamera;
    private CameraView camera ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchDemoOverview (View view) {
        Intent intent = new Intent(this, ProductOverview.class);
        startActivity(intent);
    }

    public void launchSearch (View view){
        Intent searchIntent = new Intent(this, SearchableActivity.class);
        startActivity(searchIntent);
    }

    /*Launches the camera view to mock scanning a barcode*/
    public void launchBarcodeScanner (View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Toast toast = Toast.makeText(this.getApplicationContext(), "Take a photo of the barcode", Toast.LENGTH_LONG);
            toast.show();
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }


    /*After successfully capturing a photo, launch the demo Product Overview to simulate
     the result of scanning a barcode*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, ProductOverview.class);
            startActivity(intent);
            //Bundle extras = data.getExtras();

        }
    }
}
