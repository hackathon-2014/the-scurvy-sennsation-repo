package com.example.bootywithfriends;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.jdeferred.AlwaysCallback;
import org.jdeferred.DoneCallback;
import org.jdeferred.Promise.State;
import org.jdeferred.android.AndroidDeferredManager;

import android.app.ListActivity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class MainActivity extends ListActivity {

    private GoogleMap map;
    private LocationManager manager;
    
    public static final String BOOTY = "BootyWithFriends";

    GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(BOOTY, "onCreate called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener());
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsListener());
        
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        map.setOnInfoWindowClickListener(infoWindowClickListener());
        map.setMyLocationEnabled(true);
        map.moveCamera(toMyPosition(null));
        
        final BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.small_chest);
        for(Bar bar : Bar.defaultBars){
            MarkerOptions options = new MarkerOptions();
            options.title(bar.title);
            options.position(bar.location);
            options.icon(icon);
            map.addMarker(options);
        }
        
        AndroidDeferredManager deferredManager = new AndroidDeferredManager();
        deferredManager.setAutoSubmit(true);

        deferredManager.when(loadData()).then(putDataIntoView())
                .always(logFinish());
        
    }

    private OnInfoWindowClickListener infoWindowClickListener() {
        return new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(MainActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onDestroy() {
        apiClient.disconnect();
        super.onDestroy();
    }

    private CameraUpdate toMyPosition(Location loc) {
        if (loc == null){
            loc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } 
        if (loc == null){
            Log.wtf(BOOTY, "do you have GPS turned off?");
        }
        LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
        float pretty_close_zoom_level = 13.0f;
        return CameraUpdateFactory.newLatLngZoom(latLng, pretty_close_zoom_level);
    }

    private LocationListener gpsListener() {
        return new LocationListener() {
            @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override public void onProviderEnabled(String provider) {}
            @Override public void onProviderDisabled(String provider) {}
            @Override 
            public void onLocationChanged(Location location) {
                Log.d(BOOTY, "got new location: " + location);
                map.moveCamera(toMyPosition(location));
            }
        };
    }

    private AlwaysCallback<People.LoadPeopleResult, Throwable> logFinish() {
        return new AlwaysCallback<People.LoadPeopleResult, Throwable>() {

            public void onAlways(State state, LoadPeopleResult resolved,
                    Throwable rejected) {

                if (resolved != null) {
                    Log.i(BOOTY, "Finished with state=" + state);
                }

                if (rejected != null) {
                    Log.w(BOOTY, "Finished with state=" + state, rejected);
                }

            }
        };
    }

    private DoneCallback<LoadPeopleResult> putDataIntoView() {
        return new DoneCallback<LoadPeopleResult>() {

            public void onDone(LoadPeopleResult result) {

                Log.i(BOOTY, "putting people into view count="
                        + result.getPersonBuffer().getCount());

                List<Person> plist = new ArrayList<Person>();
                for (Person p : result.getPersonBuffer()) {
                    plist.add(p);
                }

                PeopleAdapter adapter = new PeopleAdapter(MainActivity.this);
                adapter.addAll(plist);
                MainActivity.this.setListAdapter(adapter);
            }

        };
    }

    private Callable<LoadPeopleResult> loadData() {

        Log.i(BOOTY, "connecting to google API");

        apiClient = new GoogleApiClient.Builder(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).useDefaultAccount().build();
        apiClient.connect();

        Log.i(BOOTY, "connected to google API");

        return new PeopleCallable(this, apiClient);
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
