package com.example.bootywithfriends;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.plus.model.people.Person;

public final class PeopleAdapter extends ArrayAdapter<Person> {

    public PeopleAdapter(Context context) {
        super(context, R.layout.friend_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            view = inflator.inflate(R.layout.friend_item, parent,
                    false);
        }

        Person loc = getItem(position);

        if (loc == null) {
            Log.w("TreasureList", "Null data in ArrayAdapter");
            return null;
        }

        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText(loc.getDisplayName());

        TextView text2 = (TextView) view.findViewById(R.id.text2);
        text2.setText(loc.getId());

        view.setTag(R.id.tag_treasure_location, loc);

        return view;
    }
}