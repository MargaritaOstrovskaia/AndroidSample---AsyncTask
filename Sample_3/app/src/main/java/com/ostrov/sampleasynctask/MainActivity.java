package com.ostrov.sampleasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /** URL for earthquake data from the USGS dataset */
    private static final String REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.title));

        mAdapter = new EarthquakeAdapter(MainActivity.this, new ArrayList<Earthquake>());

        ListView lvEarthquake = findViewById(R.id.list);
        lvEarthquake.setAdapter(mAdapter);
        lvEarthquake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Earthquake item = mAdapter.getItem(pos);
                if (item != null) {
                    Uri uri = Uri.parse(item.getUrl());

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        });

        // Start the AsyncTask to fetch the earthquake data
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(REQUEST_URL);
    }

    /**
     * {@link AsyncTask} to perform the network request on a background thread,
     * and then update the UI with the list of earthquakes in the response.
     *
     * AsyncTask has three generic parameters: the input type, a type used for progress updates,
     * and an output type. Our task will take a String URL, and return an Earthquake.
     * We won't do progress updates, so the second generic is just Void.
     *
     * We'll only override two of the methods of AsyncTask: doInBackground() and onPostExecute().
     * The doInBackground() method runs on a background thread,
     * so it can run long-running code (like network activity),
     * without interfering with the responsiveness of the app.
     * Then onPostExecute() is passed the result of doInBackground() method,
     * but runs on the UI thread, so it can use the produced data to update the UI.
     */
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread,
         * so we return a list of {@link Earthquake}s as the result.
         */
        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null)
                return null;
            return QueryUtils.fetchEarthquakeData(urls[0]);
        }

        /**
         * This method runs on the main UI thread after the background work has been completed.
         * This method receives as input, the return value from the doInBackground() method.
         * First we clear out the adapter, to get rid of earthquake data from a previous query to USGS.
         * Then we update the adapter with the new list of earthquakes,
         * which will trigger the ListView to re-populate its list items.
         */
        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {
            mAdapter.clear();
            if (earthquakes != null && !earthquakes.isEmpty())
                mAdapter.addAll(earthquakes);
        }
    }
}
