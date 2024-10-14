package com.example.terremotoinforme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder> {

    private ArrayList<Earthquake> earthquakeList;

    public EarthquakeAdapter(ArrayList<Earthquake> earthquakeList) {
        this.earthquakeList = earthquakeList;
    }

    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.earthquake_item, parent, false);
        return new EarthquakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {
        Earthquake earthquake = earthquakeList.get(position);

        holder.magnitudeTextView.setText(String.valueOf(earthquake.getMagnitude()));
        holder.placeTextView.setText(earthquake.getPlace());

        // Formatear la fecha
        Date dateObject = new Date(earthquake.getTime());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("LLL dd, yyyy", Locale.getDefault());
        holder.dateTextView.setText(dateFormatter.format(dateObject));
    }

    @Override
    public int getItemCount() {
        return earthquakeList.size();
    }

    public static class EarthquakeViewHolder extends RecyclerView.ViewHolder {
        public TextView magnitudeTextView;
        public TextView placeTextView;
        public TextView dateTextView;

        public EarthquakeViewHolder(View itemView) {
            super(itemView);
            magnitudeTextView = itemView.findViewById(R.id.magnitudeTextView);
            placeTextView = itemView.findViewById(R.id.placeTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
