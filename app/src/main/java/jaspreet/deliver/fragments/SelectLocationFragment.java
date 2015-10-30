package jaspreet.deliver.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import jaspreet.deliver.R;
import jaspreet.deliver.activities.MainActivity;
import jaspreet.deliver.route.AbstractRouting;
import jaspreet.deliver.route.Route;
import jaspreet.deliver.route.Routing;
import jaspreet.deliver.route.RoutingListener;
import jaspreet.deliver.utils.Config;
import jaspreet.deliver.utils.Util;
import jaspreet.deliver.utils.ViewUtil;

/**
 * Created by jaspret on 17/10/15.
 */
public class SelectLocationFragment extends Fragment  implements RoutingListener{

    private String TAG;
    int PLACE_PICKER_REQUEST_PICKUP = 1;
    int PLACE_PICKER_REQUEST_DESTINATION = 2;
    private GoogleMap googleMap;
    private MainActivity mainActivity;
    private TextView pickupLocation,dropofLocation,distanceTextView;
    private static SelectLocationFragment selectLocationFragment;
    protected LatLng start;
    protected LatLng end;
    private ProgressDialog progressDialog;
    private ArrayList<Polyline> polylines;
    private FloatingActionButton fab;
    private static View view;
    private String distance;
    private int[] colors = new int[]{R.color.colorPrimary,R.color.colorAccent,R.color.colorBlackTransparent,R.color.colorPrimaryTransparent,R.color.primary_dark_material_light};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
         view=inflater.inflate(R.layout.fragment_select_address,null);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        mainActivity=(MainActivity)getActivity();
        TAG=getClass().getName();
        polylines=new ArrayList<>();
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (googleMap == null)
            googleMap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
        pickupLocation=(TextView)view.findViewById(R.id.pickupLocation);
        dropofLocation=(TextView)view.findViewById(R.id.dropofLocation);
        distanceTextView=(TextView)view.findViewById(R.id.distance);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SelectDateFragment();
                android.support.v4.app.FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment, "selectDestination"+Math.random()).addToBackStack("selectDestination");
                ft.commit();

            }
        });
        onPickupClick();
        onDropOffClick();

    }

    public static SelectLocationFragment getInstance(){
        if(selectLocationFragment!=null)
            return  selectLocationFragment;
        else return selectLocationFragment= new SelectLocationFragment();
    }

    private SelectLocationFragment(){}
    public void onPickupClick(){

        pickupLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    mainActivity.startActivityForResult(builder.build(mainActivity), PLACE_PICKER_REQUEST_PICKUP);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void onDropOffClick(){

        dropofLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    mainActivity.startActivityForResult(builder.build(mainActivity), PLACE_PICKER_REQUEST_DESTINATION);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void gotPlace(int requestCode,int resultCode,Place place){
        if (resultCode == Activity.RESULT_OK && requestCode == PLACE_PICKER_REQUEST_PICKUP) {

                pickupLocation.setText(place.getAddress());
            start=place.getLatLng();

        }else if (resultCode == Activity.RESULT_OK && requestCode == PLACE_PICKER_REQUEST_DESTINATION) {

                dropofLocation.setText(place.getAddress());
            end=place.getLatLng();
        }

        if(!pickupLocation.getText().toString().trim().equals("") && !dropofLocation.getText().toString().trim().equals("")){
            sendRequest(pickupLocation);
            fab.setVisibility(View.VISIBLE);
        }else{
            CameraUpdate center = CameraUpdateFactory.newLatLng(place.getLatLng());
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
            MarkerOptions options = new MarkerOptions();
            options.position(place.getLatLng());
            options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_on_white_24dp));
            googleMap.addMarker(options);
        }

    }

    public void sendRequest(View view)
    {
        if(Util.haveNetworkConnection(mainActivity))
        {
            route();
        }
        else
        {
            ViewUtil.showStackBar(view,getString(R.string.no_internet_connectiion),mainActivity );
        }
    }

    public void route()
    {

            progressDialog = ProgressDialog.show(mainActivity, "Please wait.",
                    "Fetching route information.", true);
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(start, end)
                    .build();
            routing.execute();

    }

    @Override
    public void onRoutingFailure() {
        progressDialog.dismiss();
    }

    @Override
    public void onRoutingStart() {
//        progressDialog.dismiss();
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        progressDialog.dismiss();
        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {
            if (i == 0){
                distance = route.get(0).getDistanceText();
            distanceTextView.setText(distance);
        }
            //In case of more than 5 alternative routes
            int colorIndex = i % colors.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(colors[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = googleMap.addPolyline(polyOptions);
            polylines.add(polyline);

//            Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(start);
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_on_white_24dp));
        googleMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(end);
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_on_white_24dp));
        googleMap.addMarker(options);
    }

    @Override
    public void onRoutingCancelled() {
        progressDialog.dismiss();
    }
}
