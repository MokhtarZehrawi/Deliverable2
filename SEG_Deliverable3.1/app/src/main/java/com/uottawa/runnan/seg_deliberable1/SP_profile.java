package com.uottawa.runnan.seg_deliberable1;

import android.app.Service;
import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.uottawa.runnan.seg_deliberable1.Model.Product;
import com.uottawa.runnan.seg_deliberable1.Model.ServiceProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SP_profile extends AppCompatActivity {
    //Firebase
    DatabaseReference databaseServices;
    DatabaseReference databaseProfiles;
    DatabaseReference databasenewSer;

    EditText address;
    EditText company;
    EditText phonenumber;
    EditText gInfo;
    TextView service;
    Button createProfile;
    Button addservice;

    ListView listviewproducts;
    List<Product> products;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_profile);

        address = (EditText)findViewById(R.id.etaddress) ;
        company = (EditText)findViewById(R.id.etcompany) ;
        phonenumber = (EditText)findViewById(R.id.etphonenumber);
        gInfo = (EditText)findViewById(R.id.etinfo);
        service = (TextView) findViewById(R.id.etservices);
        createProfile = (Button)findViewById(R.id.btncreateprofile);
        addservice = (Button)findViewById(R.id.btnaddservice);

        listviewproducts = (ListView) findViewById(R.id.servicesfromadmin);
        databaseServices = FirebaseDatabase.getInstance().getReference("services");
        databaseProfiles = FirebaseDatabase.getInstance().getReference("profiles");
        databasenewSer = FirebaseDatabase.getInstance().getReference("myServices");
        products = new ArrayList<>();

        listviewproducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Product product = products.get(i);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SP_profile.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.sp_dialog, null);
                dialogBuilder.setView(dialogView);

                final Button btnadd = (Button) dialogView.findViewById(R.id.btnaddservice);

                final AlertDialog b = dialogBuilder.create();
                b.show();

                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Product newservice = new Product(service.getText().toString());
                        service.setText(product.getProductName());
                        databasenewSer.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                databasenewSer.child(newservice.getProductName()).setValue(newservice);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        b.dismiss();
                    }
                });

            }
        });
        createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ServiceProvider sp = new ServiceProvider(address.getText().toString(), phonenumber.getText().toString(), gInfo.getText().toString(),company.getText().toString());
                databaseProfiles.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(address.getText().toString().isEmpty()){
                            Toast.makeText(SP_profile.this,"Please enter an address",Toast.LENGTH_LONG).show();
                        }
                        else if(phonenumber.getText().toString().isEmpty()){
                            Toast.makeText(SP_profile.this,"Please enter a phone number",Toast.LENGTH_LONG).show();
                        }
                        else if(!phonenumValidation(phonenumber.getText().toString())){
                            Toast.makeText(SP_profile.this,"Please enter a valid phone number",Toast.LENGTH_LONG).show();
                        }
                        else if(company.getText().toString().isEmpty()){
                            Toast.makeText(SP_profile.this,"Please enter a company name",Toast.LENGTH_LONG).show();
                        }
                        else{
                            databaseProfiles.child(sp.get_address()).setValue(sp);
                            Toast.makeText(SP_profile.this,"Success Creation!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),AvailableTime.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });
        addservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Product newservice = new Product(service.getText().toString());
                databasenewSer.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(service.getText().toString().isEmpty()){
                            Toast.makeText(SP_profile.this,"Please choose a service",Toast.LENGTH_LONG).show();
                        }
                        else{
                            databasenewSer.child(newservice.getProductName()).setValue(newservice);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    public boolean phonenumValidation(String num){
        boolean res = true;
        String pattern ="^[+]?[0-9]{10,13}$";
        if(num.matches(pattern)){
            res = true;
        }
        else{
            res = false;
        }
        return res;
    }


    @Override
    protected void  onStart() {
        super.onStart();
        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    products.add(product);
                }
                ProductList productsAdapter = new ProductList(SP_profile.this, products);
                listviewproducts.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
