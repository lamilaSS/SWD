package com.example.back4app.barbershopapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
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

public class MainActivity extends Activity {
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String url =" https://swd391fa2019.azurewebsites.net/api/Club/club";
    private ProgressDialog pDialog;

    private List<Model> modelList = new ArrayList<Model>();
    private ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomAdapter(this, modelList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Model model = new Model();
                                model.setTitle(obj.getString("clubId"));
                                model.setCategory(obj.getString("clubName"));
                                model.setDescription(obj.getString("description"));
                                model.setMembers(obj.getString("numberMemberOfClub"));
                                model.setActivities(obj.getString("numberActivityOfClub"));
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
                hidePDialog();

            }
        }
        );
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listViewItem = listView.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("clubId",((Model) listViewItem).getTitle());
                intent.putExtra("clubName", ((Model) listViewItem).getCategory());
                intent.putExtra("description", ((Model) listViewItem).getDescription());
                intent.putExtra("members", ((Model) listViewItem).getMembers());
                intent.putExtra("activities", ((Model) listViewItem).getActivities());
                startActivity(intent);

            }
        });
        // Adding request to request queue
        App.getInstance().addToRequestQueue(movieReq);

    }

    public void setupSelectedListener(){

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    public void clickToBack(View view) {
        finish();
    }
}
