package com.rshtukaraxondevgroup.bluetoothtest;

import java.util.UUID;

public interface Constants {
    String BASE_URL = "https://jsonplaceholder.typicode.com/albums/1/";

    String DATABASE = "database";
    String PHOTOS_TABLE = "photoModel";
    String PHOTOS_URL = "url";

    String EXTRA_DEVICE_ADDRESS = "device_address";

    // Message types sent from the BluetoothChatService Handler
    int MESSAGE_STATE_CHANGE = 1;
    int MESSAGE_READ = 2;
    int MESSAGE_SENT = 3;
    int MESSAGE_DEVICE_NAME = 4;
    int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothService Handler
    String DEVICE_NAME = "layout_device_name";
    String TOAST = "toast";

    // Constants that indicate the current connection state
    int STATE_NONE = 0;       // we're doing nothing
    int STATE_LISTEN = 1;     // now listening for incoming connections
    int STATE_CONNECTING = 2; // now initiating an outgoing connection
    int STATE_CONNECTED = 3;  // now connected to a remote device

    // Intent request codes
    int REQUEST_CONNECT_DEVICE_SECURE = 1;
    int REQUEST_ENABLE_BT = 2;

    // Name for the SDP record when creating server socket
    String NAME_SERVICE = "BluetoothChatInsecure";
    // Unique UUID for this application
    UUID MY_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
}
