package com.android.alex.fppg.application;

import android.app.Application;

import com.android.alex.fppg.network.ApiManager;
import com.android.alex.fppg.network.FppgService;

/**
 * Created by Alex on 25-Jan-17.
 */

public class FppgApp extends Application {

    private static FppgApp fppgApp;
    private static ApiManager apiManager;

    @Override
    public void onCreate() {
        super.onCreate();
        fppgApp = this;
        apiManager = FppgService.getClient().create(ApiManager.class);
    }

    public static FppgApp getInstance() {
        return fppgApp;
    }

    public static ApiManager getApiManager() {
        return apiManager;
    }
}
