package weather.freeuni.com.freeuniweatherapp.Managers;

import android.content.Context;

/**
 * Created by melia on 7/1/2017.
 */

public class ManagersInitializer {

    public static void initializeManagers(Context context){
        CitysManager.initCityManager(context);
        WeatherDataManager.initWeatherManager(context);
    }


}
