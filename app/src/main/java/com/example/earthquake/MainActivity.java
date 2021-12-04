package com.example.earthquake;



import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<earthQuake>> {



    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String USGS_REQUEST_URL = ("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=3&limit=15");
    private EarthAdapter mAdapter;
    private static final int EARTHQUAKE_LOADER_ID=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView EarthQuakeListView = (ListView) findViewById(R.id.main);
        mAdapter = new EarthAdapter(MainActivity.this, new ArrayList<earthQuake>());
        EarthQuakeListView.setAdapter(mAdapter);
        EarthQuakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "opening", Toast.LENGTH_SHORT).show();
                earthQuake currentEvent = mAdapter.getItem(position);
                Uri earthquakeUri = Uri.parse(currentEvent.getUrl());
                Intent webSiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(webSiteIntent);
            }
        });

        LoaderManager loaderManager = getLoaderManager();


        loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null,this);


    }




    @Override
    public Loader<List<earthQuake>> onCreateLoader(int id, Bundle bundle) {
        return new  EarthequakeLoader( this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<earthQuake>> loader, List<earthQuake> earthquakes) {
        mAdapter.clear();
       if (earthquakes!=null && !earthquakes.isEmpty()){
           mAdapter.addAll(earthquakes);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<earthQuake>> loader) {
    mAdapter.clear();

    }
}