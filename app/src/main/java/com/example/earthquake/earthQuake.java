package com.example.earthquake;

public class earthQuake {
    private  double mScaleName;
    private String mPlacesName;
    private Long mTimeInMilliseconds;
    private String mUrl;



    public earthQuake(double scaleName,String placesName,Long timeInMilliseconds,String url){
        mScaleName= scaleName;
        mPlacesName = placesName;
        mTimeInMilliseconds= timeInMilliseconds;
        mUrl = url;
    }


    public double getScaleName(){
        return mScaleName;
    }
    public String getPlacesName(){ return mPlacesName; }
    public Long getTimeInMilliseconds(){
        return mTimeInMilliseconds;
    }
public String getUrl(){ return mUrl;}


}
