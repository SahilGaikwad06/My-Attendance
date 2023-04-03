package com.valisha.myattendance.network;

import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofitCore2Service;
    private static CoreAPIs coreAPIs2;

    private static final String url = "http://74.208.84.186:94/api/values/";

    public static CoreAPIs forCore() throws URISyntaxException {
        if(retrofitCore2Service == null) retrofitCore2Service = createClient(url);
        if(coreAPIs2==null)coreAPIs2 = retrofitCore2Service.create(CoreAPIs.class);
        return coreAPIs2;
    }


    private static Retrofit createClient(String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new LoggingInterceptor())
                        .connectTimeout(5,TimeUnit.MINUTES)
                        .readTimeout(5,TimeUnit.MINUTES)
                        .writeTimeout(5,TimeUnit.MINUTES)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
