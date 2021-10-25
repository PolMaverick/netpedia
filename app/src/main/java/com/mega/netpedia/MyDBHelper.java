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
        db.execSQL("CREATE TABLE member (memEmail char(50) PRIMARY KEY, memName char(50), memPw char(50));");
        db.execSQL("CREATE TABLE movie (movieId char(50) PRIMARY KEY, movieTitle char(50), moviePoster char(50));");
        db.execSQL("CREATE TABLE rating (ratingId integer PRIMARY KEY autoincrement, movieRId char(50), memREmail char(50), movieRPoster char(50), movieRTitle char(50), rating real(50));");
        db.execSQL("CREATE TABLE memo (memoId integer PRIMARY KEY autoincrement, movieMId char(50), memMEmail char(50), movieMPoster char(50), movieMTitle char(50), memo real(50));");
        db.execSQL("CREATE TABLE wish (wishId integer PRIMARY KEY autoincrement, movieWId char(50), memWEmail char(50), movieWPoster char(50), movieWTitle char(50));");
        Log.d("sqlite3DDL", "CREATE TABLE 호출함...");
    }

    // 한번 더 확인
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS member");
        db.execSQL("DROP TABLE IF EXISTS movie");
        db.execSQL("DROP TABLE IF EXISTS rating");
        db.execSQL("DROP TABLE IF EXISTS wish");
        Log.d("sqlite3DDL", "DROP TABLE 호출함...");
        onCreate(db);
    }



}