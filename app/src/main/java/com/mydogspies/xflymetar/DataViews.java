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

public class DataViews extends Fragment {

    private ViewLogic metarViewVisibility;

    private ViewStyles STYLES = new ViewStyles();

    private View view;
    private ViewState state;
    private ImageView expandImage;
    private boolean expanded = false;

    public DataViews(ViewState state) {
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

        expandImage = view.findViewById(R.id.expandImage);

        if (state.equals(ViewState.DEPARTURE)) {
            metarViewVisibility = (MetarView) MainActivity.dataViewObjects.get(ViewState.DEPARTURE);

            LinearLayout topContainer = view.findViewById(R.id.topHeaderContainer);
            topContainer.setBackgroundColor(Color.parseColor(STYLES.FRAG_BACKGROUND_COLOR));

            TextView headerText = view.findViewById(R.id.dataTypeText);
            headerText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));
            headerText.setText(R.string.departure_metar);

        } else if (state.equals(ViewState.ARRIVAL)) {
            metarViewVisibility = (MetarView) MainActivity.dataViewObjects.get(ViewState.ARRIVAL);

            LinearLayout topContainer = view.findViewById(R.id.topHeaderContainer);
            topContainer.setBackgroundColor(Color.parseColor(STYLES.FRAG_BACKGROUND_COLOR));

            TextView headerText = view.findViewById(R.id.dataTypeText);
            headerText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));
            headerText.setText(R.string.arrival_metar);

        } else if (state.equals(ViewState.TAF)) {
            metarViewVisibility = (TafView) MainActivity.dataViewObjects.get(ViewState.TAF);

            LinearLayout topContainer = view.findViewById(R.id.topHeaderContainer);
            topContainer.setBackgroundColor(Color.parseColor(STYLES.FRAG_BACKGROUND_COLOR));

            TextView headerText = view.findViewById(R.id.dataTypeText);
            headerText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));
            headerText.setText(R.string.arrival_taf);
        }

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
            metarViewVisibility.setVisiblity(true);
            expandImage.setImageResource(R.drawable.icons8_collapse_20);
            expanded = false;
        } else {
            metarViewVisibility.setVisiblity(false);
            expandImage.setImageResource(R.drawable.icons8_expand_20);
            expanded = true;
        }
    }

    /* GETTERS and SETTERS */

    public ViewState getState() {
        return state;
    }
}
