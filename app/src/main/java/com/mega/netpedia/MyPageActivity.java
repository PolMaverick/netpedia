package com.mega.netpedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MyPageActivity extends AppCompatActivity {

    TextView userNameText, movieRatingText, movieMemoText, movieWishText, myRatingText, myTypeText;
    Button btnInfoUpdate, btnNetflix;
    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");

        userNameText = findViewById(R.id.userNameText);
        movieRatingText = findViewById(R.id.movieRatingText);
        movieMemoText = findViewById(R.id.movieMemoText);
        movieWishText = findViewById(R.id.movieWishText);
        myRatingText = findViewById(R.id.myRatingText);
        myTypeText = findViewById(R.id.myTypeText);
        btnInfoUpdate = findViewById(R.id.btnInfoUpdate);
        btnNetflix = findViewById(R.id.btnNetflix);

        userNameText.setText(name);

        btnInfoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "프로필 설정 페이지로 이동합니다.", // 토스트 텍스트
                        Toast.LENGTH_SHORT // TOAST 길이 short으로
                ).show(); // TOAST에 출력

                // SettingActivity로 넘어가는 코드
                Intent intent = new Intent(MyPageActivity.this, SettingActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        SQLiteDatabase sqlDB = myDBHelper.getReadableDatabase();
        String ratingSql = "select rating from rating where memEmail = '" + email + "'";
        String memoSql = "select memo from memo where memEmail = '" + email + "'";
        String wishSql = "select movieWTitle from wish where memEmail = '" + email + "'";
        Cursor cursor1 = sqlDB.rawQuery(ratingSql, null);
        Cursor cursor2 = sqlDB.rawQuery(memoSql, null);
        Cursor cursor3 = sqlDB.rawQuery(wishSql, null);


        movieRatingText.setText(cursor1.getCount());
        movieMemoText.setText(cursor2.getCount());
        movieWishText.setText(cursor3.getCount());


        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup(); // 기초적인 tab 초기화!!!
        
        // 각탭마다 for문으로 포스터 넣어주기
        // 각 cursor에서 포스터 경로만 빼서 setContent에 넣어주기

        // 각 탭마다의 설정을 넣음. 그냥 부품만 만들어줬을 뿐 아직 탭호스트에 안들어간 상태
        TabHost.TabSpec tabSpecHome = tabHost.newTabSpec("ratingTab").setIndicator("평가한");
        // layout을 끼워 넣음
        tabSpecHome.setContent(R.id.ratingTab);
        // 이제 탭호스트에 넣기
        tabHost.addTab(tabSpecHome);

        // 각 탭마다의 설정을 넣음. 그냥 부품만 만들어줬을 뿐 아직 탭호스트에 안들어간 상태
        TabHost.TabSpec tabSpecCategory = tabHost.newTabSpec("memoTab").setIndicator("메모한");
        // layout을 끼워 넣음
        tabSpecCategory.setContent(R.id.memoTab);
        // 이제 탭호스트에 넣기
        tabHost.addTab(tabSpecCategory);

        // 각 탭마다의 설정을 넣음. 그냥 부품만 만들어줬을 뿐 아직 탭호스트에 안들어간 상태
        TabHost.TabSpec tabSpecGift = tabHost.newTabSpec("wishTab").setIndicator("보고싶은");
        // layout을 끼워 넣음
        tabSpecGift.setContent(R.id.wishTab);
        // 이제 탭호스트에 넣기
        tabHost.addTab(tabSpecGift);

        // 어느 탭이 열려있는 상태로 시작되는지
        tabHost.setCurrentTab(0);
        
        // rating 불러와서
        // 길이만큼 나누고
        // 소수점 2자리로 반올림처리
        // 문구+평점으로 나타나게
        //myRatingText.setText("내가 준 별점의 평균은 " + myRating);
        
        // 위의 rating을 기반으로 0-1, 1-2, 2-3, 3-4, 4-5 구간별로 if문 사용해 각 문구 뜨도록
//        if (myRating <= 5 && myRating > 4){
//            myTypeText.setText("남들보다 별점을 후하게 주는 '다정파'시군요.");
//        } else if(myRating <= 4 && myRating > 3) {
//            myTypeText.setText("평균보다 후하게 별점을 주는 '인심파'시군요.");
//        } else if(myRating <= 3 && myRating > 2) {
//            myTypeText.setText("평균적으로 별점을 주는 '중립파'시군요.");
//        } else if(myRating <= 2 && myRating > 1) {
//            myTypeText.setText("평균보다 엄격하게 별점을 주는 '엄격파'시군요.");
//        } else if(myRating <= 1 && myRating > 0) {
//            myTypeText.setText("남들보다 별점을 엄격하게 주는 '평론가파'시군요.");
//        }

        btnNetflix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "넷플릭스로 이동합니다.", Toast.LENGTH_SHORT).show();
                // 다른 화면으로 넘어가는 부품(객체)
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://netflix.com")
                );
                startActivity(intent);
            }
        });
    }
}