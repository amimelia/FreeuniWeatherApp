
package weather.freeuni.com.freeuniweatherapp.models.daily16;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("geoname_id")
    @Expose
    public Integer geonameId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("lon")
    @Expose
    public Double lon;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("iso2")
    @Expose
    public String iso2;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("population")
    @Expose
    public Integer population;

}
