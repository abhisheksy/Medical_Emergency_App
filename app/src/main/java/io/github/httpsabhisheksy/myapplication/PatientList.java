package io.github.httpsabhisheksy.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yadav on 4/29/2018.
 */

public class PatientList extends ArrayAdapter<UserData>{
    private Activity context;
    private List<UserData> patientList;


    public PatientList(Activity context, List <UserData>patientList) {
        super(context,R.layout.list_layout,patientList);
        this.context=context;
        this.patientList=patientList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView name = (TextView) listViewItem.findViewById(R.id.PName);
        TextView pn = (TextView) listViewItem.findViewById(R.id.PPN);
        TextView bg = (TextView) listViewItem.findViewById(R.id.PBG);
        TextView ht = (TextView) listViewItem.findViewById(R.id.PHT);
        TextView age = (TextView) listViewItem.findViewById(R.id.PAge);
        TextView preg = (TextView) listViewItem.findViewById(R.id.PPregnant);
        TextView asth = (TextView) listViewItem.findViewById(R.id.PAsthama);
        TextView bs = (TextView) listViewItem.findViewById(R.id.PBS);
        TextView rs = (TextView) listViewItem.findViewById(R.id.PRS);
        TextView da = (TextView) listViewItem.findViewById(R.id.PDA);
        TextView pw = (TextView) listViewItem.findViewById(R.id.PW);
        TextView ph = (TextView) listViewItem.findViewById(R.id.PH);

        UserData userData = patientList.get(position);

        name.setText(userData.getUserName());
        pn.setText(userData.getPhoneNumber());
        age.setText(userData.getAge());
        da.setText(userData.getDrugAllergy());
        preg.setText(userData.getPregnant());
        asth.setText(userData.getAsthama());
        ht.setText(userData.getHT());
        bg.setText(userData.getBloodGroup());
        bs.setText(userData.getBloodSugar());
        ph.setText(userData.getHeight());
        pw.setText(userData.getWeight());
        rs.setText(userData.getRecentSurgery());

        return listViewItem;
    }
}
