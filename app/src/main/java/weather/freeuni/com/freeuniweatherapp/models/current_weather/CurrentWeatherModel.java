
package weather.freeuni.com.freeuniweatherapp.models.current_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentWeatherModel {

    @SerializedName("cnt")
    @Expose
    public Integer cnt;
    @SerializedName("list")
    @Expose
    public java.util.List<weather.freeuni.com.freeuniweatherapp.models.current_weather.List> list = null;

}
