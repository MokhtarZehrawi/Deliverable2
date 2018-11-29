package com.uottawa.runnan.seg_deliberable1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.runnan.seg_deliberable1.Model.Rate;
import com.uottawa.runnan.seg_deliberable1.Model.Rating;
import com.uottawa.runnan.seg_deliberable1.Model.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

public class RateServiceProvider extends AppCompatActivity {
    DatabaseReference databaseRates;
    DatabaseReference databaseProfiles;
    ListView listviewratings;
    List<ServiceProvider> providers;
    Button submit;
    TextView sp_name;
    TextView tv_rate;
    EditText comments;
    EditText rating;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_service_provider);

        listviewratings = (ListView)findViewById(R.id.lv_service_provider);
        submit = (Button)findViewById(R.id.btn_rate_submit);
        sp_name = (TextView)findViewById(R.id.tv_sp_name11);
        tv_rate = (TextView)findViewById(R.id.tv_rate);
        comments = (EditText)findViewById(R.id.et_comments);
        rating = (EditText)findViewById(R.id.et_rating);

        databaseRates = FirebaseDatabase.getInstance().getReference("rating");
        databaseProfiles = FirebaseDatabase.getInstance().getReference("profiles");
        providers = new ArrayList<>();

        databaseProfiles.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    ServiceProvider provider = child.getValue(ServiceProvider.class);
                    providers.add(provider);
                }
                RateServiceProviderList serviceProvidersAdapter = new RateServiceProviderList(RateServiceProvider.this, providers);
                listviewratings.setAdapter(serviceProvidersAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseRates.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(int i=0; i<providers.size(); i++){
                    String provName = providers.get(i).get_username();
                    boolean found=false;
                    for(DataSnapshot child : children){
                        String name = child.getKey();
                        if(provName.equals(name)){
                            found=true;
                        }
                    }
                    if(found==false){
                        Rating rating = new Rating(provName, 0);
                        databaseRates.child(provName).setValue(rating);

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listviewratings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ServiceProvider provider = providers.get(i);
                LayoutInflater inflater = getLayoutInflater();

                sp_name.setText(provider.get_username());

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rating_num = Integer.parseInt(rating.getText().toString()) ;
                String the_comment = comments.getText().toString();
                String name = sp_name.getText().toString();

                if(rating_num>5 || rating_num<0){

                    Toast.makeText(RateServiceProvider.this,"rate 0 to 5",Toast.LENGTH_LONG).show();

                }else if(name.equals("Name")){
                    Toast.makeText(RateServiceProvider.this,"select a provider",Toast.LENGTH_LONG).show();


                }else{
                    Rate newRate = new Rate(rating_num, the_comment);
                    addRateToFirebase(newRate, name);


                }
            }
        });



    }
    private void addRateToFirebase(Rate rate, String name) {

        final String provName = name;
        final Rate ProvRate = rate;
        final int ProvRating = rate.getRating();


        databaseRates.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot child : dataSnapshot.getChildren()){

                    if(child.getKey().equals(provName)){


                        Rating rating = child.getValue(Rating.class); //<-----------------------this line is the problem
                        Rating newRating = new Rating(rating);
                        newRating.addRating(ProvRate);
                        databaseRates.child(provName).setValue(newRating);
                        Toast.makeText(RateServiceProvider.this,"thank you for rating",Toast.LENGTH_LONG).show();

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
