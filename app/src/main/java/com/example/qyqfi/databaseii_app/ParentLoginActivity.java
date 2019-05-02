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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ParentLoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btn_login;
    private ProgressBar loading;
    private String URL_PAR_LOGIN;
    private static String name_URL = "URL_PAR_LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);

        URL_PAR_LOGIN = retrieveURL(name_URL);

        loading = findViewById(R.id.loading);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if(!mEmail.isEmpty() || !mPass.isEmpty()){
                    Login(mEmail,mPass);
                } else {
                    email.setError("Please insert email");
                    password.setError("Please insert password");
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
        return  null;
    }
    private void Login(final String email, final String password){
        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PAR_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if(success.equals("1")){
                                for(int i = 0; i< jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String name1 = object.getString("name").trim();
                                    String email1 = object.getString("email").trim();

                                    Toast.makeText(ParentLoginActivity.this, "Success Login! \n Email: "
                                            +email1+"Your name: "+name1, Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(ParentLoginActivity.this, ParentMainMenuActivity.class);
                                    intent.putExtra("email",email);
                                    startActivity(intent);

                                    loading.setVisibility(View.GONE);
                                    btn_login.setVisibility(View.VISIBLE);

                                }
                            }else {
                                Toast.makeText(ParentLoginActivity.this, "Error! Incorrect email or password " ,Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
//                            Toast.makeText(ParentLoginActivity.this, "Error! " + e.toString(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(ParentLoginActivity.this, "Error! Incorrect email or password " ,Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ParentLoginActivity.this, "Error! " + error.toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(ParentLoginActivity.this, "Error! Incorrect email or password " ,Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //onCreate email, Login(): email
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
