package io.github.httpsabhisheksy.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import static io.github.httpsabhisheksy.myapplication.R.id.userSave;

public class EditPersonalAccountInfoActivity extends AppCompatActivity {

    EditText NV;
    EditText DAV;
    EditText HTV;
    EditText AgeV;
    EditText BGV;
    EditText PNV;
    EditText AsthamaV;
    EditText PregnantV;
    EditText RSV;
    EditText BSV;
    EditText WV;
    EditText HV;

    Button saveUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_account_info);

        saveUser=(Button)findViewById(userSave);
        saveUser.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                saveUserDetails();
            }
        });

        NV= ( EditText ) findViewById(R.id.NV);
        DAV= ( EditText ) findViewById(R.id.DAV);
        HTV= ( EditText ) findViewById(R.id.HTV);
        AgeV= ( EditText ) findViewById(R.id.AgeV);
        BGV= ( EditText ) findViewById(R.id.BGV);
        PNV= ( EditText ) findViewById(R.id.PNV);
        AsthamaV= ( EditText ) findViewById(R.id.AsthamaV);
        PregnantV= ( EditText ) findViewById(R.id.PregnantV);
        RSV= ( EditText ) findViewById(R.id.RSV);
        BSV= ( EditText ) findViewById(R.id.BSV);
        WV= ( EditText ) findViewById(R.id.WV);
        HV= ( EditText ) findViewById(R.id.HV);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();

            myRef = database.getReference("users");
            myRef.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    UserData userData = snapshot.getValue(UserData.class);
                    if (userData != null){
                        NV.setText(userData.userName);
                        DAV.setText(userData.drugAllergy);
                        HTV.setText(userData.HT);
                        AgeV.setText(userData.age);
                        PNV.setText(userData.phoneNumber);
                        BGV.setText(userData.BloodGroup);
                        AsthamaV.setText(userData.asthama);
                        PregnantV.setText(userData.pregnant);
                        RSV.setText(userData.RecentSurgery);
                        BSV.setText(userData.bloodSugar);
                        WV.setText(userData.weight);
                        HV.setText(userData.height);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("The read failed: ", databaseError.getMessage());
                }
            });


    }

    public void saveUserDetails(){



        String nv=NV.getText().toString().trim();
        String dav=DAV.getText().toString().trim();
        String htv=HTV.getText().toString().trim();
        String Agev=AgeV.getText().toString().trim();
        String pnv=PNV.getText().toString().trim();
        String bgv=BGV.getText().toString().trim();
        String asthamav=AsthamaV.getText().toString().trim();
        String pregnantv=PregnantV.getText().toString().trim();
        String rsv=RSV.getText().toString().trim();
        String bsv=BSV.getText().toString().trim();
        String wv=WV.getText().toString().trim();
        String hv=HV.getText().toString().trim();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        UserData userData = new UserData(nv,uid,bgv,htv,Agev,dav,asthamav,pregnantv,rsv,pnv,bsv,wv,hv,MainActivity.lati,MainActivity.longi);
        myRef = database.getReference("users/" + uid );
        myRef.setValue(userData);
        /*myRef = database.getReference("users/" + uid + "/Name");
        myRef.setValue(nv);
        myRef = database.getReference("users/" + uid + "/PhoneNumber");
        myRef.setValue(pnv);
        myRef = database.getReference("users/" + uid + "/BloodGroup");
        myRef.setValue(bgv);
        myRef = database.getReference("users/" + uid + "/Age");
        myRef.setValue(Agev);
        myRef = database.getReference("users/" + uid + "/Asthama");
        myRef.setValue(asthamav);
        myRef = database.getReference("users/" + uid + "/Pregnancy");
        myRef.setValue(pregnantv);
        myRef = database.getReference("users/" + uid + "/DrugAllergy");
        myRef.setValue(dav);
        myRef = database.getReference("users/" + uid + "/HT");
        myRef.setValue(htv);
        myRef = database.getReference("users/" + uid + "/BloodSugar");
        myRef.setValue(bsv);
        myRef = database.getReference("users/" + uid + "/Weight");
        myRef.setValue(wv);
        myRef = database.getReference("users/" + uid + "/Height");
        myRef.setValue(hv);
        myRef = database.getReference("users/" + uid + "/RecentSurgery");
        myRef.setValue(rsv);
        myRef = database.getReference("users/" + uid + "/UID");
        myRef.setValue(uid);*/


        Toast.makeText(this,"User Information Updated",Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,MainActivity.class));


    }

}
