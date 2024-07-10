package com.data.utship.apihelper

import android.util.Log
import com.data.utship.model.*
import com.data.utship.utills.utility.AppConstant
import io.reactivex.Observable
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface RetrofitService {

    @Headers("client: mobile")
    @POST("users/auth/google")
    suspend fun googleAuth(@Body googleSocialAuthModel: GoogleSocialAuthBean?): Observable<Response<SocialAuthBean?>?>?

    @POST("/api/GetSubCategory")
    fun getSubcategoryApi(@Body requestBody: RequestBody?): Call<SubCategoryBean>

    @POST("/api/AddToCart")
    fun getAddToCartApi(@Body requestBody: RequestBody?): Call<BaseResponseBean>

    @POST("/api/AddToWishlist")
    fun getAddToWishlistApi(@Body requestBody: RequestBody?): Call<BaseResponseBean>

    @POST("/api/Login")
    fun getLoginApi(@Body requestBody: RequestBody?): Call<LoginResponseBean>

    @POST("/api/forgetPassword")
    fun forgetPassword(@Body requestBody: RequestBody?): Call<LoginResponseBean>

    @POST("/api/Signup")
    fun getRegisterApi(@Body requestBody: RequestBody?): Call<BaseResponseBean>

    @POST("/api/SendOTP")
    fun getSendOTPApi(@Body requestBody: RequestBody?): Call<SendOTPBean>

    @POST("/api/Manage_Address")
    fun getManageAddressApi(@Body requestBody: RequestBody?): Call<BaseResponseBean>

    @POST("/api/GetCity")
    fun getCityApi(@Body requestBody: RequestBody?): Call<StateBean>

  @POST("/api/GetRazorpayToken")
    fun getRazorpayTokenApi(@Body requestBody: RequestBody?): Call<RazorpayBean>

    @POST("/api/GetAddress")
    fun GetAddressApi(@Body requestBody: RequestBody?): Call<GetAddressBean>

  @POST("/api/GetProductBySearch")
    fun GetProductBySearchApi(@Body requestBody: RequestBody?): Call<SearchProductBean>

    @POST("/api/List_Direct")
    fun GetList_DirectApi(@Body requestBody: RequestBody?): Call<ListDirectBean>

    @GET("/api/GetState")
    fun getStateApi(): Call<StateBean>

  @GET("/api/GetSuggationList")
    fun getSuggationListApi(): Call<SearchSuggeationListBean>

    @POST("/api/GetProductDetail")
    fun getGetProductDetailApi(@Body requestBody: RequestBody?): Call<ProductDetailBean>
////
  @POST("/api/List_WalletHistory")
    fun getListWalletHistoryApi(@Body requestBody: RequestBody?): Call<WalletHistoryBean>//UID

    @POST("/api/List_Withdrawal")
    fun getList_WithdrawalApi(@Body requestBody: RequestBody?): Call<ListWithdrawalBean>//UID

  @POST("/api/SendWithdrawal")
    fun getSendWithdrawalApi(@Body requestBody: RequestBody?): Call<BaseResponseBean>//UID,Amount ,UPIID
////
    @POST("/api/GetAddress")
    fun getAddressApi(@Body requestBody: RequestBody?): Call<AddressListBean>

    @POST("/api/GetProfile")
    fun getProfileApi(@Body requestBody: RequestBody?): Call<ProfileDataBean>

    // @Multipart
    @POST("/api/UpdateProfile")
    fun getUpdateProfileApi(@Body requestBody: RequestBody?): Call<BaseResponseBean>

    @POST("/api/GetWishlist")
    fun getWishlistApi(@Body requestBody: RequestBody?): Call<WhislistBean>

    @POST("/api/GetCart")
    fun getCartApi(@Body requestBody: RequestBody?): Call<CartBean>

    @POST("/api/MyOrder")
    fun getMyOrderApi(@Body requestBody: RequestBody?): Call<AllOrderHistoryBean>

    @POST("/api/GetCashfreeToken")
    fun GetCashfreeTokenApi(@Body requestBody: RequestBody?): Call<CashfreeBean>

    @POST("/api/GetSubToSubCategory")
    fun getSubToSubcategoryApi(@Body requestBody: RequestBody?): Call<SubCategoryBean>

    @POST("/api/GetProduct")
    fun getGetProductApi(@Body requestBody: RequestBody?): Call<ProductBean>

    @POST("/api/GetProductByTopCategory")
    fun getGetFndStyleApi(@Body requestBody: RequestBody?): Call<DashboardBean>

 @POST("/api/GetBalance")
    fun getGetBalance(@Body requestBody: RequestBody?): Call<GetBalanceBean>

    @POST("/api/GetCartTotal")
    fun getGetCartTotal(@Body requestBody: RequestBody?): Call<TotalCartBean>//UID Iswallet

    @POST("/api/Home")
    fun getDashboardApi(@Body requestBody: RequestBody?): Call<DashboardBean>

 @POST("/api/GetAll_Category")
    fun GetAllCategoryApi(@Body requestBody: RequestBody?): Call<GetAllCategoryBean>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {

            /*   String token = PreferenceManager.getInstance(context).getToken();
        String imei = PreferenceManager.getInstance(context).getIMEI();
        String model = PreferenceManager.getInstance(context).getModel();
*/
            val apiAuth = APIAuth()
            var mHttpLoggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            var mOkHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(apiAuth)
                .addInterceptor(mHttpLoggingInterceptor)
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
            //   .build()
            //   if (retrofitService == null) {
            var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient.build())
                .build()
            retrofitService = retrofit.create(RetrofitService::class.java)
            //  }
            return retrofitService!!

            /*    if (retrofitService == null) {
                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://fake-movie-database-api.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    retrofitService = retrofit.create(RetrofitService::class.java)
                }*/

        }

        fun createBuilder(paramsName: Array<String>, paramsValue: Array<String>): FormBody.Builder {
            val builder = FormBody.Builder()
            for (i in paramsName.indices) {
                Log.e("values", "createBuilder: " + paramsName[i] + " : " + paramsValue[i])
                builder.add(paramsName[i], paramsValue[i])
            }
            return builder
        }
    }
}

