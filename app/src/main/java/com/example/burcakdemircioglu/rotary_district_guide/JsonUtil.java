package com.example.burcakdemircioglu.rotary_district_guide;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class JsonUtil {


    private static final String TAG = "JsonUtil";

    private JsonUtil() {
    }

    public static String loadJSONFromAsset(AssetManager assets) {
        String json = null;
        try {
            InputStream is = assets.open("uyelistesi2.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
