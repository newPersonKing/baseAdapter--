package com.system.translationpen.hotspot.manager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 * Created by huison on 2018/6/3.
 */

public class BluetoothConnectTask implements Runnable {

    private static final int kMsgWhatConnectionStart = 10;
    private static final int kMsgWhatConnectionSuccess = 11;
    private static final int kMsgWhatConnectionFailed = 12;

    private String mac;
    private IBluetoothConnectListener listener;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BluetoothSocket bluetoothSocket;


    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case kMsgWhatConnectionStart:
                    listener.OnConnectionStart();
                    break;
                case kMsgWhatConnectionSuccess:
                    listener.OnConnectionSuccess();
                    break;
                case kMsgWhatConnectionFailed:
                    listener.OnConnectionFailed((String) msg.obj);
                default:
                    break;
            }
        }
    };

    public BluetoothConnectTask(String mac, IBluetoothConnectListener listener) {
        if (null == listener) {
            throw new NullPointerException("IBluetoothConnectListener can not be null");
        }
        this.mac = mac;
        this.listener = listener;
    }

    @Override
    public void run() {
        BluetoothDevice remoteDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(mac);
        try {
            handler.sendEmptyMessage(kMsgWhatConnectionStart);
            bluetoothSocket = remoteDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString(SampleGattAttributes.CONNECTION_UUID_STRING));
            bluetoothSocket.connect();
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();
            handler.sendEmptyMessage(kMsgWhatConnectionSuccess);
        } catch (IOException ioe) {
            try {
                Class<?>[] paramsTypes = new Class<?>[]{Integer.TYPE};
                Object[] params = new Object[]{Integer.valueOf(1)};
                bluetoothSocket = (BluetoothSocket) remoteDevice.getClass().getMethod("createRfcommSocket", paramsTypes).invoke(remoteDevice, params);
                bluetoothSocket.connect();
                inputStream = bluetoothSocket.getInputStream();
                outputStream = bluetoothSocket.getOutputStream();
                handler.sendEmptyMessage(kMsgWhatConnectionSuccess);
            } catch (Throwable e1) {
                if (e1.getMessage().equals("read failed, socket might closed or timeout, read ret: -1")){
                    handler.sendEmptyMessage(kMsgWhatConnectionSuccess);
                }else {
                    e1.printStackTrace();
                    Message message = new Message();
                    message.obj = e1.toString();
                    message.what = kMsgWhatConnectionFailed;
                    handler.sendMessage(message);
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (Throwable e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    public InputStream getInputStream() {
        if (bluetoothSocket.isConnected()) {
            return inputStream;
        }
        return null;
    }

    public OutputStream getOutputStream() {
        if (bluetoothSocket.isConnected()) {
            return outputStream;
        }
        return null;
    }
}
