package weather.freeuni.com.freeuniweatherapp.Managers;

import android.content.Context;
import android.icu.util.Output;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import weather.freeuni.com.freeuniweatherapp.models.Country;

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

    public List<Country> coutrysList = new ArrayList<>();
    public int defultCity = 611717;//Tbilisi

    public static void initCityManager(Context context){
        _cityManager = new CitysManager(context);
    }

    public CitysManager(Context context){
        try{
            InputStream is = context.getAssets().open("citys.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line = null;

            StringBuilder responseData = new StringBuilder();
            while((line = in.readLine()) != null) {
                Country c = new Country();
                String[] splited = line.split("\\s+");
                c.name = splited[1];
                c.id = Integer.valueOf(splited[0]);
                coutrysList.add(c);
            }
        }catch (Exception e){

        }

    }

    /** @return  string of city ids we need to download current weather for
     *  ids are divided by ',' */
    public String getCurrentCityIds(){
        return "524901,703448,2643743";
    }

    public int getDefaultCityId(){
        return defultCity;
    }


    public void setDefultCity(int defultCity) {
        this.defultCity = defultCity;
    }

    public String getDefaultCityName() {
        for (Country curCountry : coutrysList){
            if (curCountry.id == getDefaultCityId())
                return curCountry.name;
        }
        return "";
    }
}
