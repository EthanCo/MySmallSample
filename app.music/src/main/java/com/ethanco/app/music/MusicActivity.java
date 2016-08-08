package com.ethanco.app.music;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ethanco.lib.frame.view.BaseActivity;
import com.ethanco.lib.frame.viewmodel.BaseViewModel;
import com.ethanco.lib.imageproxy.abs.ImageProxy;
import com.ethanco.lib.imageproxy.proxy.GlideProxy;
import com.ethanco.lib.utils.T;

public class MusicActivity extends BaseActivity implements View.OnClickListener {

    private String imgUrl = "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fbd315c6034a85edfc7fb86374f540923dd5475aa.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D3363070324%2C404915416%26fm%3D21%26gp%3D0.jpg";

    private ImageView imgCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        initToolbar("Music", true);

        imgCover = (ImageView) findViewById(R.id.img_cover);
        findViewById(R.id.btn_show).setOnClickListener(this);

        ImageProxy imageProxy = GlideProxy.getInstace();
        imageProxy.with(this).load(imgUrl).into(imgCover);
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                T.show("Music");
                break;
            default:
        }
    }
}
