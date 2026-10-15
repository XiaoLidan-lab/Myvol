package com.example.myvol.ui.team;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myvol.R;
import com.example.myvol.bean.ActivityBean;
import com.example.myvol.bean.TeamBean;
import com.example.myvol.ui.DatabaseHelper;

import java.util.List;

public class TeamDetailActivity extends AppCompatActivity {


    TeamBean activityBean;

    String[] activityName = {"关爱老人", "打扫卫生", "整理共享单车"};
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private DatabaseHelper databaseHelper;
    int[] img = {R.drawable.mian, R.drawable.yu, R.drawable.nan,};

    //    TextView tv_people;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_detail);
        activityBean = (TeamBean) getIntent().getSerializableExtra("data");
        databaseHelper = new DatabaseHelper(this);

        sp = getSharedPreferences("user", MODE_PRIVATE);
        editor = sp.edit();
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_address = (TextView) findViewById(R.id.tv_address);
        TextView tv_id = (TextView) findViewById(R.id.tv_id);
        TextView tv_time = (TextView) findViewById(R.id.tv_time);
        TextView tv_slogan = (TextView) findViewById(R.id.tv_slogan);
        TextView tv_admin = (TextView) findViewById(R.id.tv_admin);
        TextView tv_member = (TextView) findViewById(R.id.tv_member);
        TextView tv_activity = (TextView) findViewById(R.id.tv_activity);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        tv_title.setText(activityBean.getTitle());
        tv_address.setText(activityBean.getAddress());
        tv_id.setText(activityBean.getId() + "");
        tv_time.setText(activityBean.getTime());
        tv_slogan.setText(activityBean.getSlogan());
        tv_admin.setText(activityBean.getAdmin());
//        tv_member.setText(activityBean.());
        tv_slogan.setText(activityBean.getSlogan());
        tv_activity.setText(activityBean.getActivityName());
//        tv_people.append(count + "/" + activityBean.getSumcount());
        imageView.setImageResource(img[activityBean.getImg()]);

        //加入团队成员
        List<TeamBean> list = databaseHelper.selectJoinCount(activityBean.getId() + "");
        String name = "";
        for (int i = 0; i < list.size(); i++) {
            name += list.get(i).getName() + " ";
        }
        tv_member.setText(name);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = databaseHelper.selectJoinCount(activityBean.getId() + "").size();
                 if (count>=activityBean.getSumCount()){
                     Toast.makeText(TeamDetailActivity.this, "成员已满", Toast.LENGTH_SHORT).show();
                     return;
                 }
                List<TeamBean> list = databaseHelper.isjoinTeam(activityBean.getId() + "", sp.getString("username", ""));
                if (list.size() > 0) {
                    Toast.makeText(TeamDetailActivity.this, "已加入", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean result = databaseHelper.insertMyTeam(activityBean, sp.getString("username", ""));
                if (result) {
                    Toast.makeText(TeamDetailActivity.this, "加入成功", Toast.LENGTH_SHORT).show();
                    //加入团队成员
                     list = databaseHelper.selectJoinCount(activityBean.getId() + "");
                    String name = "";
                    for (int i = 0; i < list.size(); i++) {
                        name += list.get(i).getName() + " ";
                    }

                    tv_member.setText(name);
//                    tv_people.append(count + "/" + activityBean.getSumcount());
                } else {
                    Toast.makeText(TeamDetailActivity.this, "加入失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}