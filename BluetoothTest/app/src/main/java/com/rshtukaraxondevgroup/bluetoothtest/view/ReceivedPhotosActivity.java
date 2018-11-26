package com.rshtukaraxondevgroup.bluetoothtest.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.rshtukaraxondevgroup.bluetoothtest.R;
import com.rshtukaraxondevgroup.bluetoothtest.model.PhotoModel;
import com.rshtukaraxondevgroup.bluetoothtest.presenter.ReceivedPhotoPresenter;
import com.rshtukaraxondevgroup.bluetoothtest.repository.ReceivedPhotosRepository;

import java.util.List;

public class ReceivedPhotosActivity extends AppCompatActivity implements ReceivedPhotosScreen {
    private ReceivedPhotoPresenter mPresenter;
    private ReceivedPhotosRepository mReceivedPhotosRepository;
    private RecyclerView mRecyclerView;
    private ReceivedPhotoListAdapter mReceivedPhotoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_photos);
        mRecyclerView = findViewById(R.id.recycler_view_received);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mReceivedPhotoListAdapter = new ReceivedPhotoListAdapter();
        mRecyclerView.setAdapter(mReceivedPhotoListAdapter);

        mReceivedPhotosRepository = new ReceivedPhotosRepository();
        mPresenter = new ReceivedPhotoPresenter(this, mReceivedPhotosRepository);
        mPresenter.getReceivedPhotoList();
    }

    @Override
    public void addBooksList(List<PhotoModel> list) {
        mReceivedPhotoListAdapter.setList(list);
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(this, "Download error" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
