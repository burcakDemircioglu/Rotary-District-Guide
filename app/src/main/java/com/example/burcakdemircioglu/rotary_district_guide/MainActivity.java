package com.example.burcakdemircioglu.rotary_district_guide;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.burcakdemircioglu.rotary_district_guide.data.InfoLoadService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = (ListView) findViewById(R.id.member_listView);

        InfoLoadService service=new InfoLoadService();
        service.convertJsonToDatabase(getAssets(), getContentResolver());

        Log.e("main activity", "oncreate");
        try {
            JSONObject obj = new JSONObject(JsonUtil.loadJSONFromAsset(getAssets()));
            JSONArray uyeler = obj.getJSONArray("uyeler");

            for (int i = 0; i < uyeler.length(); i++) {
                JSONObject object = uyeler.getJSONObject(i);
                String club = object.getString("Kulübü");
                String id = object.getString("Üye ID No");
                String name = object.getString("Adı");
                String surname = object.getString("Soyadı");
                String spouseName = object.getString("Eşinin Adı");
                String classification = object.getString("Sınıflandırması");
                String jobPhone = object.getString("İş Tel");
                String job = object.getString("Mesleği");
                String cellPhone = object.getString("Cep Tel");
                String email = object.getString("E-mail");

//                TextView textView = (TextView) findViewById(R.id.text);
//                textView.setText(club + id + name + surname + spouseName + classification + jobPhone + job + cellPhone + email);
            }

        } catch (JSONException e) {
            Log.e("error", "error in taking json");
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        startService(new Intent(this, InfoLoadService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mRefreshingReceiver, new IntentFilter(InfoLoadService.BROADCAST_ACTION_STATE_CHANGE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mRefreshingReceiver);
    }

    private boolean mIsRefreshing = false;


    private BroadcastReceiver mRefreshingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("action", intent.getAction());
            if (InfoLoadService.BROADCAST_ACTION_STATE_CHANGE.equals(intent.getAction())) {
                mIsRefreshing = intent.getBooleanExtra(InfoLoadService.EXTRA_REFRESHING, false);
                updateRefreshingUI();
            }
        }
    };

    private void updateRefreshingUI() {
//        mSwipeRefreshLayout.setRefreshing(mIsRefreshing);
    }
}
