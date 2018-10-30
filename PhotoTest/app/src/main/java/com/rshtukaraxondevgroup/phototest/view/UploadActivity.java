package com.rshtukaraxondevgroup.phototest.view;

import android.accounts.AccountManager;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.rshtukaraxondevgroup.phototest.Constants;
import com.rshtukaraxondevgroup.phototest.R;
import com.rshtukaraxondevgroup.phototest.presenter.DropBoxPresenter;
import com.rshtukaraxondevgroup.phototest.presenter.FirebasePresenter;
import com.rshtukaraxondevgroup.phototest.presenter.GoogleDrivePresenter;
import com.rshtukaraxondevgroup.phototest.repository.DropBoxRepository;
import com.rshtukaraxondevgroup.phototest.repository.FirebaseRepository;
import com.rshtukaraxondevgroup.phototest.repository.GoogleDriveRepository;

import java.io.File;
import java.util.Objects;


public class UploadActivity extends AppCompatActivity implements UploadScreen {
    private static final String TAG = UploadActivity.class.getCanonicalName();

    private DropBoxPresenter dropBoxPresenter;
    private FirebasePresenter firebasePresenter;
    private GoogleDrivePresenter googleDrivePresenter;
    private Uri mImageUri;

    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Button mButtonDropBox;
    private Button mButtonFirebase;
    private Button mButtonGoogleDrive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mButtonDropBox = findViewById(R.id.button_dropbox);
        mButtonFirebase = findViewById(R.id.button_firebase);
        mButtonGoogleDrive = findViewById(R.id.button_google_drive);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);

        Bundle extras = getIntent().getExtras();
        mImageUri = Uri.parse(Objects.requireNonNull(extras).getString(Constants.EXTRA_URI));

        FirebaseRepository firebaseRepository = new FirebaseRepository();
        DropBoxRepository dropBoxRepository = new DropBoxRepository();
        GoogleDriveRepository googleDriveRepository = new GoogleDriveRepository(this);

        dropBoxPresenter = new DropBoxPresenter(this, dropBoxRepository);
        firebasePresenter = new FirebasePresenter(this, firebaseRepository);
        googleDrivePresenter = new GoogleDrivePresenter(this, googleDriveRepository);

        mButtonDropBox.setOnClickListener(v -> {
            mProgressBar.setVisibility(View.VISIBLE);
            dropBoxPresenter.uploadDownloadFileFromDropBox(mImageUri);
        });
        mButtonFirebase.setOnClickListener(v -> {
            Intent intent = new Intent(UploadActivity.this, AuthActivity.class);
            startActivityForResult(intent, Constants.REQUEST_SIGN_IN_FIREBASE);
        });
        mButtonGoogleDrive.setOnClickListener(v -> {
            mProgressBar.setVisibility(View.VISIBLE);
            startActivityForResult(googleDrivePresenter.signIn(), Constants.REQUEST_SIGN_IN_GOOGLE_DRIVE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_SIGN_IN_FIREBASE:
                Log.i(TAG, "Sign in Firebase request code");
                if (resultCode == RESULT_OK) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    firebasePresenter.uploadDownloadFileFromFirebase(mImageUri);
                }
                break;
            case Constants.REQUEST_SIGN_IN_GOOGLE_DRIVE:
                Log.i(TAG, "Sign in Google Drive request code");
                if (resultCode == RESULT_OK) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    googleDrivePresenter.uploadDownloadFileToGoogleDrive(mImageUri, accountName);
                }
                break;
        }
    }

    @Override
    public void showImage(File file) {
        if (file.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            mImageView.setImageBitmap(myBitmap);
        }
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
    }


}
