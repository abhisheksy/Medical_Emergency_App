package io.github.httpsabhisheksy.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements LocationListener{

    private FirebaseAuth firebaseAuth;
    LocationManager locationManager;
    public static double lati = 0, longi = 0;
    boolean pause = false;
    static double min=1000000;
    public static String uid,uidh,sid;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    //BroadcastReceiver broadcastReceiver;

    Button emergency;
    Button simul;
    Button req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=new Intent();
        firebaseAuth=FirebaseAuth.getInstance();
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            intent.setClass(this,AccountTypeActivity.class);
            startActivity(intent);
        }

        else {

            req = (Button) findViewById(R.id.request);
            req.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, NewPatientsActivity.class));
                }
            });

            emergency = (Button) findViewById(R.id.emergency);
            emergency.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "In App Manual Alert Raise Requested !!", Toast.LENGTH_LONG).show();
                    sendAlerts();
                }
            });

            simul = (Button) findViewById(R.id.simulate);
            simul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Simulated extreme pulse rate and oxygen level in blood", Toast.LENGTH_LONG).show();
                    sendAlerts();
                }
            });

            //----------------------------------Token show on app open-------------------
            /*broadcastReceiver = new BroadcastReceiver(){
                @Override
                public void onReceive(Context context, Intent intent){
                    Toast.makeText(MainActivity.this,SharedPrefManager.getInstance(MainActivity.this).getToken(),Toast.LENGTH_LONG).show();
                }
            };
            if(SharedPrefManager.getInstance(this).getToken()!=null)
                Toast.makeText(MainActivity.this,SharedPrefManager.getInstance(MainActivity.this).getToken(),Toast.LENGTH_LONG).show();
            registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIdService.TOKEN_BROADCAST));*/


            //------------------------------------Hospital UID Change Listener-----------
            SharedPreferences shared;
            shared = getSharedPreferences("activity_type", Context.MODE_PRIVATE);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            uidh = user.getUid();

            if (shared.getBoolean("radio_hospital", false)) {

                req.setVisibility(View.VISIBLE);
                emergency.setVisibility(View.INVISIBLE);
                simul.setVisibility(View.INVISIBLE);

                myRef = database.getReference("hospitals/" + uidh + "/uid");
                myRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        sid = dataSnapshot.getValue(String.class);
                        if (sid != null && uidh != null)
                            if (!sid.equals(uidh))
                                startActivity(new Intent(MainActivity.this, NewPatientsActivity.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Something is really wrong here !!", Toast.LENGTH_LONG).show();
                    }
                });
            }


            getloc();
        }

    }



    public void getloc(){
        //-------------------------------------location logic-------------------------
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 42);
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }


    private Button buttonLogout;
    public void signOut(View view){
        buttonLogout=(Button)findViewById(R.id.logoutbtn);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.removeUpdates(MainActivity.this);
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this, AccountTypeActivity.class));
                Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AccountInfoActivity(View view) {
        locationManager.removeUpdates(this);
        SharedPreferences shared;
        shared=getSharedPreferences("activity_type",Context.MODE_PRIVATE);
        Intent intent = new Intent();
        if(shared.getBoolean("radio_personal",false))
            intent.setClass(this, PersonalAccountInfoActivity.class);
        else
            intent.setClass(this, HospitalAccountInfoActivity.class);
        startActivity(intent);
    }
    public void EditAccountInfoActivity(View view) {
        locationManager.removeUpdates(this);
        SharedPreferences shared;
        shared=getSharedPreferences("activity_type",Context.MODE_PRIVATE);
        Intent intent = new Intent();
        if(shared.getBoolean("radio_personal",false))
            intent.setClass(this, EditPersonalAccountInfoActivity.class);
        else
            intent.setClass(this, EditHospitalAccountInfoActivity.class);
        startActivity(intent);
    }
    public void AboutUsActivity(View view) {
        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }

    //------------------Find Nearest Hospital--------------------------------------------------

    public static double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }
    static Double lat2,lng2;
    public void sendAlerts(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        myRef = database.getReference("hospitals");
        myRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.child("Location")!=null) {
                        lat2 = ds.child("Location").child("Latitude").getValue(Double.class);
                        lng2 = ds.child("Location").child("Longitude").getValue(Double.class);
                        if(lat2!=null && lng2!=null)
                        if(min>distance(lati,longi,lat2,lng2)) {
                            min = distance(lati, longi, lat2, lng2);
                            uid = ds.child("uid").getValue(String.class);
                        }
                    }
                    Log.d("CheckingDude","dumper " + uid + " done");
                }
                //startActivity(new Intent(MainActivity.this, NewPatientsActivity.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
            });

            //myRef.addListenerForSingleValueEvent(new ValueEventListener(){
                //FirebaseDatabase
                database = FirebaseDatabase.getInstance();
                //DatabaseReference
                //myRef = database.getReference();
                // @Override
                //public void onDataChange(DataSnapshot dataSnapshot) {
                    myRef = database.getReference("hospitals/" + uid + "/uid");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uidu = user.getUid();
                    myRef.setValue(uidu);
                //}

               // @Override
               // public void onCancelled(DatabaseError databaseError) {

                //}

           // });



    }


    //------------------Manual Detection-------------------------------------------------------

    private static final int PRESS_INTERVAL = 1000;
    private long upKeyTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_VOLUME_DOWN == event.getKeyCode()) {
            if ((event.getEventTime() - upKeyTime) < PRESS_INTERVAL) {
                SharedPreferences shared;
                shared=getSharedPreferences("activity_type",Context.MODE_PRIVATE);
                if(shared.getBoolean("radio_personal",false)){
                    Toast.makeText(this, "Finding Nearest Hospital and Doctors", Toast.LENGTH_SHORT).show();
                    sendAlerts();
                }
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_VOLUME_UP == keyCode) {
            upKeyTime = event.getEventTime();
        }
        return super.onKeyUp(keyCode, event);
    }

    //--------------Location handling--------------------

    @Override
    public void onProviderEnabled(String pe){
        getCurrentLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onLocationChanged(Location location){
        lati = location.getLatitude();
        longi = location.getLongitude();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid=new String();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            uid = user.getUid();

        SharedPreferences shared;
        shared=getSharedPreferences("activity_type",Context.MODE_PRIVATE);
        if(shared.getBoolean("radio_personal",false)){
            myRef = database.getReference("users/" + uid + "/Location/Longitude");
            myRef.setValue(longi);
            myRef = database.getReference("users/" + uid + "/Location/Latitude");
            myRef.setValue(lati);
        }
        else {
            myRef = database.getReference("hospitals/" + uid + "/Location/Longitude");
            myRef.setValue(longi);
            myRef = database.getReference("hospitals/" + uid + "/Location/Latitude");
            myRef.setValue(lati);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 42);
            return;
        }


    }
    @Override
    public void onResume() {
        super.onResume();
        pause = false;
        getCurrentLocation();
    }
    @Override
    public void onPause(){
        super.onPause();
        pause = true;
    }



}
