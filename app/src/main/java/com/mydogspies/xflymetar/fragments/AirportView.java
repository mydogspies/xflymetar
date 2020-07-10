package com.mydogspies.xflymetar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.mydogspies.xflymetar.R;
import com.mydogspies.xflymetar.ViewLogic;

/**
 * The fragment that contains the AIRPORT data output.
 * @author github.com/mydogspies.com
 * @since 0.1.0
 */
public class AirportView extends Fragment implements ViewLogic {

    // TODO this method needs to be re-implemented for the coming airport database!

    /*
    NOTE! This method is not fully implemented since we dropped the airport API in favor
    of developing an internal database. A LOT will change in here!!!
     */

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
