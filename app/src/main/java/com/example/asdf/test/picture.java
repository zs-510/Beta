
package com.example.asdf.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Script;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Useradmin on 2016/10/23.
 */
public class picture extends Activity {
    private Button add;//添加
    private Button btnupload;//上传
    private Button btndelete;//删除
    private ImageView leftDrawer;
    private TextView Map;//地图
    private TextView picture;//查看照片
    int num = 0;
    int screenWidth;
    int screenHeigh;
    private GridView gridView1;              //网格显示缩略图
    MyAdapter myAdapter;
    public static ArrayList<Integer> arrayList = new ArrayList<Integer>();
    public static ArrayList<Integer> deletedList = new ArrayList<Integer>();
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture);
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
        add = (Button) findViewById(R.id.add);
        btnupload = (Button) findViewById(R.id.btnupload);
        leftDrawer = (ImageView) findViewById(R.id.leftdrawer);
        Map = (TextView) findViewById(R.id.Map);
        picture = (TextView) findViewById(R.id.picture);
        btndelete = (Button) findViewById(R.id.btndelete);
        gridView1 = (GridView) findViewById(R.id.gridView);
        myAdapter= new MyAdapter(this);
        //注册监听事件
//        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
////                Toast.makeText(picture.this, "pic" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(picture.this, mainView.class));
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(picture.this, picture.class));
            }
        });
        for(int i=0;i<20;i++)
        {
            arrayList.add(R.drawable.ddd);
        }
        leftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture.this.finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num == 0) {
                    btndelete.setVisibility(View.VISIBLE);
                    btnupload.setVisibility(View.VISIBLE);
                    num = 1;
                } else if (num == 1) {
                    btndelete.setVisibility(View.INVISIBLE);
                    btnupload.setVisibility(View.INVISIBLE);
                    num = 0;
                }
            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnupload.getVisibility() == View.VISIBLE) {
                    startActivity(new Intent(picture.this, upload.class));
                }
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btndelete.getVisibility() == View.VISIBLE) {
                    startActivity(new Intent(picture.this, delete.class));
                }
                else
                {
                    startActivity(new Intent(picture.this,singleDetail.class));
                }
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
                if(btndelete.getVisibility()==View.VISIBLE) {
                    dialog(position);
                }
                else {
                    startActivity(new Intent(picture.this,singleDetail.class));
                }
            }
        });
    }

    //自定义适配器
    class MyAdapter extends BaseAdapter {
        //上下文对象
        private Context context;
        //图片数组
//        arrayList ={
//                R.drawable.ddd, R.drawable.ddd, R.drawable.ddd,
//                R.drawable.ddd, R.drawable.ddd, R.drawable.ddd,
//                R.drawable.ddd, R.drawable.ddd, R.drawable.ddd,
//                R.drawable.ddd, R.drawable.ddd, R.drawable.ddd,
//                R.drawable.ddd, R.drawable.ddd, R.drawable.ddd,
//                R.drawable.ddd, R.drawable.ddd, R.drawable.ddd,
//        };

        MyAdapter(Context context) {
            this.context = context;
        }

        public int getCount() {
            return arrayList.size();
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
                imageView.setLayoutParams(new GridView.LayoutParams(screenWidth/3+30, screenWidth/3+30));//设置ImageView对象布局
                imageView.setAdjustViewBounds(false);//设置边界对齐
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型
                imageView.setPadding(8, 8, 8, 8);//设置间距
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(arrayList.get(position));//为ImageView设置图片资源
            return imageView;
        }
    }
        /*
152.     * Dialog对话框提示用户删除操作
153.     * position为删除图片位置
154.     */
        protected void dialog(final int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(picture.this);
            builder.setMessage("确认移除已添加图片吗？");
            builder.setTitle("提示");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                      @Override
                       public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                arrayList.remove(position);
                                deletedList.add(arrayList.get(position));
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
