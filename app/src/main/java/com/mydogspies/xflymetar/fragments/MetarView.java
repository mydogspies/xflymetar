package com.mydogspies.xflymetar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.mydogspies.xflymetar.R;
import com.mydogspies.xflymetar.VIEWSTATE;
import com.mydogspies.xflymetar.ViewLogic;
import com.mydogspies.xflymetar.apis.APIData;
import com.mydogspies.xflymetar.apis.APIDataIO;
import com.mydogspies.xflymetar.apis.APIDataSingleton;
import com.mydogspies.xflymetar.apis.DataObserverIO;
import com.mydogspies.xflymetar.data.Metar;
import com.mydogspies.xflymetar.data.Taf;

/**
 * The fragment that contains the METAR data output.
 * Notice that the class must add itself to the observer network via the addObserver() method
 * in the constructor.
 * @author github.com/mydogspies.com
 * @since 0.1.0
 */
public class MetarView extends Fragment implements ViewLogic, DataObserverIO {

    private static APIData apiHandler = APIDataSingleton.getInstance().getHandler();

    private View view;
    private ConstraintLayout viewContainer;
    private TextView rawMetarString;
    private VIEWSTATE viewstate;

    public MetarView(VIEWSTATE state) {
        super();
        this.viewstate = state;
        apiHandler.addObserver(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.metar_readout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rawMetarString = view.findViewById(R.id.rawMetarText);
        viewContainer = view.findViewById(R.id.metarReadoutContainer);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setVisiblity(boolean value) {
        if (value) {
            viewContainer.setVisibility(View.VISIBLE);
        } else {
            viewContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateMetarFromAPI(VIEWSTATE state, Metar metar) {
        if (state.equals(viewstate)) {
            if (metar != null) {
                rawMetarString.setText(metar.getRawText());
            } else {
                rawMetarString.setText(R.string.general_http_error);
            }
        }
    }

    @Override
    public void updateTafFromAPI(VIEWSTATE state, Taf taf) {
        // NOT USED IN THIS CLASS
    }
}
