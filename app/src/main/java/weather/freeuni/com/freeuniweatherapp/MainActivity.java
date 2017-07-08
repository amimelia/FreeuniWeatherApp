package weather.freeuni.com.freeuniweatherapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import weather.freeuni.com.freeuniweatherapp.Managers.CitysManager;
import weather.freeuni.com.freeuniweatherapp.Managers.ManagersInitializer;
import weather.freeuni.com.freeuniweatherapp.Managers.WeatherDataManager;
import weather.freeuni.com.freeuniweatherapp.Utils.IconsDownlaoder;
import weather.freeuni.com.freeuniweatherapp.downloaders.DownloadResult;
import weather.freeuni.com.freeuniweatherapp.downloaders.IDownloadListener;
import weather.freeuni.com.freeuniweatherapp.models.Country;
import weather.freeuni.com.freeuniweatherapp.models.current_weather.CurrentWeatherModel;
import weather.freeuni.com.freeuniweatherapp.models.daily5.Daily5WeatherModel;
import weather.freeuni.com.freeuniweatherapp.models.daily5.Main;

public class MainActivity extends AppCompatActivity implements IDownloadListener,
        Daily5ForecastFragment.OnDaily5FragmentEventListener, ForecastByHoursFragment.OnByHoursFragmentInteractionListener{


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Daily5ForecastFragment daily5Fragment;
    private ForecastByHoursFragment forecastByHoursFragment;
    private ProgressDialog progressDialog;
    private String selectedDay = "";//selected day in list

    private TextView dayTempreture;
    private TextView dayDescription;
    private TextView dayWind;
    private TextView dayPressure;
    private TextView dayHumidity;
    private TextView daySunrise;
    private TextView daySunset;
    private ImageView dayWeatherIcon;
    private TextView lastUpdateDateTv;

    Daily5WeatherModel currentWeatherData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_day_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.one_day_toolbar);
        setSupportActionBar(toolbar);

        initializeManagers();
        initDisplayViews();
        testCurrentWeather();
    }

    private void initDisplayViews(){
        dayTempreture = (TextView) findViewById(R.id.day_temperature);
        dayDescription = (TextView) findViewById(R.id.day_description);
        dayWind = (TextView) findViewById(R.id.day_wind);
        dayPressure = (TextView) findViewById(R.id.day_pressure);
        dayHumidity = (TextView) findViewById(R.id.day_humidity);
        daySunrise = (TextView) findViewById(R.id.day_sunrise);
        daySunset = (TextView) findViewById(R.id.day_sunset);
        dayWeatherIcon = (ImageView) findViewById(R.id.day_icon);
        lastUpdateDateTv = (TextView) findViewById(R.id.data_last_update);
        initTabs();
        initProgressDialog();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Downloading Data");
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
    }

    private void initTabs(){
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        daily5Fragment = Daily5ForecastFragment.newInstance("", "");
        adapter.addFragment(daily5Fragment, getResources().getString(R.string.days5_forecast_tab_text));
        forecastByHoursFragment = ForecastByHoursFragment.newInstance("", "");
        adapter.addFragment(forecastByHoursFragment, getResources().getString(R.string.byhours_tab_text));
        viewPager.setAdapter(adapter);
    }


    private void initializeManagers(){
        ManagersInitializer.initializeManagers(this);
    }

    private void testCurrentWeather(){
        //openChooseDefaultCityPopup();
        displayDaily5WeatherData();
    }

    private void openChooseDefaultCityPopup(){
        final List<Country> countrysList = CitysManager.getInstance().coutrysList;

        CharSequence citys[] = new CharSequence[countrysList.size()];
        for (int i = 0; i < countrysList.size(); i++){
            citys[i] = countrysList.get(i).name;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a Default city");
        builder.setItems(citys, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CitysManager.getInstance().setDefultCity(countrysList.get(which).id);
                displayDaily5WeatherData();
            }
        });
        builder.show();
    }

    private void displayDaily5WeatherData(){
        int coutnryId = CitysManager.getInstance().getDefaultCityId();
        if (WeatherDataManager.getInstance().isDaily5WeatherFor(coutnryId)){
            currentWeatherData = WeatherDataManager.getInstance().geDaily5WeatherModel(coutnryId);
            showWeatherData();
        }

        WeatherDataManager.getInstance().downloadDaily5Weather(this, coutnryId);
    }

    private void showWeatherData(){
        Daily5WeatherModel data = currentWeatherData;
        if (!data.parsedDaily5WeatherData.dayDescByDate.containsKey(selectedDay))
            return;

        Main mainData = data.parsedDaily5WeatherData.dayDescByDate.get(selectedDay).weatherData.main;
        weather.freeuni.com.freeuniweatherapp.models.daily5.List mainLinst = data.parsedDaily5WeatherData.dayDescByDate.get(selectedDay).weatherData;
        dayTempreture.setText(mainData.temp.toString() + " °C");
        dayDescription.setText(mainLinst.weather.get(0).description);
        dayWind.setText("Wind: "+mainLinst.wind.speed.toString()+" m/s");
        dayPressure.setText("Pressure: " + mainData.pressure.toString() + " hpa");
        dayHumidity.setText("Humidity: "+mainData.humidity.toString()+" %");
        daySunset.setText("Min Tmp: "+ mainData.tempMin +" °C");
        daySunrise.setText("Max Tmp: "+ mainData.tempMax +" °C");
        lastUpdateDateTv.setText("Last Update: " + data.lastUpdateDate);
        getSupportActionBar().setTitle(CitysManager.getInstance().getDefaultCityName());
        IconsDownlaoder.fillWithIcon(this, dayWeatherIcon, mainLinst.weather.get(0).icon);
        updateInChildFragments(data);
    }

    private void updateInChildFragments(Daily5WeatherModel data){
        daily5Fragment.displayWeatherData(data, selectedDay);
        forecastByHoursFragment.displayWeatherData(data, selectedDay);
    }


    @Override
    public void downloadStarted() {
        progressDialog.show();
    }

    @Override
    public void downloadFinished(DownloadResult output) {
        progressDialog.dismiss();
        if (output == DownloadResult.TOO_MANY_REQUESTS || output == DownloadResult.DOWNLOAD_ERROR){
            Toast.makeText(this, "Download Finished With Error:( ", Toast.LENGTH_SHORT).show();
            return;
        }
        currentWeatherData = (Daily5WeatherModel)output.downloadedData;
        selectedDay = ((Daily5WeatherModel)output.downloadedData).firstDay;
        showWeatherData();
    }

    @Override
    public void onDaily5FragmentIteraction(){

    }

    @Override
    public void dayClickedToDisplay(String dayStr){
        selectedDay = dayStr;
        showWeatherData();
    }

    @Override
    public void onForecastByHoursFragmentInteraction(){

    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            WeatherDataManager.getInstance().downloadDaily5Weather(this, CitysManager.getInstance().getDefaultCityId());
            return true;
        }
        if (id == R.id.action_map) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_select_city) {
            openChooseDefaultCityPopup();
        }

        return super.onOptionsItemSelected(item);
    }
}
