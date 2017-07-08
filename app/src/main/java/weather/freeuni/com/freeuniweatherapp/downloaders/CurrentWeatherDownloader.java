package weather.freeuni.com.freeuniweatherapp.downloaders;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import weather.freeuni.com.freeuniweatherapp.Managers.CitysManager;
import weather.freeuni.com.freeuniweatherapp.Managers.WeatherDataManager;
import weather.freeuni.com.freeuniweatherapp.models.current_weather.CurrentWeatherModel;

/**
 * Created by melia on 7/1/2017.
 */

public class CurrentWeatherDownloader extends AbstractWeatherDownloader{

    public CurrentWeatherDownloader(IDownloadListener listener){
        super(listener);
    }

    @Override
    protected Object parseJsonResponce(String responce) {
        Gson gson = new Gson();
        CurrentWeatherModel model = gson.fromJson(responce, CurrentWeatherModel.class);
        return model;
    }

    @Override
    protected void appendUrlAction(StringBuilder ulrBuilder, String[] params) {
        ulrBuilder.append("group").append("?");
        ulrBuilder.append("id=").append(params[0]);
    }

    @Override
    protected void saveOutputInDataManager(DownloadResult output) {
        WeatherDataManager.getInstance().currentWeatherDownloaded(output);
    }
}
