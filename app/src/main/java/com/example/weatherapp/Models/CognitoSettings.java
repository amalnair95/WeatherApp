package com.example.weatherapp.models;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {
    private String userPoolId="ap-south-1_TKnoPK38F";
    private String clientId="31d0kmedhci14hc1992mqoldar";
    private String clientSecret="33qm393nsdcq23u0ugmt7574bngacookkr6pupmahn4ha9vnmtj";
    private Regions cognitoRegion=Regions.AP_SOUTH_1;

    private Context context;
    public CognitoSettings(Context context){
        this.context=context;
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }


    public Regions getCognitoRegion() {
        return cognitoRegion;
    }

    public CognitoUserPool getUserPool(){
        return new CognitoUserPool(context,userPoolId,clientId,clientSecret,cognitoRegion);
    }
}
