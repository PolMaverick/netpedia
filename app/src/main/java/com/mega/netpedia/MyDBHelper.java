package com.mega.netpedia;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {

    // db 생성
    public MyDBHelper(@Nullable Context context) {
        super(context, "npedia", null, 1);
        Log.d("sqlite3DDL", "데이터베이스 생성 호출함...");
    }

    // 추상클래스를 사용하는 것은 강제성이 있기 때문에 사용

    // 테이블 만드는 코드
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE member (memId char(50), memEmail char(50), memPw char(50), moviewRating char());");
        Log.d("sqlite3DDL", "CREATE TABLE 호출함...");
    }

    // 한번 더 확인
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS member");
        Log.d("sqlite3DDL", "DROP TABLE 호출함...");
        onCreate(db);

    }
}