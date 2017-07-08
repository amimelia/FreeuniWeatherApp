package weather.freeuni.com.freeuniweatherapp.downloaders;

import com.google.gson.Gson;

import weather.freeuni.com.freeuniweatherapp.Managers.CitysManager;
import weather.freeuni.com.freeuniweatherapp.Managers.WeatherDataManager;
import weather.freeuni.com.freeuniweatherapp.models.current_weather.CurrentWeatherModel;
import weather.freeuni.com.freeuniweatherapp.models.daily5.Daily5WeatherModel;

/**
 * Created by melia on 7/1/2017.
 */

public class Daily5WeatherDownloader extends AbstractWeatherDownloader{

    public Daily5WeatherDownloader(IDownloadListener downloadListener){
        super(downloadListener);
    }

    @Override
    protected Object parseJsonResponce(String responce) {
        Gson gson = new Gson();
        Daily5WeatherModel model = gson.fromJson(responce, Daily5WeatherModel.class);
        return model;
    }

    @Override
    protected void appendUrlAction(StringBuilder ulrBuilder, String[] params) {
        ulrBuilder.append("forecast").append("?");
        ulrBuilder.append("id=").append(params[0]);
    }

    @Override
    protected void saveOutputInDataManager(DownloadResult output) {
        WeatherDataManager.getInstance().daily5WeatherDownloaded(output);
    }
}
