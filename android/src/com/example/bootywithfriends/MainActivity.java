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

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class MainActivity extends ListActivity {

    private GoogleMap map;
    private LocationManager manager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("BootyWithFriends", "onCreate called");
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener());
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsListener());
        
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        map.setMyLocationEnabled(true);
        map.moveCamera(toMyPosition(null));
        
        AndroidDeferredManager deferredManager = new AndroidDeferredManager();
        deferredManager.setAutoSubmit(true);

        deferredManager.when(loadData()).then(putDataIntoView())
                .always(logFinish());
    }

    private CameraUpdate toMyPosition(Location loc) {
        if (loc == null){
            loc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
        float pretty_close_zoom_level = 15.0f;
        return CameraUpdateFactory.newLatLngZoom(latLng, pretty_close_zoom_level);
    }

    private LocationListener gpsListener() {
        return new LocationListener() {
            @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override public void onProviderEnabled(String provider) {}
            @Override public void onProviderDisabled(String provider) {}
            @Override 
            public void onLocationChanged(Location location) {
                Log.d("BootyWithFriends", "got new location: " + location);
                map.moveCamera(toMyPosition(location));
            }
        };
    }

    private AlwaysCallback<People.LoadPeopleResult, Throwable> logFinish() {
        return new AlwaysCallback<People.LoadPeopleResult, Throwable>() {

            public void onAlways(State state, LoadPeopleResult resolved,
                    Throwable rejected) {

                if (resolved != null) {
                    Log.i("BootyWithFriends", "Finished with state=" + state);
                }

                if (rejected != null) {
                    Log.w("BootyWithFriends", "Finished with state=" + state,
                            rejected);
                }

            }
        };
    }

    private DoneCallback<LoadPeopleResult> putDataIntoView() {
        return new DoneCallback<LoadPeopleResult>() {

            public void onDone(LoadPeopleResult result) {

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

        final GoogleApiClient client = new GoogleApiClient.Builder(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .useDefaultAccount().build();
        client.connect();

        return new Callable<LoadPeopleResult>() {
            public LoadPeopleResult call() throws Exception {
                PendingResult<LoadPeopleResult> pendingResult = Plus.PeopleApi
                        .loadVisible(client, null);
                return pendingResult.await();
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
