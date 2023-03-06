package com.example.ciclistasfinal;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;
    private LocationManager locationManager;
    private DatabaseReference databaseReference;
    private ArrayList<Marker> temporalRealTimeMarkers = new ArrayList<>();
    private ArrayList<Marker> realTimaMarkers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.w("TAG", "INIT MAP");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Quito = new LatLng(-0.255992, -78.529917);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.meta);
        int width = icon.getWidth() / 8; // Ancho original del icono / 2
        int height = icon.getHeight() / 8; // Altura original del icono / 2
        Bitmap scaledIcon = Bitmap.createScaledBitmap(icon, width, height, false);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(Quito)
                .title("META")
                .icon(BitmapDescriptorFactory.fromBitmap(scaledIcon))
                .anchor(0.5f, 0.5f); // Para centrar el icono en la ubicación
        mMap.addMarker(markerOptions);




        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        LocationManager locationManager = (LocationManager) MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);


        int permiso = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);


        //FIRESTORE get current user position
        final DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    LatLng userLocation = new LatLng((Double) snapshot.getData().get("latitud"), (Double) snapshot.getData().get("longitud"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(userLocation)
                            .zoom(14)
                            .bearing(90)
                            .tilt(45)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ciclista);
                    int width = icon.getWidth() / 8; // Ancho original del icono / 2
                    int height = icon.getHeight() / 8; // Altura original del icono / 2
                    Bitmap scaledIcon = Bitmap.createScaledBitmap(icon, width, height, false);
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(userLocation)
                            .title("Ciclista")
                            .icon(BitmapDescriptorFactory.fromBitmap(scaledIcon))
                            .anchor(0.5f, 0.5f); // Para centrar el icono en la ubicación
                   // mMap.addMarker(markerOptions);

                    Log.d("TAG", "Current data: " + snapshot.getData().get("latitud")+" : " + snapshot.getData().get("longitud"));
                } else {
                    Log.d("TAG", "Current data: null");
                }
            }
        });

        //all users firestore
        db.collection("users").whereEqualTo("rol","ciclist")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG", "Listen failed.", e);
                            return;
                        }



                        for (DocumentChange dc : value.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    Log.d("TAG", "New city: " );
                                    break;
                                case MODIFIED:
                                    for(Marker dest:realTimaMarkers){
                                        dest.remove();
                                    }
                                    Log.d("TAG", "Modified city: " );
                                    break;
                                case REMOVED:
                                    Log.d("TAG", "Removed city: " );
                                    break;
                            }
                        }




                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("name") != null) {
                                Double latitud = (Double) doc.get("latitud");
                                Double longitud = (Double) doc.get("longitud");
                                String name = (String) doc.get("name");
                                String lastName = (String) doc.get("lastName");
                                MarkerOptions markerOptions = new MarkerOptions();
                                if( doc.getId().equals(user.getUid()) ){
                                    LatLng userLocation = new LatLng(latitud,longitud);
                                    Log.w("MY USER", "MARKET."+ doc.getId());
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                                    CameraPosition cameraPosition = new CameraPosition.Builder()
                                            .target(userLocation)
                                            .zoom(14)
                                            .bearing(90)
                                            .tilt(45)
                                            .build();
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                    Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ciclista);
                                    int width = icon.getWidth() / 8; // Ancho original del icono / 2
                                    int height = icon.getHeight() / 8; // Altura original del icono / 2
                                    Bitmap scaledIcon = Bitmap.createScaledBitmap(icon, width, height, false);
                                    markerOptions
                                            .position(userLocation)
                                            .title(name).snippet(lastName)
                                            .icon(BitmapDescriptorFactory.fromBitmap(scaledIcon))
                                            .anchor(0.5f, 0.5f); // Para centrar el icono en la ubicación
                                    temporalRealTimeMarkers.add(mMap.addMarker(markerOptions));
                                }else{
                                    markerOptions.position(new LatLng(latitud, longitud)).title(name).snippet(lastName);
                                    temporalRealTimeMarkers.add(mMap.addMarker(markerOptions));
                                }



                            }
                        }
                        realTimaMarkers.clear();
                        realTimaMarkers.addAll(temporalRealTimeMarkers);


                    }
                });








    }

}