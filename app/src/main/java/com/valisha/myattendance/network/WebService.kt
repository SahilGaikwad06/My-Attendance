package com.valisha.myattendance.network

import com.valisha.myattendance.network.model.CommonResponse
import com.valisha.myattendance.network.model.Reports


interface WebService {

    interface CommonResponses {

        fun onNetworkError(q:String)
         fun onNullResponse(q:String)

    }




    interface PostLogs
    {
        interface  postLogs : CommonResponses {

            fun onLogPost()
        }
        fun getMonthWiseReport(logs : List<Reports.itemHeadWiseReport>, callback: postLogs)

    }
    fun reportInteractor() : PostLogs


}