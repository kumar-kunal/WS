package com.example.linux.ws;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.linux.ws.Interface.ItemClickListener;
import com.example.linux.ws.database_helper.DatabaseHelper;
import com.example.linux.ws.login.Item_descriptionFragment;

import java.util.List;


public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.RecycleItemViewHolder> {

    // this context will use to inflate layout
    private Context mCtx;


    //we are storing all the products in a list
    private List<RecyclerItem> recyclerItemsList;

    //getting the context and product list with constructor
    public RecyclerItemAdapter(Context mCtx, List<RecyclerItem> recyclerItemsList) {
        this.mCtx = mCtx;
        this.recyclerItemsList = recyclerItemsList;
    }

    @Override
    public RecycleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_item_layout, null);
        return new RecycleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleItemViewHolder holder, int position) {
        //getting the item of the specified position
        RecyclerItem recyclerItem = recyclerItemsList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(recyclerItem.getTitle());
        holder.textOrganiser.setText(recyclerItem.getOrganiser());
        holder.textViewLocation.setText(String.valueOf(recyclerItem.getLocation()));
        holder.textViewDate.setText(String.valueOf(recyclerItem.getDate()));
        holder.textViewDuration.setText(String.valueOf(recyclerItem.getDuration()));
        holder.textViewAppliedBy.setText(String.valueOf(recyclerItem.getApplied_by()));

        // Action listener for item in recycle view

        holder.setItemClickListener(new ItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                callRecyclerItemFragment(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return recyclerItemsList.size();
    }



    class RecycleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemClickListener itemClickListener;

        TextView textViewTitle, textOrganiser,textViewLocation, textViewDate,textViewAppliedBy,textViewDuration;

        public RecycleItemViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textOrganiser = itemView.findViewById(R.id.textViewOrganiser);
            textViewLocation = itemView.findViewById(R.id.textViewLocationDetail);
            textViewDate = itemView.findViewById(R.id.textViewDateDetail);
            textViewDuration = itemView.findViewById(R.id.textViewDurationDetail);
            textViewAppliedBy = itemView.findViewById(R.id.textViewAooliedByDetail);
            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }


    }
    // Action Listener for recycler view item, which is called from recyclerItemAdapter class


        public  void callRecyclerItemFragment(int position)
        {
            int load_data= position+1;

            Bundle bundle= new Bundle();
            bundle.putInt("message",load_data);
                Item_descriptionFragment item_descriptionFragment= new Item_descriptionFragment();
                item_descriptionFragment.setArguments(bundle);
                FragmentTransaction ft = ((AppCompatActivity)mCtx).getSupportFragmentManager()
                        .beginTransaction();
                ft.replace(R.id.fragment_container, item_descriptionFragment);
                ft.commit();

        }






}
