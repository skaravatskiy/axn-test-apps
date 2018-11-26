package com.rshtukaraxondevgroup.bluetoothtest;

import java.util.UUID;

public class Constants {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/albums/1/";

    public static final String DATABASE = "database";
    public static final String PHOTOS_TABLE = "photoModel";
    public static final String PHOTOS_URL = "url";

    public static final String EXTRA_DEVICE_ADDRESS = "device_address";

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_SENT = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothService Handler
    public static final String DEVICE_NAME = "layout_device_name";
    public static final String TOAST = "toast";

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device

    // Intent request codes
    public static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    public static final int REQUEST_ENABLE_BT = 2;

    // Name for the SDP record when creating server socket
    public static final String NAME_SERVICE = "BluetoothChatInsecure";
    // Unique UUID for this application
    public static final UUID MY_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    public static final int TYPE_LINEAR_LAYOUT_MANAGER = 0;
    public static final int TYPE_GRID_LAYOUT_MANAGER = 1;
}


