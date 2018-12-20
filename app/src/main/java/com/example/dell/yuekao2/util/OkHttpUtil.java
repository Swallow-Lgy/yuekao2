package com.example.dell.yuekao2.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtil  {
    private static volatile OkHttpUtil mIstacn;
    private OkHttpClient mClicen;
    private Handler handler = new Handler(Looper.myLooper());
    public static OkHttpUtil getmIstacn(){
        if (mIstacn==null){
            synchronized (MainThread.class){
                mIstacn = new OkHttpUtil();
            }
        }
        return mIstacn;
    }
    public OkHttpUtil(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
         mClicen = new OkHttpClient.Builder()
                 .readTimeout(10,TimeUnit.SECONDS)
                 .writeTimeout(10,TimeUnit.SECONDS)
                 .readTimeout(10,TimeUnit.SECONDS)
                 .addInterceptor(interceptor)
                 .build();
    }
    public void postEqueue(String url, Map<String,String>prams, final ICall iCall, final Class clazz){
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String,String>entry:prams.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = mClicen.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iCall.faile(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(result, clazz);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCall.success(o);
                    }
                });
            }
        });

    }
}
