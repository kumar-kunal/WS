package com.example.linux.ws.login;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.linux.ws.R;
import com.example.linux.ws.dashboard.UserDashboardFragment;
import com.example.linux.ws.database_helper.Dashboard_DatabaseHelper;
import com.example.linux.ws.database_helper.DatabaseHelper;
import com.example.linux.ws.database_helper.DbHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Item_descriptionFragment extends Fragment {

    Button apply_btn;
    TextView showMessage;
    int id; // position of item, for database query
    static String load_datas;
    String load_data;

    public Item_descriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_item_description, container, false);

        // get data from parent activity
        Bundle bundle=this.getArguments();
        id=bundle.getInt("message");

        showMessage=(TextView)rootView.findViewById(R.id.show_message);
        apply_btn = (Button) rootView.findViewById(R.id.apply);
//        final Dashboard_DatabaseHelper ddHelper=new Dashboard_DatabaseHelper(getContext(),);



        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentManager fragmentManager = getFragmentManager();

                // workshop added to dashboard
                if(new Session(getContext()).loggedin()) {

                    // add workshop_id to dashboard database
                    DatabaseHelper mydatabaseHelper= new DatabaseHelper(getContext());
                    Cursor res_cursor = mydatabaseHelper.getTitleFromDatabase(id);
                    res_cursor.moveToNext();
                    String workshopTitle=res_cursor.getString(0);

                    load_data= id + "@"+workshopTitle;

                    Bundle bundle = new Bundle();
                    bundle.putString("dataForDashboard",load_data);


                    UserDashboardFragment userDashboardFragment = new UserDashboardFragment();
                    userDashboardFragment.setArguments(bundle);

                    fragmentManager.beginTransaction().replace(R.id.fragment_container,
                            userDashboardFragment).commit();
                }
                else // if user is not login, redirect to login
                {
                    LoginFragment loginFragment= new LoginFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,
                            loginFragment).commit();
                }

            }
        });

        // function to show description of item
        showDescription(id);


        load_datas=load_data;
        return rootView;
    }

    public void showDescription(int id)
    {
        DatabaseHelper mydatabaseHelper= new DatabaseHelper(getContext());
        Cursor res_cursor = mydatabaseHelper.getDescriptionFromDatabase(id);
        res_cursor.moveToNext();
        String myDescription=res_cursor.getString(0);
        showMessage.setText(myDescription);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.description));
    }

    public static String getDataForDashboard()
    {
        return load_datas;
    }

}
