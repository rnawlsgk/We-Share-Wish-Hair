package com.example.wishhair.review.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wishhair.R;

import me.relex.circleindicator.CircleIndicator3;

public class ReviewDetailActivity extends AppCompatActivity {

    private final String[] images = new String[] {
            "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
    };

    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity_detail);

        btn_back = findViewById(R.id.toolbar_btn_back);
        btn_back.setOnClickListener(view -> finish());

        ViewPager2 sliderViewPager = findViewById(R.id.review_detail_viewPager);
        CircleIndicator3 circleIndicator = findViewById(R.id.review_detail_indicator);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(this, images));

        circleIndicator.setViewPager(sliderViewPager);

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