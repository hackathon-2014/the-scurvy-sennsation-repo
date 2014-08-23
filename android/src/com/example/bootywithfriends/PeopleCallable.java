package com.example.bootywithfriends;

import java.util.concurrent.Callable;

import org.jdeferred.android.AndroidExecutionScope;
import org.jdeferred.android.AndroidExecutionScopeable;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;

public final class PeopleCallable implements Callable<LoadPeopleResult>,
        AndroidExecutionScopeable {

    final Context context;

    final GoogleApiClient apiClient;

    public PeopleCallable(Context context, GoogleApiClient apiClient) {
        this.context = context;
        this.apiClient = apiClient;
    }

    public LoadPeopleResult call() throws Exception {

        Log.i("BOOTY", "loading list of people");
        PendingResult<LoadPeopleResult> pendingResult = Plus.PeopleApi
                .loadVisible(apiClient, null);
        return pendingResult.await();
    }

    public AndroidExecutionScope getExecutionScope() {
        return AndroidExecutionScope.BACKGROUND;
    }

}