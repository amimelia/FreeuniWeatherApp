package weather.freeuni.com.freeuniweatherapp.Managers;

import android.content.Context;

/**
 * Created by melia on 7/1/2017.
 */

public class CitysManager {

    private static CitysManager _cityManager = null;
    public static CitysManager getInstance(){
        if (_cityManager == null)
            throw new RuntimeException("City Manager Needs to be initialized first");
        return _cityManager;
    }

    public static void initCityManager(Context context){
        _cityManager = new CitysManager(context);
    }

    public CitysManager(Context context){

    }

    /** @return  string of city ids we need to download current weather for
     *  ids are divided by ',' */
    public String getCurrentCityIds(){
        return "524901,703448,2643743";
    }

    public int getDefaultCityId(){
        return 611717;
    }






}
