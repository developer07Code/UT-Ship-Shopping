package com.data.utship.apihelper;

import com.data.utship.BuildConfig;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HP on 10-10-2018.
 */

public class APIAuth implements Interceptor {
    private String token = null;
    private String device_model = null;
    private String device_imei = null;
    private String device_type = "Android";

    public APIAuth() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
               /* .addHeader("Device-Model", device_model)
                .addHeader("Device-Imei", device_imei)
                .addHeader("Device-Type", device_type)*/
                .addHeader("Device-App-Version", BuildConfig.VERSION_NAME)
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);


    }
}
