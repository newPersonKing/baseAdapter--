package com.system.translationpen.hotspot.manager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;


import com.system.translationpen.hotspot.base.BaseActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huison on 2018/6/3.
 */

public class BluetoothController implements IBluetoothListener {

    private static final String TAG = BluetoothController.class.getName();

    private static volatile BluetoothController sInstance;

    private BluetoothManager bluetoothManager;
    private BluetoothScanner bluetoothScanner;
    private BluetoothAdapter bluetoothAdapter;

    private ExecutorService executorService;

    private boolean isScanning;

    private BluetoothController() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothScanner = new BluetoothScanner(this);

        executorService = Executors.newSingleThreadExecutor();
    }

    public void init(Context context) {
        bluetoothManager = (BluetoothManager) context.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
    }

    public static BluetoothController instance() {
        if (sInstance == null) {
            synchronized (BluetoothController.class) {
                if (sInstance == null) {
                    sInstance = new BluetoothController();
                }
            }
        }
        return sInstance;
    }

    public boolean isBluetoothOpen() {
        return bluetoothAdapter.isEnabled();
    }

    public boolean openBluetooth() {
        if (!isBluetoothOpen()) {
            return bluetoothAdapter.enable();
        }
        return false;
    }

    public boolean closeBluetooth() {
        if (isBluetoothOpen()) {
            return bluetoothAdapter.disable();
        }
        return false;
    }

    public void enableDiscover(BaseActivity activity, int duration, int requestCode) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, duration);
        activity.startActivityForResult(intent, requestCode);
    }

    public void disableDiscover(Context context) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 1);
        context.startActivity(intent);
    }

    public ArrayList<BluetoothDetail> getBondedDevices() {
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        ArrayList<BluetoothDetail> bondedDevices = new ArrayList<>();
        for (BluetoothDevice device : devices) {
            BluetoothDetail bluetoothDetail = new BluetoothDetail();
            bluetoothDetail.setBluetoothDevice(device);
            bluetoothDetail.setHasMatched(true);
            bondedDevices.add(bluetoothDetail);
        }
        return bondedDevices;
    }

    public boolean isScanning() {
        return isScanning;
    }

    public int getConnectState(BluetoothDetail bluetoothDetail) {
        if (bluetoothDetail != null) {
            return bluetoothManager.getConnectionState(bluetoothDetail.getBluetoothDevice(), BluetoothProfile.GATT);
        } else {
            return BluetoothProfile.STATE_DISCONNECTED;
        }
    }

    public boolean isConnected(BluetoothDetail bluetoothDetail) {
        return getConnectState(bluetoothDetail) == BluetoothProfile.STATE_CONNECTED;
    }

    public void connect(BluetoothDetail bluetoothDetail, IBluetoothConnectListener connectListener) {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        if (TextUtils.isEmpty(bluetoothDetail.getMac())) {
            if (connectListener != null) {
                connectListener.OnConnectionFailed("Mac address is null or empty !");
            }
        }
        if (!BluetoothAdapter.checkBluetoothAddress(bluetoothDetail.getMac())) {
            if (connectListener != null) {
                connectListener.OnConnectionFailed("Mac address is not correct ! Please make sure it's upper case !");
            }
        }
        BluetoothConnectTask connectTask = new BluetoothConnectTask(bluetoothDetail.getMac(), connectListener);
        executorService.execute(connectTask);
    }

    private IBluetoothListener listener;

    public void startScan(IBluetoothListener listener) {
        if (listener == null) {
            throw new IllegalStateException("IBluetoothListener is null");
        }
        if (!isBluetoothOpen()) {
            listener.onLeScanBegan(false);
            Log.d(TAG, "Bluetooth is close, please open first");
            return;
        }
        if (isScanning) {
            listener.onLeScanBegan(false);
            Log.d(TAG, "Bluetooth is already in scanning");
            return;
        }
        this.listener = listener;
        if (bluetoothScanner != null) {
            bluetoothScanner.startScan();
            isScanning = true;
            listener.onLeScanBegan(true);
            Log.d(TAG, "Bluetooth is start scanning");
        } else {
            listener.onLeScanBegan(false);
            Log.d(TAG, "bluetoothScanner is null");
        }
    }

    // register when resume
    public void registerScanner(Context context) {
        if (bluetoothScanner != null) {
            bluetoothScanner.register(context);
        }
    }

    // unregister when pause
    public void unregisterScanner(Context context) {
        if (bluetoothScanner != null) {
            bluetoothScanner.unregister(context);
        }
    }

    public void stopScan() {
        Log.d(TAG, "Bluetooth is stop scanning");
        isScanning = false;
        if (bluetoothScanner != null) {
            bluetoothScanner.stopScan();
        }
    }

    public boolean isLEConnected(BluetoothDetail bluetoothDetail) {
        return getConnectionState(bluetoothDetail) == BluetoothProfile.STATE_CONNECTED;
    }

    public int getConnectionState(BluetoothDetail bluetoothDetail) {
        if (bluetoothDetail != null) {
            return bluetoothManager.getConnectionState(bluetoothDetail.getBluetoothDevice(), BluetoothProfile.GATT);
        } else {
            return BluetoothProfile.STATE_DISCONNECTED;
        }
    }

    public String getOwnBluetoothName() {
        String name = bluetoothAdapter.getName();
        return name == null ? "" : name;
    }

    public boolean setOwnBluetoothName(String name) {
        return bluetoothAdapter.setName(name);
    }

    @Override
    public void onLeScanBegan(boolean success) {
        if (listener != null) {
            listener.onLeScanBegan(success);
        }
    }

    @Override
    public void onLeScan(BluetoothDetail bluetoothDetail) {
        if (listener != null) {
            listener.onLeScan(bluetoothDetail);
        }
    }

    @Override
    public void onLeScanFinished(List<BluetoothDetail> bluetoothDetails) {
        isScanning = false;
        if (listener != null) {
            listener.onLeScanFinished(bluetoothDetails);
        }
    }

    public void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {

        }
    }


}
