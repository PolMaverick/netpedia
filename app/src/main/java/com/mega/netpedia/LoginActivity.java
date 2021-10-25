package com.mega.netpedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail, loginPw;
    Button btnLogin;
    ImageView imageView3;
    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.loginEmail);
        loginPw = findViewById(R.id.loginPw);
        btnLogin = findViewById(R.id.btnLogin);

        imageView3 = findViewById(R.id.imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "메인으로 돌아갑니다.", // 토스트 텍스트
                        Toast.LENGTH_SHORT // TOAST 길이 short으로
                ).show(); // TOAST에 출력
                // MainActivity로 넘어가는 코드
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString();
                String pw = loginPw.getText().toString();

                SQLiteDatabase sqlDB = myDBHelper.getReadableDatabase();
                String sql = "select * from member where memEmail = '" + email + "' and memPw = '" + pw + "'";
                Cursor cursor = sqlDB.rawQuery(sql, null);
                Log.d("sqlite3DML", "데이터 불러오기 성공...");
//                String email2 = "skyloveeagles@gmail.com";
//                String pw2 = "12345qwert";
                // db에서 가져와서 변수에 넣어주기
                String email2 = "";
                String pw2 = "";
                String name = "";
                while (cursor.moveToNext()) {
                    email2 = cursor.getString(0);
                    name = cursor.getString(1);
                    pw2 = cursor.getString(2);
                }
                if (email.equals(email2)&&pw.equals(pw2)) {
                    Toast.makeText(getApplicationContext(), name + "님이 로그인 했습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("name", name);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 다릅니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                sqlDB.close();
                cursor.close();
                Log.d("sqlite3DML", "데이터베이스 closed...");
            }
        });
    }
}