package com.example.myvol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myvol.R;
import com.example.myvol.bean.ActivityBean;

import java.util.List;

public class ActivityAdapter extends BaseAdapter {
    private Context mContext;
    private List<ActivityBean> ebl;

    int[] img = {R.drawable.no1, R.drawable.no2, R.drawable.no3,};

    public ActivityAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ActivityBean> ebl) {//设置数据更新界面
        this.ebl = ebl;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {//获取item的总数
        return ebl == null ? 0 : ebl.size();
    }

    @Override
    public ActivityBean getItem(int position) {//根据position得到的对应item的对象
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.xiang_item, null);
            vh.image = (ImageView) convertView.findViewById(R.id.image);
            vh.title = (TextView) convertView.findViewById(R.id.title);
            vh.content = (TextView) convertView.findViewById(R.id.content);
            vh.time = (TextView) convertView.findViewById(R.id.time);
            vh.address = (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //获取position对应的item的数据对象
        final ActivityBean bean = getItem(position);
        if (bean != null) {
            vh.title.setText(bean.getTitle());
            vh.content.setText(bean.getContent());
            vh.time.setText(bean.getTime());
            vh.address.setText(bean.getAddress());
            vh.image.setImageResource(img[bean.getImg()]);
        }
        //每个item的点击事件
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (bean == null) {
//                    return;
//                }
//                //Toast.makeText(mContext, "跳转到习题详情页面,尚未创建", Toast.LENGTH_SHORT).show();
//                //跳转到习题详情页面
//                Intent intent = new Intent(mContext, ExercisesDetailActivity.class);
//                //把章节Id传递到习题详情页面
//                intent.putExtra("id",bean.id);
//                //把标题传递到习题详情页面
//                intent.putExtra("title",bean.title);
//                mContext.startActivity(intent);
//                return;
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        public TextView title, content, time, address;
        private ImageView image;
    }
}
