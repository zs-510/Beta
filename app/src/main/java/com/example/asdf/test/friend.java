package com.example.asdf.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Useradmin on 2016/11/8.
 */
public class friend extends Activity {
    private ImageView leftDrawer;
    private ImageView addFriend;
    private ImageView ooo;
    private ImageView love;
    int num=0;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend);
        ooo= (ImageView) findViewById(R.id.ooo);
        addFriend= (ImageView) findViewById(R.id.addFriend);
        leftDrawer= (ImageView) findViewById(R.id.leftdrawer);
        love= (ImageView) findViewById(R.id.love);
        leftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friend.this.finish();
            }
        });
        ooo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(friend.this, tripLine.class));
            }
        });
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(friend.this,addWatch.class));
            }
        });
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num == 0) {
                    love.setBackgroundResource(R.drawable.love);
                    num = 1;
                } else if (num == 1) {
                    love.setBackgroundResource(R.drawable.before);
                    num = 0;
                }
            }
        });
    }
}
