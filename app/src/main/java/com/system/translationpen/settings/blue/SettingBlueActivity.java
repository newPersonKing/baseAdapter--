package com.system.translationpen.settings.blue;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.algorithm.android.utils.AlRecyclerViewDecoration;
import com.fanwe.lib.switchbutton.FSwitchButton;
import com.fanwe.lib.switchbutton.SwitchButton;
import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.manager.BluetoothController;
import com.system.translationpen.hotspot.manager.BluetoothDetail;
import com.system.translationpen.hotspot.manager.IBluetoothConnectListener;
import com.system.translationpen.hotspot.manager.IBluetoothListener;
import com.system.translationpen.hotspot.manager.WifiController;
import com.system.translationpen.settings.Constant;
import com.system.translationpen.settings.bean.Expandbean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getBlueMain;
import static com.system.translationpen.settings.Constant.getHintMessage;
import static com.system.translationpen.settings.Constant.getPeiDuiState;
import static com.system.translationpen.settings.Constant.getWlanState;

public class SettingBlueActivity extends BaseTitleActivity {

    TextView setting_blue_name;
    FSwitchButton blue_check_switch;
    FSwitchButton blue_switch;
    TextView check_duration;

    TextView tv_turn_name;
    TextView tv_blue_name;
    TextView open_name;
    TextView tv_equipment_name;
    TextView tv_can_use_name;

    ImageView iv_blue_refresh;

    LinearLayout ll_setting_blue;
    @BindView(R.id.blue_rel)
    RecyclerView blue_rel;



