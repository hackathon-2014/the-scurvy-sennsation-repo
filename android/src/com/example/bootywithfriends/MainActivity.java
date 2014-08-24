package com.example.bootywithfriends;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdeferred.AlwaysCallback;
import org.jdeferred.Promise.State;
import org.jdeferred.android.AndroidDeferredManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bootywithfriends.SaveBeer.Data;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {

    private GoogleMap map;
    private LocationManager manager;

    private User myUser = User.defaultUsers[0];

    public static final String BOOTY = "BootyWithFriends";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(BOOTY, "onCreate called");

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                gpsListener());
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                gpsListener());

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        map = mapFragment.getMap();
        map.setOnInfoWindowClickListener(infoWindowClickListener());
        map.setOnMarkerClickListener(markerClickListener());
        map.setMyLocationEnabled(true);
        map.moveCamera(toMyPosition(null));

        final BitmapDescriptor icon = BitmapDescriptorFactory
                .fromResource(R.drawable.small_chest);
        for (Bar bar : Bar.defaultBars) {
            MarkerOptions options = new MarkerOptions();
            options.title(bar.title);
            options.position(bar.location);
            options.icon(icon);
            map.addMarker(options);
        }

        AndroidDeferredManager deferredManager = new AndroidDeferredManager();
        deferredManager.setAutoSubmit(true);

        String username = getIntent().getStringExtra(SplashyActivity.USERNAME);
        myUser = User.fromName(username);

        Typeface piratey = Typeface.createFromAsset(getAssets(),
                "fonts/pirate_font.ttf");
        ((TextView) findViewById(R.id.info_view)).setTypeface(piratey);
        ((TextView) findViewById(R.id.location_name)).setTypeface(piratey);
        ((TextView) findViewById(R.id.save_button)).setTypeface(piratey);

        Spinner spinner = (Spinner) findViewById(R.id.user_name);
        List<String> arr = new ArrayList<String>();
        for(User user : User.defaultUsers) {
            arr.add(user.name);
        }
        spinner.setAdapter(new ArrayAdapter<String>(this,
                R.layout.spinner_layout, arr));
    }

    private void markerClick(Marker marker) {
        TextView text = (TextView) findViewById(R.id.location_name);
        text.setText(marker.getTitle());
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

        deferredManager.when(postYourBooty(data)).always(logger());

        if (errorView != null) {
            errorView.setText("Posting data");
        }
    }

    private Runnable postYourBooty(final Data data) {
        return new Runnable() {

            @Override
            public void run() {

                try {

                    String myId = myUser.uid;
                    EditText text = (EditText) findViewById(R.id.user_name);
                    String mateyName = text.getText().toString();
                    String mateyId = User.fromName(mateyName).uid;

                    String base = "http://www.mattsenn.com/Hackathon/v1/v1.cfc";
                    String parameters = "?method=Save" + "&GoogleID=" + myId
                            + "&UsrGoogleID=" + mateyId + "&LocationGUID="
                            + Bar.findBarByName(data.location).id
                            + "&BootyName=" + data.booty.replace(" ", "+");

                    URL url = new URL(base + parameters);

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(url.openStream()));

                    try {
                        String line = reader.readLine();
                        Log.i(MainActivity.BOOTY, "Read line=" + line);
                    } finally {
                        reader.close();
                    }

                } catch (Exception e) {
                    Log.e(MainActivity.BOOTY, "Exception posting", e);
                }
            }
        };
    }

    private AlwaysCallback<Void, Throwable> logger() {
        return new AlwaysCallback<Void, Throwable>() {
            @Override
            public void onAlways(State state, Void resolved, Throwable rejected) {
                Log.i(MainActivity.BOOTY, "finished saving beer");
                if (rejected != null) {
                    Log.w(MainActivity.BOOTY, "with error", rejected);
                }

                TextView errorView = (TextView) findViewById(R.id.error_view);
                if (errorView != null) {
                    errorView
                            .setText("Your booty has been dropped successfully.");
                }
            }
        };
    }

    private OnMarkerClickListener markerClickListener() {
        return new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                markerClick(marker);
                return false;
            }
        };
    }

    private OnInfoWindowClickListener infoWindowClickListener() {
        return new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                markerClick(marker);
            }
        };
    }

    private CameraUpdate toMyPosition(Location loc) {
        if (loc == null) {
            loc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (loc == null) {
            Log.wtf(BOOTY, "do you have GPS turned off?");
            return CameraUpdateFactory.zoomOut(); // cause there is no identity
                                                  // function
        }
        LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
        float pretty_close_zoom_level = 13.0f;
        return CameraUpdateFactory.newLatLngZoom(latLng,
                pretty_close_zoom_level);
    }

    private LocationListener gpsListener() {
        return new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status,
                    Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onLocationChanged(Location location) {
                Log.d(BOOTY, "got new location: " + location);
                map.moveCamera(toMyPosition(location));

                manager.removeUpdates(this);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
