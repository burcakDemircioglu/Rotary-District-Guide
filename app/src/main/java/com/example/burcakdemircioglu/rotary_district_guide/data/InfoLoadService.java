package com.example.burcakdemircioglu.rotary_district_guide.data;

/**
 * Created by burcakdemircioglu on 23/10/2016.
 */

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.example.burcakdemircioglu.rotary_district_guide.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class InfoLoadService extends IntentService {
    private static final String TAG = "UpdaterService";

    public static final String BROADCAST_ACTION_STATE_CHANGE
            = "com.example.burcakdemircioglu.wannabeer.intent.action.STATE_CHANGE";
    public static final String EXTRA_REFRESHING
            = "com.example.burcakdemircioglu.wannabeer.intent.extra.REFRESHING";

    public InfoLoadService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("InfoLoadService", "Loading");

        sendStickyBroadcast(
                new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, true));

        // Don't even inspect the intent, we only do one thing, and that's fetch content.
//        convertJsonToDatabase(getAssets(), getContentResolver());

        sendStickyBroadcast(
                new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, false));
    }

    public void convertJsonToDatabase(AssetManager asset, ContentResolver contentResolver) {
        ArrayList<ContentProviderOperation> cpo = new ArrayList<ContentProviderOperation>();

        Uri dirUri = GuideContract.Members.buildDirUri();

        // Delete all items
        cpo.add(ContentProviderOperation.newDelete(dirUri).build());

        try {
            JSONObject obj = new JSONObject(JsonUtil.loadJSONFromAsset(asset));
            JSONArray array = obj.getJSONArray("uyeler");

            if (array == null) {
                throw new JSONException("Invalid parsed item array");
            }

            for (int i = 0; i < array.length(); i++) {
                ContentValues values = new ContentValues();
                JSONObject object = array.getJSONObject(i);
//                values.put(GuideContract.Members.SERVER_ID, object.getString("id"));
                values.put(GuideContract.Members.NAME, object.getString("Adı"));
                values.put(GuideContract.Members.SURNAME, object.getString("Soyadı"));
                values.put(GuideContract.Members.CLUB, object.getString("Kulübü"));
                values.put(GuideContract.Members.SPOUSENAME, object.getString("Eşinin Adı"));
                values.put(GuideContract.Members.CLASSIFICATION, object.getString("Sınıflandırması"));
                values.put(GuideContract.Members.JOB, object.getString("Mesleği"));
                values.put(GuideContract.Members.JOBPHONE, object.getString("İş Tel"));
                values.put(GuideContract.Members.MEMBERID, object.getString("Üye ID No"));
                values.put(GuideContract.Members.CELLPHONE, object.getString("Cep Tel"));
                values.put(GuideContract.Members.EMAIL, object.getString("E-mail"));

                cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
                //Log.v("json",object.getString("description"));
            }

            contentResolver.applyBatch(GuideContract.CONTENT_AUTHORITY, cpo);

        } catch (JSONException | RemoteException | OperationApplicationException e) {
            Log.e(TAG, "Error updating content.", e);
        }

    }
}
