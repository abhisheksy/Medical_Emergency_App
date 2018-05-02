package io.github.httpsabhisheksy.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EditHospitalAccountInfoActivity extends AppCompatActivity {

    EditText HNV;
    //EditText HLV;
    EditText HPNV;
    EditText HEV;

    Button saveHospital;
    Button getLoc;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("hospitals");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hospital_account_info);

        saveHospital=(Button)findViewById(R.id.hospitalSave);
        saveHospital.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                saveHospitalDetails();
            }
        });
        getLoc=(Button)findViewById(R.id.getLocation);
        getLoc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(EditHospitalAccountInfoActivity.this,"Got Hospital Location",Toast.LENGTH_LONG).show();
            }
        });

        HNV= ( EditText ) findViewById(R.id.HNV);
        HPNV= ( EditText ) findViewById(R.id.HPNV);
        HEV= ( EditText ) findViewById(R.id.HEV);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();

        myRef = database.getReference("hospitals");
        myRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                HospitalData hospitalData = snapshot.getValue(HospitalData.class);
                if(hospitalData!=null) {
                    HNV.setText(hospitalData.HospitalName);
                    HPNV.setText(hospitalData.PhoneNumber);
                    HEV.setText(hospitalData.HospitalEMail);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });

    }

    public void saveHospitalDetails(){


        String hnv=HNV.getText().toString().trim();
        String hpnv=HPNV.getText().toString().trim();
        String hev=HEV.getText().toString().trim();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        HospitalData hospitalData = new HospitalData(hnv,hev,hpnv,MainActivity.lati,MainActivity.longi,uid);
        myRef = database.getReference("hospitals/" + uid);
        myRef.setValue(hospitalData);

        /*myRef = database.getReference("hospitals/" + uid + "/Name");
        myRef.setValue(hnv);
        myRef = database.getReference("hospitals/" + uid + "/PhoneNumber");
        myRef.setValue(hpnv);
        myRef = database.getReference("hospitals/" + uid + "/Email");
        myRef.setValue(hev);*/


        Toast.makeText(EditHospitalAccountInfoActivity.this,"Hospital Information Updated",Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,MainActivity.class));

    }

}
