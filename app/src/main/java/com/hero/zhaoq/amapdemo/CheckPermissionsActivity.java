package com.hero.zhaoq.amapdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Package_name:com.hero.zhaoq.amapdemo
 * Author:zhaoqiang
 * Email:zhaoq_hero@163.com
 * Date:2017/04/11   18/54
 * github:https://github.com/229457269  csdn:http://blog.csdn.net/u013233097
 */
public class CheckPermissionsActivity extends BaseActivity
        implements
        ActivityCompat.OnRequestPermissionsResultCallback {
    //    6.0权限的基本知识，以下是需要单独申请的权限，
    // 共分为9组，每组只要有一个权限申请成功了，就默认整组权限都可以使用了。

//    group:android.permission-group.CONTACTS   //第一组 读取 联系人权限
//    permission:android.permission.WRITE_CONTACTS
//    permission:android.permission.GET_ACCOUNTS
//    permission:android.permission.READ_CONTACTS
//
//    group:android.permission-group.PHONE //第二组  拨打电话权限
//    permission:android.permission.READ_CALL_LOG
//    permission:android.permission.READ_PHONE_STATE
//    permission:android.permission.CALL_PHONE
//    permission:android.permission.WRITE_CALL_LOG
//    permission:android.permission.USE_SIP
//    permission:android.permission.PROCESS_OUTGOING_CALLS
//    permission:com.android.voicemail.permission.ADD_VOICEMAIL
//
//    group:android.permission-group.CALENDAR  //第三组 ：允许程序读取用户的日程信息
//    permission:android.permission.READ_CALENDAR
//    permission:android.permission.WRITE_CALENDAR
//
//    group:android.permission-group.CAMERA //第四组 摄像机的 使用 允许访问摄像头进行拍照
//    permission:android.permission.CAMERA
//
//    group:android.permission-group.SENSORS // 第五组  传感器
//    permission:android.permission.BODY_SENSORS
//
//    group:android.permission-group.LOCATION //第六组 允许获得移动网络定位信息改变
//    permission:android.permission.ACCESS_FINE_LOCATION
//    permission:android.permission.ACCESS_COARSE_LOCATION
//
//    group:android.permission-group.STORAGE //第七组  允许程序写入外部存储，如SD卡上写文件
//    permission:android.permission.READ_EXTERNAL_STORAGE
//    permission:android.permission.WRITE_EXTERNAL_STORAGE
//
//    group:android.permission-group.MICROPHONE //第八组  麦风风 权限
//    permission:android.permission.RECORD_AUDIO
//
//    group:android.permission-group.SMS //第九组  读取短信  内容权限
//    permission:android.permission.READ_SMS
//    permission:android.permission.RECEIVE_WAP_PUSH
//    permission:android.permission.RECEIVE_MMS
//    permission:android.permission.RECEIVE_SMS
//    permission:android.permission.SEND_SMS
//    permission:android.permission.READ_CELL_BROADCASTS

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION, // 定位权限 位置信息  通过卫星或网络 进行定位
            Manifest.permission.ACCESS_FINE_LOCATION,  //  定位权限 位置信息  通过卫星或网络 进行定位

            Manifest.permission.WRITE_EXTERNAL_STORAGE, //  媒体内容
            Manifest.permission.READ_EXTERNAL_STORAGE, // 和文件
            Manifest.permission.READ_PHONE_STATE  //允许 拨打电话

//            Manifest.permission.CAMERA,
//            Manifest.permission.CALL_PHONE
    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    @Override
    protected void onResume() {
        super.onResume();
        if(isNeedCheck){
            checkPermissions(needPermissions); //检查权限
        }
    }

    public void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            //请求  权限
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

//    public void mCheckPermission(){
//        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS) !=
//                PackageManager.PERMISSION_GRANTED) {// 没有权限。
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.READ_CONTACTS)) {
//                // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
//
//            } else {
//                // 申请授权。
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MMM);
//            }
//        }
//    }

    /**
     * 获取   权限   集中需要   申请权限的列表
     */
    public List<String> findDeniedPermissions(String[] permissions) {
        //
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(grantResults)) {
                //如果存在     未被准许的   权限
                showMissingPermissionDialog(); // 用户未同意 权限申请
                isNeedCheck = false;
            }
        }
    }

    /**
     * 检测是否说有的权限都已经授权
     */
    public boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示信息
     * @since 2.5.0
     */
    public void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。");

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     *  启动应用的设置
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
