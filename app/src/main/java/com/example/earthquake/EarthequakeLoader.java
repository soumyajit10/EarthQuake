package com.example.earthquake;

import android.content.Context;


import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public   class EarthequakeLoader extends AsyncTaskLoader<List<earthQuake>> {
    private static final String LOG_TAG =EarthequakeLoader.class.getName();
    private String mUrl;



    public EarthequakeLoader(Context context, String url) {
        super(context);
        mUrl= url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<earthQuake> loadInBackground( ) {
        if (mUrl==null){
            return null;
        }
        List<earthQuake> earthquakes = QueryUtils.fetchEarthQuakeData(mUrl);
        return earthquakes;

    }
}
