package com.valisha.myattendance.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "logs")
public class Logs {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name="enrollID")
    private String enrollID;

    @ColumnInfo (name = "date")
    private String date;

    @ColumnInfo (name = "time")
    private String time;

    @ColumnInfo(name="lat")
    private double lat;

    @ColumnInfo(name = "lng")
    private double lng;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "userName")
    private String userName;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "status")
    private String status;


    public Logs(@NonNull String enrollID, String date, String time, double lat, double lng, String address, String userName, String password, String status) {
        this.enrollID = enrollID;
        this.date = date;
        this.time = time;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.userName = userName;
        this.password = password;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getEnrollID() {
        return enrollID;
    }

    public void setEnrollID(@NonNull String enrollID) {
        this.enrollID = enrollID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
