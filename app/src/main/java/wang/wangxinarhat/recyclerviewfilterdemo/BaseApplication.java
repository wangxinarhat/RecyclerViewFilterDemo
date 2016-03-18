package wang.wangxinarhat.recyclerviewfilterdemo;

import android.app.Application;

/**
 * Created by wang on 2016/3/18.
 */
public class BaseApplication extends Application {


    private static BaseApplication mApplication;

    public static synchronized BaseApplication getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (null == mApplication) {
            mApplication = this;
        }
    }
}
