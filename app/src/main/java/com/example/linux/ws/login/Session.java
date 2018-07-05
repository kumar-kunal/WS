package com.example.linux.ws.login;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences prefs,database_pref;
    SharedPreferences.Editor editor,database_editor;
    Context ctx;
    int userDatabaseId=-1;

    public Session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
        database_pref=ctx.getSharedPreferences("data_pref",Context.MODE_PRIVATE);
        database_editor=database_pref.edit();
    }



    public void setDatabase_pref(boolean dataLoaded)
    {
        database_editor.putBoolean("dataLoadedMode",dataLoaded);
        database_editor.commit();
    }

    public void setLoggedin(boolean logggedin){
        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public boolean isDatabaseLoaded(){return database_pref.getBoolean("dataLoadedMode",false);}

    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }
}