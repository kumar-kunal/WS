package com.example.linux.ws;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.linux.ws.database_helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerFragment extends Fragment {

    Cursor result;
    // For Database operation
    DatabaseHelper databaseHelper;

    //the recyclerview
    RecyclerView recyclerView;
    //a list to store all the products
    List<RecyclerItem> recyclerItemList;


    public RecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_recycler, container, false);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initializing the recyclerItemList
        recyclerItemList = new ArrayList<>();

        // DatabaseHelper class initialisation
        databaseHelper = new DatabaseHelper(rootView.getContext());
        // Add data to recycler from databse
        result = databaseHelper.getDataFromDatabase();
        AddDataToRecyclerFromDatabse();

        //creating recyclerview adapter
        RecyclerItemAdapter adapter = new RecyclerItemAdapter(getContext(), recyclerItemList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);


        return  rootView;
    }

    private void AddDataToRecyclerFromDatabse() {
        while (result.moveToNext()) {
            recyclerItemList.add(new RecyclerItem(
                    Integer.parseInt(result.getString(0)),
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6))
            );
        }
    }

}
