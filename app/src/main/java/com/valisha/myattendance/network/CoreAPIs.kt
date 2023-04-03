package com.valisha.myattendance.network

import com.valisha.myattendance.network.model.CommonResponse
import com.valisha.myattendance.network.model.Reports
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface CoreAPIs {

    companion object {
        const val API = "SaveLogFromApp/"
    }

    @POST(API)
    fun postLogs(@Body request:Reports.postLogs.Request) : Call<JSONArray>


}
