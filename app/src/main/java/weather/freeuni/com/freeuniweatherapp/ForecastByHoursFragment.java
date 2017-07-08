package weather.freeuni.com.freeuniweatherapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import weather.freeuni.com.freeuniweatherapp.Utils.GetWeekDayForDateStr;
import weather.freeuni.com.freeuniweatherapp.adapters.ByHoursDataAdapter;
import weather.freeuni.com.freeuniweatherapp.models.ParsedDaily5Model;
import weather.freeuni.com.freeuniweatherapp.models.daily5.Daily5WeatherModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForecastByHoursFragment.OnByHoursFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForecastByHoursFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastByHoursFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<weather.freeuni.com.freeuniweatherapp.models.daily5.List> weatherDataForCurDate  = new ArrayList<>();
    private OnByHoursFragmentInteractionListener mListener;

    public ForecastByHoursFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForecastByHoursFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForecastByHoursFragment newInstance(String param1, String param2) {
        ForecastByHoursFragment fragment = new ForecastByHoursFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.by_hours_display_layout, container, false);
        initRecViewver(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onForecastByHoursFragmentInteraction();
        }
    }

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    ByHoursDataAdapter mAdapter;

    private void initRecViewver(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.by_hours_recyle_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(App.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ByHoursDataAdapter(weatherDataForCurDate);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnByHoursFragmentInteractionListener) {
            mListener = (OnByHoursFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnByHoursFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void displayWeatherData(Daily5WeatherModel data, String selectedIndx){
        List<weather.freeuni.com.freeuniweatherapp.models.daily5.List> weatherDataForCurDate = new ArrayList<>();
        for(weather.freeuni.com.freeuniweatherapp.models.daily5.List listItm : data.list){

            String dateTxt = listItm.dtTxt.substring(0, 10);

            if (dateTxt.equals(selectedIndx)){
                weatherDataForCurDate.add(listItm);
                //default time to take some features
            }
        }
        setWeatherDataForCurDate(weatherDataForCurDate);
    }

    private void setWeatherDataForCurDate(List<weather.freeuni.com.freeuniweatherapp.models.daily5.List> weatherDataForCurDate){
        this.weatherDataForCurDate.clear();
        this.weatherDataForCurDate.addAll(weatherDataForCurDate);
        mAdapter.notifyDataSetChanged();
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
    public interface OnByHoursFragmentInteractionListener {
        // TODO: Update argument type and name
        void onForecastByHoursFragmentInteraction();
    }
}
