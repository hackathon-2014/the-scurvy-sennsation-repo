package com.example.bootywithfriends;

import java.io.DataOutputStream;
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

                URL url = new URL(
                        "http://www.mattsenn.com/hackathon/v1/v1.cfc?method=Save");

                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                try {
                    connection.setDoOutput(true);

                    String parameters = "PersonName=" + data.name
                            + "&LocationName=" + data.location + "&BootyName="
                            + data.booty;

                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded");
                    connection.setRequestProperty("charset", "utf-8");
                    connection.setRequestProperty("Content-Length", ""
                            + Integer.toString(parameters.getBytes().length));

                    DataOutputStream wr = new DataOutputStream(
                            connection.getOutputStream());
                    wr.writeBytes(parameters);
                    wr.flush();
                    wr.close();
                } finally {
                    connection.disconnect();
                }

            } catch (Exception e) {
                Log.e(MainActivity.BOOTY, "Exception posting", e);
            }
        }
    }
}
