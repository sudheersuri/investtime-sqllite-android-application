package com.sudheersuri.investtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements ActivityDetailsFragment.BottomSheetListener {
    DatabaseHelper myDb;
    BottomNavigationView navigationView;
    FloatingActionButton addbtn;
    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setBackground(null);

        myDb = new DatabaseHelper(this);
        ActivitiesFragment activitiesFragment=new ActivitiesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                activitiesFragment).commit();
        addbtn = findViewById(R.id.addactivity);
        navigationView.getMenu().getItem(1).setEnabled(false);
        navigationView.getMenu().getItem(2).setEnabled(false);
        navigationView.getMenu().getItem(3).setEnabled(false);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.miHome:
                        selectedFragment = new ActivitiesFragment();
                        break;
                    case R.id.summary:
                        selectedFragment = new SummaryFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityDetailsFragment activityDetailsFragment = new ActivityDetailsFragment();
                activityDetailsFragment.show(getSupportFragmentManager(), "ActivityDetails");
            }
        });


    }

    @Override
    public void onButtonClicked(String activityname, int investedtime) {
        if(activityname==null || investedtime==0)
            return;
        boolean isInserted = myDb.insertData(activityname, investedtime);
        if (isInserted == true) {
            Toast.makeText(MainActivity.this, "Activity Added", Toast.LENGTH_LONG).show();
            /*ActivitiesFragment activitiesFragment=new ActivitiesFragment();*/
            if(selectedFragment!=null)
                getSupportFragmentManager().beginTransaction().detach(selectedFragment).attach(selectedFragment).commit();
            else
            {
                ActivitiesFragment activitiesFragment=new ActivitiesFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,activitiesFragment).commit();
            }
        }
        else
            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(f instanceof DateSelectorFragment)
        {
            ActivitiesFragment activitiesFragment=new ActivitiesFragment();
            // do something with f ActivitiesFragment activitiesFragment=new ActivitiesFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    activitiesFragment).commit();
        }
        else
            super.onBackPressed();
    }
}