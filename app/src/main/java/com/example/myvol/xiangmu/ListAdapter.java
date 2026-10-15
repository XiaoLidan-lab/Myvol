package com.example.myvol.xiangmu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myvol.R;

import java.util.List;

//用于将上下文、listview 子项布局的 id 和数据都传递过来
public class ListAdapter extends ArrayAdapter<XiangMu> {
    public ListAdapter(@NonNull Context context, int resource, @NonNull List<XiangMu> objects) {
        super(context, resource, objects);
    }
    //每个子项被滚动到屏幕内的时候会被调用
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        XiangMu item=getItem(position);//得到当前项的 Fruit 实例
        //为每一个子项加载设定的布局
        View view=LayoutInflater.from(getContext()).inflate(R.layout.xiang_item,parent,false);
        //分别获取 image view 和 textview 的实例
//        ImageView fruitimage =view.findViewById(R.id.fruit_image);
//        TextView fruitname =view.findViewById(R.id.fruit_name);
//        TextView fruitprice=view.findViewById(R.id.fruit_price);
//        TextView date =view.findViewById(R.id.date);
//        TextView destination=view.findViewById(R.id.destination);
//        // 设置要显示的图片和文字
//        fruitimage.setImageResource(item.getImageID());
//        fruitname.setText(item.getName());
//        fruitprice.setText(item.getDetail());
//        date.setText(item.getDate());
//        destination.setText(item.getDestination());

        return view;
    }
}
