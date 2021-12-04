 package com.example.earthquake;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthAdapter extends ArrayAdapter<earthQuake> {
    private static final String LOCATION_SEPARATOR= "of";

    public EarthAdapter (Activity context,ArrayList<earthQuake> EarthQuakes) {
        super(context, 0,EarthQuakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earth, parent, false);
        }

        earthQuake currentEvent = (earthQuake) getItem(position);


        String originalLocation  = currentEvent.getPlacesName();
        String primaryLocation ;
        String locationOffset;
        if (originalLocation.contains(LOCATION_SEPARATOR)){
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            primaryLocation = parts[1];
            locationOffset = parts[0]+ LOCATION_SEPARATOR;
        }
        else {
            primaryLocation= originalLocation;
            locationOffset = getContext().getString(R.string.near_the);
        }


        TextView scaleType = (TextView)listItemView.findViewById(R.id.earthquakeIntense);
        String formattedScale = formatMagnitude(currentEvent.getScaleName());
        scaleType.setText(formattedScale);

        TextView primaryLocationName= (TextView)listItemView.findViewById(R.id.Name);
        primaryLocationName.setText(primaryLocation);

         TextView locationOffsetName = (TextView)listItemView.findViewById(R.id.subName);
         locationOffsetName.setText(locationOffset);


        Date dateObject = new Date(currentEvent.getTimeInMilliseconds());
        TextView dateView = (TextView) listItemView.findViewById(R.id.placesAbout);
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);


        TextView timeView = (TextView)listItemView.findViewById(R.id.aboutTime);
        String formattedTime = formatTime(dateObject);
        timeView.setText(formattedTime);



        GradientDrawable magnitudeCircle = (GradientDrawable) scaleType.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEvent.getScaleName());
        magnitudeCircle.setColor(magnitudeColor);






        return (listItemView);
    }
    private String formatDate (Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/LL/yyyy");
        return dateFormat.format(dateObject);

    }
    private String formatTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);

    }
    private String formatMagnitude(double magnitude){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(magnitude);

    } private  int getMagnitudeColor(double magnitude){
        int colorResourceId ;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                colorResourceId = R.color.magnitude1;
                break;
            case 2:
                colorResourceId= R.color.magnitude2;
                break;
            case 3:
                colorResourceId= R.color.magnitude3;
                break;
            case 4:
                colorResourceId=R.color.magnitude4;
                break;
            case 5:
                colorResourceId= R.color.magnitude5;
                break;
            case 6:
                colorResourceId= R.color.magnitude6;
                break;
            case 7:
                colorResourceId= R.color.magnitude7;
                break;
            case 8:
                colorResourceId = R.color.magnitude8;
                break;
            case 9:
                colorResourceId= R.color.magnitude9;
                break;
            default:
                colorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),colorResourceId);


    }

}
