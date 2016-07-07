package com.cormaclavery.mapstest;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if (mGoogleApiClient == null) {
            Log.d(TAG, "Building mGoogleApiClient request");
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            Log.d(TAG, "Building mGoogleApiCLient request complete");

        }

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        //HTTP request to google directions API
        //Parse JSON to POJO

        String origin = "52.168757,-9.701667";
        String destination = "53.318875, -6.265677";

        getJsonResponse(origin, destination);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "mGoogleApiCLient connection Attempt");
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void setUpMap(LatLng latLng){
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.addMarker(new MarkerOptions().position(latLng).title("You are here"));



    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.d(TAG, "Connected to Google API");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(TAG, "In permissions conditional");
            return;
        }
        Log.d(TAG, "after permission check");

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        Log.d(TAG, "after location services.FusedLocationAPi used and Location update request made");

        if (mLastLocation != null){
            handleNewLocation(mLastLocation);
        }else{
            Log.d(TAG, "Location is null");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "mGoogleApiCLient connection Suspended");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "mGoogleApiCLient connection Failed");
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged called");
        handleNewLocation(location);
    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        Log.d(TAG, latLng.toString());
        Log.d(TAG, latLng.latitude + "," +latLng.longitude);

        setUpMap(latLng);


    }

    private void getJsonResponse(String location1, String location2) {

        String APIkey = getString(R.string.google_maps_web_api_key);

        String requestURL = "https://maps.googleapis.com/maps/api/directions/json?origin="
                +location1+
                "&destination="
                +location2+ "&mode=Driving&key=" + APIkey;


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestURL)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "Failed to retrieve JSON");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                Log.d(TAG, "this is the response to the request" + jsonData);
                try {
                    ConvertJson(jsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void ConvertJson(String data) throws JSONException {
        Log.d(TAG, "attempting to initialise directionsResponse JSON object");
        JSONObject directionsResponse = new JSONObject(data);
        Log.d(TAG, "attempting to initialise routes JSON ARRAY");
        JSONArray routes = directionsResponse.getJSONArray("routes");
        Log.d(TAG, "attempting to initialise route JSON Object");
        JSONObject route = routes.getJSONObject(0);
        Log.d(TAG, "attempting to initialise legs JSON Array");
        JSONArray legs = route.getJSONArray("legs");
        Log.d(TAG, "attempting to initialise leg JSON Object");
        JSONObject leg = legs.getJSONObject(0);
        Log.d(TAG, "attempting to initialise duration JSON Object");
        JSONObject duration = leg.getJSONObject("duration");
        Log.d(TAG, "attempting to get duration object variable text");
        String time = duration.getString("text");
        String startAddress = leg.getString("start_address");
        String endAddress = leg.getString("end_address");
        Log.d(TAG, "this is the time it takes to travel from "+ startAddress +" to " + endAddress +":"+ time);


    }


}
