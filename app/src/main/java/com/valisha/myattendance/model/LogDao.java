package com.valisha.myattendance.model;

 import androidx.lifecycle.LiveData;
 import androidx.room.Dao;
 import androidx.room.Insert;
 import androidx.room.OnConflictStrategy;
 import androidx.room.Query;

 import java.util.List;

@Dao
public interface LogDao {

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 void insert(Logs log);

 @Query("select count(*) from logs")
 LiveData<Integer> getLogsCount();

 @Query("select * from logs where status = 'not synced'")
  LiveData<List<Logs>> getAllLogs() ;

 @Query("select * from logs order by  id desc")
 LiveData<List<Logs>> getLogs();

 @Query("update logs set status = 'synced' where id = :id1")
 void updateStatus(int id1);

 @Query("delete from logs where id =:id2")
 void deleteLog(int id2);


}
