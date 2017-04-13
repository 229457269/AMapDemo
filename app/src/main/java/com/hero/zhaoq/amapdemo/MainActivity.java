package com.hero.zhaoq.amapdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;


public class MainActivity extends CheckPermissionsActivity {

    private TextView txt_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_info = (TextView) findViewById(R.id.txt_info);
    }

    int i = 0;

    //处理 ui 变化
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //可在其中解析 amapLocation获取相应内容。
                txt_info.setText(amapLocation.toStr() + (i++));
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Toast.makeText(this,"定位失败",Toast.LENGTH_LONG).show();
//                Log.i("info",amapLocation.getErrorCode() + ":::"+amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    protected void onResume() {
        Log.i("info","=====");
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
