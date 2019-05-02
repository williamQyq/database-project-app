package com.example.qyqfi.databaseii_app;

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

public class ParentRegisterActivity extends AppCompatActivity {

    private EditText name, email,child_email, password, c_password,phone_num;
    private Button btn_regist;
    private ProgressBar loading;
    private String URL_PAR_REGIST;
    private static String name_URL = "URL_PAR_REGIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);

        URL_PAR_REGIST = retrieveURL(name_URL);

        loading = findViewById(R.id.loading);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        child_email = findViewById(R.id.child_email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        phone_num = findViewById(R.id.phone_num);
        btn_regist = findViewById(R.id.btn_regist);
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPassword()) {
                    Regist();
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
        return  null;
    }
    private boolean checkPassword(){
        final String password = this.password.getText().toString().trim();
        final String c_password = this.c_password.getText().toString().trim();
        if(password.equals(c_password)){
            return true;
        }
        Toast.makeText(ParentRegisterActivity.this, "passwords are not same!",Toast.LENGTH_SHORT).show();
        return false;
    }
    private void Regist(){
        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);

        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String child_email = this.child_email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String phone_num = this.phone_num.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PAR_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ParentRegisterActivity.this, "Register Success!",Toast.LENGTH_SHORT).show();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
//                            Toast.makeText(ParentRegisterActivity.this, "Register Error! " + e.toString(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(ParentRegisterActivity.this, "Register Error! You may enter Child Email incorrect",Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_regist.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(ParentRegisterActivity.this, "Register Error! " + error.toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(ParentRegisterActivity.this, "Register Error! You may enter Child Email incorrect",Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("child_email",child_email);
                params.put("password",password);
                params.put("phone_num",phone_num);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
