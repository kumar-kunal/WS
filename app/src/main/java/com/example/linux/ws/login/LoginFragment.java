package com.example.linux.ws.login;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linux.ws.MainActivity;
import com.example.linux.ws.R;
import com.example.linux.ws.dashboard.UserDashboardFragment;
import com.example.linux.ws.database_helper.DbHelper;
import com.example.linux.ws.signup.signupFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText userName_textbox,password_textbox;
    Button login_btn;
    TextView signup_link;
    private DbHelper db;
    private Session session;
    String tableName;
    static String tableNames;



    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_login, container, false);

        userName_textbox=(EditText)rootView.findViewById(R.id.input_user);
        password_textbox=(EditText)rootView.findViewById(R.id.input_password);
        login_btn=(Button)rootView.findViewById(R.id.btn_login);
        signup_link=(TextView)rootView.findViewById(R.id.link_signup);
        db=new DbHelper(getContext());
        session= new Session(getContext());



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code For login using Shared preferences
                String userEmail = userName_textbox.getText().toString();
                String password = password_textbox.getText().toString();

                if(db.getUser(userEmail,password)){
                    session.setLoggedin(true);
                    String []emailToken=userEmail.split("@",2);
                    tableName=emailToken[0];
                    Bundle bundle = new Bundle();
                    bundle.putString("TableName",tableName);
                    UserDashboardFragment userDashboardFragment = new UserDashboardFragment();
                    userDashboardFragment.setArguments(bundle);
                    FragmentManager fragmentManager=getFragmentManager();

                    fragmentManager.beginTransaction().replace(R.id.fragment_container,userDashboardFragment).commit();

                }else{
                    Toast.makeText(getContext(), "Wrong email/password",Toast.LENGTH_SHORT).show();
                }

                tableNames=tableName;


            }
        });


        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code to call signup fragment

                signupFragment signupfragment = new signupFragment();
                FragmentManager fragmentManager= getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,signupfragment).commit();

            }
        });


        return rootView;
    }


    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.login));
    }

    public static String getUserName()
    {
        return tableNames;
    }

}
