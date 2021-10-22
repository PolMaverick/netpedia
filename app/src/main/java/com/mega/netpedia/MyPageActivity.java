package com.mega.netpedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MyPageActivity extends AppCompatActivity {

    TextView userNameText, movieRatingText, movieMemoText, movieWishText, myRatingText, myTypeText;
    Button btnInfoUpdate;
    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        userNameText = findViewById(R.id.userNameText);
        movieRatingText = findViewById(R.id.movieRatingText);
        movieMemoText = findViewById(R.id.movieMemoText);
        movieWishText = findViewById(R.id.movieWishText);
        myRatingText = findViewById(R.id.myRatingText);
        myTypeText = findViewById(R.id.myTypeText);
        btnInfoUpdate = findViewById(R.id.btnInfoUpdate);

        btnInfoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "프로필 설정 페이지로 이동합니다.", // 토스트 텍스트
                        Toast.LENGTH_SHORT // TOAST 길이 short으로
                ).show(); // TOAST에 출력

                // SettingActivity로 넘어가는 코드
                Intent intent = new Intent(MyPageActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });


        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup(); // 기초적인 tab 초기화!!!

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


    }
}