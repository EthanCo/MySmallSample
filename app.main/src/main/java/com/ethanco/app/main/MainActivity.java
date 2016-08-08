package com.ethanco.app.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ethanco.lib.network.APIService;
import com.ethanco.lib.network.AppCommandType;
import com.ethanco.lib.network.RetrofitFactory;
import com.ethanco.lib.network.bean.request.CmdRequest;
import com.ethanco.lib.network.bean.response.TimeResponse;
import com.ethanco.lib.utils.L;
import com.ethanco.lib.utils.T;
import com.ethanco.lib.utils.dialog.LoadingDialog;

import net.wequick.small.Small;

import java.io.File;

import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Z-main";
    private EditText etUpgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        net.wequick.small.Bundle bundle = Small.getBundle("com.ethanco.app.main");
        File file = bundle.getPatchFile();
        Log.i(TAG, "onCreate patch path: " + file.getPath());

        etUpgrade = (EditText) findViewById(R.id.et_upgrade);

        findViewById(R.id.btn_upgrade).setOnClickListener(this);
        findViewById(R.id.btn_go_music_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upgrade:
                String bundlePackageName = etUpgrade.getText().toString();
                if (TextUtils.isEmpty(bundlePackageName)) {
                    T.show("请输入Bundle包名~");
                    L.w("请输入Bundle包名");
                    LoadingDialog.show(this);

                    Retrofit retrofit = RetrofitFactory.getInstance().createRetrofit("http://121.40.227.8:8088/");
                    CmdRequest cmd = new CmdRequest();
                    cmd.setCmd(AppCommandType.GET_SERVICE_TIME);
                    retrofit.create(APIService.class).getServerTime(cmd)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Action1<TimeResponse>() {
                                @Override
                                public void call(TimeResponse timeResponse) {
                                    String time = timeResponse.getData().getTime();
                                    L.i("time:" + time);
                                    T.show("time:" + time);
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    L.i("throwable:" + throwable.getMessage());
                                }
                            }, new Action0() {
                                @Override
                                public void call() {
                                    L.i("complete");
                                }
                            });
                    return;
                }

                net.wequick.small.Bundle bundle = Small.getBundle(bundlePackageName);
                Log.i(TAG, "onClick music patch path: " + bundle.getPatchFile().getPath());
                bundle.upgrade();
                T.show("success");

                break;
            case R.id.btn_go_music_activity:
                Small.openUri("music", MainActivity.this);
                break;
            default:
        }
    }
}
