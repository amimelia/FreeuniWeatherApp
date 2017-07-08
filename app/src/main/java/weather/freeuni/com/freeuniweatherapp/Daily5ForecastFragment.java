package weather.freeuni.com.freeuniweatherapp;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import weather.freeuni.com.freeuniweatherapp.Utils.IconsDownlaoder;
import weather.freeuni.com.freeuniweatherapp.models.ParsedDaily5Model;
import weather.freeuni.com.freeuniweatherapp.models.daily5.Daily5WeatherModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Daily5ForecastFragment.OnDaily5FragmentEventListener} interface
 * to handle interaction events.
 * Use the {@link Daily5ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Daily5ForecastFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnDaily5FragmentEventListener mListener;

    public Daily5ForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Daily5ForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Daily5ForecastFragment newInstance(String param1, String param2) {
        Daily5ForecastFragment fragment = new Daily5ForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View curView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        curView = inflater.inflate(R.layout.daily5_fragment_layout, container, false);
        if (runLater){
            runLater = false;
            displayWeatherData(data, selectedIndx);
        }
        return curView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDaily5FragmentIteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDaily5FragmentEventListener) {
            mListener = (OnDaily5FragmentEventListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDaily5FragmentEventListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if (runLater){
            runLater = false;
            displayWeatherData(data, selectedIndx);
        }
    }

    Daily5WeatherModel data;
    String selectedIndx;
    boolean runLater = false;

    public void displayWeatherData(Daily5WeatherModel data, String selectedIndx){
        if (curView == null)
        {
            runLater = true;
            this.data = data;
            this.selectedIndx = selectedIndx;
            return;
        }
        LinearLayout ll =  (LinearLayout) getView().findViewById(R.id.single_days_holder);
        final int childCount = ll.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = ll.getChildAt(i);

            int j = 0; String ithDayStr = "";
            ArrayList<String> daysList =  new ArrayList<>(data.parsedDaily5WeatherData.dayDescByDate.keySet());
            java.util.Collections.sort(daysList);
            for(String dayStr : daysList){
                //if (dayStr.equals(selectedIndx))
                   // continue;
                if (i == j){
                    ithDayStr = dayStr;
                    break;
                }
                j++;
            }
            final String dayDsc = ithDayStr;
            ParsedDaily5Model.DayDescription curDay =  data.parsedDaily5WeatherData.dayDescByDate.get(ithDayStr);

            TextView dayName = (TextView) v.findViewById(R.id.day_name);
            ImageView dayIcon = (ImageView) v.findViewById(R.id.day_weather_icon);
            TextView dayTempreture = (TextView) v.findViewById(R.id.tempreture);

            dayName.setText(curDay.dayText);
            IconsDownlaoder.fillWithIcon(getContext(), dayIcon, curDay.weatherData.weather.get(0).icon);
            dayTempreture.setText(curDay.weatherData.main.temp.toString() + " °C");

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dayClicked(dayDsc);
                }
            });
        }

    }

    private void dayClicked(String dayDsc) {
        mListener.dayClickedToDisplay(dayDsc);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDaily5FragmentEventListener {
        void onDaily5FragmentIteraction();
        void dayClickedToDisplay(String dayStr);
    }
}
