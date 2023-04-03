package com.valisha.myattendance.UI.Dashboard;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.Observer;

import com.valisha.myattendance.apps.IApp;
import com.valisha.myattendance.model.Db;
import com.valisha.myattendance.model.Logs;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AsycTask extends AsyncTask<String, String, String> {


    Dashboard dashboard;
    IApp app;
    int id;
    String getEnrollID,getDate,getTime,getLat,getLng,getAddress,getUname,getPass;

    public AsycTask(final Dashboard context, IApp app) {
        this.dashboard = context;
        this.app = app;
    }




    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Db.getInstance(dashboard).logDao().getAllLogs().observe(dashboard, new Observer<List<Logs>>() {
            @Override
            public void onChanged(List<Logs> logs) {
                for(Logs logs1 : logs) {

//                    doInBackground(String.valueOf(logs1.getId()),
//                            logs1.getEnrollID(),
//                            logs1.getDate(),
//                            logs1.getTime(),
//                            String.valueOf(logs1.getLat()),
//                            String.valueOf(logs1.getLng()),
//                            logs1.getAddress(),
//                            logs1.getUserName(),
//                            logs1.getPassword());




                    getEnrollID =  logs1.getEnrollID();
                      getDate   =   logs1.getDate();
                       getTime =     logs1.getTime();
                          getLat =  String.valueOf(logs1.getLat());
                         getLng =  String.valueOf(logs1.getLng());
            getAddress = logs1.getAddress();
                           getUname = logs1.getUserName();
                          getPass =  logs1.getPassword();
                }

            }});
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.e("TAG", "onPostExecute: "+s);

//        if(s.equals("sync"))
//          new Thread(new Runnable() {
//              @Override
//              public void run() {
//                  Log.e("TAG", "Synced: "+id );
//                  app.db().logDao().updateStatus(id);
//              }
//          }).start();
//        else
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Log.e("TAG", "Synced: "+id );
//                    app.db().logDao().deleteLog(id);
//                }
//            }).start();

    }

    @Override
    protected String doInBackground(String... strings) {


        Log.e("TAG", "doInBackground: "+getUname);
        Log.e("TAG", "doInBackground: "+getPass);



            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
           MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "[\r\n        {\r\n           " +
                    " \"EnrollId\": \""+getEnrollID+"\",\r\n           " +
                    " \"PunchDate\": \""+getDate+"\",\r\n          " +
                    "  \"PunchTime\": \""+getTime+"\",\r\n          " +
                    "  \"Longitude\": \""+ getLng+"\",\r\n           " +
                    " \"Latitude\": \""+getLat+"\",\r\n           " +
                    " \"Address\": \""+ getAddress+"\",\r\n           " +
                    " \"UserName\": \""+getUname+"\",\r\n           " +
                    " \"password\": \""+getPass+"\"\r\n       " +
                    " }\r\n    ]");

           String url = "http://"+app.preference().getIP()+":"+app.preference().getPort()+"/api/values/SaveLogFromApp1/";



        Request request = new Request.Builder()
                .url(url)
                .method("POST",body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error

                        Log.e("TAG", "onFailure: "+e );
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();

                        Log.e("TAG", "onResponse: "+res );
                    }
                });

        return null;
    }
}
