/*
    Written by: Kunal Kumar
    Date: Jun 20, 2018
 */
package com.example.linux.ws;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linux.ws.Interface.ItemClickListener;
import com.example.linux.ws.dashboard.UserDashboardFragment;
import com.example.linux.ws.database_helper.DatabaseHelper;
import com.example.linux.ws.login.Item_descriptionFragment;
import com.example.linux.ws.login.LoginFragment;
import com.example.linux.ws.login.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Session class to check user login
    Session session;
    Cursor result;

    // Drawer layout
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    // For Database operation
    DatabaseHelper databaseHelper;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // instance of session

        session=new Session(this);


        // Getting instance for Drawer and implement listener
        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.bringToFront();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



        // DatabaseHelper class initialisation
        databaseHelper = new DatabaseHelper(this);
        // Add Data to database (automatically)
        if (!session.isDatabaseLoaded()){ AddDataToDatabse();}




        if(session.loggedin()){
            UserDashboardFragment userDashboardFragment = new UserDashboardFragment();
            FragmentManager fragmentManager= getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,userDashboardFragment).commit();
        }
        else
        {
            RecyclerFragment recyclerFragment = new RecyclerFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,recyclerFragment).commit();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Handle navigation view item clicks here.
                switch (menuItem.getItemId())
                {
                    case R.id.workshop:
                        //code to go for workshop screen
                        menuItem.setChecked(true);

                        break;

                    case R.id.dashboard:
                        // code to open dashboard screen
                        if(session.loggedin()) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.fragment_container,
                                    new UserDashboardFragment()).commit();
                            menuItem.setChecked(true);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Please Login to see your Dashbord",Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.login:
                        // code to open login fragment
                        FragmentManager fragmentManager1=getSupportFragmentManager();
                        fragmentManager1.beginTransaction().replace(R.id.fragment_container,
                                new LoginFragment()).commit();
                        menuItem.setChecked(true);
                        break;

                    case R.id.logout:
                        // code for logout
                        menuItem.setChecked(true);
                        if (session.loggedin()) {
                            session.setLoggedin(false);
                            Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "You are already Logout", Toast.LENGTH_SHORT).show();

                        }
                        break;

                    case R.id.about:
                        // code for about
                        Toast.makeText(getApplicationContext(),"Coming Soon...",Toast.LENGTH_SHORT).show();
                        menuItem.setChecked(true);
                        break;
                }
                //close navigation drawer
                drawerLayout.closeDrawer(GravityCompat.START);


                return true;
            }
        });




    }
    // Drawer Menu item listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void AddDataToDatabse() {
        int i = 1;
        while (i != 0) {
            databaseHelper.insertData("Machine Learning",
                    "Google",
                    "Mumbai",
                    "22-July-2018",
                    "3Days",
                    "10-July-2018",
                    "This workshop will focus on the use of machine learning for MRI applications, both on the technical aspects of MRI and PET/MRI for improved reconstruction as well as on its use for adding diagnostic value to existing images. This latter aspect would include methods for more robust segmentation as well as disease classification and prediction.");
            databaseHelper.insertData("Artificial Intelligence",
                    "Baidu",
                    "Delhi",
                    "12-July-2018",
                    "3Days",
                    "1-July-2018",
                    "Artificial intelligence (AI, also machine intelligence, MI) is intelligence demonstrated by machines, in contrast to the natural intelligence (NI) displayed by humans and other animals. ");
            databaseHelper.insertData("Deep Learning",
                    "Microsoft",
                    "Bangalore",
                    "2-July-2018",
                    "3Days",
                    "10-Jun-2018",
                    "Deep learning (also known as deep structured learning or hierarchical learning) is part of a broader family of machine learning methods based on learning data representations, as opposed to task-specific algorithms. Learning can be supervised, semi-supervised or unsupervised");
            databaseHelper.insertData("Blockchain",
                    "Etherium",
                    "Mumbai",
                    "27-July-2018",
                    "3Days",
                    "10-July-2018",
                    "The blockchain is an incorruptible digital ledger of economic transactions that can be programmed to record not just financial transactions but virtually everything of value.");
            databaseHelper.insertData("Digital Twin",
                    "Delotti",
                    "Pune",
                    "18-July-2018",
                    "3Days",
                    "6-July-2018",
                    "Digital twin is the ability to make a virtual representation of the physical elements and the dynamics of how an Internet of Things device operates and works. It's more than a blueprint, it's more than a schematic.");
            databaseHelper.insertData("Internet of Things",
                    "Directi",
                    "Kolkata",
                    "24-July-2018",
                    "3Days",
                    "14-July-2018",
                    "IoT is short for Internet of Things. The Internet of Things refers to the ever-growing network of physical objects that feature an IP address for internet connectivity, and the communication that occurs between these objects and other Internet-enabled devices and systems.");
            i--;
        }
        session.setDatabase_pref(true);

    }



    // This will remove only fragment on backpress , not whole activity
    @Override
    public void onBackPressed() {
        DrawerLayout layout = (DrawerLayout)findViewById(R.id.drawer);

        //change title after coming out from fragment
        getSupportActionBar().setTitle(getString(R.string.app_name));

        RecyclerFragment recyclerFragment= new RecyclerFragment();

        FragmentTransaction fragmentTransaction;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
        }
        else if(fragment!=null){
            if(fragment instanceof RecyclerFragment)
            {
                super.onBackPressed();
            }
            else  {
                fragmentTransaction.replace(R.id.fragment_container, recyclerFragment);
                fragmentTransaction.commit();
            }
        }
         else {
            super.onBackPressed();
        }

    }




}

