package com.example.back4app.barbershopapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url ="https://swd391fa2019.azurewebsites.net/api/Activity/clubId?clubId=";
    private List<Model> modelList = new ArrayList<Model>();
    private ListView listView;
    private CustomAdapter adapter;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomAdapter(this, modelList);
        listView.setAdapter(adapter);
        Bundle bundle = getIntent().getExtras();
        String clubName = bundle.getString("clubId");

        JsonArrayRequest accReq = new JsonArrayRequest(url + clubName,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());


                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Model model = new Model();
                                model.setCategory(obj.getString("activityName"));
<<<<<<< Updated upstream

=======
                                model.setEvent(obj.getString("eventDate").substring(0,10));
>>>>>>> Stashed changes
                                // adding model to movies array
                                modelList.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());


            }
        }
        );
        App.getInstance().addToRequestQueue(accReq);
    }

    public void clickToBack(View view) {
        finish();
    }
}
