package com.example.myvol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myvol.R;
import com.example.myvol.bean.TeamBean;

import java.util.List;

public class TeamAdapter extends BaseAdapter {
    private Context mContext;
    private List<TeamBean> ebl;

    int[] img = {R.drawable.mian, R.drawable.yu, R.drawable.nan,};

    public TeamAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<TeamBean> ebl) {//设置数据更新界面
        this.ebl = ebl;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {//获取item的总数
        return ebl == null ? 0 : ebl.size();
    }

    @Override
    public TeamBean getItem(int position) {//根据position得到的对应item的对象
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.team_item, null);
            vh.name_text = (TextView) convertView.findViewById(R.id.name_text);
            vh.address_text = (TextView) convertView.findViewById(R.id.address_text);
            vh.activity_count_text = (TextView) convertView.findViewById(R.id.activity_count_text);
            vh.team_size_text = (TextView) convertView.findViewById(R.id.team_size_text);
            vh.imageView = (ImageView) convertView.findViewById(R.id.profile_image);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //获取position对应的item的数据对象
        final TeamBean bean = getItem(position);
        if (bean != null) {
            vh.name_text.setText(bean.getTitle());
            vh.address_text.setText(bean.getAddress());
            vh.activity_count_text.setText(bean.getActivityCount() + "");
            vh.team_size_text.setText(bean.getCount() + "/" +bean.getSumCount()+"");
            vh.imageView.setImageResource(img[bean.getImg()]);
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
        public TextView name_text, address_text, activity_count_text, team_size_text;
        ImageView imageView;
    }
}
