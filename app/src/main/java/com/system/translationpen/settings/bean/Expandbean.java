package com.system.translationpen.settings.bean;

import com.system.translationpen.hotspot.manager.BluetoothDetail;

import java.util.ArrayList;
import java.util.List;

public class Expandbean {

    List<ExpandBeanData> datas=new ArrayList<>();

    public List<ExpandBeanData> getDatas() {
        return datas;
    }

    public void setDatas(List<ExpandBeanData> datas) {
        this.datas = datas;
    }

    public static class ExpandBeanData{

        private String title;

        private List<BluetoothDetail> Details=new ArrayList<>();

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<BluetoothDetail> getDetails() {
            return Details;
        }

        public void setDetails(List<BluetoothDetail> details) {
            Details = details;
        }
    }
}
