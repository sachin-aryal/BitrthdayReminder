package org.application.bitrthdayreminder.Implementation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.application.bitrthdayreminder.Implementation.Model.Friend;
import org.application.bitrthdayreminder.R;

import java.util.List;

/**
 * Created by sachin on 2/17/2016.
 */
public class FriendsAdapter extends ArrayAdapter<Friend>{

    public FriendsAdapter(Context context, List<Friend> friends) {
        super(context, 0, friends);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Friend friend = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_list, parent, false);
        }

        TextView friendId = (TextView) convertView.findViewById(R.id.friend_id);
        TextView friendName = (TextView) convertView.findViewById(R.id.name);
        TextView friendLocation = (TextView) convertView.findViewById(R.id.Phone);
        TextView friendPhone = (TextView) convertView.findViewById(R.id.Location);
        TextView friendDOB = (TextView) convertView.findViewById(R.id.DOB);

        friendId.setText(friend.getId()+"");
        friendName.setText(friend.getName());
        friendLocation.setText(friend.getLocation());
        friendPhone.setText(friend.getContact());
        friendDOB.setText(friend.getDob());

        return convertView;
    }

}
