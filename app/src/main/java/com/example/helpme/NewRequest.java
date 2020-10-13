package com.example.helpme;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.BackendlessGeoQuery;
import com.backendless.geo.GeoCategory;
import com.backendless.geo.GeoPoint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewRequest extends AppCompatActivity {

    EditText buttonName, buttonNumber, buttonEmail, buttonComments, buttonLocation;
    Button sendLocation, buttonSubmit;



    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);

//linking the input fields and buttons
        buttonName = findViewById(R.id.buttonName);
        buttonNumber = findViewById(R.id.buttonNumber);
        buttonEmail = findViewById(R.id.buttonEmail);
        buttonComments = findViewById(R.id.buttonComments);
        buttonLocation = findViewById(R.id.buttonLocation);

//loading bar
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);


        buttonSubmit = findViewById(R.id.buttonSubmit);


        sendLocation = findViewById(R.id.sendLocation);



        sendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //asking for permission, open map activity

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(NewRequest.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, 0);

                }
                else
                {
                    Intent intent = new Intent(NewRequest.this, MapsActivity.class);
                    startActivity(intent);
                }
            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validations

                if (buttonName.getText().toString().isEmpty() || buttonNumber.getText().toString().isEmpty() ||
                        buttonEmail.getText().toString().isEmpty() || buttonComments.getText().toString().isEmpty() ||
                        buttonLocation.getText().toString().isEmpty()){
                    //should be !=empty
                    Toast.makeText(NewRequest.this, "All gaps must be not empty", Toast.LENGTH_SHORT).show();
                } else {
                    String name = buttonName.getText().toString().trim();
                    String number = buttonNumber.getText().toString().trim();
                    String email = buttonEmail.getText().toString().trim();
                    String comments = buttonComments.getText().toString().trim();
                    String location = buttonLocation.getText().toString().trim();

                   //passing the users information
                    AskingHelp askingHelp = new AskingHelp();
                    askingHelp.setName(name);
                    askingHelp.setNumber(number);
                    askingHelp.setEmail(email);
                    askingHelp.setComments(comments);
                    askingHelp.setLocation(location);

                    showProgress(true);
                    tvLoad.setText("Uploading your ask for help...");


                    Backendless.Persistence.save(askingHelp, new AsyncCallback<AskingHelp>() {
                        @Override
                        public void handleResponse(AskingHelp response) {

                            //if fields are ok ,publish
                            Toast.makeText(NewRequest.this, "Your help has been published", Toast.LENGTH_SHORT).show();
                            showProgress(false);
                            NewRequest.this.finish();//go to main Activity
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                            //if not show the error message.
                            Toast.makeText(NewRequest.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);

                        }
                    });






                }


            }
        });

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
// for very easy animations. If available, use these APIs to fade-in
// the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
// The ViewPropertyAnimator APIs are not available, so simply show
// and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}