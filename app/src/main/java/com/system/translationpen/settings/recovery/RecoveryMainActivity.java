package com.system.translationpen.settings.recovery;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getLanguage;
import static com.system.translationpen.settings.Constant.getRecovery;

public class RecoveryMainActivity extends BaseTitleActivity {

    @BindView(R.id.tv_clear_title)
    TextView tv_clear_title;
    @BindView(R.id.tv_clear_content)
    TextView tv_clear_content;
    @BindView(R.id.btn_recovery)
    Button btn_recovery;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_recovery_main;
    }

    @Override
    protected void onInitData() {
         setTitle(R.drawable.wback,getRecovery(0),0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getLanguage()==1){
            tv_clear_title.setText(getResources().getString(R.string.reset_title_c));
            tv_clear_content.setText(getResources().getString(R.string.reset_content_c));
        }else {
            tv_clear_title.setText(getResources().getString(R.string.reset_title_e));
            tv_clear_content.setText(getResources().getString(R.string.reset_content_e));
        }
    }

    @OnClick({
            R.id.btn_recovery
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.btn_recovery:
                intent.setClass(this,RecoveryResultActivity.class);
                /*这里进行一些删除操作*/
                DeleteTask deleteTask=new DeleteTask();
                deleteTask.execute();
                break;
        }
    }

    public class DeleteTask<T,E,G> extends AsyncTask<T,E,G>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected G doInBackground(T... ts) {
            return null;
        }

        @Override
        protected void onPostExecute(G g) {
            super.onPostExecute(g);
        }
    }

    @Override
    public void refreshSettingState() {
        super.refreshSettingState();
        btn_recovery.setText(getRecovery(1));
    }
}
