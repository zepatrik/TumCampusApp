package de.tum.in.tumcampusapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.Collections;
import java.util.List;

import de.tum.in.tumcampusapp.R;
import de.tum.in.tumcampusapp.activities.generic.ActivityForSearchingTumOnline;
import de.tum.in.tumcampusapp.adapters.GradingAdapter;
import de.tum.in.tumcampusapp.adapters.NoResultsAdapter;
import de.tum.in.tumcampusapp.auxiliary.LectureSearchSuggestionProvider;
import de.tum.in.tumcampusapp.models.tumo.LecturesSearchRow;
import de.tum.in.tumcampusapp.models.tumo.LecturesSearchRowSet;
import de.tum.in.tumcampusapp.tumonline.TUMOnlineConst;
import de.tum.in.tumcampusapp.tumonline.TUMOnlineRequest;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class GradingActivity  extends ActivityForSearchingTumOnline<LecturesSearchRowSet> {



    private StickyListHeadersListView lvMyLecturesList;
private final static String P_SUCHE = "pSuche";

    public GradingActivity() {
        super(TUMOnlineConst.LECTURES_PERSONAL, R.layout.activity_lectures, LectureSearchSuggestionProvider.AUTHORITY, 4);
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        // bind UI elements
        lvMyLecturesList = (StickyListHeadersListView) findViewById(R.id.lvMyLecturesList);

        // handle on click events by showing its LectureDetails
        lvMyLecturesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lvMyLecturesList.getItemAtPosition(position);
                LecturesSearchRow item = (LecturesSearchRow) o;

                // set bundle for LectureDetails and show it
                Bundle bundle = new Bundle();
                // we need the stp_sp_nr
                bundle.putString(LecturesSearchRow.STP_SP_NR, item.getStp_sp_nr());
                Intent intent = new Intent(GradingActivity.this, GradingDetailsActivity.class);
                intent.putExtras(bundle);
                // start LectureDetails for given stp_sp_nr
                startActivity(intent);
            }
        });

        onStartSearch();









    }

    @Override
    protected void onStartSearch() {
        enableRefresh();
        requestHandler = new TUMOnlineRequest<>(TUMOnlineConst.LECTURES_PERSONAL, this, true);
        requestFetch();
    }

    @Override
    protected void onStartSearch(String query) {
        disableRefresh();
        requestHandler = new TUMOnlineRequest<>(TUMOnlineConst.LECTURES_SEARCH, this, true);
        requestHandler.setParameter(P_SUCHE, query);
        requestFetch();
    }

    @Override
    public void onLoadFinished(LecturesSearchRowSet response) {
        if (response == null || response.getLehrveranstaltungen() == null) {
            // no results found
            lvMyLecturesList.setAdapter(new NoResultsAdapter(this));
        } else {
            // Sort lectures by semester id
            List<LecturesSearchRow> lectures = response.getLehrveranstaltungen();
            Collections.sort(lectures);

            // set ListView to data via the LecturesListAdapter
            lvMyLecturesList.setAdapter(GradingAdapter.newInstance(this, lectures));
        }
    }

}