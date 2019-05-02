package com.example.qyqfi.databaseii_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewSectionActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private RequestQueue mQueue;
    private String URL_GET_SECTION;
    private static String name_URL = "URL_GET_SECTION";

    private String extralEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_section);

        URL_GET_SECTION = retrieveURL(name_URL);

        linearLayout = findViewById(R.id.rootLayout);

        //retrieve account email
        Intent intent = getIntent();
        final String extraEmail = intent.getStringExtra("email");
        this.extralEmail = extraEmail;

        mQueue = Volley.newRequestQueue(this);

        jsonParseSection();

    }
    public String retrieveURL(String nameURL){
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
        String result = prefs.getString(nameURL,null);
        if(result != null){
            return result;
        }
        return  null;
    }
    private void jsonParseSection(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_GET_SECTION, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("sections");
                            for(int i = 0; i< jsonArray.length();i++){
                                JSONObject sections = jsonArray.getJSONObject(i);
                                String cid = sections.getString("cid");
                                String title = sections.getString("title");
                                String sec_id = sections.getString("sec_id");
                                String startDate = sections.getString("startDate");
                                String endDate = sections.getString("endDate");
                                //String capacity = sections.getString("capacity");
                                String startTime = sections.getString("startTime");
                                String endTime = sections.getString("endTime");
                                String weekDay = sections.getString("weekDay");

                                String section_info = title+" "+sec_id+" "+
                                                        weekDay+";"+startTime+"-"+endTime+ " "+
                                                        startDate+"-"+endDate;
                                createTextViewGroup(cid,title,sec_id,section_info);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
    public void createTextViewGroup(final String cid, final String title, final String sec_id, String section_info){
        // Create TextView programmatically.
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setPadding(30,20,30,20);
            textView.setText(section_info);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(18);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ViewSectionActivity.this, "Section Clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ViewSectionActivity.this, EnrollSectionActivity.class);
                    intent.putExtra("cid",cid);
                    intent.putExtra("title",title);
                    intent.putExtra("sec_id",sec_id);
                    intent.putExtra("email",extralEmail);
                    startActivity(intent);
                }
            });

            // Add TextView to LinearLayout
            linearLayout.addView(textView);
        }
}
