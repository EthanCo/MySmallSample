package com.ethanco.app.main.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ethanco.app.main.R;
import com.ethanco.app.main.view.abs.MainView;
import com.ethanco.app.main.viewmodel.abs.MainViewModel;
import com.lib.frame.view.BaseActivity;
import com.lib.network.RetrofitFactory;
import com.lib.utils.L;
import com.lib.utils.T;
import com.lib.utils.dialog.LoadingDialog;

import net.wequick.small.Small;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainView, MainViewModel> implements MainView, View.OnClickListener {

    @Bind(R.id.et_upgrade)
    EditText etUpgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar("Main", false);

        /*net.wequick.small.Bundle bundle = Small.getBundle("com.ethanco.app.main");
        File file = bundle.getPatchFile();
        L.i("onCreate patch path: " + file.getPath());*/

        etUpgrade = (EditText) findViewById(R.id.et_upgrade);

        findViewById(R.id.btn_upgrade).setOnClickListener(this);
        findViewById(R.id.btn_go_music_activity).setOnClickListener(this);
        findViewById(R.id.btn_network).setOnClickListener(this);

        RetrofitFactory.init(getApplication());
    }

    @Override
    protected MainViewModel createViewModel() {
        return new MainViewModel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upgrade:
                String bundlePackageName = etUpgrade.getText().toString();
                if (TextUtils.isEmpty(bundlePackageName)) {
                    T.show(this, "请输入Bundle包名");
                    return;
                }

                net.wequick.small.Bundle bundle = Small.getBundle(bundlePackageName);
                L.i("onClick music patch path: " + bundle.getPatchFile().getPath());
                bundle.upgrade();
                T.show(this, "success");

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
        T.show(this, "time:" + time);
    }

    @Override
    public void getServiceTimeFailed(String error) {
        T.show(this, error);
    }
}
