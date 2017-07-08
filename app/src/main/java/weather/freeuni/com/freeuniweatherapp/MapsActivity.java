package weather.freeuni.com.freeuniweatherapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import weather.freeuni.com.freeuniweatherapp.Managers.CitysManager;
import weather.freeuni.com.freeuniweatherapp.Managers.WeatherDataManager;
import weather.freeuni.com.freeuniweatherapp.Utils.IconsDownlaoder;
import weather.freeuni.com.freeuniweatherapp.models.current_weather.CurrentWeatherModel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //add markers
        addMarkers();

    }

    public void addMarkers(){
        Map<Integer, CurrentWeatherModel> currentWeatherDataMap = WeatherDataManager.getInstance().getCurrentWeatherDataMap();
        Map<Integer, CurrentWeatherModel> weatherDataCopy = new HashMap<>(currentWeatherDataMap);
        LatLng defaultCity = null;

        for (Integer cityId : weatherDataCopy.keySet()){
            CurrentWeatherModel curModel = weatherDataCopy.get(cityId);
            LatLng curLat = new LatLng(curModel.list.get(0).coord.lat, curModel.list.get(0).coord.lon);
            mMap.addMarker(new MarkerOptions().position(curLat).title("" + curModel.list.get(0).name).
                    icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(curModel))));
            if (defaultCity == null || curModel.list.get(0).id == CitysManager.getInstance().getDefaultCityId()){
                defaultCity = curLat;
            }
        }


        if (weatherDataCopy.keySet().size() != 0){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultCity));
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(7);
            mMap.animateCamera(zoom);
        }

    }

    private Bitmap getMarkerBitmapFromView(CurrentWeatherModel curModel) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        TextView markerTextView = (TextView) customMarkerView.findViewById(R.id.marker_text);
        IconsDownlaoder.fillWithIcon(this, markerImageView, curModel.list.get(0).weather.get(0).icon);
        markerTextView.setText(curModel.list.get(0).main.temp + " °C");

        //markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }
}
