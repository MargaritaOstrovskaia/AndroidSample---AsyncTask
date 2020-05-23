package com.ostrov.sampleasynctask;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private static final String LOCATION_SEPARATOR = " of ";

    EarthquakeAdapter(@NonNull Context context, @NonNull ArrayList<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        Earthquake earthquake = getItem(position);
        if (earthquake != null) {
            // format the magnitude to show 1 decimal place
            String magnitude = formatMagnitude(earthquake.getMagnitude());
            TextView tvMagnitude = view.findViewById(R.id.item_magnitude);
            tvMagnitude.setText(magnitude);

            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            GradientDrawable magnitudeCircle = (GradientDrawable) tvMagnitude.getBackground();

            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());

            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);

            // set location value
            String locationOffset;
            String primaryLocation;
            String location = earthquake.getLocation();
            if (location.contains(LOCATION_SEPARATOR)) {
                String[] parts = location.split(LOCATION_SEPARATOR);
                locationOffset = parts[0] + LOCATION_SEPARATOR;
                primaryLocation = parts[1];
            } else {
                locationOffset = getContext().getString(R.string.near_the);
                primaryLocation = location;
            }

            TextView tvLocationOffset = view.findViewById(R.id.location_offset);
            tvLocationOffset.setText(locationOffset);

            TextView tvPrimaryLocation = view.findViewById(R.id.primary_location);
            tvPrimaryLocation.setText(primaryLocation);

            // Create a new Date object from the time in milliseconds of the earthquake
            Date date = new Date(earthquake.getTime());

            // Format the date string (i.e. "Mar 3, 1984")
            String dateToDisplay = formatDate(date);
            TextView tvDate = view.findViewById(R.id.item_date);
            tvDate.setText(dateToDisplay);

            // Format the time string (i.e. "4:30PM")
            String timeToDisplay = formatTime(date);
            TextView tvTime = view.findViewById(R.id.item_time);
            tvTime.setText(timeToDisplay);
        }

        return view;
    }

    /**
     * Return the color for the magnitude circle based on the intensity of the earthquake.
     * @param magnitude of the earthquake
     */
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat =
                new DecimalFormat(getContext().getString(R.string.decimal_format));
        return magnitudeFormat.format(magnitude);
    }

    /** Return the formatted date string (i.e. "Mar 3, 1984") from a Date object. */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(getContext().getString(R.string.date_format));
        return dateFormat.format(dateObject);
    }

    /** Return the formatted date string (i.e. "4:30 PM") from a Date object. */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat =
                new SimpleDateFormat(getContext().getString(R.string.time_format));
        return timeFormat.format(dateObject);
    }
}