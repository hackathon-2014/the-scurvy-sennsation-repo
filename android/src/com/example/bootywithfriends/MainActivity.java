package com.example.bootywithfriends;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.jdeferred.AlwaysCallback;
import org.jdeferred.DoneCallback;
import org.jdeferred.Promise.State;
import org.jdeferred.android.AndroidDeferredManager;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class MainActivity extends ListActivity {

    public static final String BOOTY = "BootyWithFriends";

    GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(BOOTY, "onCreate called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidDeferredManager deferredManager = new AndroidDeferredManager();
        deferredManager.setAutoSubmit(true);

        deferredManager.when(loadData()).then(putDataIntoView())
                .always(logFinish());
        
    }

    @Override
    protected void onDestroy() {
        apiClient.disconnect();
        super.onDestroy();
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
