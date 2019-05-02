package com.example.qyqfi.databaseii_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ParentModerateSectionActivity extends AppCompatActivity {

    private Button btn_moderate_as_moderator;
    private ProgressBar loading;
    private LinearLayout linearLayout;
    private String URL;
    private String URL_MTOR_MTEE_MDTOR_INFO;
    private static String name_URL_1 = "URL_MODERATE_MDTOR_SECTION";
    private static String getName_URL_3 = "URL_GET_MTOR_MTEE_MDTOR_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_moderate_section);

        URL = retrieveURL(name_URL_1);
        URL_MTOR_MTEE_MDTOR_INFO = retrieveURL(getName_URL_3);

        linearLayout = findViewById(R.id.rootLayout);
        loading = findViewById(R.id.loading);
        btn_moderate_as_moderator = findViewById(R.id.btn_moderate_as_modtor);

        Intent intent = getIntent();
        final String extraEmail = intent.getStringExtra("email");
        final String cid = intent.getStringExtra("cid");
        final String title = intent.getStringExtra("title");
        final String sec_id = intent.getStringExtra("sec_id");
        String info = title + " "+sec_id;
        createTextViewGroup(info);

        jsonParseMentorMenteeMdtor(extraEmail,cid,title,sec_id);

        btn_moderate_as_moderator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moderate(extraEmail,cid,title,sec_id);
                finish();
            }
        });
    }
    public String retrieveURL(String nameURL){
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
        String result = prefs.getString(nameURL,null);
        if(result != null){
            return result;
        }
        return  null;
    }
    private void jsonParseMentorMenteeMdtor(final String email, final String cid, final String title, final String sec_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MTOR_MTEE_MDTOR_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArrayMentees = jsonObject.getJSONArray("mentees");
                            JSONArray jsonArrayMentors = jsonObject.getJSONArray("mentors");
                            JSONArray jsonArrayModerators = jsonObject.getJSONArray("moderators");
                            if(success.equals("1")){
                                Toast.makeText(ParentModerateSectionActivity.this, "Successful load Mentor Mentee info!", Toast.LENGTH_SHORT).show();
                                String viewGroupTitleMentee = "\nMentees Name:";
                                createTextViewGroup(viewGroupTitleMentee);
                                for(int i = 0; i<jsonArrayMentees.length();i++){
                                    JSONObject menteeObject = jsonArrayMentees.getJSONObject(i);
                                    String mentee = menteeObject.getString("name").trim();
                                    createTextViewGroup(mentee);
                                }
                                String viewGroupTitleMentor = "\nMentors Name:";
                                createTextViewGroup(viewGroupTitleMentor);
                                for(int i = 0; i<jsonArrayMentors.length();i++){
                                    JSONObject mentorObject = jsonArrayMentors.getJSONObject(i);
                                    String mentor = mentorObject.getString("name").trim();
                                    createTextViewGroup(mentor);
                                }
                                String viewGroupTitleModerator = "\nModerators Name:";
                                createTextViewGroup(viewGroupTitleModerator);
                                for(int i = 0; i<jsonArrayModerators.length();i++){
                                    JSONObject moderatorObject = jsonArrayModerators.getJSONObject(i);
                                    String moderator = moderatorObject.getString("name").trim();
                                    createTextViewGroup(moderator);
                                }
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ParentModerateSectionActivity.this, "Error! " + e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ParentModerateSectionActivity.this, "Error! " + error.toString(),Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email",email);
                params.put("cid",cid);
                params.put("title",title);
                params.put("sec_id",sec_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void moderate(final String email, final String cid, final String title, final String sec_id){
        loading.setVisibility(View.VISIBLE);
        btn_moderate_as_moderator.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ParentModerateSectionActivity.this, "Successful Moderate!", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_moderate_as_moderator.setVisibility(View.VISIBLE);

                            }
                        }catch(JSONException e){
                            e.printStackTrace();
//                            Toast.makeText(EnrollSectionActivity.this, "Error! " + e.toString(),Toast.LENGTH_LONG).show();
                            Toast.makeText(ParentModerateSectionActivity.this, "You have already moderated! ",Toast.LENGTH_LONG).show();
                            loading.setVisibility(View.GONE);
                            btn_moderate_as_moderator.setVisibility(View.VISIBLE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(EnrollSectionActivity.this, "Error! " + error.toString(),Toast.LENGTH_LONG).show();
                        Toast.makeText(ParentModerateSectionActivity.this, "You have already Moderate! ",Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        btn_moderate_as_moderator.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email",email);
                params.put("cid",cid);
                params.put("title",title);
                params.put("sec_id",sec_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void createTextViewGroup(String info){
        // Create TextView programmatically.
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setPadding(30,20,30,20);
        textView.setText(info);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        // Add TextView to LinearLayout
        linearLayout.addView(textView);
    }
}
