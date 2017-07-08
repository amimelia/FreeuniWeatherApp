package weather.freeuni.com.freeuniweatherapp.downloaders;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.util.StringBuilderPrinter;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import weather.freeuni.com.freeuniweatherapp.App;
import weather.freeuni.com.freeuniweatherapp.Managers.CitysManager;
import weather.freeuni.com.freeuniweatherapp.R;

/**
 * Created by melia on 7/1/2017.
 */

public abstract class AbstractWeatherDownloader extends AsyncTask<String, String, DownloadResult> {

    IDownloadListener downloadListener;

    public AbstractWeatherDownloader(IDownloadListener downloadListener){
        this.downloadListener =  downloadListener;
    }

    @Override
    protected void onPreExecute() {
        if (downloadListener != null)
            downloadListener.downloadStarted();
    }

    @Override
    protected DownloadResult doInBackground(String... params) {
        DownloadResult output = new DownloadResult();

        if (params.length > 0){
            output.countryID = Integer.valueOf(params[0]);
        }

        String response = "";
            try {
                URL url = getDownloadUrl(params);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                    BufferedReader r = new BufferedReader(inputStreamReader);

                    int responseCode = urlConnection.getResponseCode();
                    String line = null;
                    while ((line = r.readLine()) != null) {
                        response += line + "\n";
                    }
                    r.close();
                    urlConnection.disconnect();
                }
                else if (urlConnection.getResponseCode() == 429) {
                    return DownloadResult.TOO_MANY_REQUESTS;
                }
                else {
                    return DownloadResult.DOWNLOAD_ERROR;
                }
            } catch (Exception e) {
                return DownloadResult.DOWNLOAD_ERROR;
            }

        output.downloadedData = parseJsonResponce(response);
        return output;
    }

    protected URL getDownloadUrl(String[] coords) throws UnsupportedEncodingException, MalformedURLException{
        StringBuilder urlBuilder = new StringBuilder("http://api.openweathermap.org/data/2.5/");
        String apiKey = App.getContext().getResources().getString(R.string.openWeatherAppKey);
        appendUrlAction(urlBuilder, coords);
        urlBuilder.append("&units=").append("metric");
        urlBuilder.append("&mode=json");
        urlBuilder.append("&appid=").append(apiKey);
        return new URL(urlBuilder.toString());
    }



    @Override
    protected void onPostExecute(DownloadResult output) {

        
        if(output != DownloadResult.DOWNLOAD_ERROR && output != DownloadResult.TOO_MANY_REQUESTS)
            saveOutputInDataManager(output);

        if (downloadListener != null)downloadListener.downloadFinished(output);
    }

    protected abstract Object parseJsonResponce(String responce);
    protected abstract void appendUrlAction(StringBuilder ulrBuilder, String[] params);
    protected abstract void saveOutputInDataManager(DownloadResult output);
}
