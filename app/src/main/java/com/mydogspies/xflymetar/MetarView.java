package com.mydogspies.xflymetar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class MetarView extends Fragment {

    private View view;
    private ViewState state;

    public MetarView(ViewState state) {
        super();
        this.state = state;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.departure_metar_view, container, false);
        return view;
        // return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (state.equals(ViewState.DEPARTURE)) {
            ConstraintLayout topContainer = view.findViewById(R.id.metarViewContainer);
            topContainer.setBackgroundColor(Color.YELLOW);
            TextView text = view.findViewById(R.id.testText);
            text.setText("DEPARTURE");
        } else if (state.equals(ViewState.ARRIVAL)) {
            ConstraintLayout topContainer = view.findViewById(R.id.metarViewContainer);
            topContainer.setBackgroundColor(Color.CYAN);
            TextView text = view.findViewById(R.id.testText);
            text.setText("ARRIVAL - METAR");
        } else if (state.equals(ViewState.TAF)) {
            ConstraintLayout topContainer = view.findViewById(R.id.metarViewContainer);
            topContainer.setBackgroundColor(Color.MAGENTA);
            TextView text = view.findViewById(R.id.testText);
            text.setText("ARRIVAL - TAF");
        }


        super.onViewCreated(view, savedInstanceState);
    }
}
