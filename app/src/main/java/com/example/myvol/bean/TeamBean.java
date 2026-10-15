package com.example.myvol.bean;

import java.io.Serializable;

public class TeamBean implements Serializable {
    int id;
    int img;
    String admin;
    String address;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    int count ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int sumCount;
    String name;
    int activityCount;
    String activityName;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String slogan;
    String time;
    String title;






//    String teamTable = "CREATE TABLE " + TABLE_TEAM + " ("
//            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + COL_IMG + " INTEGER, "
//            + COL_TITLE + " TEXT, "
//            + COL_TIME + " TEXT, "
//            + COL_ADMIN + " TEXT, "
//            + COL_Address + " TEXT, "
//            + COL_COUNT + " INTEGER, "
//            + COL_SUMCOUNT + " INTEGER, "
//            + COL_ACTIVITYCOUNT + " INTEGER, "
//            + COL_SLOGAN + " TEXT)";
}
