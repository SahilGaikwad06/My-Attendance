package com.valisha.myattendance.network

import android.util.Log
import com.valisha.myattendance.network.model.CommonResponse
import com.valisha.myattendance.network.model.Reports
import org.json.JSONArray

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException
import javax.security.auth.login.LoginException


class WebServiceImpl : WebService {

     private lateinit var mLoginSignUpInteractor: WebService.PostLogs


    override fun reportInteractor(): WebService.PostLogs {
        if (!::mLoginSignUpInteractor.isInitialized) mLoginSignUpInteractor = createLoginSignupInteractor()
        return mLoginSignUpInteractor
    }



    private fun createLoginSignupInteractor(): WebService.PostLogs {
        return object  : WebService.PostLogs {

            override fun getMonthWiseReport(logs: List<Reports.itemHeadWiseReport>, callback: WebService.PostLogs.postLogs) {
                val request = Reports.postLogs.Request(logs)
                RetrofitClient.forCore().postLogs(request).enqueue(object : Callback<JSONArray>
                {
                    override fun onResponse(call: Call<JSONArray>, response: Response<JSONArray>) {
                        val resp = response.body()



                        var  jsonArray =  JSONArray(response.body().toString());
                        var  resultCode = jsonArray.getString(0).split(":")[1].trim();
                        var result = jsonArray.getString(1).split(":")[1].trim();

                        Log.e("TAG", "onResponse: $resultCode")
                        Log.e("TAG", "onResponse: $result")

//                        if(result.equals("success"))
//                        {
//                            callback.onLogPost()
//                        }
//                        else
//                        {
//                            onCommonResponse(Throwable(),response.message(),callback)
//                        }

//                        when(resp?.result_code){
//                            null-> callback.onNullResponse("Null")
//                            0 -> callback.onLogPost()
//                            else -> onCommonResponse(Throwable(),response.message(),callback)
//                        }
                    }

                    override fun onFailure(call: Call<JSONArray>, t: Throwable) {
                       // onCommonResponse(t,"\n${t.message}",callback)
                    }

                })

            }



//            private fun onCommonResponse(t:Throwable,q:String,callback: WebService.CommonResponses) {
//                if(t is UnknownHostException) callback.onNetworkError(q)
//                else
//                {
//                    Log.e("WebServiceImpl", "onCommonResponse: ",t )
//                    callback.onNullResponse(q)
//                }
//
//            }


        }

    }






}
