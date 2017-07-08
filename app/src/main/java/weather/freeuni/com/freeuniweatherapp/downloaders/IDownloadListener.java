package weather.freeuni.com.freeuniweatherapp.downloaders;

/**
 * Created by melia on 7/1/2017.
 */

public interface IDownloadListener {
    void downloadStarted();
    void downloadFinished(DownloadResult output);
}
