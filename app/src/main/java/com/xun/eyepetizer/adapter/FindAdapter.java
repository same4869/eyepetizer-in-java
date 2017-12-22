package com.xun.eyepetizer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xun.eyepetizer.R;
import com.xun.eyepetizer.mvp.model.bean.FindBean;
import com.xun.eyepetizer.utils.ImageLoadUtils;

import java.util.ArrayList;

/**
 * Created by xunwang on 2017/12/22.
 */

public class FindAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<FindBean> mList;
    private LayoutInflater mInflater;

    public FindAdapter(Context context, ArrayList<FindBean> list) {
        this.mContext = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(ArrayList<FindBean> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FindViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_find, parent, false);
            holder = new FindViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (FindViewHolder) convertView.getTag();
        }
        ImageLoadUtils.display(mContext, holder.iv_photo, mList.get(position).getBgPicture());
        holder.tv_title.setText(mList.get(position).getName());
        return convertView;
    }

    class FindViewHolder {
        ImageView iv_photo;
        TextView tv_title;

        public FindViewHolder(View itemView) {
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_photo = (ImageView) itemView.findViewById(R.id.iv_photo);
        }
    }
}
