package com.example.qyqfi.databaseii_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private Button btn_logout,btn_change_profile,btn_view_section;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btn_logout = findViewById(R.id.btn_logout);
        btn_change_profile = findViewById(R.id.btn_change_profile);
        btn_view_section = findViewById(R.id.btn_view_section);

        Intent intent = getIntent();
        final String extraEmail = intent.getStringExtra("email");


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_change_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangeProfileActivity(extraEmail);
            }
        });
        btn_view_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewSectionActivity(extraEmail);
            }
        });

    }
    public void openChangeProfileActivity(String email){
        Intent intent = new Intent(this, ChangeProfileActivity.class);
        intent.putExtra("email",email);
        startActivity(intent);
        finish();
    }
    public void openViewSectionActivity(String email){
        Intent intent = new Intent(this, ViewSectionActivity.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }
}
