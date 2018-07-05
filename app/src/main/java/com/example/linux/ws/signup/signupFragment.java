package com.example.linux.ws.signup;


import android.content.Context;
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

import com.example.linux.ws.R;
import com.example.linux.ws.database_helper.Dashboard_DatabaseHelper;
import com.example.linux.ws.database_helper.DbHelper;
import com.example.linux.ws.login.LoginFragment;
import com.example.linux.ws.login.Session;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class signupFragment extends Fragment {

    EditText name_textbox,emailid_textbox,password_textbox;
    Button signup_btn;
    TextView login_link;
    DbHelper db;
    Session session;


    public signupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView= inflater.inflate(R.layout.fragment_signup, container, false);
            name_textbox=(EditText)rootView.findViewById(R.id.input_name);
            emailid_textbox=(EditText)rootView.findViewById(R.id.input_email);
            password_textbox=(EditText)rootView.findViewById(R.id.input_password);
            signup_btn=(Button)rootView.findViewById(R.id.btn_signup);
            login_link=(TextView)rootView.findViewById(R.id.link_login);
            session = new Session(getContext());
            db=new DbHelper(getContext());

            signup_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // code for signup

                    //SharedPreferences preferences = getContext().getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
                    String newUserName=name_textbox.getText().toString();
                    String newPassword=password_textbox.getText().toString();
                    String newEmailId=emailid_textbox.getText().toString();


                    if(newEmailId.isEmpty() && newPassword.isEmpty()){
                        displayToast("Email id/password field empty");
                    }
                    else if(!checkSymbol(newEmailId))
                    {
                        displayToast("Email must contain @ e.g.abc@gmail.com ");

                    }
                    else{
                        db.addUser(newEmailId,newPassword);
                        // create new Table in Dashboard for new user
                        String[] emailToken= newEmailId.split("@",2);
                        String tableName=emailToken[0];

                        Dashboard_DatabaseHelper DDHelper = new Dashboard_DatabaseHelper(getContext(),tableName);
                        DDHelper.createUserTable(tableName);

                        displayToast("User registered");

                        LoginFragment loginFragment= new LoginFragment();
                        FragmentManager fragmentManager= getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container,loginFragment).commit();
                    }





                }
            });

            login_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // code to return back to login screen
                    LoginFragment loginFragment = new LoginFragment();
                    FragmentManager fragmentManager= getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,loginFragment).commit();

                }
            });

        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.signup));
    }


    private void displayToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public boolean checkSymbol(String str)
    {
        for(int i=0;i<str.length();i++)
        {
            if(str.charAt(i)=='@')
            {
                return true;
            }
        }
        return false;
    }

}
