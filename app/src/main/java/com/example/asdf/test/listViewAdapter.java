package com.example.asdf.test;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class listViewAdapter extends BaseAdapter {
//    private ArrayList<ArrayList<HashMap<String, Object>>> mList;
    private Context context;
    private ArrayList<ArrayList<HashMap<String, Object>>> mList;
    public listViewAdapter( ArrayList<ArrayList<HashMap<String, Object>>> mList, Context mContext) {
        super();
        this.mList = mList;
        this.context = mContext;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return this.mList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mList == null) {
            return null;
        } else {
            return this.mList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.list_item, null, false);
            holder.enter = (ImageView) convertView.findViewById(R.id.enter);
            holder.examplePicture = (ImageView) convertView.findViewById(R.id.examplePicture);
            holder.friendName = (TextView) convertView.findViewById(R.id.friendName);
            holder.entertext = (TextView) convertView.findViewById(R.id.entertext);
            holder.exampleText = (TextView) convertView.findViewById(R.id.exampleText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (this.mList != null) {
            if (holder.enter != null) {
                holder.enter.setImageDrawable
                        (context.getResources().getDrawable(R.drawable.enter));
            }
            if (holder.examplePicture != null) {
                holder.examplePicture.setImageDrawable
                        (context.getResources().getDrawable(R.drawable.ddd));
            }
            if (holder.friendName != null) {
                holder.friendName.setText("好友名");
            }
            if (holder.entertext != null) {
                holder.entertext.setText("进入主页");
            }
            if (holder.exampleText != null) {
                holder.exampleText.setText("213213243243543543535345435345");
            }
        }

        return convertView;

    }


    private class ViewHolder {
        ImageView enter;
        TextView friendName;
        TextView entertext;
        ImageView examplePicture;
        TextView exampleText;
    }
}