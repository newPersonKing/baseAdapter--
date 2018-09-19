package com.system.translationpen.hotspot.manager;


import com.system.translationpen.hotspot.bean.WifiDetail;

import java.util.List;

/**
 * Created by huison on 2018/6/2.
 */

public interface IWifiListener {

    void onWifiStateChanged(int state);

    void onWifiScanChanged(List<WifiDetail> wifiDetails);
}
