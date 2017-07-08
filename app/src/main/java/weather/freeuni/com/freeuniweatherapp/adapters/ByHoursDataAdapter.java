package weather.freeuni.com.freeuniweatherapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import weather.freeuni.com.freeuniweatherapp.App;
import weather.freeuni.com.freeuniweatherapp.R;
import weather.freeuni.com.freeuniweatherapp.Utils.IconsDownlaoder;
import weather.freeuni.com.freeuniweatherapp.models.daily5.List;

/**
 * Created by melia on 7/5/2017.
 */

public class ByHoursDataAdapter extends RecyclerView.Adapter<ByHoursDataAdapter.ViewHolder> {

    private ArrayList<List> mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mHoursTextView;
        public TextView mTempTextView;
        public ImageView mWeatherIconImageView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            mHoursTextView = (TextView) v.findViewById(R.id.hour_text);
            mTempTextView = (TextView) v.findViewById(R.id.tempreture);
            mWeatherIconImageView = (ImageView) v.findViewById(R.id.weather_icon);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ByHoursDataAdapter(ArrayList<List> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ByHoursDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.by_hours_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List curItem =  mDataset.get(position);
        holder.mHoursTextView.setText(curItem.dtTxt.substring(11, 16));
        IconsDownlaoder.fillWithIcon(App.getContext(), holder.mWeatherIconImageView, curItem.weather.get(0).icon);
        holder.mTempTextView.setText(curItem.main.temp + " °C");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}