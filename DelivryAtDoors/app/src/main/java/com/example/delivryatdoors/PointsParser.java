package com.example.delivryatdoors;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

interface TaskLoadedCallback {
    void onTaskDone(Object... values);
}

public class PointsParser extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
    TaskLoadedCallback taskCallback;
    String directionMode = "driving";

    public PointsParser(Context mContext, String directionMode) {
        this.taskCallback = (TaskLoadedCallback) mContext;
        this.directionMode = directionMode;
    }

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
        JSONObject jOb;
        List<List<HashMap<String, String>>> routes = null;
        try{
            jOb=new JSONObject(jsonData[0]);
            DataParser parser = new DataParser();
//            Log.e("PointsParser", parser.toString());
            // Starts parsing data
            routes = parser.parse(jOb);
            Log.e("PointsParser", "Executing routes");
  //          Log.e("PointsParser", routes.toString());
        }catch (Exception e){
            Log.e("PointsParser",e.toString());
            e.printStackTrace();
        }

        return routes;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        super.onPostExecute(result);
        ArrayList<LatLng> points;
        PolylineOptions lineOptions = null;
        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<>();
            lineOptions = new PolylineOptions();
            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);
            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);
                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);
                points.add(position);
            }
            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            if (directionMode.equalsIgnoreCase("walking")) {
                lineOptions.width(10);
                lineOptions.color(Color.MAGENTA);
            } else {
                lineOptions.width(15);
                lineOptions.color(Color.BLUE);
            }
        }

        if (lineOptions!=null)
                taskCallback.onTaskDone(lineOptions);// adding line
            else
                Log.e("Pointsparser","Drawing without Polyline");
    }
}
