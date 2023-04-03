package com.valisha.myattendance.UI.Logs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.valisha.myattendance.R
import com.valisha.myattendance.model.Db
import com.valisha.myattendance.model.Logs

class AttendanceLogs : AppCompatActivity() {


    private lateinit var recyclerView : RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_logs)

        recyclerView = findViewById(R.id.rv_logs)


        val recyclerLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = recyclerLayoutManager

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            recyclerLayoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        Db.getInstance(this).logDao().logs.observe(this, Observer<List<Any?>?>
        { logs ->

            Log.e("TAG", "onCreate: $logs")


                var logs = logs as ArrayList<Logs?>
                var attendanceLogsAdapter =AttendanceLogsAdapter(logs, applicationContext)
                attendanceLogsAdapter.filterList(logs)
                recyclerView.adapter = attendanceLogsAdapter

            })

    }
}