package com.valisha.myattendance.network.model

import com.google.gson.annotations.SerializedName

open class CommonResponse {
    @SerializedName("result_code") var result_code : Int = -1
    @SerializedName("result") lateinit var result : String
 }