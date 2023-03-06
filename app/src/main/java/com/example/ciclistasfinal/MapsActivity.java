//package com.example.ciclistasfinal;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.FragmentActivity;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private LocationManager locationManager;
//    private DatabaseReference databaseReference;
//    private ArrayList<Marker> temporalRealTimeMarkers = new ArrayList<>();
//    private ArrayList<Marker> realTimaMarkers = new ArrayList<>();
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//    }
//
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        LatLng jalapa = new LatLng(-0.255992, -78.529917);
//        mMap.addMarker(new MarkerOptions().position(jalapa).title("Carnitas"));
////                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_prueba2)).anchor(0.0f, 0.0f));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(jalapa));
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(jalapa)
//                .zoom(14)
//                .bearing(90)
//                .tilt(45)
//                .build();
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//
//        mMap.getUiSettings().setMyLocationButtonEnabled(false);
//
//        LocationManager locationManager = (LocationManager) MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);
//        LocationListener locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
//                mMap.addMarker(new MarkerOptions().position(miUbicacion).title("Repartidor"));
////                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ciclista)).anchor(0.0f, 0.0f));
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//
//        int permiso = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
//
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//
//        databaseReference.child("destinos").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(Marker dest:realTimaMarkers){
//                    dest.remove();
//                }
//                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    Destinos dt = snapshot.getValue(Destinos.class);
//                    Double latitud = dt.getLatitud();
//                    Double longitud = dt.getLongitud();
//                    String codigo = dt.getCodigo();
//                    String telefono = dt.getTelefono();
//
//                    String paquete = "No. Paquete " + codigo;
//                    String telefonoUno = "Tel. " + telefono;
//
//
//                    MarkerOptions markerOptions = new MarkerOptions();
//
//                    markerOptions.position(new LatLng(latitud, longitud)).title(paquete).snippet(telefonoUno)
//                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
//
//                    temporalRealTimeMarkers.add(mMap.addMarker(markerOptions));
//                }
//                realTimaMarkers.clear();
//                realTimaMarkers.addAll(temporalRealTimeMarkers);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//}



package com.example.ciclistasfinal;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private DatabaseReference databaseReference;
    private ArrayList<Marker> temporalRealTimeMarkers = new ArrayList<>();
    private ArrayList<Marker> realTimaMarkers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng jalapa = new LatLng(-0.255992, -78.529917);
        mMap.addMarker(new MarkerOptions().position(jalapa).title("LLEGADA"));
//                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ciclista)).anchor(0.0f, 0.0f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(jalapa));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(jalapa)
                .zoom(14)
                .bearing(90)
                .tilt(45)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

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
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(miUbicacion).title("Repartidor"));
//                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_prueba2)).anchor(0.0f, 0.0f));
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
        };

        int permiso = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        databaseReference.child("destinos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(Marker dest:realTimaMarkers){
                    dest.remove();
                }
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Destinos dt = snapshot.getValue(Destinos.class);
                    Double latitud = dt.getLatitud();
                    Double longitud = dt.getLongitud();
                    String codigo = dt.getCodigo();
                    String telefono = dt.getTelefono();

                    String paquete = "No. Paquete " + codigo;
                    String telefonoUno = "Tel. " + telefono;


                    MarkerOptions markerOptions = new MarkerOptions();

                    markerOptions.position(new LatLng(latitud, longitud)).title(paquete).snippet(telefonoUno);
//                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_llegada));

                    temporalRealTimeMarkers.add(mMap.addMarker(markerOptions));
                }
                realTimaMarkers.clear();
                realTimaMarkers.addAll(temporalRealTimeMarkers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}