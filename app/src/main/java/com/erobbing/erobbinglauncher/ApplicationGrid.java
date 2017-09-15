/*
 * Copyright (C) 2016
 * zhangzhaolei@erobbing.com
 */

package com.erobbing.erobbinglauncher;

import java.util.List;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.KeyEvent;

public class ApplicationGrid extends Activity {
    private PackageManager pm;
    private ImageView mIcon;
    private TextView mTitle;
    private GridView mAllappsGridView;
    private List<ResolveInfo> mResolveInfoList;
    private List<ResolveInfo> mApplicationList = new ArrayList<>();
    private ResolveInfo mResolveInfo;
    private View mGridItemView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.application_grid);

        mAllappsGridView = (GridView) this.findViewById(R.id.allapps_gridview);
        pm = this.getPackageManager();
        Intent in = new Intent();
        in.setAction(Intent.ACTION_MAIN);
        in.addCategory(Intent.CATEGORY_LAUNCHER);
        mResolveInfoList = pm.queryIntentActivities(in, 0);
        for (int i = 0; i < mResolveInfoList.size(); i++) {
            mResolveInfo = mResolveInfoList.get(i);

            /*if (shouldIgnore(mResolveInfoList.get(i).toString())) {
                continue;
            }*/
            mApplicationList.add(mResolveInfo);
            ComponentName cn = new ComponentName(mResolveInfo.activityInfo.packageName,
                    mResolveInfo.activityInfo.name);
            in.setComponent(cn);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        }
        AllAppsGridAdapter mAllAppsGridAdapter = new AllAppsGridAdapter();
        mAllappsGridView.setAdapter(mAllAppsGridAdapter);

        mAllappsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {
                Intent in = new Intent();
                mResolveInfo = mApplicationList.get(position);
                ComponentName cn = new ComponentName(
                        mResolveInfo.activityInfo.packageName, mResolveInfo.activityInfo.name);
                in.setComponent(cn);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                ApplicationGrid.this.startActivity(in);
            }
        });
    }

    private boolean shouldIgnore(String packageName) {
        return packageName.contains("com.baidu.BaiduMap")
                || packageName.contains("cn.kuwo.kwmusiccar")
                || packageName.contains("com.erobbing.launcher4")
                //|| packageName.contains("com.android.settings")
                || packageName.contains("com.autonavi.amapauto")
                //|| packageName.contains("com.erobbing.btdialer")
                || packageName.contains("com.sprd.appbackup")
                || packageName.contains("com.hdsc.edog")
                || packageName.contains("com.android.fmtx")
                || packageName.contains("com.sprd.fileexplorer")
                || packageName.contains("com.androits.gps.test.qcom")
                || packageName.contains("com.erobbing.hotspot")
                || packageName.contains("com.unisound.unicar.gui")
                || packageName.contains("com.spreadst.favorites")
                || packageName.contains("com.ximalaya.ting.android.car")
                || packageName.contains("com.ublox.ucenter")
                || packageName.contains("com.sprd.firewall")
                || packageName.contains("com.cmcc.homepage");
    }

    private class AllAppsGridAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mApplicationList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ApplicationGrid.this.getLayoutInflater();
            mResolveInfo = mApplicationList.get(position);
            mGridItemView = inflater.inflate(R.layout.item_grid, null);
            mIcon = (ImageView) mGridItemView.findViewById(R.id.icon);
            mIcon.setImageDrawable(mResolveInfo.loadIcon(pm));
            Drawable d;

            mTitle = (TextView) mGridItemView.findViewById(R.id.title);
            mTitle.setText(mResolveInfo.loadLabel(pm));
            return mGridItemView;
        }

    }
}
