package com.rshtukaraxondevgroup.bluetoothtest.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.rshtukaraxondevgroup.bluetoothtest.R;

public class MainActivity extends AppCompatActivity {
    private Button mButtonPhoto;
    private Button mReceivedPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonPhoto = findViewById(R.id.btn_photos);
        mReceivedPhotos = findViewById(R.id.btn_received_photos);

        mButtonPhoto.setOnClickListener(v -> startActivity(new Intent(this, PhotosActivity.class)));
        mReceivedPhotos.setOnClickListener(v -> startActivity(new Intent(this, ReceivedPhotosActivity.class)));
    }
}
