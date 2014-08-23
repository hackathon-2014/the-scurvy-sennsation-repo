package com.example.bootywithfriends;

import java.util.List;

import org.jdeferred.AlwaysCallback;
import org.jdeferred.DoneCallback;
import org.jdeferred.DoneFilter;
import org.jdeferred.FailCallback;
import org.jdeferred.Promise.State;
import org.jdeferred.android.AndroidDeferredManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowTreasuresActivity extends Activity {
    /*
     * AndroidDeferredManager deferredManager;
     * 
     * @Override protected void onCreate(Bundle savedInstanceState) {
     * super.onCreate(savedInstanceState);
     * setContentView(R.layout.activity_show_treasures);
     * 
     * if (savedInstanceState != null) { return; }
     * 
     * deferredManager = new AndroidDeferredManager();
     * deferredManager.setAutoSubmit(true);
     * 
     * deferredManager.when(loadData()).then(mungeData())
     * .then(putDataIntoView()).fail(logFailure()).always(logFinish()); }
     * 
     * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
     * menu; this adds items to the action bar if it is present.
     * getMenuInflater().inflate(R.menu.show_treasures, menu); return true; }
     * 
     * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
     * action bar item clicks here. The action bar will // automatically handle
     * clicks on the Home/Up button, so long // as you specify a parent activity
     * in AndroidManifest.xml. int id = item.getItemId(); if (id ==
     * R.id.action_settings) { return true; } return
     * super.onOptionsItemSelected(item); }
     * 
     * private DoneFilter<Data, TreasureMap> mungeData() { return new
     * DoneFilter<Data, TreasureMap>() {
     * 
     * @Override public TreasureMap filterDone(Data result) { TreasureMap map =
     * new TreasureMap(); for (List<Object> datum : result.DATA) {
     * TreasureLocation loc = TreasureLocation.from(datum);
     * map.locations.add(loc); }
     * 
     * return map; } }; }
     * 
     * private AlwaysCallback<TreasureMap, Object> logFinish() { return new
     * AlwaysCallback<TreasureMap, Object>() {
     * 
     * @Override public void onAlways(State state, TreasureMap resolved, Object
     * rejected) { Log.i("TreasureList", "Finished with state=" + state +
     * " resolved=" + resolved + " rejected=" + rejected); if (rejected != null)
     * { Log.e("TreasureList", "May not have finished", (Throwable) rejected); }
     * } }; }
     * 
     * private LoadDataCallable loadData() { return new LoadDataCallable(); }
     * 
     * private FailCallback<Object> logFailure() { return new
     * FailCallback<Object>() {
     * 
     * @Override public void onFail(Object result) { Log.e("TreasureList",
     * "Failure in callback", (Throwable) result); } }; }
     * 
     * private DoneCallback<TreasureMap> putDataIntoView() { return new
     * DoneCallback<TreasureMap>() {
     * 
     * @Override public void onDone(TreasureMap result) {
     * ArrayAdapter<TreasureLocation> adapter = new TreasureMapAdapter(
     * TreasureList.this); adapter.addAll(result.locations);
     * TreasureList.this.setListAdapter(adapter); } }; }
     * 
     * @Override protected void onDestroy() { deferredManager = null;
     * super.onDestroy(); }
     * 
     * @Override protected void onListItemClick(ListView l, View v, int
     * position, long id) { TreasureLocation loc = (TreasureLocation)
     * l.getItemAtPosition(position); if (loc == null) { Log.w("TreasureList",
     * "null treasure"); return; }
     * 
     * if (loc.latitude == null || loc.longitude == null) {
     * Log.w("TreasureList", "treasure has null location id=" + loc.id); return;
     * }
     * 
     * Log.i("TreasureList", "Clicked on id=" + loc.id);
     * 
     * Uri uri = Uri.parse("geo:" + loc.latitude + "," + loc.longitude); Intent
     * intent = new Intent(Intent.ACTION_VIEW); intent.setData(uri); if
     * (intent.resolveActivity(getPackageManager()) != null) {
     * Log.i("TreasureList", "starting maps activity"); startActivity(intent); }
     * 
     * }
     */
}
