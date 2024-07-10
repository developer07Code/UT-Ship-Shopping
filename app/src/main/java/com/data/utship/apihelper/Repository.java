package com.data.utship.apihelper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.data.utship.model.BaseResponseModel;
import com.data.utship.model.FacebookSocialAuthBean;
import com.data.utship.model.GoogleSocialAuthBean;
import com.data.utship.model.LoginBean;
import com.data.utship.model.SocialAuthBean;


import io.reactivex.BackpressureStrategy;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Repository {
    private Retrofit mainClient;

    //HEADERS
    public static final String AUTH_TOKEN_HEADER = "Authorization";

    public Repository() {
        mainClient = RetrofitClient.getMainInstance();
    }

    //Account Sign in/up & password reset
    public LiveData<Response<LoginBean>> signIn(LoginBean signInModel){
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(ServerAPI.class)
                        .userLogin(signInModel)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn( exception -> {
                            exception.printStackTrace();

                            if(exception.getClass() == HttpException.class)
                                return Response.error(((HttpException)exception).code(), ResponseBody.create(null,""));
                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null,""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }


    public LiveData<Response<SocialAuthBean>> googleAuth(GoogleSocialAuthBean socialAuthModel){
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(ServerAPI.class)
                        .googleAuth(socialAuthModel)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn( exception -> {
                            exception.printStackTrace();

                            if(exception.getClass() == HttpException.class)
                                return Response.error(((HttpException)exception).code(), ResponseBody.create(null,""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null,""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );
    }

    public LiveData<Response<SocialAuthBean>> facebookAuth(FacebookSocialAuthBean socialAuthModel){
        return LiveDataReactiveStreams.fromPublisher(
                mainClient.create(ServerAPI.class)
                        .facebookAuth(socialAuthModel)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn( exception -> {
                            exception.printStackTrace();

                            if(exception.getClass() == HttpException.class)
                                return Response.error(((HttpException)exception).code(), ResponseBody.create(null,""));

                            return Response.error(BaseResponseModel.FAILED_REQUEST_FAILURE, ResponseBody.create(null,""));
                        })
                        .toFlowable(BackpressureStrategy.LATEST)
        );

    }

}
