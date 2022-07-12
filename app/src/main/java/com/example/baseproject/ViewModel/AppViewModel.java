package com.example.baseproject.ViewModel;

import static android.content.Context.USAGE_STATS_SERVICE;

import android.app.Application;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.baseproject.Model.App;
import com.example.baseproject.Util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppViewModel extends ViewModel {
    private Application mApplication;
    private MutableLiveData<List<App>> mListAppLiveData = new MutableLiveData<>();
    private MutableLiveData<App> longestUsageTimeApp = new MutableLiveData<>();
    private List<App> mListApp;


    private MutableLiveData<Boolean> isLoadingAppList = new MutableLiveData<>(false);
    public AppViewModel(Application mApplication) {
    }

    public void getAppList() {
        new Thread(() -> {
            isLoadingAppList.postValue(true);

            List<App> apps = initData();
            mListAppLiveData.postValue(apps);

            isLoadingAppList.postValue(false);

        }).start();
    }

    private List<App> initData() {
        List<App> mListApp = new ArrayList<>();
        UsageStatsManager usm = (UsageStatsManager) mApplication.getSystemService(USAGE_STATS_SERVICE);
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                System.currentTimeMillis() - 1000 * 3600 * 24,
                System.currentTimeMillis());
        if (appList.size() > 0) {
            for (UsageStats usageStats : appList) {
                if (usageStats.getTotalTimeInForeground() > 0) {
                    String packageName = mApplication.getPackageName();

                    String appName = Util.getAppNameByPackageName(mApplication, packageName);
                    Drawable appIcon = Util.getAppIconByPackageName(mApplication, packageName);
                    String appDuration = getDurationBreakdown(usageStats.getTotalTimeInForeground());
                    App a = new App(appIcon, appName, appDuration);
                    mListApp.add(a);
                }
            }
        }
        return mListApp;
    }
    private String getDurationBreakdown(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        return (hours + " h " +  minutes + " m " + seconds + " s");
    }


    public MutableLiveData<List<App>> getListAppLiveData() {
        return mListAppLiveData;
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


            //asudhkkuyhiohqerkjhqioumkjnbasfuioyqjkbreqwiohckjxbiuq
        }
    }
}
