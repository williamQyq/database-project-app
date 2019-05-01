package com.example.qyqfi.databaseii_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangeChildProfileActivity extends AppCompatActivity {
    private EditText old_child_email,name, email, password, c_password,phone_num;
    private Button btn_regist;
    private ProgressBar loading;
    private String URL;
    private static String name_URL = "URL_CHANGE_CHILD_PROFILE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_child_profile);

        URL = retrieveURL(name_URL);

        Intent intent = getIntent();
        final String extraEmail = intent.getStringExtra("email");

        loading = findViewById(R.id.loading);
        old_child_email = findViewById(R.id.old_child_email);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        phone_num = findViewById(R.id.phone_num);
        btn_regist = findViewById(R.id.btn_regist);
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPassword()) {
                    Regist(extraEmail);
                    finish();
                }
            }
        });
    }
    public String retrieveURL(String nameURL){
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
        String result = prefs.getString(nameURL,null);
        if(result != null){
            return result;
        }
        return null;
    }
    private boolean checkPassword(){
        final String password = this.password.getText().toString().trim();
        final String c_password = this.c_password.getText().toString().trim();
        if(password.equals(c_password)){
            return true;
        }
        Toast.makeText(ChangeChildProfileActivity.this, "passwords are not same!",Toast.LENGTH_SHORT).show();
        return false;
    }
    private void Regist(String extraEmail){
        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);

        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String phone_num = this.phone_num.getText().toString().trim();
        final String old_child_email = this.old_child_email.getText().toString().trim();
        final String oldEmail = extraEmail;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ChangeChildProfileActivity.this, "Change Profile Success!",Toast.LENGTH_SHORT).show();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ChangeChildProfileActivity.this, "Register Error! " + e.toString(),Toast.LENGTH_LONG).show();
                            loading.setVisibility(View.GONE);
                            btn_regist.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChangeChildProfileActivity.this, "Register Error! " + error.toString(),Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("old_child_email",old_child_email);
                params.put("name",name);
                params.put("email",email);
                params.put("password",password);
                params.put("phone_num",phone_num);
                params.put("old_email",oldEmail);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
