package com.uottawa.runnan.seg_deliberable1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileInformation extends  AppCompatActivity{


    TextView profileInformation;
    TextView companyName;
    TextView companyAddress;
    TextView generalDescription;
    CheckBox licensed;

    EditText name;
    EditText address;
    EditText number;
    EditText generalDes;

    Button submit;


    Spinner sType;
    ArrayAdapter<String> sTypeAdapter;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference services;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_information);


        profileInformation = (TextView) findViewById(R.id.tv_profile_information);
        companyName = (TextView) findViewById(R.id.tv_company_name);
        companyAddress = (TextView) findViewById(R.id.tv_address);
        generalDescription = (TextView) findViewById(R.id.tv_general_description);
        licensed = (CheckBox) findViewById(R.id.cb_licensed);

        name = (EditText) findViewById(R.id.et_company_name);
        address = (EditText) findViewById(R.id.et_street_name);
        number = (EditText) findViewById(R.id.et_street_number);
        generalDes = (EditText) findViewById(R.id.et_general_description);
        sType = (Spinner) findViewById(R.id.sp_street_type);
        submit = (Button) findViewById(R.id.btn_submit);

        sTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.streetType));
        sTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sType.setAdapter(sTypeAdapter);




    }
}
