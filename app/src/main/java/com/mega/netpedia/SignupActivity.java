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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    EditText signupName, signupEmail, signupPw;
    Button btnSignup;
    ImageView imageView;
    MyDBHelper myDBHelper;

    // git에 올리려고 수정 중
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signupName);
        signupEmail = findViewById(R.id.signupEmail);
        signupPw = findViewById(R.id.signupPw);
        btnSignup = findViewById(R.id.btnSignup);
        imageView = findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "메인으로 돌아갑니다.", // 토스트 텍스트
                        Toast.LENGTH_SHORT // TOAST 길이 short으로
                ).show(); // TOAST에 출력
                // MainActivity로 넘어가는 코드
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameData = signupName.getText().toString();
                Pattern namePattern = Pattern.compile("^.{2,}$");
                Matcher nameMatcher = namePattern.matcher(nameData);

                String emailData = signupEmail.getText().toString();

                String pwData = signupPw.getText().toString();
                Pattern pwPattern = Pattern.compile("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{10,}$");
                Matcher pwMatcher = pwPattern.matcher(pwData);

                SQLiteDatabase sqlDB2 = myDBHelper.getReadableDatabase();
                String email2 = signupEmail.getText().toString();
                String emailSql = "select memEmail from member where memEmail = '" + email2 + "'";
                Cursor cursor = sqlDB2.rawQuery(emailSql, null);

//                Log.d("check",   pwMatcher.find() +"");
//                Log.d("check",  nameMatcher.find() + "");
//                Log.d("check",  nameData + "");
//                Log.d("check",android.util.Patterns.EMAIL_ADDRESS.matcher(emailData).matches() + "");
                if (nameMatcher.find()) { // name re check
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailData).matches()) { // email re check
                        //if (cursor.getCount() == 0) {
                            if (pwMatcher.find()) { // pw re check
                                SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
                                String name = signupName.getText().toString();
                                String email = signupEmail.getText().toString();
                                String pw = signupPw.getText().toString();
                                String sql = "insert into member values ('" + email + "', '" + name + "', '" + pw + "');";
                                sqlDB.execSQL(sql);
                                Log.d("sqlite3DML", "데이터 삽입 성공...");
                                sqlDB.close();
                                sqlDB2.close();
                                cursor.close();
                                Log.d("sqlite3DML", "데이터베이스 closed...");

                                Toast.makeText(getApplicationContext(),
                                        "회원가입이 완료되었습니다. 로그인 페이지로 넘어갑니다.", // 토스트 텍스트
                                        Toast.LENGTH_SHORT // TOAST 길이 short으로
                                ).show(); // TOAST에 출력

                                // LoginActivity로 넘어가는 코드
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "옳바른 형식의 비밀번호가 아닙니다. 다시 입력해주세요.", // 토스트 텍스트
                                        Toast.LENGTH_SHORT // TOAST 길이 short으로
                                ).show(); // TOAST에 출력
                            } // pw re check end
//                        } else {
//                            Toast.makeText(getApplicationContext(),
//                                    "이미 가입한 이메일 계정입니다. 다른 이메일로 가입해주세요.", // 토스트 텍스트
//                                    Toast.LENGTH_SHORT // TOAST 길이 short으로
//                            ).show(); // TOAST에 출력
//                        } // duplication check end
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "옳바른 형식의 이메일 주소가 아닙니다. 다시 입력해주세요.", // 토스트 텍스트
                                Toast.LENGTH_SHORT // TOAST 길이 short으로
                        ).show(); // TOAST에 출력
                    } // email re check end
                } else {
                    Toast.makeText(getApplicationContext(),
                            "이름은 2자 이상 입력해주세요.", // 토스트 텍스트
                            Toast.LENGTH_SHORT // TOAST 길이 short으로
                    ).show(); // TOAST에 출력
                } // name re check end
            } // onClick end
        }); // btnSignup end
    } //onCreate end
} //class end