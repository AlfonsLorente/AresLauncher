package com.example.areslauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * list adapter for the score item xml, it lets you inflate arrays of score values to the screen
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

    //OVERRIDES METHODS

    /**
     * Gets the size of the scoreModels array
     * @return int
     */
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.score_item, null, true);

            holder.userTextView = (TextView) convertView.findViewById(R.id.user_text_view);
            holder.scoreTextView = (TextView) convertView.findViewById(R.id.score_text_view);
            holder.timeTextView = (TextView) convertView.findViewById(R.id.time_text_view);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

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
