package com.example.back4app.barbershopapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JoinActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private int memId;
    private List<String> memAct = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        Bundle bundle = getIntent().getExtras();
        String activityId = bundle.getString("activityId");
        String clubId = bundle.getString("clubId");
        String studentId = bundle.getString("studentId");
        String point = "10";
        checkStatus(memId,activityId);
        checkMemberActivity(memId,activityId);
        checkStatus(memId,activityId);
    }

    public void joinActivity(View view) {
        Bundle bundle = getIntent().getExtras();
        String activityId = bundle.getString("activityId");
        String clubId = bundle.getString("clubId");
        String studentId = bundle.getString("studentId");
        String point = "10";
        getMemberId(clubId, studentId);
        if(checkStatus(memId,activityId)){
            //leave
        }else {
            sendWorkPostRequest(activityId,memId,point);
        }


    }

    private void sendWorkPostRequest(String activityId, int memId, String point) {

        try {
            String URL = "https://swd391fa2019.azurewebsites.net/api/Attendance/create";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("activityId", activityId);
            jsonBody.put("memberId", memId);
            jsonBody.put("point", point);

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

    private void getMemberId(String clubId, String studentId){
        String url = "https://swd391fa2019.azurewebsites.net/api/Member/"+ studentId +"/" + clubId;
        JsonArrayRequest accReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                memId = obj.getInt("memberId");
//                                checkStatus(clubId, studentId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        System.out.println("OK I'm Fine");

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

    private boolean checkStatus(int memId, String activityId){
        Button button = this.findViewById(R.id.Join);
//        isStudentJoinedClub(clubId, studentId);
        if(memAct.contains(activityId)){
            button.setText("Leave");
            return true;
        } else {
            button.setText("Join");
            return false;
        }
    }

    private void checkMemberActivity(final int memId, final String activityId){
        String url = "https://swd391fa2019.azurewebsites.net/api/Attendance/memberId?memberId=" + memId;
        JsonArrayRequest accReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                if(!memAct.contains(obj.getString("activityId")))
                                memAct.add(obj.getString("activityId"));
//                                checkStatus(clubId, studentId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        checkStatus(memId,activityId);

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
}
