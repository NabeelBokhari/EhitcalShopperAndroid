package com.example.nabeel.myapplication;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.*;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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

    public void launchBarcodeScanner (View view) {
       /* setContentView(R.layout.activity_camera_view);
        mCamera = Camera.open();
        camera = new CameraView(getApplicationContext(), mCamera);;
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(preview)*/;


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Toast toast = Toast.makeText(this.getApplicationContext(), "Take a photo of the barcode", Toast.LENGTH_LONG);
            toast.show();
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, ProductOverview.class);
            startActivity(intent);
            //Bundle extras = data.getExtras();

        }
    }
}
