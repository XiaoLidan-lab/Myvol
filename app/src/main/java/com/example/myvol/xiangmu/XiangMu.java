package com.example.myvol.xiangmu;

public class XiangMu {
    private  int imageID;
    private String name;
    private String detail;
    private String date;
    private String destination;
    public  int getImageID() {
        return imageID;
    }
    public String getName() {
        return name;
    }
    public String getDetail() {
        return detail;
    }
    public String getDate() {return date;}
    public String getDestination(){return destination;}
    public XiangMu(int imageID, String name, String detail,String date,String destination) {
        this.imageID = imageID;
        this.name = name;
        this.detail = detail;
        this.date=date;
        this.destination=destination;
    }
}