package com.example.helpme;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import org.w3c.dom.Text;

public class ListInfo extends AppCompatActivity {


    TextView  textName, readcomments;
    ImageView iconcall, iconemail, iconlocation, iconremove;

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_info);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
//linking to layout
        textName = findViewById(R.id.textName);
        readcomments = findViewById(R.id.readcomments);

        iconcall = findViewById(R.id.iconcall);
        iconemail = findViewById(R.id.iconemail);
        iconlocation = findViewById(R.id.iconlocation);
        iconremove = findViewById(R.id.iconremove);


        final int index = getIntent().getIntExtra("index", 0);

 //set users name, and comments
        textName.setText(ApplicationClass.visitors.get(index).getName());
        readcomments.setText(ApplicationClass.visitors.get(index).getComments());


        iconcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //using implicity,to dial ,pop the selected user number
                String uri = "tel:" + ApplicationClass.visitors.get(index).getNumber();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);

            }
        });
        iconemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//using implicity,to email ,pop the selected user number
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, ApplicationClass.visitors.get(index).getEmail());
                //choose mail services
                startActivity(Intent.createChooser(intent, "Send email to " + ApplicationClass.visitors.get(index).getName()));

            }
        });
              //ask for permission
        iconlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get location using implicity
                    String uri = "geo:0,0?q=" + ApplicationClass.visitors.get(index).getLocation();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);

                }

        });
        iconremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(ListInfo.this);
                dialog.setMessage("Have your resolved persons problem? If not please do not Delete it.");
                dialog.setPositiveButton("Yes, i did", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        showProgress(true);
                        tvLoad.setText("Removing the asked help");

                        Backendless.Persistence.of(AskingHelp.class).remove(ApplicationClass.visitors.get(index), new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {

                                ApplicationClass.visitors.remove(index);//delete from ApplicationClass.vistors
                                Toast.makeText(ListInfo.this, "Thanks for giving a hand . You are HERO!", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                ListInfo.this.finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                                Toast.makeText(ListInfo.this, "Error: " + fault.getMessage(),
                                        Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });

                dialog.setNegativeButton("Not yet ...", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();

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
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
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
