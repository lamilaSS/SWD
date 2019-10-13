package com.example.back4app.barbershopapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
   private Activity activity;
   private LayoutInflater inflater;
   private List<Model> modelItems;


   public CustomAdapter(Activity activity, List<Model> modelItems) {
       this.activity = activity;
       this.modelItems = modelItems;
   }

   @Override
   public int getCount() {
       return modelItems.size();
   }

   @Override
   public Object getItem(int location) {
       return modelItems.get(location);
   }

   @Override
   public long getItemId(int position) {
       return position;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {

       if (inflater == null)
           inflater = (LayoutInflater) activity
                   .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       if (convertView == null)
           convertView = inflater.inflate(R.layout.list_row, null);



       TextView title = (TextView) convertView.findViewById(R.id.title);
       TextView category = (TextView) convertView.findViewById(R.id.category);



       // getting model data for the row
       Model m = modelItems.get(position);

       // title
       title.setText("Title: " + String.valueOf(m.getTitle()));
       category.setText("Category: "+ String.valueOf(m.getCategory()));
        // category


       // thumbnail image


       return convertView;
   }

}