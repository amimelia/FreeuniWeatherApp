package weather.freeuni.com.freeuniweatherapp.Managers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import weather.freeuni.com.freeuniweatherapp.Utils.GetWeekDayForDateStr;
import weather.freeuni.com.freeuniweatherapp.downloaders.CurrentWeatherDownloader;
import weather.freeuni.com.freeuniweatherapp.downloaders.Daily5WeatherDownloader;
import weather.freeuni.com.freeuniweatherapp.downloaders.DownloadResult;
import weather.freeuni.com.freeuniweatherapp.downloaders.IDownloadListener;
import weather.freeuni.com.freeuniweatherapp.models.Country;
import weather.freeuni.com.freeuniweatherapp.models.ParsedDaily5Model;
import weather.freeuni.com.freeuniweatherapp.models.current_weather.CurrentWeatherModel;
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
    Map<Integer, CurrentWeatherModel> currentWeatherDataMap = new HashMap<>();

    public WeatherDataManager(Context context){
        startCurrentWeatherDownloader();
    }

    public void getCurrentWeather(IDownloadListener downloadListener){
        CurrentWeatherDownloader currentWeatherDownloader = new CurrentWeatherDownloader(downloadListener);
        currentWeatherDownloader.execute();

    }

    public Map<Integer, CurrentWeatherModel> getCurrentWeatherDataMap(){
        return currentWeatherDataMap;
    }

    private void startCurrentWeatherDownloader(){
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runCurrentDataDownload();
                        }
                    }).start();
                }
                catch (Exception e) {

                }
                finally{
                    handler.postDelayed(this, 3600 * 1000);//run in every 1 hour to update current data
                }
            }
        };
        runnable.run();
    }

    private void runCurrentDataDownload(){
        try{
            List<Country> countryList = CitysManager.getInstance().coutrysList;
            for(Country c : countryList){
                CurrentWeatherDownloader downloader = new CurrentWeatherDownloader(null);
                downloader.execute("" + c.id);
                Thread.sleep(3000);
            }
        }catch(Exception e ){

        }

    }

    public void currentWeatherDownloaded(DownloadResult result){
        currentWeatherDataMap.put(result.countryID, (CurrentWeatherModel)result.downloadedData);
    }

    public void downloadDaily5Weather(IDownloadListener listener, int cityId){
        Daily5WeatherDownloader downloader = new Daily5WeatherDownloader(listener);
        downloader.execute(String.valueOf(cityId));
    }

    public void daily5WeatherDownloaded(DownloadResult result){
        Daily5WeatherModel downloadResult = (Daily5WeatherModel) result.downloadedData;
        getParsedDaily5DataModel(downloadResult);
        daily5WeatherDataMap.put(result.countryID, downloadResult);
    }

    private ParsedDaily5Model getParsedDaily5DataModel(Daily5WeatherModel downloadedData) {
        ParsedDaily5Model resultModel = new ParsedDaily5Model();

        for(weather.freeuni.com.freeuniweatherapp.models.daily5.List listItm : downloadedData.list){

            String dateTxt = listItm.dtTxt.substring(0, 10);
            if (downloadedData.firstDay == ""){
                downloadedData.firstDay  = dateTxt;
            }
            if (listItm.dtTxt.equals(dateTxt + " 15:00:00") || downloadedData.list.get(0) == listItm){
                String weekDay = GetWeekDayForDateStr.getWeekDay(dateTxt);
                ParsedDaily5Model.DayDescription desc = new ParsedDaily5Model.DayDescription();
                desc.dayText = weekDay;
                desc.weatherData = listItm;
                resultModel.dayDescByDate.put(dateTxt, desc);
                //default time to take some features
            }


        }
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        downloadedData.lastUpdateDate = timeStamp;
        downloadedData.parsedDaily5WeatherData = resultModel;
        return resultModel;
    }

    public boolean isDaily5WeatherFor(int countryId){
        return daily5WeatherDataMap.containsKey(countryId);
    }

    public Daily5WeatherModel geDaily5WeatherModel(int countryId){
        return daily5WeatherDataMap.get(countryId);
    }
}
