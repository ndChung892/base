package com.example.baseproject.Model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class App implements Serializable {
    private Drawable iconApp;
    private String nameApp;
    private String usageDuration;

    public App() {
    }

    public App(Drawable iconApp, String nameApp, String usageDuration) {
        this.iconApp = iconApp;
        this.nameApp = nameApp;
        this.usageDuration = usageDuration;
    }

    public Drawable getIconApp() {
        return iconApp;
    }

    public void setIconApp(Drawable iconApp) {
        this.iconApp = iconApp;
    }

    public String getNameApp() {
        return nameApp;
    }

    public void setNameApp(String nameApp) {
        this.nameApp = nameApp;
    }

    public String getUsageDuration() {
        return usageDuration;
    }

    public void setUsageDuration(String usageDuration) {
        this.usageDuration = usageDuration;
    }
}
