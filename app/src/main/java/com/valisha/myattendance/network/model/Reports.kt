package com.valisha.myattendance.network.model

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class Reports {

    class postLogs {

        class Request(
            @SerializedName("logs") private val logs: List<itemHeadWiseReport>,

        )
    }



    class itemHeadWiseReport(
        @SerializedName("EnrollId") val EnrollId: String,
        @SerializedName("PunchDate") val PunchDate: String,
        @SerializedName("PunchTime") val PunchTime: String,
        @SerializedName("Longitude") val Longitude: String,
        @SerializedName("Latitude") val Latitude: String,
        @SerializedName("Address") val Address: String,
        @SerializedName("UserName") val UserName: String,
        @SerializedName("password") val password: String,)





}