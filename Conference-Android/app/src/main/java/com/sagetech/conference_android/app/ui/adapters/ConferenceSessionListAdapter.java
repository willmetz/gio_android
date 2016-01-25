package com.sagetech.conference_android.app.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sagetech.conference_android.app.R;
import com.sagetech.conference_android.app.ui.Views.ConferenceSessionListItemHeader;
import com.sagetech.conference_android.app.ui.Views.ConferenceSessionViewItem;
import com.sagetech.conference_android.app.ui.viewModel.ConferenceSessionViewModel;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by carlushenry on 3/5/15.
 */
public class ConferenceSessionListAdapter extends RecyclerView.Adapter<ConferenceSessionListAdapter.ViewHolder>
    implements StickyRecyclerHeadersAdapter<ConferenceSessionListAdapter.DayViewHolder>

{
    private final List<ConferenceSessionViewModel> conferenceSessions;
    private ConferenceSessionListOnClickListener onClickListener;
    private ArrayList<Integer> dateChangedIndex;

    public interface ConferenceSessionListOnClickListener {
        void clicked(Long id);
    }

    public ConferenceSessionListAdapter(List<ConferenceSessionViewModel> conferenceSessions, ConferenceSessionListOnClickListener onClickListener) {
        this.conferenceSessions = conferenceSessions;
        this.onClickListener = onClickListener;
        dateChangedIndex = new ArrayList<>();
        dateChangedIndex.add(0);
        int count = 0;

        Calendar previousSession = Calendar.getInstance();
        Calendar currentSession = Calendar.getInstance();

        for( int i = 1; i < conferenceSessions.size(); i++ )
        {
            previousSession.setTime( conferenceSessions.get( i - 1 ).getStartDttm() );
            currentSession.setTime( conferenceSessions.get( i ).getStartDttm() );
            if( previousSession.get( Calendar.DAY_OF_YEAR ) != currentSession.get( Calendar.DAY_OF_YEAR ) )
            {
                dateChangedIndex.add( i );
                count = i;
            }
            else
            {
                dateChangedIndex.add( count );
            }
        }
    }

    //RecyclerView.Adapter implementation
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder( new ConferenceSessionViewItem( parent.getContext() ) );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.setData(getItem(position));
    }

    @Override
    public long getItemId(int position)
    {
        return getItem(position).getId();
    }


    //StickyRecyclerHeadersAdapter implementation
    @Override
    public long getHeaderId(int position)
    {

        return dateChangedIndex.get( position );
    }

    @Override
    public DayViewHolder onCreateHeaderViewHolder(ViewGroup parent)
    {
        return new DayViewHolder( new ConferenceSessionListItemHeader( parent.getContext() ) );
    }

    @Override
    public void onBindHeaderViewHolder(DayViewHolder holder, int position)
    {
        holder.setData( getItem( position ) );
    }

    @Override
    public int getItemCount() {
        return conferenceSessions.size();
    }

    public ConferenceSessionViewModel getItem(int position) {
        return conferenceSessions.get(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ConferenceSessionViewModel conferenceSessionViewModel;
        private ConferenceSessionViewItem itemView;

        // each data item is just a string in this case
        public ViewHolder(View v)
        {
            super(v);

            if( v instanceof ConferenceSessionViewItem ) {
                itemView = (ConferenceSessionViewItem) v;
            }

            itemView.setOnClickListener( this );

        }


        public void setData(ConferenceSessionViewModel conferenceSessionViewModel)
        {
            this.conferenceSessionViewModel = conferenceSessionViewModel;

            itemView.setSessionInfo( conferenceSessionViewModel );
        }

        @Override
        public void onClick(View v) {
            onClickListener.clicked(conferenceSessionViewModel.getId());
        }
    }




    public class DayViewHolder extends RecyclerView.ViewHolder
    {

        private ConferenceSessionListItemHeader headerView;

        // each data item is just a string in this case
        public DayViewHolder(View v)
        {
            super(v);

            if( v instanceof  ConferenceSessionListItemHeader )
            {
                headerView = (ConferenceSessionListItemHeader)v;
            }
        }



        public void setData(ConferenceSessionViewModel conferenceSessionViewModel)
        {
            headerView.setHeaderInfo(conferenceSessionViewModel.getDay());
        }
    }

}
