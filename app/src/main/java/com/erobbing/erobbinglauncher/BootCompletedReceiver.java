package com.erobbing.erobbinglauncher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.app.AlertDialog;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;

public class BootCompletedReceiver extends BroadcastReceiver {
    private final String TAG = "ErobbingLauncher.BootCompletedReceiver";
    private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    private Context mContext;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //ShowDialog(REASON_NO_SDCARD);
                    //Log.d(TAG,"Launcher4.REASON_NO_SDCARD");
                    break;
                case 2:
                    //ShowDialog(REASON_LOW_SPACE);
                    //Toast.makeText(mContext, R.string.toast_memory_low, Toast.LENGTH_SHORT).show();
                    //Log.d(TAG,"Launcher4.REASON_LOW_SPACE");
                    break;
            }
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        if (ACTION_BOOT.equals(intent.getAction())) {
            Log.e("====", "=============onReceive");
            copyFile();
        }
    }

    public void copyFile() {
        try {
            Process p = Runtime.getRuntime().exec(
                    "/system/bin/sh /system/etc/init.iflytek.sh");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
