package com.example.linux.ws.dashboard;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.linux.ws.R;
import com.example.linux.ws.database_helper.Dashboard_DatabaseHelper;
import com.example.linux.ws.database_helper.DatabaseHelper;
import com.example.linux.ws.database_helper.DbHelper;
import com.example.linux.ws.login.LoginFragment;
import com.example.linux.ws.signup.signupFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.crypto.Cipher;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDashboardFragment extends Fragment implements View.OnClickListener {


    ListView listView;
    ArrayAdapter<String> mAdapter;
    Dashboard_DatabaseHelper DDHelper;
    Button b;
    String tableName;
    String []load_token;



    public UserDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.fragment_user_dashboard, container, false);

        Bundle bundle = getArguments();

//        if(bundle.getString("TableName")!=null)
//        {
//            tableName=bundle.getString("TableName");
//        }
//        else
//        {
//        }
            tableName=LoginFragment.getUserName();




       try{ if(bundle.getString("dataForDashboard")!=null){

            String loadForDashboard= bundle.getString("dataForDashboard");
            load_token=loadForDashboard.split("@",2);

            int workshop_id = Integer.parseInt(load_token[0]);
            String workshop_name=load_token[1];
            DDHelper= new Dashboard_DatabaseHelper(getContext(),tableName);
            if(!DDHelper.getItem(workshop_id ,workshop_name,tableName))
            {
                DDHelper.insert_data(workshop_id,workshop_name,tableName);
            }
        }} catch (Exception e){}



        listView=(ListView) rootView.findViewById(R.id.list_workshop);
        DDHelper= new Dashboard_DatabaseHelper(rootView.getContext(),tableName);

        try{updateUI();}catch (Exception e){}


        b = (Button) rootView.findViewById(R.id.remove_workshopBtn);

        try{b.setOnClickListener(this);}catch (Exception e){}

        return rootView;
    }



    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.dashboard));
    }


    public void updateUI()
    {
        ArrayList<String> workshopList=new ArrayList<>();
        Cursor res=DDHelper.getWorkshopTitle(tableName);
        while(res.moveToNext())
        {
            workshopList.add(res.getString(0));
        }

        if(mAdapter==null)
        {
            mAdapter=new ArrayAdapter<String>(getActivity(),R.layout.dashboard_item,R.id.workshop_title,workshopList);
            listView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.clear();
            mAdapter.addAll(workshopList);
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View view) {
        TextView textView=(TextView) view.findViewById(R.id.workshop_title);
        String workshopTitle=String.valueOf(textView.getText());
        SQLiteDatabase db = DDHelper.getWritableDatabase();
        db.delete(tableName,DDHelper.COL_3 +" = ?",new String[]{workshopTitle});
        db.close();
        updateUI();

    }
}
