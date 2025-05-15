package com.example.tanksgame.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.tanksgame.R;
import com.example.tanksgame.activities.LoginActivity;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter implements ListAdapter {

    private final Activity activity;
    private final ArrayList<String> players;
    private static LayoutInflater inflater;
    private final DatabaseHelper dbHelper;
    public Resources res;
    private String tempUser;

    public CustomAdapter(Activity a, Resources resLocal) {
        activity = a;
        dbHelper = LoginActivity.dbHelper;
        this.players = dbHelper.getAllUserNames();
        res = resLocal;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * @return size of Passed Arraylist Size
     */
    public int getCount() {
        return players.size();
    }

    /**
     * @param position
     * @return player in the passed position
     */
    public Object getItem(int position) {
        return this.players.get(position);
    }

    /**
     * @param position
     * @return id of item in the passed position
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * creates  a holder Class to contain inflated xml file elements
     */
    public static class ViewHolder {
        public TextView tvName;
        public TextView tvPoints;
    }

    /**
     * @return view of each row
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;

        if (convertView == null) {

            vi = inflater.inflate(R.layout.custom_leaderboard_component, null);

            holder = new ViewHolder();
            holder.tvName = vi.findViewById(R.id.playerName);
            holder.tvPoints = vi.findViewById(R.id.playerScore);

            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (players.size() <= 0) {
            holder.tvName.setText(R.string.no_data);
        } else {
            tempUser = players.get(position);

            holder.tvName.setText(tempUser);
            String points = "" + LoginActivity.dbHelper.getUsernameRecord(tempUser);
            holder.tvPoints.setText(points);
        }
        return vi;
    }
}
