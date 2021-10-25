package com.mega.netpedia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class SettingActivity extends AppCompatActivity {

    EditText currentPw, newPw, newPwCheck;
    Button btnPwUpdate, btnLogout, btnRatingInitialize, btnDeleteAccount;
    ImageView imageView4;
    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");

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

        btnPwUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentPwData = currentPw.getText().toString();
                String newPwData = newPw.getText().toString();
                String newPwCheckData = newPwCheck.getText().toString();

                Pattern pwPattern = Pattern.compile("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{10,}$");
                Matcher pwMatcher = pwPattern.matcher(newPwData);

                SQLiteDatabase sqlDB2 = myDBHelper.getReadableDatabase();
                String pwSql = "select memPw from member where memEmail = '" + email + "'";
                Cursor cursor = sqlDB2.rawQuery(pwSql, null);
                String pw = cursor.getString(0);
                //String pw = "12345qwert";

//                Log.d("check",   pwMatcher.find() +"");
                if (currentPwData.equals(pw)) {
                    if (newPwData.equals(newPwCheckData)) {
                        if (pwMatcher.find()) { // pw re check
                            SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
                            String sql ="update member set memPw = " + newPw + " where memEmail = '" + email + "';";
                            sqlDB.execSQL(sql);
                            Log.d("sqlite3DML", "데이터베이스 수정 호출함...");
                            sqlDB.close();
                            Toast.makeText(getApplicationContext(),
                                    "비밀번호 변경이 완료되었습니다..", // 토스트 텍스트
                                    Toast.LENGTH_SHORT // TOAST 길이 short으로
                            ).show(); // TOAST에 출력
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "옳바른 형식의 비밀번호가 아닙니다. 다시 입력해주세요.", // 토스트 텍스트
                                    Toast.LENGTH_SHORT // TOAST 길이 short으로
                            ).show(); // TOAST에 출력
                        } // pw re check end
                    } else{
                        Toast.makeText(getApplicationContext(),
                                "재입력한 비밀번호과 일치하지 않습니다. 다시 입력해주세요", // 토스트 텍스트
                                Toast.LENGTH_SHORT // TOAST 길이 short으로
                        ).show(); // TOAST에 출력
                    } // new pw check end
                } else {
                    Toast.makeText(getApplicationContext(),
                            "현재 비밀번호가 정확하지 않습니다. 다시 입력해주세요.", // 토스트 텍스트
                            Toast.LENGTH_SHORT // TOAST 길이 short으로
                    ).show(); // TOAST에 출력
                } // current pw check end
            }
        }); // pw update end

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnRatingInitialize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("평가내역 초기화");
                builder.setMessage("평가내역을 정말 초기화 하시겠습니까? 초기화 이후에는 복구가 불가능 합니다.");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
                        String sql = "delete from rating where memEmail = '" + email + "';";
                        sqlDB.execSQL(sql);
                        Log.d("sqlite3DML", "데이터베이스 삭제 호출함...");
                        Toast.makeText(getApplicationContext(),
                                "평가내역 초기화가 완료되었습니다.", // 토스트 텍스트
                                Toast.LENGTH_SHORT // TOAST 길이 short으로
                        ).show(); // TOAST에 출력
                        sqlDB.close();
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),
                                "평가내역 초기화가 취소되었습니다.", // 토스트 텍스트
                                Toast.LENGTH_SHORT // TOAST 길이 short으로
                        ).show(); // TOAST에 출력
                    }
                });
                builder.create().show();
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("탈퇴하기");
                builder.setMessage("넷피디아를 정말 탈퇴하시겠습니까? 탈퇴 이후에는 복구가 불가능 합니다.");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
                        String sql = "delete from member where memEmail = '" + email + "';";
                        sqlDB.execSQL(sql);
                        Log.d("sqlite3DML", "데이터베이스 삭제 호출함...");
                        Toast.makeText(getApplicationContext(),
                                "탈퇴가 완료되었습니다.", // 토스트 텍스트
                                Toast.LENGTH_SHORT // TOAST 길이 short으로
                        ).show(); // TOAST에 출력
                        sqlDB.close();
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),
                                "탈퇴가 취소되었습니다.", // 토스트 텍스트
                                Toast.LENGTH_SHORT // TOAST 길이 short으로
                        ).show(); // TOAST에 출력
                    }
                });
                builder.create().show();
            }
        });
    }
}