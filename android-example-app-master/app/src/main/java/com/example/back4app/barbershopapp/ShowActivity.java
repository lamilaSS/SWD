package com.example.back4app.barbershopapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url ="https://swd391fa2019.azurewebsites.net/api/Activity/clubId?clubId=";
    private List<Model> modelList = new ArrayList<Model>();
    private List<String> students = new ArrayList<String>();
    private ListView listView;
    private CustomAdapter adapter;
    private ProgressDialog pDialog;
    private String descrition;
    private String memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomAdapter(this, modelList);
        listView.setAdapter(adapter);

        //Start: Get info from MainActivity
        Bundle bundle = getIntent().getExtras();
        String clubId = bundle.getString("clubId");
        String studentId = ParseUser.getCurrentUser().get("username").toString();
        String clubName = bundle.getString("clubName");
        String description = bundle.getString("description");
        String members = bundle.getString("members");
//        Toast.makeText(getApplicationContext(), "Response:  " + "Success" , Toast.LENGTH_SHORT).show();
        String activities = bundle.getString("activities");
        getMemberId(clubId);
        Toast.makeText(getApplicationContext(),"Hello Javatpoint" + memberId ,Toast.LENGTH_SHORT).show();

        setInfoClub(clubName,clubId,description, members, activities);
        isStudentJoinedClub(clubId, studentId);
        checkStatus(clubId, studentId);
        JsonArrayRequest accReq = new JsonArrayRequest(url + clubId,
                      new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());


                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Model model = new Model();
                                model.setTitle(obj.getString("clubId"));
                                model.setCategory(obj.getString("activityName"));

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
        checkStatus(clubName, studentId);
    }

    //Set name for club onClick
    private void setInfoClub(String clubName, String clubId, String description, String members, String activities){
        TextView name = findViewById(R.id.club_name);
        TextView id = findViewById(R.id.club_id);
        TextView des = findViewById(R.id.club_description);
        TextView mem = findViewById(R.id.club_members);
        TextView act = findViewById(R.id.club_activities);
        name.setText(clubName);
        id.setText(clubId);
        des.setText(description);
        mem.setText(members);
        act.setText(activities);
    }

    public void joinClub(View view) {
        Bundle bundle = getIntent().getExtras();
        String clubName = bundle.getString("clubId");
        String studentId = ParseUser.getCurrentUser().get("username").toString();

        if(checkStatus(clubName, studentId)){
            sendWorkPutRequest(memberId);
        } else {
            sendWorkPostRequest(clubName, studentId);
        }
        checkStatus(clubName,studentId);
    }

    //Change button status: Joined -> true otherwise false
    private boolean checkStatus(String clubId, String studentId){
        Button button = this.findViewById(R.id.action);
//        isStudentJoinedClub(clubId, studentId);
        if(students.contains(studentId)){
            button.setText("Leave Club");
            return true;
        } else {
            button.setText("Join Club");
            return false;
        }
    }

    private void sendWorkPostRequest(String clubName, String studentId) {

        try {
            String URL = "https://swd391fa2019.azurewebsites.net/api/Member/Join";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("clubId", clubName);
            jsonBody.put("studentId", studentId);


            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    onBackPressed();

                }
            }) {

            };
            App.getInstance().addToRequestQueue(jsonOblect);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }



    //Check student joined club
    private void isStudentJoinedClub(final String clubId, final String studentId){
        String url = "https://swd391fa2019.azurewebsites.net/api/Member/get-all-member-by-clubid?clubId=" + clubId;
        JsonArrayRequest accReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        students.clear();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                if(!students.contains(obj.getString("studentId")))
                                students.add(obj.getString("studentId"));
//                                checkStatus(clubId, studentId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        checkStatus(clubId, studentId);
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

    private void sendWorkPutRequest(String memberId) {

        try {
            String URL = "https://swd391fa2019.azurewebsites.net/api/Member/delete";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("memberId", memberId);



            JsonObjectRequest jsonOblect2 = new JsonObjectRequest(Request.Method.DELETE, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(getApplicationContext(), "Response:  " + "Success", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    onBackPressed();

                }
            }) {

            };
            App.getInstance().addToRequestQueue(jsonOblect2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }

    private void getMemberId(String clubId){

        String url = "https://swd391fa2019.azurewebsites.net/api/Member/" + ParseUser.getCurrentUser().get("username").toString() +"/" + clubId ;
        JsonObjectRequest memReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    String memberId1 = response.getString("memberId");

                    memberId = response.getString("memberId");
                    Toast.makeText(getApplicationContext(),"Hello Javatpoint" + memberId ,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, response.getString("memberId"));
                    Log.d(TAG, "ditme" +memberId );

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().addToRequestQueue(memReq);

    }


    public void clickToBack(View view) {
        Intent intent = new Intent(ShowActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
