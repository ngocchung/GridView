package com.example.gridview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final Context mContext = this;
    private final ArrayList<Item> gridArray = new ArrayList<>();
    private CustomGridViewAdapter customGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        customGridAdapter = new CustomGridViewAdapter(mContext, R.layout.row_grid, gridArray);
        gridView.setAdapter(customGridAdapter);
        String url = "http://justedhak.comlu.com/get-data.php";
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null && !response.isNull("result")) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("result");
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (jsonObject != null && !jsonObject.isNull("path")) {
                                    String imagePath = jsonObject.getString("path");
                                    if (imagePath != null && !imagePath.isEmpty()) {
                                        gridArray.add(new Item(imagePath, "BNK"));
                                    }
                                }
                            }
                            customGridAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GridView", error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }
}
