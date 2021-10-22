package com.mega.netpedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SettingActivity extends AppCompatActivity {

    EditText currentPw, newPw, newPwCheck;
    Button btnPwUpdate, btnLogout, btnRatingInitialize, btnDeleteAccount;
    ImageView imageView4;
    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        currentPw = findViewById(R.id.currentPw);
        newPw = findViewById(R.id.newPw);
        newPwCheck = findViewById(R.id.newPwCheck);
        btnPwUpdate = findViewById(R.id.btnPwUpdate);
        btnLogout = findViewById(R.id.btnLogout);
        btnRatingInitialize = findViewById(R.id.btnRatingInitialize);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        imageView4 = findViewById(R.id.imageView4);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MyPageActivity로 넘어가는 코드
                Intent intent = new Intent(SettingActivity.this, MyPageActivity.class);
                startActivity(intent);
            }
        });

    }
}