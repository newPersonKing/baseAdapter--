package com.system.translationpen.hotspot.manager;

/**
 * Created by huison on 2018/6/20.
 */

public interface IBluetoothConnectListener {

    void OnConnectionStart();

    void OnConnectionSuccess();

    void OnConnectionFailed(String error);
}
