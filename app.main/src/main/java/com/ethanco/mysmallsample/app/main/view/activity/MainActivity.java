package com.ethanco.mysmallsample.app.main.view.activity;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ethanco.mysmallsample.app.main.R;
import com.ethanco.mysmallsample.app.main.view.abs.ITimeView;
import com.ethanco.mysmallsample.app.main.viewmodel.TimeViewModel;
import com.ethanco.mysmallsample.lib.frame.view.BaseActivity;
import com.ethanco.mysmallsample.lib.network.NetFacade;
import com.ethanco.mysmallsample.lib.style.dialog.LoadingDialog;
import com.ethanco.mysmallsample.lib.utils.L;
import com.ethanco.mysmallsample.lib.utils.T;
import com.ethanco.mysmallsample.lib.utils.config.AppConfig;

import net.wequick.small.Small;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<ITimeView, TimeViewModel> implements ITimeView, View.OnClickListener {

    @Bind(R.id.et_upgrade)
    EditText etUpgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar("Main", false);

        initGlobal(getApplication());

        /*net.wequick.small.Bundle bundle = Small.getBundle("com.ethanco.app.main");
        File file = bundle.getPatchFile();
        L.i("onCreate patch path: " + file.getPath());*/

        findViewById(R.id.btn_upgrade).setOnClickListener(this);
        findViewById(R.id.btn_go_music_activity).setOnClickListener(this);
        findViewById(R.id.btn_network).setOnClickListener(this);
    }

    private void initGlobal(Application application) {
        T.init(application);
        NetFacade.init(application);
        AppConfig.init(application);
    }

    @Override
    protected TimeViewModel createViewModel() {
        return new TimeViewModel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upgrade:
                String bundlePackageName = etUpgrade.getText().toString();
                if (TextUtils.isEmpty(bundlePackageName)) {
                    T.show("请输入Bundle包名");
                    return;
                }

                net.wequick.small.Bundle bundle = Small.getBundle(bundlePackageName);
                L.i("onClick music patch path: " + bundle.getPatchFile().getPath());
                bundle.upgrade();
                T.show("success");

                break;
            case R.id.btn_go_music_activity:
                Small.openUri("music", MainActivity.this);
                break;
            case R.id.btn_network:
                mViewModel.getServiceTime();
                break;
            default:
        }
    }

    @Override
    public void showProgressDialog() {
        LoadingDialog.show(this);
    }

    @Override
    public void dismissProgressDialog() {
        LoadingDialog.dismiss();
    }

    @Override
    public void getServiceTimeSuccess(String time) {
        T.show("time:" + time);
    }

    @Override
    public void getServiceTimeFailed(String error) {
        T.show(error);
    }
}
