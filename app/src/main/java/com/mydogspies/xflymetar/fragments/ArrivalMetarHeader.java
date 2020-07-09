package com.mydogspies.xflymetar.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mydogspies.xflymetar.MainActivity;
import com.mydogspies.xflymetar.MetarView;
import com.mydogspies.xflymetar.R;
import com.mydogspies.xflymetar.VIEWSTATE;
import com.mydogspies.xflymetar.ViewLogic;
import com.mydogspies.xflymetar.ViewStyles;
import com.mydogspies.xflymetar.apis.APIData;
import com.mydogspies.xflymetar.apis.APIDataSingleton;
import com.mydogspies.xflymetar.apis.DataObserverIO;
import com.mydogspies.xflymetar.data.Metar;
import com.mydogspies.xflymetar.data.Taf;

public class ArrivalMetarHeader extends Fragment implements DataObserverIO {

    private static APIData apiHandler = APIDataSingleton.getInstance().getHandler();
    private ViewLogic dataViewVisibility;
    private ViewStyles STYLES = new ViewStyles();

    private View view;
    private VIEWSTATE state;
    private ImageView expandImage;
    private TextView headerText;
    private boolean expanded = false;

    public ArrivalMetarHeader(VIEWSTATE state) {
        super();
        this.state = state;
        apiHandler.addObserver(this);
    }


    /* FRAGMENT LOGIC */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.header_views, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        expandImage = view.findViewById(R.id.expandImage);
        dataViewVisibility = (MetarView) MainActivity.dataViewObjects.get(VIEWSTATE.ARRIVAL_METAR);
        initElements();

        // sets the action listener for the expand/collapse icon on each header element
        expandImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleExpand();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void toggleExpand() {

        if (expanded) {
            dataViewVisibility.setVisiblity(true);
            expandImage.setImageResource(R.drawable.icons8_collapse_20);
            expanded = false;
        } else {
            dataViewVisibility.setVisiblity(false);
            expandImage.setImageResource(R.drawable.icons8_expand_20);
            expanded = true;
        }
    }


    /* OBSERVER METHODS */

    @Override
    public void updateMetarFromAPI(VIEWSTATE state, Metar metar) {
        if (state.equals(VIEWSTATE.ARRIVAL_METAR)) {
            if (metar.getStationID() != null) {
                headerText.setText(getString(R.string.arrival_metar, metar.getStationID(), metar.getObservationTime()));
            } else {
                headerText.setText(getString(R.string.arrival_metar_no_data));
            }
        }

    }

    @Override
    public void updateTafFromAPI(VIEWSTATE state, Taf taf) {

    }


    /* INIT ELEMENTS */

    private void initElements() {
        LinearLayout topContainer = view.findViewById(R.id.topHeaderContainer);
        topContainer.setBackgroundColor(Color.parseColor(STYLES.FRAG_BACKGROUND_COLOR));

        headerText = view.findViewById(R.id.dataTypeText);
        headerText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));
        headerText.setText(R.string.arrival_metar);
    }


    /* GETTERS and SETTERS */

    public VIEWSTATE getState() {
        return state;
    }
}
