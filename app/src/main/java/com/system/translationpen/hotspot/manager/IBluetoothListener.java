package com.system.translationpen.hotspot.manager;

import java.util.List;

/**
 * Created by huison on 2018/6/3.
 */

public interface IBluetoothListener {

    void onLeScanBegan(boolean success);

    void onLeScan(BluetoothDetail bluetoothDetail);

    void onLeScanFinished(List<BluetoothDetail> bluetoothDetails);
}
