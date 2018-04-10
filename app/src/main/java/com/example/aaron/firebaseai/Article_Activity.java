package com.example.aaron.firebaseai;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.aaron.firebaseai.Fragment.FriendsList_Fragment;
import com.example.aaron.firebaseai.Fragment.Main_Fragment;
import com.example.aaron.firebaseai.Fragment.Tag_Fragment;
import com.example.aaron.firebaseai.Fragment.Write_Fragment;

public class Article_Activity extends AppCompatActivity{
    FragmentTransaction mTransaction;
    Main_Fragment mMainFragment = new Main_Fragment();
    FriendsList_Fragment mFriendsListFragment = new FriendsList_Fragment();
    Tag_Fragment mTagFragment = new Tag_Fragment();
    Write_Fragment mWriteFragment = new Write_Fragment();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_article);
    }

    private void setBottomNavigation() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.tab_main:
                        mTransaction.hide(mWriteFragment);
                        mTransaction.hide(mFriendsListFragment);
                        mTransaction.hide(mTagFragment);
                        mTransaction.show(mMainFragment);
                        mTransaction.commit();
                        break;
                    case R.id.tab_discover:
                        mTransaction.hide(mWriteFragment);
                        mTransaction.hide(mFriendsListFragment);
                        mTransaction.hide(mMainFragment);
                        mTransaction.show(mTagFragment);
                        mTransaction.commit();
                        break;
                    case R.id.tab_friend:
                        mTransaction.hide(mWriteFragment);
                        mTransaction.hide(mMainFragment);
                        mTransaction.hide(mTagFragment);
                        mTransaction.show(mFriendsListFragment);
                        mTransaction.commit();
                        break;
                    case R.id.tab_write_article:
                        mTransaction.hide(mMainFragment);
                        mTransaction.hide(mFriendsListFragment);
                        mTransaction.hide(mTagFragment);
                        mTransaction.show(mWriteFragment);
                        mTransaction.commit();
                        break;
                }
                return true;
            }
        });
    }
}
