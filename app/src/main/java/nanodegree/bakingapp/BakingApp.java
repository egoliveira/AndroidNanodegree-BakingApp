package nanodegree.bakingapp;

import android.app.Application;

public class BakingApp extends Application {
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public static synchronized Application getInstance() {
        return sInstance;
    }
}
