package com.sagetech.conference_android.app.ui.activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sagetech.conference_android.app.R;
import com.sagetech.conference_android.app.api.ConferenceController;
import com.sagetech.conference_android.app.model.EventData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class EventDetailActivity extends InjectableActionBarActivity {
    @Inject
    ConferenceController conferenceController;

    @InjectView(R.id.title)
    TextView title;

    @InjectView(R.id.location)
    TextView location;

    @InjectView(R.id.description)
    TextView description;

    @InjectView(R.id.name1)
    TextView name1;

    @InjectView(R.id.name2)
    TextView name2;

    @InjectView(R.id.bio1)
    TextView bio1;

    @InjectView(R.id.bio2)
    TextView bio2;

    @InjectView(R.id.scheduled)
    ImageView img;

    private Integer eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.inject(this);

        eventId = getIntent().getExtras().getInt("id");

        EventData event = conferenceController.getEvent(eventId);
        updateScreen(event);
    }

    private void updateScreen(EventData eventData) {

        String name = null;
        String name2 = null;
        if (!eventData.getEvent_leaders().isEmpty()) {
            name = eventData.getEvent_leaders().get(0).getFirst_name() + " " + eventData.getEvent_leaders().get(0).getLast_name();
            this.bio1.setText(eventData.getEvent_leaders().get(0).getBiography());
            this.name1.setText(name);

            if (eventData.getEvent_leaders().size() > 1) {
                name2 = eventData.getEvent_leaders().get(1).getFirst_name() + " " + eventData.getEvent_leaders().get(1).getLast_name();
                this.bio2.setText(eventData.getEvent_leaders().get(1).getBiography());
                this.name2.setText(name2);
            }
        }

        this.title.setText(eventData.getEvent().getTitle());
        String roomName = null;
        if (eventData.getRoom() != null) {
            roomName = eventData.getRoom().getName();
        }
        this.location.setText(formatLocation(eventData.getStart_dttm(), eventData.getEnd_dttm(), roomName));
        this.description.setText(eventData.getEvent().getDescription());

        if (eventData.getEvent().getEvent_type().equals("Presentation"))
            img.setImageResource(R.drawable.presentation_icon);
        else if (eventData.getEvent().getEvent_type().equals("Code Lab"))
            img.setImageResource(R.drawable.codelabs_icon);

    }

    private String formatLocation(String startString, String endString, String roomName) {
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(startString);
            endDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(endString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormatter1 = new SimpleDateFormat("EEE, h:mm-", Locale.US);
        SimpleDateFormat timeFormatter2 = new SimpleDateFormat("h:mm aaa", Locale.US);

        if (roomName == null || roomName.trim().length() == 0) {
            return timeFormatter1.format(startDate) + timeFormatter2.format(endDate);
        }

        return timeFormatter1.format(startDate) + timeFormatter2.format(endDate) + " in " + roomName;
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
}