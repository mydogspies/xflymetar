package com.mydogspies.xflymetar;

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

import com.mydogspies.xflymetar.apis.APIDataIO;
import com.mydogspies.xflymetar.apis.GetAPIDataSingleton;

import java.util.Date;
import java.util.Map;

public class HeaderElementViews extends Fragment {

    private static APIDataIO apiIO = GetAPIDataSingleton.getInstance().getHandler();
    private Map<ViewState, Map<Date, Object>> apiData = MainActivity.savedAPIData;

    private ViewLogic dataViewVisibility;

    private ViewStyles STYLES = new ViewStyles();

    private View view;
    private ViewState state;
    private ImageView expandImage;
    private boolean expanded = false;

    public HeaderElementViews(ViewState state) {
        super();
        this.state = state;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.data_view, container, false);
        return view;
        // return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // first check however we already have up to date data and if not then fetch it
        fetchUpdatedAPIData();

        expandImage = view.findViewById(R.id.expandImage);

        if (state.equals(ViewState.DEPARTURE)) {

            dataViewVisibility = (MetarView) MainActivity.dataViewObjects.get(ViewState.DEPARTURE);
            initDepartureElements();

        } else if (state.equals(ViewState.ARRIVAL)) {
            dataViewVisibility = (MetarView) MainActivity.dataViewObjects.get(ViewState.ARRIVAL);
            initArrivalElements();

        } else if (state.equals(ViewState.TAF)) {
            dataViewVisibility = (TafView) MainActivity.dataViewObjects.get(ViewState.TAF);
            initTafElements();
        }

        // sets the action listener for the expand/collapse icon on each header element
        expandImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleExpand();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void fetchUpdatedAPIData() {

    }

    /**
     * Toggles the visibility of the data fragment below the header element as well as
     * swapping the expand/collapse icons.
     */
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

    /* LAYOUT INIT */

    private void initDepartureElements() {
        LinearLayout topContainer = view.findViewById(R.id.topHeaderContainer);
        topContainer.setBackgroundColor(Color.parseColor(STYLES.FRAG_BACKGROUND_COLOR));

        TextView headerText = view.findViewById(R.id.dataTypeText);
        headerText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));
        headerText.setText(R.string.departure_metar);
    }

    private void initArrivalElements() {
        LinearLayout topContainer = view.findViewById(R.id.topHeaderContainer);
        topContainer.setBackgroundColor(Color.parseColor(STYLES.FRAG_BACKGROUND_COLOR));

        TextView headerText = view.findViewById(R.id.dataTypeText);
        headerText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));
        headerText.setText(R.string.arrival_metar);
    }

    private void initTafElements() {
        LinearLayout topContainer = view.findViewById(R.id.topHeaderContainer);
        topContainer.setBackgroundColor(Color.parseColor(STYLES.FRAG_BACKGROUND_COLOR));

        TextView headerText = view.findViewById(R.id.dataTypeText);
        headerText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));
        headerText.setText(R.string.arrival_taf);
    }

    /* GETTERS and SETTERS */

    public ViewState getState() {
        return state;
    }
}
