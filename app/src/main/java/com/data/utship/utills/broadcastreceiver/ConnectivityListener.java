package com.data.utship.utills.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.data.utship.apihelper.APIAuth;
import com.data.utship.apihelper.ServerAPI;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectivityListener extends BroadcastReceiver {
    public static ConnectivityReceiverListener connectivityReceiverListener;

    public ConnectivityListener() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            checkConnectivity(context);
        }
    }

    private void checkConnectivity(final Context context) {
      /*  if(!isNetworkInterfaceAvailable(context))
        {
          //  Toast.makeText(context,"You are Offline",Toast.LENGTH_SHORT).show();
            if(connectivityReceiverListener!=null)
            {
                connectivityReceiverListener.onNetworkConnectionChange(false);
            }
            return;
        }else
        {
            connectivityReceiverListener.onNetworkConnectionChange(true);
        }*/
        if (isNetworkInterfaceAvailable(context)) {
            ConnectivityListener.connectivityReceiverListener.onNetworkConnectionChange(true);
        } else {
            ConnectivityListener.connectivityReceiverListener.onNetworkConnectionChange(false);
        }
    }

    private boolean isNetworkInterfaceAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    //This makes a real connection to an url and checks if you can connect to this url, this needs to be wrapped in a background thread
    private boolean isAbleToConnect(String url, int timeout) {
        try {
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CheckIntenet() {
        APIAuth apiAuth = new APIAuth();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(apiAuth)
                .addInterceptor(logging)
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS);

        okHttpClient.addInterceptor(logging);
        final boolean[] flag = {false};
        Retrofit builder = new Retrofit.Builder().baseUrl("http://google.com/").client(okHttpClient.build()).addConverterFactory(GsonConverterFactory.create()).build();
        builder.create(ServerAPI.class).checkInternet()
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response != null) {
                            if (response.body() != null) {
                                if (!response.body().equalsIgnoreCase("")) {
                                    flag[0] = true;
                                    // Toast.makeText(context,"Connected to internet",Toast.LENGTH_SHORT).show();
                                } else {
                                    flag[0] = false;
                                    //  Toast.makeText(context,"not connected to internet",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        flag[0] = false;
                        // Toast.makeText(context,"Error "+t,Toast.LENGTH_SHORT).show();

                    }
                });
        return flag[0];

    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChange(boolean isconnected);
    }
}
