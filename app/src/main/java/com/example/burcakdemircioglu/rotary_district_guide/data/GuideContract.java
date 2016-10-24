package com.example.burcakdemircioglu.rotary_district_guide.data;

import android.net.Uri;

/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class GuideContract {
    public static final String CONTENT_AUTHORITY = "com.example.burcakdemircioglu.rotary_district_guide";
    public static final Uri BASE_URI = Uri.parse("content://com.example.burcakdemircioglu.rotary_district_guide");

    interface MembersColumns {
        /** Type: INTEGER PRIMARY KEY AUTOINCREMENT */
        String _ID = "_id";
        /** Type: TEXT */
        String SERVER_ID = "server_id";
        /** Type: TEXT NOT NULL */
        String NAME = "name";
        /** Type: TEXT NOT NULL */
        String SURNAME = "surname";
        /** Type: TEXT NOT NULL */
        String MEMBERID = "memberId";
        /** Type: TEXT NOT NULL */
        String CLUB = "club";
        /** Type: TEXT NOT NULL */
        String SPOUSENAME = "spouseName";
        /** Type: TEXT NOT NULL */
        String CLASSIFICATION = "classification";
        /** Type: TEXT NOT NULL */
        String JOBPHONE = "jobPhone";
        /** Type: TEXT NOT NULL */
        String JOB = "job";
        /** Type: TEXT NOT NULL */
        String CELLPHONE = "cellPhone";
        /** Type: TEXT NOT NULL */
        String EMAIL = "email";
    }
    public static class Members implements MembersColumns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.example.burcakdemircioglu.rotary_district_guide.members";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.member/vnd.com.example.burcakdemircioglu.rotary_district_guide.members";

        public static final String DEFAULT_SORT = NAME + " ASC";

        /** Matches: /members/ */
        public static Uri buildDirUri() {
            return BASE_URI.buildUpon().appendPath("members").build();
        }

        /** Matches: /members/[_id]/ */
        public static Uri buildMemberUri(long _id) {
            return BASE_URI.buildUpon().appendPath("members").appendPath(Long.toString(_id)).build();
        }
        /** Matches: /members/name/ */
        public static Uri buildMemberUri(String name) {
            return BASE_URI.buildUpon().appendPath("members").appendPath("name").appendPath(name).build();
        }

        /** Read member ID member detail URI. */
        public static long getMemberId(Uri memberUri) {
            return Long.parseLong(memberUri.getPathSegments().get(1));
        }
    }
}
