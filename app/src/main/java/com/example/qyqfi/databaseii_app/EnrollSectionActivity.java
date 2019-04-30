package com.example.qyqfi.databaseii_app;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class EnrollSectionActivity extends AppCompatActivity {

    private Button btn_enroll_as_mtor,btn_enroll_as_mtee;
    private ProgressBar loading;
    private LinearLayout linearLayout;
    private static String URL_MTOR_MTEE_INFO = "http://192.168.1.174/db_android/get_mtor_mtee_info.php";
    public String URL_ENROLL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_section);

        linearLayout = findViewById(R.id.rootLayout);
        loading = findViewById(R.id.loading);
        btn_enroll_as_mtee = findViewById(R.id.btn_enroll_as_mtee);
        btn_enroll_as_mtor = findViewById(R.id.btn_enroll_as_mtor);

        Intent intent = getIntent();
        final String extraEmail = intent.getStringExtra("email");
        final String cid = intent.getStringExtra("cid");
        final String title = intent.getStringExtra("title");
        final String sec_id = intent.getStringExtra("sec_id");
        String info = title + " "+sec_id;
        createTextViewGroup(info);
//===================================================
//        if (role.equals("mtee")){
//            btn_enroll_as_mtor.setVisibility(View.GONE);
//        }else if (role.equals("mtor")){
//            btn_enroll_as_mtee.setVisibility(View.GONE);
//        } else {
//            btn_enroll_as_mtee.setVisibility(View.VISIBLE);
//            btn_enroll_as_mtor.setVisibility(View.VISIBLE);
//        }

        jsonParseMentorMentee(extraEmail,cid,title,sec_id);

        btn_enroll_as_mtor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    URL_ENROLL = "http://192.168.1.174/db_android/enroll_mtor_section.php";
                    enroll(extraEmail,cid,title,sec_id);
            }
        });
        btn_enroll_as_mtee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL_ENROLL = "http://192.168.1.174/db_android/enroll_mtee_section.php";
                enroll(extraEmail,cid,title,sec_id);
            }
        });
    }
    private void jsonParseMentorMentee(final String email, final String cid, final String title, final String sec_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MTOR_MTEE_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArrayMentees = jsonObject.getJSONArray("mentees");
                            JSONArray jsonArrayMentors = jsonObject.getJSONArray("mentors");
                            if(success.equals("1")){
                                Toast.makeText(EnrollSectionActivity.this, "Successful load Mentor Mentee info!", Toast.LENGTH_SHORT).show();
                                for(int i = 0; i<jsonArrayMentees.length();i++){
                                    JSONObject object = jsonArrayMentees.getJSONObject(i);

                                }
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(EnrollSectionActivity.this, "Error! " + e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EnrollSectionActivity.this, "Error! " + error.toString(),Toast.LENGTH_LONG).show();
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
    private void enroll(final String email, final String cid, final String title, final String sec_id){
        loading.setVisibility(View.VISIBLE);
        btn_enroll_as_mtor.setVisibility(View.GONE);
        btn_enroll_as_mtee.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ENROLL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                    Toast.makeText(EnrollSectionActivity.this, "Successful Enroll!", Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                    btn_enroll_as_mtor.setVisibility(View.VISIBLE);
                                    btn_enroll_as_mtee.setVisibility(View.VISIBLE);
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(EnrollSectionActivity.this, "Error! " + e.toString(),Toast.LENGTH_LONG).show();
                            loading.setVisibility(View.GONE);
                            btn_enroll_as_mtor.setVisibility(View.VISIBLE);
                            btn_enroll_as_mtee.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EnrollSectionActivity.this, "Error! " + error.toString(),Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        btn_enroll_as_mtor.setVisibility(View.VISIBLE);
                        btn_enroll_as_mtee.setVisibility(View.VISIBLE);
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
