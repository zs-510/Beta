package com.example.asdf.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Useradmin on 2016/10/23.
 */
public class delete extends Activity {
    private ImageView leftDrawer;
    private GridView gridView1;
    MyAdapter myAdapter;
    int screenWidth;
    int screenHeigh;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);
        myAdapter= new MyAdapter(this);
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
        leftDrawer= (ImageView) findViewById(R.id.leftdrawer);
        gridView1 = (GridView) findViewById(R.id.gridView);
        leftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.this.finish();
            }
        });
        gridView1.setAdapter(myAdapter);
          /*
66.         * 监听GridView点击事件
67.         * 报错:该函数必须抽象方法 故需要手动导入import android.view.View;
68.         */
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                dialog(position);
            }
        });
    }

    //自定义适配器
    class MyAdapter extends BaseAdapter {
        //上下文对象
        private Context context;
        MyAdapter(Context context) {
            this.context = context;
        }

        public int getCount() {
            return picture.deletedList.size();
        }

        public Object getItem(int item) {
            return item;
        }

        public long getItemId(int id) {
            return id;
        }

        //创建View方法
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(screenWidth / 3 + 30, screenWidth / 3 + 30));//设置ImageView对象布局
                imageView.setAdjustViewBounds(false);//设置边界对齐
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型
                imageView.setPadding(8, 8, 8, 8);//设置间距
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(picture.deletedList.get(position));//为ImageView设置图片资源
            return imageView;
        }
    }
        protected void dialog(final int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(delete.this);
            builder.setMessage("确认恢复这张图片吗？");
            builder.setTitle("提示");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    picture.deletedList.remove(position);
                    picture.arrayList.add(picture.deletedList.get(position));
                    myAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
}
