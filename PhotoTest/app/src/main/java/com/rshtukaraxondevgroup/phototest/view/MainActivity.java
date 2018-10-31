package com.rshtukaraxondevgroup.phototest.view;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.rshtukaraxondevgroup.phototest.Constants;
import com.rshtukaraxondevgroup.phototest.R;
import com.rshtukaraxondevgroup.phototest.Utils;
import com.rshtukaraxondevgroup.phototest.exception.CreateDirectoryException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    private Uri mImageUri;
    private ImageView mImageView;
    private Button mButtonEdit, mButtonUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.image_view);
        mButtonEdit = findViewById(R.id.button_edit);
        mButtonUpload = findViewById(R.id.button_upload);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.GET_ACCOUNTS}, 0);
        } else {
            takePhoto();
        }

        mButtonEdit.setOnClickListener(v -> clickOnEdit());
        mButtonUpload.setOnClickListener(v -> clickOnUpload());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                ContentResolver cr = getContentResolver();
                Bitmap bitmap;
                try {
                    bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
                    mImageView.setImageBitmap(bitmap);
                    Toast.makeText(this, mImageUri.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void takePhoto() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            mImageUri = Uri.fromFile(Utils.getOutputMediaFile(Constants.FILE_NAME));
        } catch (CreateDirectoryException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, e.getMessage());
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, Constants.TAKE_PICTURE);
    }

    private void clickOnEdit() {
        if (mImageUri != null) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra(Constants.EXTRA_URI, mImageUri.toString());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Take a photo", Toast.LENGTH_LONG).show();
        }
    }

    private void clickOnUpload() {
        if (mImageUri != null) {
            Intent intent = new Intent(this, UploadActivity.class);
            intent.putExtra(Constants.EXTRA_URI, mImageUri.toString());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Take a photo", Toast.LENGTH_LONG).show();
        }
    }

}
