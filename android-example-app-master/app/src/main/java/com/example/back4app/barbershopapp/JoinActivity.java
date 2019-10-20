package com.example.back4app.barbershopapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    public void joinActivity(View view) {
        Bundle bundle = getIntent().getExtras();
        String activityId = bundle.getString("activityId");
        String clubId = bundle.getString("clubId");
        String studentId = bundle.getString("studentId");
        String point = "10";
    }
}
