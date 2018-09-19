package com.system.translationpen.hotspot.manager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;


import com.system.translationpen.hotspot.untils.HandlerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huison on 2018/6/3.
 */

public class BluetoothScanner {

    private static final int kScanTimeout = 20 * 1000;

    private BluetoothAdapter bluetoothAdapter;
    private IBluetoothListener bluetoothListener;

    private HashMap<String, BluetoothDetail> bluetoothDetailHashMap;

    private boolean isFinishScan = false;

    public BluetoothScanner(IBluetoothListener listener) {
        bluetoothListener = listener;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothDetailHashMap = new HashMap<>();
    }

    public void register(Context context) {
        if (context != null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            context.registerReceiver(broadcastReceiver, filter);
        }
    }

    public void unregister(Context context) {
        if (context != null) {
            context.unregisterReceiver(broadcastReceiver);
        }
    }

    public void startScan() {
        isFinishScan = false;
        bluetoothDetailHashMap.clear();
        bluetoothAdapter.startDiscovery();
        HandlerUtils.runOnUIThreadDelay(new Runnable() {
            @Override
            public void run() {
                stopScan();
            }
        }, kScanTimeout);
    }

    public void stopScan() {
        if (isFinishScan) {
            return;
        }
        isFinishScan = true;
        bluetoothAdapter.cancelDiscovery();
        if (bluetoothListener != null) {
            List<BluetoothDetail> bluetoothDetails = new ArrayList<>(bluetoothDetailHashMap.values());
            bluetoothListener.onLeScanFinished(bluetoothDetails);
        }
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int rssi = intent.getIntExtra(BluetoothDevice.EXTRA_RSSI, 0);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    BluetoothDetail bluetoothDetail = new BluetoothDetail();
                    bluetoothDetail.setBluetoothDevice(device);
                    bluetoothDetail.setRssi(rssi);
                    bluetoothDetail.setConnected(BluetoothController.instance().isLEConnected(bluetoothDetail));
                    String key = device.getName() + device.getAddress();
                    if (!bluetoothDetailHashMap.containsKey(key)) {
                        bluetoothDetailHashMap.put(key, bluetoothDetail);
                        if (bluetoothListener != null) {
                            bluetoothListener.onLeScan(bluetoothDetail);
                        }
                    }
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                stopScan();
            }
        }
    };
}
