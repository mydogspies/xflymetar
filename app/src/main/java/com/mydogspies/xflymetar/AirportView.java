package com.mydogspies.xflymetar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class AirportView extends Fragment implements ViewLogic{

    private View view;
    private ConstraintLayout viewContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.airport_readout, container, false);
        return view;
        // return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewContainer = view.findViewById(R.id.airportReadoutContainer);
        setVisiblity(false); // start with airport info hidden by default
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setVisiblity(boolean value) {
        if(value) {
            viewContainer.setVisibility(View.VISIBLE);
        } else {
            viewContainer.setVisibility(View.GONE);
        }
    }
}
