package com.example.myvol.ui;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myvol.bean.ActivityBean;
import com.example.myvol.bean.NewsBean;
import com.example.myvol.bean.TeamBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "User.db";


    //通用字段
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_CONTENT = "content";
    private static final String COL_TIME = "time";
    private static final String COL_IMG = "img";
    private static final String COL_COUNT = "count";
    private static final String COL_SUMCOUNT = "sumcount";
    private static final String COL_Address = "address";
    //========用戶==============
    private static final String TABLE_USER = "users";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_PHONE = "phone";

    //========新聞==============
    private static final String TABLE_NEWS = "news";

    //========活動==============
    private static final String TABLE_ACTIVITY = "activity";
    private static final String COL_CREATETEAM = "createteam";


    //========团队=============
    private static final String TABLE_TEAM = "team";
    private static final String COL_NAME = "name";
    private static final String COL_SLOGAN = "slogan";
    private static final String COL_ADMIN = "admin";
    private static final String COL_ACTIVITYCOUNT = "activitycount";
    private static final String COL_ACTIVITYNAME = "activityname";


    //========加入团队=============
    private static final String TABLE_JIONTEAM = "jionteam";

    private static final String COL_TEAMID = "teamid";
    //加入活动
    private static final String TABLE_JIONACTIVITY = "jionactivity";

    private static final String COL_ACTIVITYID = "activiid";


    String userTable = "CREATE TABLE " + TABLE_USER + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_USERNAME + " TEXT, "
            + COL_PASSWORD + " TEXT,"
            + COL_PHONE + " TEXT)";
    //活動
    String activityTable = " CREATE TABLE " + TABLE_ACTIVITY + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_IMG + " INTEGER, "
            + COL_TITLE + " TEXT, "
            + COL_TIME + " TEXT, "
            + COL_CREATETEAM + " TEXT, "
            + COL_Address + " TEXT, "
            + COL_COUNT + " INTEGER, "
            + COL_SUMCOUNT + " INTEGER, "
            + COL_CONTENT + " TEXT)";
    //新聞
    String newsTable = "CREATE TABLE " + TABLE_NEWS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TITLE + " TEXT, "
            + COL_CONTENT + " TEXT,"
            + COL_TIME + " TEXT)";
    // 團隊
    String teamTable = "CREATE TABLE " + TABLE_TEAM + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_IMG + " INTEGER, "
            + COL_TITLE + " TEXT, "
            + COL_TIME + " TEXT, "
            + COL_ADMIN + " TEXT, "
            + COL_Address + " TEXT, "
            + COL_ACTIVITYNAME + " TEXT, "
            + COL_COUNT + " INTEGER, "
            + COL_SUMCOUNT + " INTEGER, "
            + COL_ACTIVITYCOUNT + " INTEGER, "
            + COL_SLOGAN + " TEXT)";

    //加入的

    //加入活动
    String joinActivityTable = "CREATE TABLE " + TABLE_JIONACTIVITY + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_ACTIVITYID + " INTEGER, "
            + COL_IMG + " INTEGER, "
            + COL_TITLE + " TEXT, "
            + COL_TIME + " TEXT, "
            + COL_CREATETEAM + " TEXT, "
            + COL_Address + " TEXT, "
            + COL_COUNT + " INTEGER, "
            + COL_SUMCOUNT + " INTEGER, "
            + COL_NAME + " TEXT, "
            + COL_CONTENT + " TEXT)";

    //加入的团队
    String joinTeamTable = "CREATE TABLE " + TABLE_JIONTEAM + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_IMG + " INTEGER, "
            + COL_TITLE + " TEXT, "
            + COL_TIME + " TEXT, "
            + COL_ADMIN + " TEXT, "
            + COL_Address + " TEXT, "
            + COL_ACTIVITYNAME + " TEXT, "
            + COL_COUNT + " INTEGER, "
            + COL_SUMCOUNT + " INTEGER, "
            + COL_ACTIVITYCOUNT + " INTEGER, "
            + COL_TEAMID + " INTEGER, "
            + COL_NAME + " TEXT, "
            + COL_SLOGAN + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(userTable);
        db.execSQL(teamTable);
        db.execSQL(activityTable);
        db.execSQL(newsTable);
        db.execSQL(joinActivityTable);
        db.execSQL(joinTeamTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean insertUser(String username, String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_PHONE, phone);
//        contentValues.put(COL_PHONE, phone);
        long result = db.insert(TABLE_USER, null, contentValues);
        return result != -1;
    }
    /**
     * 新闻列表增加数据
     *
     * @param bean
     * @return
     */
    public boolean insertNews(NewsBean bean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TITLE, bean.getTitle());
        contentValues.put(COL_CONTENT, bean.getContent());
        contentValues.put(COL_TIME, bean.getTime());
        long result = db.insert(TABLE_NEWS, null, contentValues);
        return result != -1;
    }
    /**
     * 活动列表增加数据
     *
     * @param bean
     * @return
     */
    public boolean insertActivity(ActivityBean bean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IMG, bean.getImg());
        contentValues.put(COL_TITLE, bean.getTitle());
        contentValues.put(COL_CONTENT, bean.getContent());
        contentValues.put(COL_TIME, bean.getTime());
        contentValues.put(COL_SUMCOUNT, bean.getSumcount());
        contentValues.put(COL_Address, bean.getAddress());
        contentValues.put(COL_CREATETEAM, bean.getCreateTeam());
