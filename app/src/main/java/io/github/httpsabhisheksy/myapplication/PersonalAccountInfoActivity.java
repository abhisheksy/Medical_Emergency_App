package io.github.httpsabhisheksy.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonalAccountInfoActivity extends AppCompatActivity {

    TextView Name, Age,DA, HT, W, H, BS, BG, AS, PR, RS, SEX, PH;
    Button backB;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String un = "Name: ", ua = "Age: ", us = "SEX: ", uda = "Drug Allergy: ", uht = "HT: ", ubg = "Blood Group: ", ubs = "Blood Sugar", uas = "Asthama: ";
    String upr = "Pregnancy: ", urs = "Recent Surgery: ", uw = "Weight: ", uh = "Height: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_account_info);

        Name = (TextView) findViewById(R.id.textView3);
        Age = (TextView) findViewById(R.id.textView14);
        SEX = (TextView) findViewById(R.id.textView25);
        DA = (TextView) findViewById(R.id.textView24);
        HT = (TextView) findViewById(R.id.textView23);
        BG = (TextView) findViewById(R.id.textView21);
        BS = (TextView) findViewById(R.id.textView20);
        AS = (TextView) findViewById(R.id.textView19);
        PR = (TextView) findViewById(R.id.textView18);
        RS = (TextView) findViewById(R.id.textView17);
        W = (TextView) findViewById(R.id.textView16);
        H = (TextView) findViewById(R.id.textView15);
        PH = (TextView) findViewById(R.id.textView22);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        myRef = database.getReference("users");
        myRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UserData userData = snapshot.getValue(UserData.class);
                if (userData != null){
                    Name.setText( un + userData.userName);
                    Age.setText( ua + userData.age);
                    //SEX.setText("Name: " + userData.sex);
                    DA.setText(uda + userData.drugAllergy);
                    HT.setText(uht + userData.HT);
                    BG.setText(ubg + userData.BloodGroup);
                    BS.setText(ubs + userData.bloodSugar);
                    AS.setText(uas + userData.asthama);
                    PR.setText(upr + userData.pregnant);
                    RS.setText(urs + userData.RecentSurgery);
                    W.setText(uw + userData.weight);
                    H.setText(uh + userData.height);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });


    }

    public void backB(View view){
        backB=(Button)findViewById(R.id.backB);
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalAccountInfoActivity.this, MainActivity.class));
            }
        });
    }


}
