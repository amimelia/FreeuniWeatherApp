package weather.freeuni.com.freeuniweatherapp.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by melia on 7/2/2017.
 */

public class IconsDownlaoder {
    public static void fillWithIcon(Context context, ImageView imgView, String icon){
        String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

        Picasso.with(context).load(iconUrl).into(imgView);
    }
}
