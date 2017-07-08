package weather.freeuni.com.freeuniweatherapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by melia on 7/1/2017.
 */

public class App extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}