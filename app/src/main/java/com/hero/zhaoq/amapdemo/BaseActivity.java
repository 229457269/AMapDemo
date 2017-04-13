package com.hero.zhaoq.amapdemo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

public abstract class BaseActivity extends AppCompatActivity implements AMapLocationListener {

    private AMapUtils utils = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册广播：
        initMapListener();
    }

    //===========================================  需要子类 实现的  方法
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {}

    //============================================
    private void initMapListener() {
        utils = AMapUtils.getInstance(this);
        utils.setLocationChangedListener(this);
        utils.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁广播
        AMapUtils.getInstance(this).destory();
    }

}
