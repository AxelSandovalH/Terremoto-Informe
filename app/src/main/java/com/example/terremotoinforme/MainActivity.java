package com.example.terremotoinforme;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EarthquakeAdapter earthquakeAdapter;
    private ArrayList<Earthquake> earthquakeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        earthquakeAdapter = new EarthquakeAdapter(earthquakeList);
        recyclerView.setAdapter(earthquakeAdapter);

        fetchEarthquakeData();
    }

    private void fetchEarthquakeData() {
        String url = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("MainActivity", "Error fetching earthquake data", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    try {
                        parseEarthquakeData(jsonResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void parseEarthquakeData(String jsonResponse) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray featuresArray = jsonObject.getJSONArray("features");

        for (int i = 0; i < featuresArray.length(); i++) {
            JSONObject featureObject = featuresArray.getJSONObject(i);
            JSONObject properties = featureObject.getJSONObject("properties");
            double magnitude = properties.getDouble("mag");
            String place = properties.getString("place");
            long time = properties.getLong("time");

            Earthquake earthquake = new Earthquake(magnitude, place, time);
            earthquakeList.add(earthquake);
        }

        runOnUiThread(() -> earthquakeAdapter.notifyDataSetChanged());
    }
}

