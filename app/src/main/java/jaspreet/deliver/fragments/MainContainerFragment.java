package jaspreet.deliver.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import jaspreet.deliver.R;
import jaspreet.deliver.activities.MainActivity;
import jaspreet.deliver.utils.Config;

/**
 * Created by jaspret on 17/10/15.
 */
public class MainContainerFragment extends Fragment {

    private MainActivity activity;
    private String TAG;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_container,null);
        activity=(MainActivity)getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TAG=getClass().getName();
        view.findViewById(R.id.destination).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Fragment fragment = SelectLocationFragment.getInstance();
                android.support.v4.app.FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment, "selectDestination"+Math.random()).addToBackStack("selectDestination");
                ft.commit();

            }
        });
    }

}
