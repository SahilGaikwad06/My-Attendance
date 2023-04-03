package com.valisha.myattendance.UI.Dashboard;

import android.util.Log;

import androidx.annotation.NonNull;

import com.valisha.myattendance.apps.IApp;
import com.valisha.myattendance.network.WebService;
import com.valisha.myattendance.network.model.Reports;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DashBoardPresenter implements WebService.PostLogs.postLogs {



    private IApp app;
    private int id;
    private DashboardViewModel dashboardViewModel;

    public DashBoardPresenter(IApp app, DashboardViewModel dashboardViewModel) {
        this.app = app;
        this.dashboardViewModel = dashboardViewModel;
    }

    public void postLog(int id,String enrollID,String punchData, String punchTime,String lat,String lng,String address,String username,String password)
    {
        this.id=id;
        dashboardViewModel.showProgress();
       // Reports.itemHeadWiseReport item= new Reports.itemHeadWiseReport(enrollID,punchData,punchTime,lng,lat,address,username,password);
      //  app.webService().reportInteractor().getMonthWiseReport(List.of(item),this);

        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "[\r\n        {\r\n           " +
                    " \"EnrollId\": \""+enrollID+"\",\r\n           " +
                    " \"PunchDate\": \""+punchData+"\",\r\n          " +
                    "  \"PunchTime\": \""+punchTime+"\",\r\n          " +
                    "  \"Longitude\": \""+lng+"\",\r\n           " +
                    " \"Latitude\": \""+lat+"\",\r\n           " +
                    " \"Address\": \""+address+"\",\r\n           " +
                    " \"UserName\": \""+username+"\",\r\n           " +
                    " \"password\": \""+password+"\"\r\n       " +
                    " }\r\n    ]");

            Log.e("TAG", "postLog: "+"[\r\n        {\r\n           " +
                    " \"EnrollId\": \""+enrollID+"\",\r\n           " +
                    " \"PunchDate\": \""+punchData+"\",\r\n          " +
                    "  \"PunchTime\": \""+punchTime+"\",\r\n          " +
                    "  \"Longitude\": \""+lng+"\",\r\n           " +
                    " \"Latitude\": \""+lat+"\",\r\n           " +
                    " \"Address\": \""+address+"\",\r\n           " +
                    " \"UserName\": \""+username+"\",\r\n           " +
                    " \"password\": \""+password+"\"\r\n       " +
                    " }\r\n    ]");


            String url = "http://"+app.preference().getIP()+":"+app.preference().getPort()+"/api/values/SaveLogFromApp1/";
            Log.e("TAG", "postLog: "+url);
            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

            assert response.body() != null;
            String response1 = response.body().string();

            Log.e("TAG", "postLog: "+response1 );

            switch (response1)
            {
                case "\"APP Log Saved\"":
                    onLogPost();
                    dashboardViewModel.logPOst(response1);
                    break;
                case "{\"Message\":\"The requested resource does not support http method 'POST'.\"}":
                    deleteLog();
                    dashboardViewModel.logPostFailed("");
                    break;
                case "\"User Not Registered\"":
                case "{\"Message\":\"An error has occurred.\"}":
                    deleteLog();
                    dashboardViewModel.userNotRegister();
                    break;
            }




//            JSONArray jsonArray = new JSONArray(response.body().string());
//            String resultCode = jsonArray.getString(0).split(":")[1].trim();
//            String result = jsonArray.getString(1).split(":")[1].trim();
//
//            Log.e("TAG", "doInBackground: "+result );
////            Log.e("TAG", "response: "+response.body().string() );
////            Log.e("TAG", "request: "+request.body().toString() );
//            if(result.equals("success")){
//
//                onLogPost();
//                dashboardViewModel.logPOst(result);
//            }
//            else
//                dashboardViewModel.logPostFailed("");
//









        }catch (Exception exception)
        {
            dashboardViewModel.hideProgress();


            Log.e("TAG", "Exception: "+exception );
        }

    }

    @Override
    public void onNetworkError(@NonNull String q) {
        dashboardViewModel.hideProgress();
        Log.e("TAG", "onNetworkError: "+q );

    }

    @Override
    public void onNullResponse(@NonNull String q) {
        dashboardViewModel.hideProgress();
        Log.e("TAG", "onNullResponse: "+q );


    }

    @Override
    public void onLogPost() {

        Log.e("TAG", "Synced: "+id );

        dashboardViewModel.hideProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "Synced: "+id );
                app.db().logDao().updateStatus(id);
            }
        }).start();

    }

    public void deleteLog()
    {
        dashboardViewModel.hideProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "Synced: "+id );
                app.db().logDao().deleteLog(id);
            }
        }).start();
    }
}