//        contentValues.put(COL_PHONE, phone);
        long result = db.insert(TABLE_ACTIVITY, null, contentValues);
        return result != -1;
    }

    /**
     * 团队列表新增数据
     *
     * @param bean
     * @return
     */
    public boolean insertTeam(TeamBean bean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IMG, bean.getImg());
        contentValues.put(COL_TITLE, bean.getTitle());
        contentValues.put(COL_TIME, bean.getTime());
        contentValues.put(COL_SUMCOUNT, bean.getSumCount());
        contentValues.put(COL_Address, bean.getAddress());
        contentValues.put(COL_ADMIN, bean.getAdmin());
        contentValues.put(COL_SLOGAN, bean.getSlogan());
        contentValues.put(COL_ACTIVITYNAME, bean.getActivityName());
        contentValues.put(COL_ACTIVITYCOUNT, bean.getActivityCount());
        long result = db.insert(TABLE_TEAM, null, contentValues);
        return result != -1;
    }

    /**
     * 加入活动
     *
     * @param bean
     * @param name
     * @return
     */
    public boolean insertMyActivity(ActivityBean bean, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IMG, bean.getImg());
        contentValues.put(COL_TITLE, bean.getTitle());
        contentValues.put(COL_CONTENT, bean.getContent());
        contentValues.put(COL_TIME, bean.getTime());
        contentValues.put(COL_SUMCOUNT, bean.getSumcount());
        contentValues.put(COL_Address, bean.getAddress());
        contentValues.put(COL_CREATETEAM, bean.getCreateTeam());
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_ACTIVITYID, bean.getId());
        long result = db.insert(TABLE_JIONACTIVITY, null, contentValues);
        return result != -1;
    }
    /**
     * 加入团队
     *
     * @param bean
     * @param name
     * @return
     */
    public boolean insertMyTeam(TeamBean bean, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IMG, bean.getImg());
        contentValues.put(COL_TITLE, bean.getTitle());
        contentValues.put(COL_TIME, bean.getTime());
        contentValues.put(COL_SUMCOUNT, bean.getSumCount());
        contentValues.put(COL_Address, bean.getAddress());
        contentValues.put(COL_ADMIN, bean.getAdmin());
        contentValues.put(COL_SLOGAN, bean.getSlogan());
        contentValues.put(COL_ACTIVITYNAME, bean.getActivityName());
        contentValues.put(COL_ACTIVITYCOUNT, bean.getActivityCount());
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_TEAMID, bean.getId());
        long result = db.insert(TABLE_JIONTEAM, null, contentValues);
        return result != -1;
    }
    /**
     * 查询我的活动列表
     *
     * @param username
     * @return
     */
    public List<ActivityBean> selectMyActivity(String username) {
        List<ActivityBean> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_NAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_JIONACTIVITY, null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            ActivityBean activityBean = new ActivityBean();
            @SuppressLint("Range") String createteam = cursor.getString(cursor.getColumnIndex(COL_CREATETEAM));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COL_ACTIVITYID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(COL_CONTENT));
            @SuppressLint("Range") int img = cursor.getInt(cursor.getColumnIndex(COL_IMG));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(COL_Address));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COL_TIME));
            @SuppressLint("Range") int sumcount = cursor.getInt(cursor.getColumnIndex(COL_SUMCOUNT));
            activityBean.setCreateTeam(createteam);
            activityBean.setTitle(title);
            activityBean.setSumcount(sumcount);
            activityBean.setImg(img);
            activityBean.setAddress(address);
            activityBean.setContent(content);
            activityBean.setTime(time);
            activityBean.setId(id);
            list.add(activityBean);
        }
        cursor.close();
        return list;
    }
    /**
     * 查询我的团队列表
     *
     * @param username
     * @return
     */
    public List<TeamBean> selectMyTeam(String username) {
        List<TeamBean> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_NAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_JIONTEAM, null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            TeamBean activityBean = new TeamBean();
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COL_TEAMID));
            @SuppressLint("Range") int activitycount = cursor.getInt(cursor.getColumnIndex(COL_ACTIVITYCOUNT));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
            @SuppressLint("Range") String slogan = cursor.getString(cursor.getColumnIndex(COL_SLOGAN));
            @SuppressLint("Range") String admin = cursor.getString(cursor.getColumnIndex(COL_ADMIN));
            @SuppressLint("Range") int img = cursor.getInt(cursor.getColumnIndex(COL_IMG));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(COL_Address));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COL_TIME));
            @SuppressLint("Range") int sumcount = cursor.getInt(cursor.getColumnIndex(COL_SUMCOUNT));
            @SuppressLint("Range") String activityname = cursor.getString(cursor.getColumnIndex(COL_ACTIVITYNAME));
            activityBean.setActivityCount(activitycount);
            activityBean.setTitle(title);
            activityBean.setSumCount(sumcount);
            activityBean.setImg(img);
            activityBean.setAddress(address);
            activityBean.setSlogan(slogan);
            activityBean.setAdmin(admin);
            activityBean.setTime(time);
            activityBean.setActivityName(activityname);
            activityBean.setId(id);
            list.add(activityBean);
        }
        cursor.close();
        return list;
    }
    /**
     * 查询当前活动加入的人数
     *
     * @param
     * @return
     */
    public List<ActivityBean> selectActivityCount(String id) {
        List<ActivityBean> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_ACTIVITYID + " = ?";
        String[] selectionArgs = {id};
        Cursor cursor = db.query(TABLE_JIONACTIVITY, null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            ActivityBean activityBean = new ActivityBean();
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
            activityBean.setTitle(title);
            list.add(activityBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 查询当前团队加入的人数
     *
     * @param
     * @return
     */
    public List<TeamBean> selectJoinCount(String id) {
        List<TeamBean> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_TEAMID + " = ?";
        String[] selectionArgs = {id};
        Cursor cursor = db.query(TABLE_JIONTEAM, null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            TeamBean activityBean = new TeamBean();
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            activityBean.setTitle(title);
            activityBean.setName(name);
            list.add(activityBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 查询当前用户是否加入此活动
     *
     * @param
     * @return
     */
    public List<ActivityBean> isjoinActivity(String id, String name) {
        List<ActivityBean> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_ACTIVITYID + " = ? AND " + COL_NAME + " = ?";
        String[] selectionArgs = {id, name};
        Cursor cursor = db.query(TABLE_JIONACTIVITY, null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            ActivityBean activityBean = new ActivityBean();
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
            activityBean.setTitle(title);
            list.add(activityBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 查询当前用户是否报名团队
     *
     * @param
     * @return
     */
    public List<TeamBean> isjoinTeam(String id, String name) {
        List<TeamBean> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_TEAMID + " = ? AND " + COL_NAME + " = ?";
        String[] selectionArgs = {id, name};
        Cursor cursor = db.query(TABLE_JIONTEAM, null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            TeamBean activityBean = new TeamBean();
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
            activityBean.setTitle(title);
            list.add(activityBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 查询所有活动列表
     *
     * @return
     */
    public List<ActivityBean> selectActivity() {
        List<ActivityBean> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACTIVITY, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            ActivityBean activityBean = new ActivityBean();
            @SuppressLint("Range") String createteam = cursor.getString(cursor.getColumnIndex(COL_CREATETEAM));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(COL_CONTENT));
            @SuppressLint("Range") int img = cursor.getInt(cursor.getColumnIndex(COL_IMG));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(COL_Address));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COL_TIME));
            @SuppressLint("Range") int sumcount = cursor.getInt(cursor.getColumnIndex(COL_SUMCOUNT));
            activityBean.setCreateTeam(createteam);
            activityBean.setTitle(title);
            activityBean.setSumcount(sumcount);
            activityBean.setImg(img);
            activityBean.setAddress(address);
            activityBean.setContent(content);
            activityBean.setTime(time);
            activityBean.setId(id);
            list.add(activityBean);
        }
        cursor.close();
        return list;
    }
    /**
     * 查询所有新闻列表
     *
     * @return
     */
    public List<NewsBean> selectNews() {
        List<NewsBean> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NEWS, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            NewsBean activityBean = new NewsBean();
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(COL_CONTENT));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COL_TIME));
            activityBean.setTitle(title);
            activityBean.setContent(content);
            activityBean.setTime(time);
            list.add(activityBean);
        }
        cursor.close();
        return list;
    }
    /**
     * 查询所有团队列表
     *
     * @return
     */
    public List<TeamBean> selectTeam() {
        List<TeamBean> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEAM, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            TeamBean activityBean = new TeamBean();
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
            @SuppressLint("Range") int activitycount = cursor.getInt(cursor.getColumnIndex(COL_ACTIVITYCOUNT));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
            @SuppressLint("Range") String slogan = cursor.getString(cursor.getColumnIndex(COL_SLOGAN));
            @SuppressLint("Range") String admin = cursor.getString(cursor.getColumnIndex(COL_ADMIN));
            @SuppressLint("Range") int img = cursor.getInt(cursor.getColumnIndex(COL_IMG));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(COL_Address));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COL_TIME));
            @SuppressLint("Range") int sumcount = cursor.getInt(cursor.getColumnIndex(COL_SUMCOUNT));
            @SuppressLint("Range") String activityname = cursor.getString(cursor.getColumnIndex(COL_ACTIVITYNAME));
            activityBean.setActivityCount(activitycount);
            activityBean.setTitle(title);
            activityBean.setSumCount(sumcount);
            activityBean.setImg(img);
            activityBean.setAddress(address);
            activityBean.setSlogan(slogan);
            activityBean.setAdmin(admin);
            activityBean.setTime(time);
            activityBean.setActivityName(activityname);
            activityBean.setId(id);
            list.add(activityBean);
        }
        cursor.close();
        return list;
    }

    public boolean checkUsernameExist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COL_USERNAME};
        String selection = COL_USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USER, projection, selection, selectionArgs, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }


    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USER, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public List<Map<String, String>> selectUser(String username, String password) {
        List<Map<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USER, null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
            Map<String, String> map = new HashMap<>();
            map.put("phone", phone);
            list.add(map);
        }
        cursor.close();
        return list;
    }
}
