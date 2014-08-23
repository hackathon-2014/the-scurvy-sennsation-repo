package com.example.bootywithfriends;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.jdeferred.AlwaysCallback;
import org.jdeferred.DoneCallback;
import org.jdeferred.DoneFilter;
import org.jdeferred.FailCallback;
import org.jdeferred.Promise.State;
import org.jdeferred.android.AndroidDeferredManager;
import org.jdeferred.android.AndroidExecutionScope;
import org.jdeferred.android.AndroidExecutionScopeable;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

public class ShowTreasuresActivity extends ListActivity {

    static final class Data {
        public List<String> COLUMNS;
        public List<List<Object>> DATA;
    }

    static final class TreasureMaps {
        final List<TreasureMap> maps = new ArrayList<TreasureMap>();
    }

    static final class TreasureMap {

        User user;
        String booty;
        Bar location;
        String treasureId;
        boolean claimed;

        List<Object> original;

        public static TreasureMap from(List<Object> datum) {
            TreasureMap map = new TreasureMap();
            map.original = datum;
            map.user = User.fromUid((String) datum.get(0));
            map.booty = (String) datum.get(1);
            map.location = Bar.findBarById((String) datum.get(2));
            return map;
        }
    }

    public final class TreasureMapAdapter extends ArrayAdapter<TreasureMap> {

        public TreasureMapAdapter(Context context) {
            super(context, R.layout.show_treasures_item);
            Log.i(MainActivity.BOOTY, "Creating TreasureMapAdapter");
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflator = LayoutInflater.from(getContext());
                view = inflator.inflate(R.layout.show_treasures_item, parent,
                        false);
            }

            final TreasureMap map = getItem(position);

            if (map == null) {
                Log.w(MainActivity.BOOTY, "Null data in ArrayAdapter");
                return null;
            }

            TextView text1 = (TextView) view.findViewById(R.id.text1);
            text1.setText(map.booty);

            TextView text2 = (TextView) view.findViewById(R.id.text2);
            text2.setText(map.location.title);

            TextView text4 = (TextView) view.findViewById(R.id.text4);
            text4.setText(map.user.name);

            final Button digUpButton = (Button) view
                    .findViewById(R.id.dig_up_button);
            if (map.claimed) {
                digUpButton.setVisibility(View.GONE);
            } else {
                final TextView claimedLabel = (TextView) view
                        .findViewById(R.id.claimed_label);
                claimedLabel.setVisibility(View.GONE);

                digUpButton.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        onDigUpButtonClicked(map);
                        digUpButton.setVisibility(View.GONE);
                        claimedLabel.setVisibility(View.VISIBLE);
                    }
                });
            }

            view.setTag(R.id.tag_treasure_location, map);
            return view;
        }
    }

    static final class LoadDataCallable implements Callable<Data>,
            AndroidExecutionScopeable {

        @Override
        public Data call() throws IOException {

            String myId = "E409F57C-106A-4E70-8DE7-85AC90FC60AE";

            String base = "http://www.mattsenn.com/Hackathon/V2/v2.cfc";
            String parameters = "?method=WhereGoogleID&GoogleID=" + myId;

            Log.i(MainActivity.BOOTY, "Opening URL to get list of treasures");

            URL url = new URL(base + parameters);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            try {
                Gson gson = new Gson();
                Data data = gson.fromJson(reader, Data.class);
                Log.i(MainActivity.BOOTY, "Read list of treasures count="
                        + data.DATA.size());
                return data;

            } finally {
                reader.close();
            }
        }

        @Override
        public AndroidExecutionScope getExecutionScope() {
            return AndroidExecutionScope.BACKGROUND;
        }
    }

    AndroidDeferredManager deferredManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_treasures);
        if (savedInstanceState != null) {
            return;
        }

        deferredManager = new AndroidDeferredManager();
        deferredManager.setAutoSubmit(true);

        deferredManager.when(loadData()).then(mungeData())
                .then(putDataIntoView()).fail(logFailure()).always(logFinish());
    }

    private DoneFilter<Data, TreasureMaps> mungeData() {
        return new DoneFilter<Data, TreasureMaps>() {

            @Override
            public TreasureMaps filterDone(Data result) {
                TreasureMaps maps = new TreasureMaps();

                for (List<Object> datum : result.DATA) {
                    TreasureMap map = TreasureMap.from(datum);
                    maps.maps.add(map);
                }

                Log.i(MainActivity.BOOTY, "Munged list of maps count="
                        + maps.maps.size());
                return maps;
            }
        };
    }

    private AlwaysCallback<TreasureMaps, Object> logFinish() {
        return new AlwaysCallback<TreasureMaps, Object>() {

            @Override
            public void onAlways(State state, TreasureMaps resolved,
                    Object rejected) {
                Log.i(MainActivity.BOOTY, "Finished with state=" + state
                        + " resolved=" + resolved + " rejected=" + rejected);
                if (rejected != null) {
                    Log.e("TreasureList", "May not have finished",
                            (Throwable) rejected);
                }
            }
        };
    }

    private LoadDataCallable loadData() {
        return new LoadDataCallable();
    }

    private FailCallback<Object> logFailure() {
        return new FailCallback<Object>() {

            @Override
            public void onFail(Object result) {
                Log.e("TreasureList", "Failure in callback", (Throwable) result);
            }
        };
    }

    private DoneCallback<TreasureMaps> putDataIntoView() {
        return new DoneCallback<TreasureMaps>() {

            @Override
            public void onDone(TreasureMaps result) {
                ArrayAdapter<TreasureMap> adapter = new TreasureMapAdapter(
                        ShowTreasuresActivity.this);
                adapter.addAll(result.maps);

                Log.i(MainActivity.BOOTY, "Setting list adapter");
                ShowTreasuresActivity.this.setListAdapter(adapter);
            }
        };
    }

    @Override
    protected void onDestroy() {
        deferredManager = null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_treasures, menu);
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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(MainActivity.BOOTY, "clicked on item=" + position);

        TreasureMap map = (TreasureMap) v.getTag(R.id.tag_treasure_location);
        if (map != null) {
            Bar bar = map.location;

            Uri uri = Uri.parse("geo:0,0?q=" + bar.location.latitude + ","
                    + bar.location.longitude + "(" + bar.title + ")");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                Log.i(MainActivity.BOOTY, "starting maps activity");
                startActivity(intent);
            }

        }

    }

    public void onDigUpButtonClicked(TreasureMap map) {

        Log.i(MainActivity.BOOTY, "Digging up something");
        try {

            String base = "http://www.mattsenn.com/Hackathon/v1/V1.cfc";
            String parameters = "?method=Save"
                    + "&GoogleID=86365E2F-3C03-4DDE-9F01-34AC2F9B04EA"
                    + "&UsrGoogleID=E409F57C-106A-4E70-8DE7-85AC90FC60AE"
                    + "&LocationName=The+Sparrow" + "&BootyName=Budweiser";

            URL url = new URL(base + parameters);

            // HttpURLConnection connection = (HttpURLConnection) url
            // .openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    url.openStream()));

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
}