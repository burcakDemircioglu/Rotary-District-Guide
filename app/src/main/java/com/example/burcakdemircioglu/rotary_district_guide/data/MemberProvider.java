package com.example.burcakdemircioglu.rotary_district_guide.data;

/**
 * Created by burcakdemircioglu on 23/10/2016.
 */

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class MemberProvider extends ContentProvider

{
    private SQLiteOpenHelper mOpenHelper;

    interface Tables {
        String MEMBERS = "members";
    }

    private static final int MEMBERS = 0;
    private static final int MEMBERS__ID = 1;
    private static final int MEMBERS__NAME = 2;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = GuideContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, "members", MEMBERS);
        matcher.addURI(authority, "members/#", MEMBERS__ID);
        matcher.addURI(authority, "members/name/*", MEMBERS__NAME);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new GuideDatabase(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MEMBERS:
                return GuideContract.Members.CONTENT_TYPE;
            case MEMBERS__ID:
                return GuideContract.Members.CONTENT_ITEM_TYPE;
            case MEMBERS__NAME:
                return GuideContract.Members.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        Cursor cursor = builder.where(selection, selectionArgs).query(db, projection, sortOrder);
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MEMBERS: {
                final long _id = db.insertOrThrow(Tables.MEMBERS, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return GuideContract.Members.buildMemberUri(_id);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return builder.where(selection, selectionArgs).update(db, values);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return builder.where(selection, selectionArgs).delete(db);
    }


    private SelectionBuilder buildSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        return buildSelection(uri, match, builder);
    }

    private SelectionBuilder buildSelection(Uri uri, int match, SelectionBuilder builder) {
        final List<String> paths = uri.getPathSegments();
        Log.e("buildselection", String.valueOf(match));
        Log.e("buildselection", String.valueOf(uri));

        switch (match) {
            case MEMBERS: {
                return builder.table(Tables.MEMBERS);
            }
            case MEMBERS__ID: {
                final String _id = paths.get(1);
                return builder.table(Tables.MEMBERS).where(GuideContract.Members._ID + "=?", _id);
            }

            case MEMBERS__NAME: {
                final String name = paths.get(2);
                return builder.table(Tables.MEMBERS).where(GuideContract.Members.NAME + " LIKE ?", name + "%");
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    /**
     * Apply the given set of {@link ContentProviderOperation}, executing inside
     * a {@link SQLiteDatabase} transaction. All changes will be rolled back if
     * any single one fails.
     */
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }
}

