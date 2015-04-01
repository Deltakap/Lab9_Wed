package com.example.student.lab9_wed;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity
        implements GoogleMap.OnMapLongClickListener,LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    LocationManager locationManager;
    int place=0;
    LatLng oldPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mMap.setOnMapLongClickListener(this);

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        LatLng BKK = new LatLng(13.7563, 100.5018);
        LatLng HK = new LatLng(22.2783, 114.1747);

        mMap.addMarker(new MarkerOptions().position(BKK).title("Bangkok"));
        mMap.addMarker(new MarkerOptions().position(HK).title("Hong Kong"));

        //Set Center of the map
        CameraUpdate start_map = CameraUpdateFactory.newLatLng(BKK);
        mMap.moveCamera(start_map);

        //Set initial zoom level
        CameraUpdate start_zoom = CameraUpdateFactory.zoomTo(5);
        mMap.animateCamera(start_zoom);

        //Draw lines between points
        mMap.addPolyline(new PolylineOptions().add(BKK).add(HK));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title("Place"+ ++place));

        CameraUpdate start_map = CameraUpdateFactory.newLatLng(latLng);
        mMap.moveCamera(start_map);

        if(place != 1)
            mMap.addPolyline(new PolylineOptions().add(latLng).add(oldPoint));

        CameraUpdate start_zoom = CameraUpdateFactory.zoomTo(5);
        mMap.animateCamera(start_zoom);

        oldPoint = latLng;
    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng newLL = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(newLL).title("Place"+ ++place));

        CameraUpdate start_map = CameraUpdateFactory.newLatLng(newLL);
        mMap.moveCamera(start_map);

        if(place != 1)
            mMap.addPolyline(new PolylineOptions().add(newLL).add(oldPoint));

        CameraUpdate start_zoom = CameraUpdateFactory.zoomTo(5);
        mMap.animateCamera(start_zoom);

        oldPoint = newLL;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
