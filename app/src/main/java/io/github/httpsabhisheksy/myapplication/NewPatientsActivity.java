package io.github.httpsabhisheksy.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.github.httpsabhisheksy.myapplication.MainActivity.sid;
import static io.github.httpsabhisheksy.myapplication.MainActivity.uidh;

public class NewPatientsActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    StringBuilder sb = new StringBuilder();
    TextView show1,show2,show3,show4,show5,show6,show7,show8,show9,show10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patients);

        show1= (TextView) findViewById(R.id.textView4);
        show2= (TextView) findViewById(R.id.textView5);
        show3= (TextView) findViewById(R.id.textView6);
        show4= (TextView) findViewById(R.id.textView7);
        show5= (TextView) findViewById(R.id.textView8);
        show6= (TextView) findViewById(R.id.textView9);
        show7= (TextView) findViewById(R.id.textView10);
        show8= (TextView) findViewById(R.id.textView11);
        show9= (TextView) findViewById(R.id.textView12);
        show10= (TextView) findViewById(R.id.textView13);

        /*show1.setVisibility(View.INVISIBLE);
        show2.setVisibility(View.INVISIBLE);
        show3.setVisibility(View.INVISIBLE);
        show4.setVisibility(View.INVISIBLE);
        show5.setVisibility(View.INVISIBLE);
        show6.setVisibility(View.INVISIBLE);
        show7.setVisibility(View.INVISIBLE);
        show8.setVisibility(View.INVISIBLE);
        show9.setVisibility(View.INVISIBLE);
        show10.setVisibility(View.INVISIBLE);*/

        //listViewPatients = (ListView) findViewById(R.id.NewPatientsList);
        show();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    public void show(){

        SharedPreferences shared;
        shared=getSharedPreferences("activity_type", Context.MODE_PRIVATE);

            if (shared.getBoolean("radio_personal", false)) {
                myRef = database.getReference("hospitals");
                if(uidh!=null) {
                    myRef.child(uidh).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            HospitalData hospitalData = dataSnapshot.getValue(HospitalData.class);
                            show1.setText(hospitalData.HospitalName + System.getProperty("line.separator") + hospitalData.HospitalEMail + System.getProperty("line.separator") + hospitalData.PhoneNumber);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(NewPatientsActivity.this, "Something gone wrong !", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    show1.setText("No Hospital Registered Yet !");
                }
            } else {
                if (sid == null) {
                    show1.setText("No Requests Received !");
                } else {
                    myRef = database.getReference("users");
                    myRef.child(sid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserData userData = dataSnapshot.getValue(UserData.class);
                            sb.append("Name: " + userData.userName + System.getProperty("line.separator"));
                            sb.append("Age : " + userData.age + System.getProperty("line.separator"));
                            sb.append("Blood Group : " + userData.BloodGroup + System.getProperty("line.separator"));
                            sb.append("Drug Allergy : " + userData.drugAllergy + System.getProperty("line.separator"));
                            sb.append("Pregnancy : " + userData.pregnant + System.getProperty("line.separator"));
                            sb.append("Asthama : " + userData.asthama + System.getProperty("line.separator"));
                            sb.append("Harmone Therapy : " + userData.HT + System.getProperty("line.separator"));
                            sb.append("Blood Sugar : " + userData.bloodSugar + System.getProperty("line.separator"));
                            sb.append("Recent Surgery : " + userData.RecentSurgery + System.getProperty("line.separator"));
                            sb.append("Height : " + userData.height + System.getProperty("line.separator"));
                            sb.append("Weight : " + userData.weight + System.getProperty("line.separator"));
                            sb.append("Phone Number : " + userData.phoneNumber + System.getProperty("line.separator"));
                            show1.setText(sb);
                             /*show2.setText(null);
                             show3.setText(null);
                             show4.setText(null);
                             show5.setText(null);
                             show6.setText(null);
                             show7.setText(null);
                             show8.setText(null);
                             show9.setText(null);
                             show10.setText(null);*/

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    myRef = database.getReference("hospitals");
                    myRef.child(uidh).child("uid").setValue(uidh);
                }
            }
    }


}
