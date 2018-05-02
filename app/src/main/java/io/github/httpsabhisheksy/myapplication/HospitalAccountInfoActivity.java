package io.github.httpsabhisheksy.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalAccountInfoActivity extends AppCompatActivity {

    TextView Name,PN, EM;
    Button backBt;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String un = "Name: ", pn = "Phone Number: ", em = "Email: ";

    public void backbtn(View view){
        backBt=(Button)findViewById(R.id.backBt);
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalAccountInfoActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_account_info);

        Name = (TextView) findViewById(R.id.textView28);
        PN = (TextView) findViewById(R.id.textView29);
        EM = (TextView) findViewById(R.id.textView30);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        myRef = database.getReference("hospitals");
        myRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                HospitalData hospitalData = snapshot.getValue(HospitalData.class);
                if (hospitalData != null){
                    Name.setText( un + hospitalData.HospitalName);
                    PN.setText( pn + hospitalData.PhoneNumber);
                    EM.setText(em + hospitalData.HospitalEMail);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });


    }


}
