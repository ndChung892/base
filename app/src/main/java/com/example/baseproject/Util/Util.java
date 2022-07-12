package com.example.baseproject.Util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.example.baseproject.R;

public class Util {
    //    @SuppressLint("UseCompatLoadingForDrawables")
    public static Drawable getAppIconByPackageName(@NonNull Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return context.getDrawable(R.drawable.no_image);
    }

    public static String getAppNameByPackageName(@NonNull Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            packageManager.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (applicationInfo == null) {
            return " ";
        } else {
            return (String) packageManager.getApplicationLabel(applicationInfo);
        }
    }


}
