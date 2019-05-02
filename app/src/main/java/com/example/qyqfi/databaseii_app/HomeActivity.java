package com.example.qyqfi.databaseii_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private static String iPAddress = "192.168.1.174";

    private Button btn_stu_regist,btn_par_regist,btn_stu_login,btn_par_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_stu_regist = findViewById(R.id.btn_stu_regist);
        btn_par_regist = findViewById(R.id.btn_par_regist);
        btn_stu_login = findViewById(R.id.btn_stu_login);
        btn_par_login = findViewById(R.id.btn_par_login);

        setSharedPreferences();

        btn_stu_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        btn_stu_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
        btn_par_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openParentRegisterActivity();
            }
        });
        btn_par_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openParentLoginActivity();
            }
        });
    }
    private void setSharedPreferences(){
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile",MODE_PRIVATE).edit();
        editor.putString("URL_LOGIN","http://"+iPAddress+"/db_android/login.php");
        editor.putString("URL_PAR_LOGIN","http://"+iPAddress+"/db_android/parent_login.php");
        editor.putString("URL_CHANGE_PROFILE","http://"+iPAddress+"/db_android/change_profile.php");
        editor.putString("URL_CHANGE_CHILD_PROFILE","http://"+iPAddress+"/db_android/change_child_profile.php");
        editor.putString("URL_ENROLL_MTEE_SECTION","http://"+iPAddress+"/db_android/enroll_mtee_section.php");
        editor.putString("URL_ENROLL_MTOR_SECTION","http://"+iPAddress+"/db_android/enroll_mtor_section.php");
        editor.putString("URL_MODERATE_MDTOR_SECTION","http://"+iPAddress+"/db_android/moderate_mdtor_section.php");

        editor.putString("URL_GET_SECTION","http://"+iPAddress+"/db_android/get_section.php");
        editor.putString("URL_REGIST","http://"+iPAddress+"/db_android/register.php");
        editor.putString("URL_PAR_REGIST","http://"+iPAddress+"/db_android/parent_register.php");
        editor.putString("URL_GET_MTOR_MTEE_INFO","http://"+iPAddress+"/db_android/get_mtor_mtee_info.php");
        editor.putString("URL_GET_MTOR_MTEE_MDTOR_INFO","http://"+iPAddress+"/db_android/get_mtor_mtee_mdtor_info.php");

        editor.apply();
    }
    public void openRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void openParentRegisterActivity(){
        Intent intent = new Intent(this, ParentRegisterActivity.class);
        startActivity(intent);
    }
    public void openParentLoginActivity(){
        Intent intent = new Intent(this, ParentLoginActivity.class);
        startActivity(intent);
    }
}
