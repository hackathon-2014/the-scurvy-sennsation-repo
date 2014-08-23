package com.example.bootywithfriends;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jdeferred.AlwaysCallback;
import org.jdeferred.Promise.State;
import org.jdeferred.android.AndroidDeferredManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SaveBeer extends Activity {

    static final class Data {
        String name;
        String location;
        String booty;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_beer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_beer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveClick(View button) {

        AndroidDeferredManager deferredManager = new AndroidDeferredManager();
        deferredManager.setAutoSubmit(true);

        Data data = new Data();

        TextView view = (TextView) findViewById(R.id.user_name);
        data.name = view.getText().toString();

        view = (TextView) findViewById(R.id.location_name);
        data.location = view.getText().toString();

        view = (TextView) findViewById(R.id.booty_name);
        data.booty = view.getText().toString();

        final TextView errorView = (TextView) findViewById(R.id.error_view);

        deferredManager.when(new PostBeerRunnable(data)).always(
                new AlwaysCallback<Void, Throwable>() {

                    public void onAlways(State state, Void resolved,
                            Throwable rejected) {
                        Log.i(MainActivity.BOOTY, "finished saving beer");
                        if (rejected != null) {
                            Log.w(MainActivity.BOOTY, "with error", rejected);
                        }

                        if (errorView != null) {
                            errorView.setText("Finished saving beer");
                        }
                    }
                });

        if (errorView != null) {
            errorView.setText("Posting data");
        }
    }

    public void onCancelClick(View view) {

    }

    public static final class PostBeerRunnable implements Runnable {

        Data data;

        public PostBeerRunnable(Data data) {
            this.data = data;
        }

        public void run() {

            try {

                String base = "http://www.mattsenn.com/Hackathon/v1/V1.cfc";
                String parameters = "?method=Save"
                        + "&GoogleID=86365E2F-3C03-4DDE-9F01-34AC2F9B04EA"
                        + "&UsrGoogleID=E409F57C-106A-4E70-8DE7-85AC90FC60AE"
                        + "&LocationName=The+Sparrow" + "&BootyName=Budweiser";

                URL url = new URL(base + parameters);

                // HttpURLConnection connection = (HttpURLConnection) url
                // .openConnection();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(url.openStream()));

                try {
                    String line = reader.readLine();
                    Log.i(MainActivity.BOOTY, "Read line=" + line);
                } finally {
                    reader.close();
                }

                /*
                 * try { connection.setDoOutput(true);
                 * 
                 * String parameters = "?method=Save" +
                 * "&GoogleID=86365E2F-3C03-4DDE-9F01-34AC2F9B04EA" +
                 * "&UsrGoogleID=E409F57C-106A-4E70-8DE7-85AC90FC60AE" +
                 * "&LocationName=The+Sparrow" + "&BootyName=Budweiser";
                 * 
                 * connection.setRequestMethod("POST");
                 * connection.setRequestProperty("Content-Type",
                 * "application/x-www-form-urlencoded");
                 * connection.setRequestProperty("charset", "utf-8");
                 * connection.setRequestProperty("Content-Length", "" +
                 * Integer.toString(parameters.getBytes().length));
                 * 
                 * Log.i(MainActivity.BOOTY, "Writing bytes=" +
                 * parameters.getBytes().length);
                 * 
                 * DataOutputStream wr = new DataOutputStream(
                 * connection.getOutputStream()); wr.writeBytes(parameters);
                 * wr.flush(); wr.close(); } finally { connection.disconnect();
                 * }
                 */

            } catch (Exception e) {
                Log.e(MainActivity.BOOTY, "Exception posting", e);
            }
        }
    }
}
