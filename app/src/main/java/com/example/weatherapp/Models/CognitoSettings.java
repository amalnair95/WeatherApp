package com.example.weatherapp.models;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {
    //private String userPoolId="ap-south-1_TKnoPK38F";
    private String userPoolId="ap-south-1_fcSp9mi5f";
    //private String clientId="31d0kmedhci14hc1992mqoldar";
    private String clientId="4b8llkunqjtkj996klfout81fh";
    private String poolUrl="famersapp.auth.ap-south-1";


    //private String clientSecret="33qm393nsdcq23u0ugmt7574bngacookkr6pupmahn4ha9vnmtj";
    private String clientSecret="12o399kraiqj9on4hjpk6ro9j3q7n4faod6474o8kp15qrdjq3mf";
    private Regions cognitoRegion=Regions.AP_SOUTH_1;

    private Context context;
    public CognitoSettings(Context context){
        this.context=context;
    }


    public String getPoolUrl() {
        return poolUrl;
    }

    public void setPoolUrl(String poolUrl) {
        this.poolUrl = poolUrl;
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
