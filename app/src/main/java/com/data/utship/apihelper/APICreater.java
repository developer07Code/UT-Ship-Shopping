package com.data.utship.apihelper;

import com.data.utship.utills.utility.AppConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HP on 10-10-2018.
 */

public class APICreater {

    private static Retrofit retrofit = null;
    private static ServerAPI serverAPI = null;

    public static ServerAPI getServerApi() {
        String baseurl = AppConstant.BASE_URL;

     /*   String token = PreferenceManager.getInstance(context).getToken();
        String imei = PreferenceManager.getInstance(context).getIMEI();
        String model = PreferenceManager.getInstance(context).getModel();
*/
        APIAuth apiAuth = new APIAuth();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(apiAuth)
                .addInterceptor(logging)
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();
        serverAPI = retrofit.create(ServerAPI.class);
        return serverAPI;
    }
}
