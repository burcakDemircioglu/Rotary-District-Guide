package com.example.burcakdemircioglu.rotary_district_guide;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.burcakdemircioglu.rotary_district_guide.data.GuideLoader;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private View mRootView;
    private Cursor mCursor;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getLoaderManager().initLoader(0, null, this);

        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        bindViews();
        return mRootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return GuideLoader.newAllMembersInstance(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (!isAdded()) {
            if (cursor != null) {
                cursor.close();
            }
            return;
        }

        mCursor = cursor;
        if (mCursor != null && !mCursor.moveToFirst()) {
            Log.e("Members", "Error reading members cursor");
            mCursor.close();
            mCursor = null;
        }
        if (mCursor == null)
            Log.e("member", "Cursor is null!!");
        bindViews();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;

    }

    private void bindViews() {
        if (mRootView == null) {
            return;
        }

        if (mCursor != null) {
            mRootView.setAlpha(0);
            mRootView.setVisibility(View.VISIBLE);
            mRootView.animate().alpha(1);
            ListView beerList = (ListView) mRootView.findViewById(R.id.member_listView);

            beerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
//                    mCursor.moveToPosition(position);
//                    Intent intent=new Intent(getActivity(), BeerDetailActivityWithoutPager.class);
//                    intent.putExtra("ItemId", mCursor.getLong(InfoLoader.Query._ID));
//                    //BeersContract.Items.buildItemUri(mCursor.getInt(InfoLoader.Query._ID)));
//                    startActivity(intent);
                }
            });
            MemberListAdapter memberAdapter = new MemberListAdapter(getActivity(), mCursor);
            beerList.setAdapter(memberAdapter);

        } else {
            mRootView.setVisibility(View.GONE);
        }
    }
}
