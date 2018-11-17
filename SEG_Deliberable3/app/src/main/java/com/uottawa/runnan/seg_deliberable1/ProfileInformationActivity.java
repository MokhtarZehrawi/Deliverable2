package com.uottawa.runnan.seg_deliberable1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uottawa.runnan.seg_deliberable1.Model.ProfileInformation;

public class ProfileInformationActivity extends  AppCompatActivity{


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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String namePattern = "^[a-zA-Z\\s]+";

                String companyName1 = name.getText().toString();
                String address1 = address.getText().toString();
                String description = generalDes.getText().toString();
                String type = sType.getSelectedItem().toString();


                if(companyName1.length()==0){
                    name.setError("Enter name of Company");
                    return;
                }else if(address1.length()==0){
                    address.setError("Enter an address");
                    return;
                }
                if (type.equals("Type")){
                    Toast.makeText(ProfileInformationActivity.this,"Select Street Type", Toast.LENGTH_LONG).show();
                    return;
                }else{
                    type = convertStreetType(type);
                }
                try{
                    int num = Integer.parseInt(number.getText().toString());

                }catch (Exception e){
                    number.setError("Enter street number");
                    return;
                }


                if(companyName1.matches(namePattern) && address1.matches(namePattern)){

                    // enter code here


                    return;

                }else if(!companyName1.matches(namePattern)){

                    Toast.makeText(ProfileInformationActivity.this,"Invalid Name Input", Toast.LENGTH_LONG).show();

                }else if(!address1.matches(namePattern)){

                    Toast.makeText(ProfileInformationActivity.this,"Invalid Address Input", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private String convertStreetType(String selectedItem){

        if(selectedItem.equals("Rd.")) {
            return "Road";
        }
        if(selectedItem.equals("St.")) {
            return "Street";
        }
        if(selectedItem.equals("Rd.")) {
            return "Road";
        }
        if(selectedItem.equals("Dr.")) {
            return "Drive";
        }
        if(selectedItem.equals("Cres.")) {
            return "Crescent";
        }
        if(selectedItem.equals("Ave.")) {
            return "Avenue";
        }
        if(selectedItem.equals("Ln.")) {
            return "Lane";
        }
        if(selectedItem.equals("Pkwy.")) {
            return "Parkway";
        }
        if(selectedItem.equals("Blvd.")) {
            return "Boulevard";
        }
        if(selectedItem.equals("Ct.")) {
            return "Court";
        }else {
            return null;
        }

    }



}
