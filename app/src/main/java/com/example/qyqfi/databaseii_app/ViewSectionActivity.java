package com.example.qyqfi.databaseii_app;

import android.content.Intent;
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
    private static String url = "http://192.168.1.174/db_android/get_section.php";
    private String extralEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_section);

        linearLayout = findViewById(R.id.rootLayout);

        //retrieve account email
        Intent intent = getIntent();
        final String extraEmail = intent.getStringExtra("email");
        this.extralEmail = extraEmail;

        mQueue = Volley.newRequestQueue(this);

        jsonParse();

    }
    public void jsonParse(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("sections");
                            for(int i = 0; i< jsonArray.length();i++){
                                JSONObject sections = jsonArray.getJSONObject(i);
                                int cid = sections.getInt("cid");
                                String title = sections.getString("title");
                                //String.valueOf(age)
                                int sec_id = sections.getInt("sec_id");
                                //int ses_id = sections.getInt("ses_id");
                                createTextViewGroup(cid,title,sec_id);
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
    public void createTextViewGroup(int cid, String title, final int sec_id){
        final int course_id = cid;
        final String section_title = title;
        // Create TextView programmatically.
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(title);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewSectionActivity.this, "Section Clicked", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, enrollSectionActivity.class);
                    intent.putExtra("cid",course_id);
                    intent.putExtra("title",section_title);
                    intent.putExtra("sec_id",sec_id);
                    intent.putExtra("email",extralEmail);
                    startActivity(intent);
                }
            });

            // Add TextView to LinearLayout
            linearLayout.addView(textView);
        }
}
