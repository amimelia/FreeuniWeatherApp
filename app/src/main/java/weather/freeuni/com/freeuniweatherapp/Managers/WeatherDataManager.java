package weather.freeuni.com.freeuniweatherapp.Managers;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import weather.freeuni.com.freeuniweatherapp.downloaders.CurrentWeatherDownloader;
import weather.freeuni.com.freeuniweatherapp.downloaders.Daily5WeatherDownloader;
import weather.freeuni.com.freeuniweatherapp.downloaders.DownloadResult;
import weather.freeuni.com.freeuniweatherapp.downloaders.IDownloadListener;
import weather.freeuni.com.freeuniweatherapp.models.daily5.Daily5WeatherModel;

/**
 * Created by melia on 7/1/2017.
 */

public class WeatherDataManager {

    private static WeatherDataManager _weatherManager = null;
    public static WeatherDataManager getInstance(){
        if (_weatherManager  == null)
            throw new RuntimeException("Weather Manager Needs to be initialized first");
        return _weatherManager ;
    }

    public static void initWeatherManager(Context context){
        _weatherManager  = new WeatherDataManager(context);
    }

    Map<Integer, Daily5WeatherModel> daily5WeatherDataMap = new HashMap<>();

    public WeatherDataManager(Context context){

    }

    public void getCurrentWeather(IDownloadListener downloadListener){
        CurrentWeatherDownloader currentWeatherDownloader = new CurrentWeatherDownloader(downloadListener);
        currentWeatherDownloader.execute();

    }

    public void downloadDaily5Weather(IDownloadListener listener, int cityId){
        Daily5WeatherDownloader downloader = new Daily5WeatherDownloader(listener);
        downloader.execute(String.valueOf(cityId));
    }

    public void daily5WeatherDownloaded(DownloadResult result){
        daily5WeatherDataMap.put(result.countryID, (Daily5WeatherModel) result.downloadedData);
    }

    public boolean isDaily5WeatherFor(int countryId){
        return daily5WeatherDataMap.containsKey(countryId);
    }

    public Daily5WeatherModel geDaily5WeatherModel(int countryId){
        return daily5WeatherDataMap.get(countryId);
    }
}
