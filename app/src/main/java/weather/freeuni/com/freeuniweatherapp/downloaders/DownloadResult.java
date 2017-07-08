package weather.freeuni.com.freeuniweatherapp.downloaders;

/**
 * Created by melia on 7/1/2017.
 */

public class DownloadResult {
    public static DownloadResult TOO_MANY_REQUESTS = new DownloadResult();
    public static DownloadResult DOWNLOAD_ERROR = new DownloadResult();

    public int countryID = 0;
    public Object downloadedData = null;
}
