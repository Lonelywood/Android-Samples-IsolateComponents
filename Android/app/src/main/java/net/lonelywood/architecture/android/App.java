package net.lonelywood.architecture.android;

import android.app.Application;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        instance = this;
    }

    public static App getInstance(){
        return instance;
    }
}