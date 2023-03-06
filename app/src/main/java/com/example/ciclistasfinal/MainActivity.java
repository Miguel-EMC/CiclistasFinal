package com.example.ciclistasfinal;

import android.Manifest;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class MainActivity extends AppCompatActivity {
CardView ubicacionesCicl,registro;
    FirebaseAuth auth;
    Button button;
    Button button2;
    TextView textView;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String editLat = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        button2 = findViewById(R.id.inicio);


        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }else {
            textView.setText(user.getEmail());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);

                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        location.getLatitude();
                        location.getLongitude();

                        Map<String, Object> positions = new HashMap<>();
                        positions.put("latitud",    location.getLatitude());
                        positions.put("longitud",  location.getLongitude());


                        Log.d("TAG", "NEW");

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users")
                                .document(user.getUid())
                                .collection("position")
                                .document(user.getUid()+"Position")
                                .set(positions)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(MainActivity.this, "Account Saved.", Toast.LENGTH_SHORT).show();
                                        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        //startActivity(intent);
                                        //finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });



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
                int permiso = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                StyleableToast.makeText(getApplicationContext(), "Ubicacion generada Con exito",
                        Toast.LENGTH_LONG, R.style.mytoast).show();

            }
        });
        //muestre el titulo
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_titulo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
         this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

         //metodos para abrir paginas
        //metodo admin y castear
        //registro=(CardView) findViewById(R.id.idcard1);
        ubicacionesCicl=(CardView) findViewById(R.id.idcard2);


        //llaammos al metodo
        getubicacionesCicl();
        getLocalizacion();
        //geRegistro();
    }

    private void getLocalizacion() {
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permiso == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }


    private void getubicacionesCicl() {
        ubicacionesCicl.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }

            });
        }


    }