    boolean isOpen=false;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("mm:ss");
    int time=2*60;
    Handler handler=new Handler();

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if (time==0){
                return;
            }
            time= time-1;
            String text=Constant.getBlueMain(4)+"("+simpleDateFormat.format(new Date(time*1000))+")";
            check_duration.setText(text);
            handler.postDelayed(this,1000);
        }
    };

    private CommonAdapter<BluetoothDetail> mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private List<BluetoothDetail> mDatas = new ArrayList<>();


    private int connectIndex=-1;
    @Override
    protected int onSetContentView() {

        return R.layout.activity_setting_blue;
    }

    @Override
    protected void onInitData() {

        setTitle(R.drawable.wback, Constant.getBlueMain(0),0);



        if (BluetoothController.instance().isBluetoothOpen()){
            mProgressDialog.setMessage(getHintMessage(0));
            mProgressDialog.show();
        }

        blue_rel.setLayoutManager(new LinearLayoutManager(this));
        /*新引入的adapter 替换原来的*/
        mAdapter = new CommonAdapter<BluetoothDetail>(this, R.layout.item_setting_blue, mDatas){
            @Override
            protected void convert(ViewHolder holder, BluetoothDetail bluetoothDetail, int position){


                holder.setText(R.id.blue_name,bluetoothDetail.getBluetoothDevice().getName());

                if (bluetoothDetail.hasMatched()){
                    holder.setText(R.id.blue_state,getPeiDuiState(3));
                    holder.setTextColor(R.id.blue_state,R.color.language_checked_color);
                }else {
                    holder.setText(R.id.blue_state,getPeiDuiState(4));
                }

            }
        };
        initHeader();


        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                connectIndex=position-1;
                BluetoothDetail detail=mDatas.get(position-1);
                if (detail.hasMatched()){
//                    BluetoothController.instance().unpairDevice(detail.getBluetoothDevice());
//                    scanBlue();
                }else {
                    mProgressDialog.setMessage(getPeiDuiState(2));
                    mProgressDialog.show();
                    BluetoothController.instance().connect(mDatas.get(position-1), new IBluetoothConnectListener() {
                        @Override
                        public void OnConnectionStart() {

                        }

                        @Override
                        public void OnConnectionSuccess() {
                            mProgressDialog.dismiss();
                            Toast.makeText(SettingBlueActivity.this,getPeiDuiState(0),Toast.LENGTH_SHORT).show();
                            mDatas.get(connectIndex).setHasMatched(true);
                            mHeaderAndFooterWrapper.notifyDataSetChanged();
                        }

                        @Override
                        public void OnConnectionFailed(String error) {

                            mProgressDialog.dismiss();
                            Toast.makeText(SettingBlueActivity.this,getPeiDuiState(1),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        blue_rel.setAdapter(mHeaderAndFooterWrapper);

        blue_switch.setOnCheckedChangeCallback(new SwitchButton.OnCheckedChangeCallback() {
            @Override
            public void onCheckedChanged(boolean checked, SwitchButton view) {
                isOpen=checked;
                if (checked){
                    blue_check_switch.setClickable(true);
                    BluetoothController.instance().openBluetooth();
                    scanBlue();
                }else {
                    BluetoothController.instance().closeBluetooth();
                    if (time!=0){
                        handler.removeCallbacks(runnable);
                    }
                    time=2*60;
                    String text=Constant.getBlueMain(4)+"("+simpleDateFormat.format(new Date(time*1000))+")";
                    check_duration.setText(text);

                    blue_check_switch.setChecked(false,false,false);
                    blue_check_switch.setClickable(false);
                    clearData();
                }
            }
        });

        blue_check_switch.setOnCheckedChangeCallback(new SwitchButton.OnCheckedChangeCallback() {
            @Override
            public void onCheckedChanged(boolean checked, SwitchButton view) {

                if (!BluetoothController.instance().isBluetoothOpen()){
                    blue_check_switch.setChecked(false,false,false);
                    return;
                }

                if (checked){
                    handler.postDelayed(runnable,1000);
                    BluetoothController.instance().enableDiscover(SettingBlueActivity.this,2*60*1000,100);
                }else {
                    if (time!=0){
                        handler.removeCallbacks(runnable);
                    }
                    time=2*60;
                    String text=Constant.getBlueMain(4)+"("+simpleDateFormat.format(new Date(time*1000))+")";
                    check_duration.setText(text);
                    BluetoothController.instance().disableDiscover(SettingBlueActivity.this);
                }
            }
        });


        if (BluetoothController.instance().isBluetoothOpen()){
            blue_switch.setChecked(true,false,false);
            blue_check_switch.setClickable(true);
            blue_check_switch.setChecked(false,false,false);
            scanBlue();
        }else {
            blue_switch.setChecked(false,false,false);
            blue_check_switch.setChecked(false,false,false);
            blue_check_switch.setClickable(false);
        }


        setting_blue_name.setText(BluetoothController.instance().getOwnBluetoothName());
    }

    @Override
    protected void onResume() {
        super.onResume();

        BluetoothController.instance().registerScanner(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BluetoothController.instance().unregisterScanner(this);
    }


    /*已配对设备*/
    Expandbean.ExpandBeanData connectData;
    /*可连接设备*/
    Expandbean.ExpandBeanData data;
    /*扫描所有的蓝牙设备*/
    private void scanBlue(){
        BluetoothController.instance().startScan(new IBluetoothListener() {
            @Override
            public void onLeScanBegan(boolean success) {

            }

            @Override
            public void onLeScan(BluetoothDetail bluetoothDetail) {

            }

            @Override
            public void onLeScanFinished(List<BluetoothDetail> bluetoothDetails) {
                mProgressDialog.dismiss();

                mDatas.clear();
                mDatas.addAll(BluetoothController.instance().getBondedDevices());
                mDatas.addAll(bluetoothDetails);
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }
        });
    }
    /*todo 判断是否是音乐 或者音响 但是目前还是不知道 音乐跟音响的值是多少 */
    private void checkBlue(BluetoothDetail bluetoothDetail){
        int type= bluetoothDetail.getBluetoothDevice().getBluetoothClass().getMajorDeviceClass();

    }

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        tv_turn_name.setText(Constant.getBlueMain(1));
        tv_blue_name.setText(Constant.getBlueMain(2));
        open_name.setText(Constant.getBlueMain(3));
        tv_equipment_name.setText(Constant.getBlueMain(5));
        tv_can_use_name.setText(getBlueMain(6));

        String text=Constant.getBlueMain(4)+"("+simpleDateFormat.format(new Date(time*1000))+")";
        check_duration.setText(text);
    }

    public void clearData(){
        mDatas.clear();
        mHeaderAndFooterWrapper.notifyDataSetChanged();
    }

    private void initHeader(){
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        View view=LayoutInflater.from(this).inflate(R.layout.blue_header,blue_rel,false);
        mHeaderAndFooterWrapper.addHeaderView(view);

        setting_blue_name=view.findViewById(R.id.setting_blue_name);
        blue_check_switch=view.findViewById(R.id.blue_check_switch);
        blue_switch=view.findViewById(R.id.blue_switch);
        check_duration=view.findViewById(R.id.check_duration);
        tv_turn_name=view.findViewById(R.id.tv_turn_name);
        tv_blue_name=view.findViewById(R.id.tv_blue_name);
        open_name=view.findViewById(R.id.open_name);
        tv_equipment_name=view.findViewById(R.id.tv_equipment_name);
        ll_setting_blue=view.findViewById(R.id.ll_setting_blue);
        tv_can_use_name=view.findViewById(R.id.tv_can_use_name);
        ll_setting_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingBlueActivity.this,EditBlueNameActivity.class);
                startActivity(intent);
            }
        });

        iv_blue_refresh=view.findViewById(R.id.iv_blue_refresh);
        iv_blue_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage(getHintMessage(0));
                mProgressDialog.show();
                scanBlue();
            }
        });
    }
}
