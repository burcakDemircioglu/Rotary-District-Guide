package com.example.burcakdemircioglu.rotary_district_guide.data;

/**
 * Created by burcakdemircioglu on 23/10/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.burcakdemircioglu.rotary_district_guide.data.MemberProvider.Tables;

/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class GuideDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "rotary.db";
    private static final int DATABASE_VERSION = 1;

    public GuideDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.MEMBERS + " ("
                + GuideContract.MembersColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + GuideContract.MembersColumns.SERVER_ID + " TEXT,"
                + GuideContract.MembersColumns.NAME + " TEXT NOT NULL,"
                + GuideContract.MembersColumns.SURNAME + " TEXT NOT NULL,"
                + GuideContract.MembersColumns.MEMBERID + " TEXT,"
                + GuideContract.MembersColumns.CLUB + " TEXT NOT NULL,"
                + GuideContract.MembersColumns.SPOUSENAME + " TEXT,"
                + GuideContract.MembersColumns.CLASSIFICATION + " TEXT,"
                + GuideContract.MembersColumns.JOBPHONE + " TEXT,"
                + GuideContract.MembersColumns.JOB + " TEXT,"
                + GuideContract.MembersColumns.CELLPHONE + " TEXT,"
                + GuideContract.MembersColumns.EMAIL + " TEXT"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.MEMBERS);
        onCreate(db);
    }
}
