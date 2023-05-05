package com.example.wishhair.review.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.wishhair.R;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class MyReviewDetailActivity extends AppCompatActivity {

    private TextView hairStyleName, tags, score, likes, date, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_detail_activity_my);

        Button btn_back = findViewById(R.id.toolbar_btn_back);
        btn_back.setOnClickListener(view -> finish());

//        image
        ViewPager2 sliderViewPager = findViewById(R.id.review_detail_my_viewPager);
        sliderViewPager.setOffscreenPageLimit(1);
        ArrayList<String> imageUrls = getIntent().getStringArrayListExtra("imageUrls");
        sliderViewPager.setAdapter(new ImageSliderAdapter(this, imageUrls));

        CircleIndicator3 circleIndicator = findViewById(R.id.review_detail_my_indicator);
        circleIndicator.setViewPager(sliderViewPager);

        hairStyleName = findViewById(R.id.review_detail_my_hairStyleName);
        hairStyleName.setText(getIntent().getStringExtra("hairStyleName"));

        tags = findViewById(R.id.review_detail_my_tags);
        ArrayList<String> hashTags = getIntent().getStringArrayListExtra("tags");
        StringBuilder stringTags = new StringBuilder();
        for (int i = 0; i < hashTags.size(); i++) {
            stringTags.append("#").append(hashTags.get(i)).append(" ");
        }
        tags.setText(stringTags);

        score = findViewById(R.id.review_detail_my_tv_score);
        score.setText(getIntent().getStringExtra("score"));

        likes = findViewById(R.id.review_detail_my_tv_likes);
        likes.setText(String.valueOf(getIntent().getIntExtra("likes", 0)));

        date = findViewById(R.id.review_detail_my_tv_date);
        date.setText(getIntent().getStringExtra("date"));

        content = findViewById(R.id.review_detail_my_tv_content);
        content.setText(getIntent().getStringExtra("content"));

    }

    public void showMenu(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();
        menu.setOnMenuItemClickListener(this::onMenuItemClick);
        inflater.inflate(R.menu.menu_review_detail, menu.getMenu());
        menu.show();
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_detail_modify:
                Log.d("menu selectd", "modify");
                return true;
            case R.id.menu_detail_delete:
                Log.d("menu selectd", "delete");
                return true;
            default:
                return false;
        }
    }
}