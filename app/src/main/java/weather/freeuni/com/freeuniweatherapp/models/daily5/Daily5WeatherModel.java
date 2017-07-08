
package weather.freeuni.com.freeuniweatherapp.models.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import weather.freeuni.com.freeuniweatherapp.models.ParsedDaily5Model;

public class Daily5WeatherModel {

    @SerializedName("cod")
    @Expose
    public String cod;
    @SerializedName("message")
    @Expose
    public Double message;
    @SerializedName("cnt")
    @Expose
    public Integer cnt;
    @SerializedName("list")
    @Expose
    public java.util.List<weather.freeuni.com.freeuniweatherapp.models.daily5.List> list = null;
    @SerializedName("city")
    @Expose
    public City city;

    public String lastUpdateDate;
    public ParsedDaily5Model parsedDaily5WeatherData = new ParsedDaily5Model();
    public String firstDay = "";
}
