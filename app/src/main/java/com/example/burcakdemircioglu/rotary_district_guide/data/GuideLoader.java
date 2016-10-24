package com.example.burcakdemircioglu.rotary_district_guide.data;

/**
 * Created by burcakdemircioglu on 23/10/2016.
 */

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;


/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class GuideLoader extends CursorLoader {
    public static GuideLoader newAllMembersInstance(Context context) {
        return new GuideLoader(context, GuideContract.Members.buildDirUri());
    }

    public static GuideLoader newInstanceForMemberId(Context context, long memberId) {
        return new GuideLoader(context, GuideContract.Members.buildMemberUri(memberId));
    }
    public static GuideLoader newInstanceForMemberName(Context context, String memberName) {
        return new GuideLoader(context, GuideContract.Members.buildMemberUri(memberName));
    }

    private GuideLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, GuideContract.Members.DEFAULT_SORT);
    }


    public interface Query {
        String[] PROJECTION = {
                GuideContract.Members._ID,
                GuideContract.Members.NAME,
                GuideContract.Members.SURNAME,
                GuideContract.Members.MEMBERID,
                GuideContract.Members.SPOUSENAME,
                GuideContract.Members.CLASSIFICATION,
                GuideContract.Members.JOB,
                GuideContract.Members.JOBPHONE,
                GuideContract.Members.EMAIL,
                GuideContract.Members.CELLPHONE,
                GuideContract.Members.CLUB
        };

        int _ID = 0;
        int NAME = 1;
        int SURNAME = 2;
        int MEMBERID=3;
        int SPOUSENAME = 4;
        int CLASSIFICATION = 5;
        int JOB=6;
        int JOBPHONE = 7;
        int EMAIL = 8;
        int CELLPHONE = 9;
        int CLUB = 10;

    }
}
