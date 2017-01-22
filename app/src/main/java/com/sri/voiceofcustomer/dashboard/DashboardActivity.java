package com.sri.voiceofcustomer.dashboard;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.sri.voiceofcustomer.R;
import com.sri.voiceofcustomer.database.models.User;
import com.sri.voiceofcustomer.login.LoginActivity;
import com.sri.voiceofcustomer.survey.fragment.admin.CreateSurveyFragment;
import com.sri.voiceofcustomer.survey.fragment.admin.InProgressSurvey;
import com.sri.voiceofcustomer.survey.fragment.admin.NewSurveyFragment;
import com.sri.voiceofcustomer.survey.fragment.admin.StatsFragment;



public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ProgressBar progressBar;
    private TextView logout_textView;
    private FirebaseAuth.AuthStateListener authListener;
    /*private DatabaseReference mDatabase;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private User authUser = new User();*/
    boolean isCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*mDatabase = FirebaseDatabase.getInstance().getReference("userdetail");
        user = auth.getInstance().getCurrentUser();
        Log.d("Email Id of user",user.getEmail());
        int keyIndex = user.getEmail().indexOf('@');
        String keyId = user.getEmail().substring(0,keyIndex);
        mDatabase.child(keyId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                authUser = dataSnapshot.getValue(User.class);

                if(authUser!=null)
                {
                    Log.d("ROLE",authUser.role);
                    String role = authUser.role;
                    if(role.equals("customer"))
                        isCustomer=true;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    finish();
                    startActivity(new Intent(DashboardActivity.this, LoginActivity.class));

                }
                else
                {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("surveys");
                }
            }
        };

        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        isCustomer=getIntent().getBooleanExtra("isCustomer",false);
        if(isCustomer) {
            Menu menu = navigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.nav_create_survey);
            menuItem.setVisible(false);
        }
        Fragment defaultFragment = new StatsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_dashboard,defaultFragment);
        transaction.commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment newFragment=null;
        if (id == R.id.nav_create_survey) {
            // Handle the  action
            newFragment = new CreateSurveyFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_dashboard,newFragment);
            transaction.commit();

        } else if (id == R.id.nav_new_survey) {
            if(isCustomer)
            {
                newFragment = new NewSurveyFragment();
            }else{
                newFragment = new NewSurveyFragment();
            }
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_dashboard,newFragment);
            transaction.commit();

        } else if (id == R.id.nav_inprogress_survey) {

            /*newFragment = new InProgressSurvey();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_dashboard,newFragment);
            transaction.commit();*/

        } else if (id == R.id.statistics) {
            newFragment = new StatsFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_dashboard,newFragment);
            transaction.commit();
        }

         else {
            if (id == R.id.logout) {

                    FirebaseAuth.getInstance().signOut();// this listener will be called when there is change in firebase user session
                finish();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener

    }




   /* @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        if (!isConnected) {
            String message;
            int color;
            View sbView = getWindow().getDecorView();
            message = "Check your Network and try again.";
            Snackbar.make(sbView, message, Snackbar.LENGTH_LONG).show();

        }

    }*/

}
