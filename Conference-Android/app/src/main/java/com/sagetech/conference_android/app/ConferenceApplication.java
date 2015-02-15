package com.sagetech.conference_android.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.sagetech.conference_android.app.api.ConferenceController;
import com.sagetech.conference_android.app.model.EventData;
import com.sagetech.conference_android.app.util.ConferenceModule;

import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import rx.Observable;

/**
 * Created by carlushenry on 5/28/14.
 */
public class ConferenceApplication extends Application {
    private ObjectGraph objectGraph;
    @Inject ConferenceController conferenceController;
    @Inject Observable<List<EventData>> cachedGetEventsObservable;

    @Override
    public void onCreate() {
        super.onCreate();

        buildObjectGraphAndInject();
    }

    public void buildObjectGraphAndInject() {
        objectGraph = ObjectGraph.create(new ConferenceModule(this));
        objectGraph.inject(this);
    }

    public void inject(Object o) {
        objectGraph.inject(o);
    }

    public static ConferenceApplication get(Context context) {
        return (ConferenceApplication) context.getApplicationContext();
    }

    /* ============================================================ */
    // LEGACY OPERATIONS

    /**
     * @deprecated
     * @return
     */
    public ConferenceController getConferenceController() {
        return conferenceController;
    }

    /**
     * @deprecated
     * @return
     */
    public Observable<List<EventData>> getCachedGetEventsObservable() {
        return cachedGetEventsObservable;
    }
}