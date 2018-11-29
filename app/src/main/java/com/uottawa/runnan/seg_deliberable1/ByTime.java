package com.uottawa.runnan.seg_deliberable1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.uottawa.runnan.seg_deliberable1.Model.Availability;
import com.uottawa.runnan.seg_deliberable1.Model.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

public class ByTime extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_TEXT = "selectedspnamebytime";
    public static final String EXTRA_TEXT1 = "selectspbytime";
    TextView selectedtime;
    EditText day;

    DatabaseReference availability;
    ListView listofspname;
    List<Availability> serviceprovidernames;

    DatabaseReference bookedServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_time);

        final TextView hoName = (TextView)findViewById(R.id.tvhoname);
        Intent intent = getIntent();
        String  name = intent.getStringExtra(SearchSP.EXTRA_TEXT);
        hoName.setText(name);
        Spinner time = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.time,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(adapter);
        time.setOnItemSelectedListener(this);

        availability = FirebaseDatabase.getInstance().getReference("availability");
        serviceprovidernames = new ArrayList<>();
        listofspname = (ListView)findViewById(R.id.spnamelist);

        bookedServices = FirebaseDatabase.getInstance().getReference("bookedServices");

        day = (EditText)findViewById(R.id.day);
        selectedtime = (TextView)findViewById(R.id.time);

        listofspname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Availability a = serviceprovidernames.get(position);
                String selectedsp = a.getName();
                String honamebytime = hoName.getText().toString();
                Intent intent = new Intent(getApplicationContext(), SPServiceByTime.class);
                intent.putExtra(EXTRA_TEXT, selectedsp);
                intent.putExtra(EXTRA_TEXT1, honamebytime);
                startActivity(intent);
            }
        });

    }

    private void showReserveDialog(final String nameOfsp){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_book_service,null);
        dialogBuilder.setView(dialogView);

        final Button reserve = (Button)findViewById(R.id.btnbook);

        final AlertDialog b = dialogBuilder.create();
        b.show();

    }
    @Override
    protected void onStart(){
        super.onStart();
        availability.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceprovidernames.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Availability avai = postSnapshot.getValue(Availability.class);
                    serviceprovidernames.add(avai);
                }
                ServiceProviderList splAdapter = new ServiceProviderList(ByTime.this, serviceprovidernames);
                listofspname.setAdapter(splAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        selectedtime.setText(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
