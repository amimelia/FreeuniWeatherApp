
package weather.freeuni.com.freeuniweatherapp.models.daily16;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Daily16WeatherModel {

    @SerializedName("cod")
    @Expose
    public String cod;
    @SerializedName("message")
    @Expose
    public Integer message;
    @SerializedName("city")
    @Expose
    public City city;
    @SerializedName("cnt")
    @Expose
    public Integer cnt;
    @SerializedName("list")
    @Expose
    public java.util.List<weather.freeuni.com.freeuniweatherapp.models.daily16.List> list = null;

}
