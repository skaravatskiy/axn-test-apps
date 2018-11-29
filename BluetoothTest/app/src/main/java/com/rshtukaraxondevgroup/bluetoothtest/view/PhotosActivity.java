package com.rshtukaraxondevgroup.bluetoothtest.view;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rshtukaraxondevgroup.bluetoothtest.BluetoothService;
import com.rshtukaraxondevgroup.bluetoothtest.Constants;
import com.rshtukaraxondevgroup.bluetoothtest.R;
import com.rshtukaraxondevgroup.bluetoothtest.model.PhotoModel;
import com.rshtukaraxondevgroup.bluetoothtest.presenter.PhotoPresenter;
import com.rshtukaraxondevgroup.bluetoothtest.repository.PhotosRepository;

import java.util.List;

import static com.rshtukaraxondevgroup.bluetoothtest.Constants.EXTRA_DEVICE_ADDRESS;
import static com.rshtukaraxondevgroup.bluetoothtest.Constants.REQUEST_CONNECT_DEVICE_SECURE;
import static com.rshtukaraxondevgroup.bluetoothtest.Constants.REQUEST_ENABLE_BT;
import static com.rshtukaraxondevgroup.bluetoothtest.Constants.STATE_CONNECTED;
import static com.rshtukaraxondevgroup.bluetoothtest.Constants.STATE_CONNECTING;
import static com.rshtukaraxondevgroup.bluetoothtest.Constants.STATE_NONE;
import static com.rshtukaraxondevgroup.bluetoothtest.Utils.convertListToByte;

public class PhotosActivity extends AppCompatActivity implements PhotosScreen {
    private static final String TAG = PhotosActivity.class.getCanonicalName();

    private RecyclerView mRecyclerView;
    private PhotoListAdapter mPhotoListAdapter;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private PhotoPresenter mPresenter;
    private Menu mMenu;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothService mChatService = null;
    private String mConnectedDeviceName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        mRecyclerView = findViewById(R.id.recycler_view_photos);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mPhotoListAdapter = new PhotoListAdapter();
        mRecyclerView.setAdapter(mPhotoListAdapter);

        mPresenter = new PhotoPresenter(this, new PhotosRepository());
        mPresenter.getPhotosList();
        initBluetooth();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mChatService != null) {
            if (mChatService.getState() == STATE_NONE) {
                mChatService.start();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_send_via_Bluetooth:
                sendViaBluetooth();
                break;
            case R.id.menu_set_layout_manager:
                setLayoutManager();
                break;
            case R.id.menu_discoverable:
                ensureDiscoverable();
                break;
        }
        return false;
    }

    @Override
    public void addPhotosList(List<PhotoModel> list) {
        mPhotoListAdapter.setList(list);
    }

    @Override
    public void showError(String throwable) {
        Toast.makeText(this, "Download error" + throwable, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data);
                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    sendViaBluetooth();
                } else {
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, "Bluetooth was not enabled.", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void initBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    private void sendViaBluetooth() {
        Log.d(TAG, "BT sendViaBluetooth");
        if (mPhotoListAdapter.getCheckBoxList().size() > 0) {
            if (mChatService != null) {
                if (mChatService.getState() == STATE_CONNECTED) {
                    sendMessage();
                } else {
                    Intent serverIntent = new Intent(this, DeviceListActivity.class);
                    startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                }
            } else {
                Intent serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            }
        } else {
            Toast.makeText(this, "Select item", Toast.LENGTH_SHORT).show();
        }
    }

    private void connectDevice(Intent data) {
        String address = data.getExtras().getString(EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (mChatService == null) {
            mChatService = new BluetoothService(mHandler);
        }
        mChatService.connect(device);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            break;
                        case STATE_CONNECTING:
                            setStatus(getString(R.string.title_connecting));
                            break;
                        case STATE_NONE:
                            setStatus(getString(R.string.title_not_connected));
                            break;
                    }
                    break;
                case Constants.MESSAGE_SENT:
                    Toast.makeText(PhotosActivity.this, "File sent", Toast.LENGTH_SHORT).show();
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    Toast.makeText(PhotosActivity.this, "Connected to " + mConnectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    mPresenter.saveReceivedPhotos(readBuf);
                    Toast.makeText(PhotosActivity.this, mConnectedDeviceName + ":  send file",
                            Toast.LENGTH_SHORT).show();
                    break;
                case Constants.MESSAGE_TOAST:
                    Toast.makeText(PhotosActivity.this, msg.getData().getString(Constants.TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void sendMessage() {
        if (mChatService.getState() == STATE_CONNECTED) {
            List<PhotoModel> list = mPhotoListAdapter.getCheckBoxList();
            if (list.size() > 0) {
                mChatService.write(convertListToByte(list));
            }
        } else {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
        }
    }

    private void setStatus(CharSequence subTitle) {
        ActionBar actionBar = PhotosActivity.this.getSupportActionBar();
        if (null != actionBar) {
            actionBar.setSubtitle(subTitle);
        }
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
        if (mChatService != null) {
            mChatService.stop();
            mChatService.start();
        } else {
            mChatService = new BluetoothService(mHandler);
        }
    }

    private void setLayoutManager() {
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mPhotoListAdapter.setLinearLayoutManager(true);
            mMenu.getItem(1).setIcon(R.drawable.ic_view_list);
        } else if (layoutManager instanceof LinearLayoutManager) {
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mPhotoListAdapter.setLinearLayoutManager(false);
            mMenu.getItem(1).setIcon(R.drawable.ic_grid);
        }
    }
}
