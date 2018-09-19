package com.system.translationpen.hotspot.manager;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by huison on 2018/6/3.
 */

public class BluetoothDetail implements Comparable<BluetoothDetail>, Parcelable {

    private BluetoothDevice bluetoothDevice;
    private int rssi;
    private boolean isConnected;
    private boolean hasMatched;

    public static final Creator<BluetoothDetail> CREATOR = new Creator<BluetoothDetail>() {
        @Override
        public BluetoothDetail createFromParcel(Parcel in) {
            return new BluetoothDetail(in);
        }

        @Override
        public BluetoothDetail[] newArray(int size) {
            return new BluetoothDetail[size];
        }
    };

    public BluetoothDetail() {

    }

    protected BluetoothDetail(Parcel in) {
        bluetoothDevice = in.readParcelable(BluetoothDevice.class.getClassLoader());
        rssi = in.readInt();
        isConnected = in.readByte() != 0;
    }


    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getRssi() {
        return rssi;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setHasMatched(boolean hasMatched) {
        this.hasMatched = hasMatched;
    }

    public boolean hasMatched() {
        return hasMatched;
    }

    public String getDeviceName() {
        return bluetoothDevice == null ? "" : TextUtils.isEmpty(bluetoothDevice.getName()) ? "未知" : bluetoothDevice.getName();
    }

    public String getMac() {
        return bluetoothDevice == null ? "" : bluetoothDevice.getAddress();
    }

    @Override
    public int compareTo(@NonNull BluetoothDetail o) {
        return o.rssi - rssi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(bluetoothDevice, flags);
        dest.writeInt(rssi);
        dest.writeBooleanArray(new boolean[]{isConnected});
    }
}
