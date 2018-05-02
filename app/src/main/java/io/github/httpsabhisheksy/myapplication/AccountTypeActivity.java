package io.github.httpsabhisheksy.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class AccountTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);
        SharedPreferences shared;
        shared=getSharedPreferences("activity_type",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = shared.edit();
        editor.putBoolean("radio_personal", false);
        editor.putBoolean("radio_hospital", false);
        editor.apply();
    }

    public void AccountType (View view){
        boolean checked = ((RadioButton) view).isChecked();

        Intent intent=new Intent();

        SharedPreferences shared;
        shared=getSharedPreferences("activity_type",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        switch(view.getId()) {
            case R.id.radioPersonal:
                if (checked)
                    intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                editor = shared.edit();
                editor.putBoolean("radio_personal", true);
                editor.apply();
                break;
            case R.id.radioHospital:
                if (checked)
                    intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                editor=shared.edit();
                editor.putBoolean("radio_hospital", true);
                editor.apply();
                break;
        }
    }
}
