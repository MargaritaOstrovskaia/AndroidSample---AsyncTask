package com.ostrov.sampleasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Displays the perceived strength of a single earthquake event based on responses
 * from people who felt the earthquake.
 */
public class MainActivity extends AppCompatActivity {
    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.title));

        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }

    /**
     * {@link AsyncTask} to perform the network request on a background thread,
     * and then update the UI with the first earthquake in the response.
     */
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, Event> {
        /**
         * This method is invoked (or called) on a background thread,
         * so we can perform long-running operations like making a network request.
         *
         * It is NOT okay to update the UI from a background thread,
         * so we just return an {@link Event} object as the result.
         */
        @Override
        protected Event doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null)
                return null;

            // Perform the HTTP request for earthquake data and process the response.
            return Utils.fetchEarthquakeData(urls[0]);
        }

        /**
         * This method is invoked on the main UI thread after the background work has been completed.
         *
         * It IS okay to modify the UI within this method. We take the {@link Event} object
         * (which was returned from the doInBackground() method) and update the views on the screen.
         */
        @Override
        protected void onPostExecute(Event event) {
            // If there is no result, do nothing.
            if (event == null)
                return;

            // Update the information displayed to the user.
            updateUi(event);
        }
    }

    /** Update the UI with the given earthquake information. */
    private void updateUi(Event earthquake) {
        TextView titleTextView = findViewById(R.id.title);
        titleTextView.setText(earthquake.getTitle());

        TextView tsunamiTextView = findViewById(R.id.number_of_people);
        tsunamiTextView.setText(getString(R.string.num_people_felt_it, earthquake.getNumOfPeople()));

        TextView magnitudeTextView = findViewById(R.id.perceived_magnitude);
        magnitudeTextView.setText(earthquake.getPerceivedStrength());
    }
}
