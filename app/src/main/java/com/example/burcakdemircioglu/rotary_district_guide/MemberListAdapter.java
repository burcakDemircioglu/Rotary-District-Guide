package com.example.burcakdemircioglu.rotary_district_guide;

/**
 * Created by burcakdemircioglu on 24/10/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.burcakdemircioglu.rotary_district_guide.data.GuideLoader;


/**
 * Created by burcakdemircioglu on 10/05/16.
 */
public class MemberListAdapter extends CursorAdapter {
    Context activity;

    public MemberListAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);

        if (cursor == null)
            Log.e("BeerListAdapter", "cursor is null");
        else {
            Log.e("BeerListAdapter", "cursor is not null" + String.valueOf(cursor.getCount()));

        }

        activity = context;
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View root = LayoutInflater.from(context).inflate(R.layout.member_list_item, parent, false);
        return root;
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.e("ListAdapter", "in bind view");

        // Find fields to populate in inflated template
        final MemberListItemViewHolder holder = new MemberListItemViewHolder();
        holder.nameView = (TextView) view.findViewById(R.id.member_name);
        holder.clubView = (TextView) view.findViewById(R.id.member_club);

        // Extract properties from cursor
        String name = cursor.getString(GuideLoader.Query.NAME);
        String club = cursor.getString(GuideLoader.Query.CLUB);
        // Populate fields with extracted properties
        holder.nameView.setText(name);
        holder.clubView.setText(club);
    }
    public class MemberListItemViewHolder {
        public TextView nameView;
        public TextView clubView;
    }
}