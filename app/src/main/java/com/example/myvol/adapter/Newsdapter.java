package com.example.myvol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myvol.R;
import com.example.myvol.bean.NewsBean;
import com.example.myvol.bean.NewsBean;

import java.util.List;

public class Newsdapter extends BaseAdapter {
    private Context mContext;
    private List<NewsBean> ebl;


    public Newsdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<NewsBean> ebl) {//设置数据更新界面
        this.ebl = ebl;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {//获取item的总数
        return ebl == null ? 0 : ebl.size();
    }

    @Override
    public NewsBean getItem(int position) {//根据position得到的对应item的对象
        return ebl == null ? null : ebl.get(position);
    }

    @Override
    public long getItemId(int position) {//根据position得到对应Item的id
        return position;
    }

    //得到相应的position对应的item试图，position是当前item的位置，
    //convertView参数就是画出屏幕的item的view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {//复用convertView
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_news, null);
            vh.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            vh.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //获取position对应的item的数据对象
        final NewsBean bean = getItem(position);
        if (bean != null) {
            vh.tv_title.setText(bean.getTitle());
            vh.tv_time.setText(bean.getTime());
        }
        return convertView;
    }

    class ViewHolder {
        public TextView tv_title, tv_time;
    }
}
