package com.system.translationpen.exchangerate.until;

import android.content.Context;
import android.util.Log;


import com.system.translationpen.daomaster.DaoMaster;
import com.system.translationpen.daomaster.DaoSession;
import com.system.translationpen.daomaster.ExChangeMultipleItemDao;
import com.system.translationpen.daomaster.FirstPageBeanDao;
import com.system.translationpen.daomaster.WifiConnectDao;
import com.system.translationpen.exchangerate.bean.ExChangeMultipleItem;
import com.system.translationpen.exchangerate.bean.FirstPageBean;
import com.system.translationpen.settings.bean.WifiConnect;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;

public class DaoUntils {

    public static DaoSession daoSession;
    public static void init(Context context){
        DaoMaster.DevOpenHelper openHelper=new DaoMaster.DevOpenHelper(context,"trans.db");
        DaoMaster daoMaster=new DaoMaster(openHelper.getWritableDb());
        daoSession=daoMaster.newSession();

    }

    public static void insertItem(ExChangeMultipleItem item){
          List<ExChangeMultipleItem> datas=queryByCurno(item.getCurno());
        if (datas!=null){
            item.setId(datas.get(0).getId());
            daoSession.getExChangeMultipleItemDao().update(item);
        }else {
            item.setId(null);
            long insertId= daoSession.getExChangeMultipleItemDao().insert(item);
        }
    }

    public static List<ExChangeMultipleItem> queryByCurno(String curno){

        return daoSession.getExChangeMultipleItemDao().queryBuilder().where(ExChangeMultipleItemDao.Properties.Curno.eq(curno)).list();
    }

    public static void insertItems(List<ExChangeMultipleItem> items){
        if (getAll().size()>0){

        }else {
            daoSession.getExChangeMultipleItemDao().insertInTx(items);
        }
    }

    public static List<ExChangeMultipleItem> queryByQuyu(String quyu){
        return daoSession.getExChangeMultipleItemDao().queryBuilder().where(ExChangeMultipleItemDao.Properties.Quyu.eq(quyu)).list();
    }

    public static List<ExChangeMultipleItem> getAll(){
        return daoSession.getExChangeMultipleItemDao().loadAll();
    }

    public static void insertFirstBean(FirstPageBean firstPageBean){
        List<FirstPageBean> datas=daoSession.getFirstPageBeanDao().loadAll();
        if (datas.size()==3){
          return;
        }else {
            daoSession.getFirstPageBeanDao().insert(firstPageBean);
        }
    }

    public static void updateFirstBean(FirstPageBean firstPageBean){
        List<FirstPageBean> datas=daoSession.getFirstPageBeanDao().queryBuilder().where(FirstPageBeanDao.Properties.Position.eq(firstPageBean.getPosition())).list();
        firstPageBean.setId(datas.get(0).getId());
        updateMidddle(firstPageBean);
    }
    /*只是更新middle*/
    public static void updateMidddle(FirstPageBean firstPageBean){
        daoSession.getFirstPageBeanDao().update(firstPageBean);
    }

    public static void upadteByCurno(FirstPageBean firstPageBean){
       List<FirstPageBean> datas= daoSession.getFirstPageBeanDao().queryBuilder().where(FirstPageBeanDao.Properties.Curno.eq(firstPageBean.getCurno())).list();
       firstPageBean.setId(datas.get(0).getId());
       firstPageBean.setPosition(datas.get(0).getPosition());
       updateMidddle(firstPageBean);
    }

    public static List<FirstPageBean> loadAllFirstBean(){
        return daoSession.getFirstPageBeanDao().loadAll();
    }

    public static void remember(String name,String password){
        Log.i("cccccccccccc","remeber==="+name);
        WifiConnect wifiConnect=new WifiConnect();
        wifiConnect.setName(name);
        wifiConnect.setPassword(password);
        daoSession.getWifiConnectDao().insert(wifiConnect);
    }

    public static String getRememberPassword(String name){
        List<WifiConnect> wifiConnects=daoSession.getWifiConnectDao().queryBuilder().where(WifiConnectDao.Properties.Name.eq(name)).list();
        Log.i("cccccccccccc","remeber==="+wifiConnects.size()+"name=="+name);
        if (wifiConnects.size()>0){
            return wifiConnects.get(0).getPassword();
        }else {
            return "";
        }
    }

    public static void updatePassword(String name,String password){
        List<WifiConnect> wifiConnects=daoSession.getWifiConnectDao().queryBuilder().where(WifiConnectDao.Properties.Name.eq(name)).list();
        if (wifiConnects.size()>0){
           WifiConnect wifiConnect= wifiConnects.get(0);
           wifiConnect.setPassword(password);
           daoSession.getWifiConnectDao().update(wifiConnect);
        }
    }
}
