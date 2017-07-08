package weather.freeuni.com.freeuniweatherapp.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by melia on 7/5/2017.
 */

public class GetWeekDayForDateStr {

    public static String getWeekDay(String date){
        try{
            String input_date=date;
            SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
            Date dt1= format1.parse(input_date);
            DateFormat format2=new SimpleDateFormat("EE");
            String finalDay = format2.format(dt1);
            return finalDay;
        }catch (Exception e){
            e.printStackTrace();
            Log.e("GetWeekDayForDateStr", "Error m=" + e);
            return "";
        }
    }
}
