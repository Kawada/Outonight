package edu.fst.m2.ipii.outonight.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.service.EstablishmentService;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentServiceImpl;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Inject
    EstablishmentService establishmentService = EstablishmentServiceImpl.getInstance();

    Map<Marker, Integer> establishmentMarkers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setUpMapIfNeeded();

        setUpMapZoom();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapZoom() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Map.Entry<Marker, Integer> entry : establishmentMarkers.entrySet()) {
            builder.include(entry.getKey().getPosition());
        }
        LatLngBounds bounds = builder.build();

        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        mMap.moveCamera(cu);
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

        for (Establishment establishment : establishmentService.getAllCached()) {

            establishmentMarkers.put(mMap.addMarker(new MarkerOptions().position(new LatLng(establishment.getAddress().getLat(), establishment.getAddress().getLng())).title(establishment.getName())),
                                    establishment.getEstablishmentId());
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra("establishmentId", establishmentMarkers.get(marker));

        startActivity(intent);

        Toast.makeText(this, "Passage à la vue détail....", Toast.LENGTH_SHORT).show();

        return false;
    }
}
