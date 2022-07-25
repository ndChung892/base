package com.example.baseproject.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.baseproject.util.Util;
import com.example.baseproject.model.App;

import java.util.ArrayList;
import java.util.List;

public class AppViewModel extends ViewModel {
    private static final String USAGE_STATS_SERVICE = "usagestats";
    private Application mApplication;
    private MutableLiveData<List<App>> mAppList = new MutableLiveData<>();
    private MutableLiveData<App> mLongestUsageTime= new MutableLiveData<>();

    public AppViewModel(Application application) {
        mApplication = application;
    }

    public LiveData<List<App>> getAppList() {
        return mAppList;
    }

    public LiveData<App> getLongestUsageTime() {
        return mLongestUsageTime;
    }


    public void requestAppList() {
        new Thread(() -> {
            List<App> apps = getApps();
            mAppList.postValue(apps);
            App longestUsageTime = getLongestUsageTime(apps);
            mLongestUsageTime.postValue(longestUsageTime);
        }).start();
    }
    @NonNull
    private List<App> getApps() {
        List<App> apps = new ArrayList<>();
        @SuppressLint("WrongConstant")
        UsageStatsManager usm = (UsageStatsManager) mApplication
                .getSystemService(USAGE_STATS_SERVICE);
        List<UsageStats> usageStatsList = usm.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                System.currentTimeMillis() - (1000 * 3600   * 24),
                System.currentTimeMillis()
        );
        for (UsageStats usageStats : usageStatsList) {
            if(usageStats.getTotalTimeInForeground() > 0) {
                String packageName = usageStats.getPackageName();
                String appName = Util.getAppNameByPackageName(mApplication, packageName);
                Drawable appIcon = Util.getAppIconByPackageName(mApplication, packageName);
                long appUsageTime = usageStats.getTotalTimeInForeground();
                App a = new App( packageName, appName, appIcon, appUsageTime);
                apps.add(a);
            }
        }
        return apps;
    }
    private App getLongestUsageTime(List<App> apps) {

        if (apps == null || apps.isEmpty()) {
            return null;
        }

        App longestUsageTime = apps.get(0);
        for (App App : apps) {
            if (App.getAppUsageTime() > longestUsageTime.getAppUsageTime()) {
                longestUsageTime = App;
            }
        }
        return longestUsageTime;
    }
    public static class MainViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public MainViewModelFactory(Application application) {
            mApplication = application;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(AppViewModel.class)) {
                return (T) new AppViewModel(mApplication);
            }
            throw new IllegalStateException("unEnabled constructor");
        }
    }

}