package com.example.qyqfi.databaseii_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private Button btn_logout,btn_change_profile,btn_view_section,btn_view_mtor_mtee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btn_logout = findViewById(R.id.btn_logout);
        btn_change_profile = findViewById(R.id.btn_change_profile);
        btn_view_section = findViewById(R.id.btn_view_section);
        btn_view_mtor_mtee = findViewById(R.id.btn_view_mtor_mtee);

        Intent intent = getIntent();
        String extraEmail = intent.getStringExtra("email");


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_change_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangeProfileActivity();
            }
        });
        btn_view_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewSectionActivity();
            }
        });
        btn_view_mtor_mtee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewMtorMteeActivity();
            }
        });
    }
    public void openChangeProfileActivity(){
        Intent intent = new Intent(this, ChangeProfileActivity.class);
        startActivity(intent);
    }
    public void openViewSectionActivity(){
        Intent intent = new Intent(this, ViewSectionActivity.class);
        startActivity(intent);
    }
    public void openViewMtorMteeActivity(){
        Intent intent = new Intent(this, ViewMtorMteeActivity.class);
        startActivity(intent);
    }
}
