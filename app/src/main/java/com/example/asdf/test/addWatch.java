package com.example.asdf.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Useradmin on 2016/10/23.
 */
public class addWatch extends Activity {
    private ImageView leftDrawer;
    private ImageView enter;
    private ListView mListView;
    private listViewAdapter mListViewAdapter;
    private ArrayList<ArrayList<HashMap<String,Object>>> mArrayList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addwatch);
        init();
//        enter= (ImageView) findViewById(R.id.enter);
        leftDrawer= (ImageView) findViewById(R.id.leftdrawer);
        leftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addWatch.this.finish();
            }
        });
//        enter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(addWatch.this, friend.class));
//            }
//        });
    }
    private void init(){
        mListView=(ListView) findViewById(R.id.listview);
        initData();
        mListViewAdapter=new listViewAdapter(mArrayList, addWatch.this);
        mListView.setAdapter(mListViewAdapter);
    }
    private void initData(){
        mArrayList=new ArrayList<ArrayList<HashMap<String,Object>>>();
        HashMap<String, Object> hashMap=null;
        ArrayList<HashMap<String,Object>> arrayListForEveryGridView;
        for (int i = 0; i < 4; i++) {
            arrayListForEveryGridView=new ArrayList<HashMap<String,Object>>();
            for (int j = 0; j < 5; j++) {
                hashMap=new HashMap<String, Object>();
                hashMap.put("enter",R.drawable.enter);
                hashMap.put("examplePicture",R.drawable.love);
                hashMap.put("friendName","111");
                hashMap.put("exampleText","222222222222222");
                hashMap.put("entertext","进入主页");
                arrayListForEveryGridView.add(hashMap);
            }
            mArrayList.add(arrayListForEveryGridView);
        }

    }


}

