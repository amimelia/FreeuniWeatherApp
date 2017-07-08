package weather.freeuni.com.freeuniweatherapp.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by melia on 7/5/2017.
 */

public class ParsedDaily5Model {
    public Map<String, DayDescription>  dayDescByDate = new HashMap<>();

    public static class DayDescription{
        public String dayText;
        public weather.freeuni.com.freeuniweatherapp.models.daily5.List weatherData;
    }
}
