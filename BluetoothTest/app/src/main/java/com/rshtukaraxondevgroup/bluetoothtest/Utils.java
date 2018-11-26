package com.rshtukaraxondevgroup.bluetoothtest;

import android.util.Log;

import com.rshtukaraxondevgroup.bluetoothtest.model.PhotoModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static final String TAG = Utils.class.getCanonicalName();

    public static List<PhotoModel> convertByteToList(byte[] photoModelsByte) {
        List<PhotoModel> modelList = new ArrayList<>();
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(photoModelsByte);
            ObjectInputStream is = new ObjectInputStream(in);
            modelList = (List<PhotoModel>) is.readObject();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return modelList;
    }

    public static byte[] convertListToByte(List<PhotoModel> photoModelList) {
        byte[] bytes = new byte[1024];
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(photoModelList);
            bytes = bos.toByteArray();

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return bytes;
    }
}
