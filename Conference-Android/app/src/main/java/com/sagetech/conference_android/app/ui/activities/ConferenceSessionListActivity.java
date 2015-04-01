package com.sagetech.conference_android.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.sagetech.conference_android.app.R;
import com.sagetech.conference_android.app.api.ConferenceController;
import com.sagetech.conference_android.app.model.ConferenceSessionData;
import com.sagetech.conference_android.app.ui.adapters.ConferenceSessionsAdapter;
import com.sagetech.conference_android.app.ui.presenter.ConferenceSessionListActivityPresenter;
import com.sagetech.conference_android.app.ui.presenter.IConferenceSessionActivity;
import com.sagetech.conference_android.app.ui.presenter.IConferenceSessionListPresenter;
import com.sagetech.conference_android.app.ui.viewModel.ConferenceSessionViewModel;
import com.sagetech.conference_android.app.util.module.ConferenceListModule;
import com.sagetech.conference_android.app.util.module.ConferenceSessionListModule;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

/**
 * Created by adam on 2/21/15.
 */
public class ConferenceSessionListActivity extends InjectableActionBarActivity implements IConferenceSessionActivity, ConferenceSessionsAdapter.ConferenceSessionsOnClickListener {

    @Inject
    IConferenceSessionListPresenter presenter = null;

    @InjectView(R.id.confView)
    RecyclerView mRecyclerView;

    private ConferenceSessionsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_session_items);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.actionbar_title_activity_conference_sessions);
        ButterKnife.inject(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Integer conferenceId = getIntent().getIntExtra("id", 0);
        presenter.initialize(conferenceId);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ConferenceSessionListModule(this));
    }


    @Override
    public void populateConferenceSessions(List<ConferenceSessionViewModel> conferenceSessions) {
        mAdapter = new ConferenceSessionsAdapter(conferenceSessions, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


    private void launchEventDetailActivity(long sessionId) {
        Timber.d(String.format("Session Selected: %s", sessionId));

        Intent eventDetailIntent = new Intent(this, NewEventDetailActivity.class);
        eventDetailIntent.putExtra("id", sessionId);
        startActivity(eventDetailIntent);
    }


    @Override
    public void clicked(Long id) {
        launchEventDetailActivity(id);
    }
}
