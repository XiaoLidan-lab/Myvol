package com.example.myvol.xiangmu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myvol.R;
import com.example.myvol.bean.ActivityBean;
import com.example.myvol.ui.DatabaseHelper;
import com.example.myvol.ui.team.TeamDetailActivity;

import java.util.List;

public class DetailedActivity extends AppCompatActivity {


    ActivityBean activityBean;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private DatabaseHelper databaseHelper;
    int[] img = {R.drawable.no1, R.drawable.no2, R.drawable.no3,};
    TextView tv_people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        activityBean = (ActivityBean) getIntent().getSerializableExtra("data");
        databaseHelper = new DatabaseHelper(this);

        sp = getSharedPreferences("user", MODE_PRIVATE);
        editor = sp.edit();
//        String name = getIntent().getStringExtra("name");
//        String detail = getIntent().getStringExtra("detail");
//        int imageID = getIntent().getIntExtra("imageID", 1);
//        String date = getIntent().getStringExtra("date");
//        String destination = getIntent().getStringExtra("destination");
        TextView txtname = (TextView) findViewById(R.id.name);
        TextView txtdetail = (TextView) findViewById(R.id.detail);
        TextView txtdate = (TextView) findViewById(R.id.date);
        TextView txtdest = (TextView) findViewById(R.id.destination);
        TextView createTeam = (TextView) findViewById(R.id.tv_createTeam);
        tv_people = (TextView) findViewById(R.id.tv_people);
        int count = databaseHelper.selectActivityCount(activityBean.getId() + "").size();
        ImageView imageView = (ImageView) findViewById(R.id.im);
        txtname.setText(activityBean.getTitle());
        txtdetail.setText(activityBean.getContent());
        txtdate.append(activityBean.getTime());
        txtdest.append(activityBean.getAddress());
        createTeam.append(activityBean.getCreateTeam());
        tv_people.append(count + "/" + activityBean.getSumcount());
        imageView.setImageResource(img[activityBean.getImg()]);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = databaseHelper.selectActivityCount(activityBean.getId() + "").size();
                if (count >= activityBean.getSumcount()) {
                    Toast.makeText(DetailedActivity.this, "成员已满", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<ActivityBean> list = databaseHelper.isjoinActivity(activityBean.getId() + "", sp.getString("username", ""));
                if (list.size() > 0) {
                    Toast.makeText(DetailedActivity.this, "已报名", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean result = databaseHelper.insertMyActivity(activityBean, sp.getString("username", ""));
                if (result) {
                    Toast.makeText(DetailedActivity.this, "报名成功", Toast.LENGTH_SHORT).show();
                     count = databaseHelper.selectActivityCount(activityBean.getId() + "").size();
                    tv_people.setText("报名人数："+count + "/" + activityBean.getSumcount());
                } else {
                    Toast.makeText(DetailedActivity.this, "报名失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}