package com.example.helpme;

import android.app.Application;

import com.backendless.Backendless;



import java.util.List;

public class ApplicationClass extends Application {

//var to connect with server
    public static final String APPLICATION_ID = "8EB5DAF7-A9F6-A945-FFB6-20D7C837FC00";
    public static final String API_KEY = "8725FA36-08BA-45E9-B116-E4ABDCE69A92";
    public static final String SERVER_URL = "https://api.backendless.com";



    public static List<AskingHelp> visitors;//list of AskigHelp visitors




    @Override
    public void onCreate() {
        super.onCreate();


        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );


    }
}
