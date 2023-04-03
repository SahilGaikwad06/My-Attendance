package com.valisha.myattendance.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Logs.class},exportSchema = false,version = 1)
//@TypeConverters({Database.Converter.class})
public abstract class Db extends RoomDatabase {

    private static final String TAG = Db.class.getSimpleName();
    private static volatile Db INSTANCE;

    public static Db getInstance(Context context){
        if(INSTANCE==null){
            synchronized (Db.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(), Db.class, "myAttendance")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    public abstract LogDao logDao();


 }
