package com.example.areslauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * list adapter for the score item xml, it lets you inflate arrays of score values to the screen setting it to a ListView
 */
public class ScoreAdapter extends BaseAdapter {
    //VARIABLES
    private Context context;
    private ArrayList<ScoreModel> scoreModels;

    //CONSTRUCTOR
    /**
     * Initialize variables
     * @param context Context
     * @param scoreModels ArrayLists of scoreModels
     */
    public ScoreAdapter(Context context, ArrayList<ScoreModel> scoreModels) {
        this.context = context;
        this.scoreModels = scoreModels;
    }

    //OVERRIDES METHODS - GETTERS AND SETTERS

    @Override
    public int getCount() {
        return scoreModels.size();
    }


    @Override
    public Object getItem(int i) {
        return scoreModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Sets the convert view with the score item, and the values of the scoreModels list to the ViewHolder values.
     * @param position int
     * @param convertView convertView
     * @param parent ViewGroup
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        //If the convertView is null
        if (convertView == null) {
            holder = new ViewHolder();
            //Initialize the convert view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.score_item, null, true);
            //Initialize the holder values
            holder.userTextView = (TextView) convertView.findViewById(R.id.user_text_view);
            holder.scoreTextView = (TextView) convertView.findViewById(R.id.score_text_view);
            holder.timeTextView = (TextView) convertView.findViewById(R.id.time_text_view);
            //set the convert view tag
            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
        //Set to the holder the values of the scoreModels list
        holder.userTextView.setText(scoreModels.get(position).getUser());
        holder.scoreTextView.setText(String.valueOf(scoreModels.get(position).getHighScore()));
        holder.timeTextView.setText(scoreModels.get(position).getTime());

        return convertView;
    }

    /**
     * Inner class that contains the 3 values of score
     */
    private class ViewHolder {
        //VARIABLES
        protected TextView userTextView, scoreTextView, timeTextView;

    }
}
