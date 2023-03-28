package com.example.wishhair;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.wishhair.MyPage.InformationModifyFragment;
import com.example.wishhair.MyPage.MyCouponFragment;
import com.example.wishhair.MyPage.MyInformationFragment;
import com.example.wishhair.MyPage.MyPageFragment;
import com.example.wishhair.MyPage.MyPointList;
import com.example.wishhair.MyPage.MyHeartlistFragment;
import com.example.wishhair.MyPage.MyStyleFragment;

import com.example.wishhair.home.HomeFragment;
import com.example.wishhair.review.ReviewFragment;
import com.example.wishhair.sign.UrlConst;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final HomeFragment homeFragment = new HomeFragment();
    private final RecommendFragment recommendFragment = new RecommendFragment();
    private final ReviewFragment reviewFragment = new ReviewFragment();
    private final MyPageFragment myPageFragment = new MyPageFragment();
    private final MyStyleFragment myStyleFragment = new MyStyleFragment();
    private final MyHeartlistFragment myHeartlistFragment = new MyHeartlistFragment();
    private final InformationModifyFragment informationModifyFragment = new InformationModifyFragment();
    private final MyCouponFragment myCouponFragment = new MyCouponFragment();
    private final MyPointList myPointList = new MyPointList();

    private final MyInformationFragment myInformationFragment = new MyInformationFragment();


    final static private String url = UrlConst.URL + "/api/my_page";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainLayout, homeFragment).commitAllowingStateLoss();

        BottomNavigationView BottomNavigation = findViewById(R.id.BottomNavigation);
        BottomNavigation.setOnItemSelectedListener(new BottomNavigationItemSelectedListener());
        

    }

    class BottomNavigationItemSelectedListener implements NavigationBarView.OnItemSelectedListener{

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(item.getItemId())
            {
                case R.id.bot_nav_home:
                    transaction.replace(R.id.MainLayout, homeFragment).commitAllowingStateLoss();
                    break;
                case R.id.bot_nav_recommend:
                    transaction.replace(R.id.MainLayout, recommendFragment).commitAllowingStateLoss();
                    break;
                case R.id.bot_nav_review:
                    transaction.replace(R.id.MainLayout, reviewFragment).commitAllowingStateLoss();
                    break;
                case R.id.bot_nav_myPage:
                    transaction.replace(R.id.MainLayout, myPageFragment).commitAllowingStateLoss();

                    break;
            }
            return true;
        }
    }

    public void ChangeFragment(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch(index)
        {
            case 1:
                transaction.replace(R.id.MainLayout, myInformationFragment).commitAllowingStateLoss();
                break;
            case 2:
                transaction.replace(R.id.MainLayout, myPageFragment).commitAllowingStateLoss();
                break;
            case 3:
                transaction.replace(R.id.MainLayout, myStyleFragment).commitAllowingStateLoss();
                break;
            case 4:
                transaction.replace(R.id.MainLayout, myHeartlistFragment).commitAllowingStateLoss();
                break;
            case 5:
                transaction.replace(R.id.MainLayout, informationModifyFragment).commitAllowingStateLoss();
                break;
            case 6:
                transaction.replace(R.id.MainLayout, myCouponFragment).commitAllowingStateLoss();
                break;
            case 7:
                transaction.replace(R.id.MainLayout, myPointList).commitAllowingStateLoss();
                break;
        }

    }


}