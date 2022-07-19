package com.example.baseproject.model;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.io.Serializable;

public class App implements Serializable {
    private String mId;
    private String appName;
    private Drawable appIcon;
    private long appUsageTime;

    public App() {
    }

    public App(String mId, String appName, Drawable appIcon, long appUsageTime) {
        this.mId = mId;
        this.appName = appName;
        this.appIcon = appIcon;
        this.appUsageTime = appUsageTime;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public long getAppUsageTime() {
        return appUsageTime;
    }

    public void setAppUsageTime(long appUsageTime) {
        this.appUsageTime = appUsageTime;
    }

    public static DiffUtil.ItemCallback<App> diffCallback = new DiffUtil.ItemCallback<App>() {
        @Override
        public boolean areItemsTheSame(@NonNull App oldItem, @NonNull App newItem) {
            return oldItem.mId.equals(newItem.mId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull App oldItem, @NonNull App newItem) {
            return false;
        }
    };
}
