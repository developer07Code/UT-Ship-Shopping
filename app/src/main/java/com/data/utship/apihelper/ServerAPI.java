package com.data.utship.apihelper;


import com.data.utship.model.FacebookSocialAuthBean;
import com.data.utship.model.GoogleSocialAuthBean;
import com.data.utship.model.LoginBean;
import com.data.utship.model.SocialAuthBean;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by HP on 10-10-2018.
 */

public interface ServerAPI {

/*    @GET("login")
    Call<LoginBean> userLogin(@Query("customerid") String email,
                              @Query("password") String password);  */

 /*   @GET("login")
    Call<SignInModel> userLogin(@Query("customerid") String email,
                                @Query("password") String password);*/

    @Headers({"client: mobile"})
    @POST("users/auth/signin")
    Observable<Response<LoginBean>> userLogin(@Body LoginBean signInModel);


    @Headers({"client: mobile"})
    @POST("users/auth/google")
    Observable<Response<SocialAuthBean>> googleAuth(@Body GoogleSocialAuthBean googleSocialAuthModel);

    @Headers({"client: mobile"})
    @POST("users/auth/facebook")
    Observable<Response<SocialAuthBean>> facebookAuth(@Body FacebookSocialAuthBean facebookSocialAuthModel);


    /*  @GET("forgotpassword")
    Call<LoginBean> forgotPassword(@Path("MemberId") String email);


    @GET("dailycashback")
    Call<DailyCashbackBean> dailyCashback(@Query("msrno") String msrno, @Query("packid") String packid);

    @GET("levelincome")
    Call<LevelIncome> dailyLevelIncome(@Query("umsrno") String umsrno);

    @GET("passbook_nonworking_history")
    Call<NonWorkingHistoryBean> getNonWorkingHistory(@Query("umsrno") String umsrno);

    @GET("passbook_working_history")
    Call<WorkingHistoryBean> getWorkingHistory(@Query("umsrno") String umsrno);

    @GET("withdrawlist")
    Call<WithdrawaHistoryBean> getWithdrawaHistory(@Query("msrno") String umsrno);

    @GET("withdrwareqworking")
    Call<WithdrawalWorkBean> getWithdrawaWorking(@Query("msrno") String umsrno, @Query("amount") String amount, @Query("otp") String otp);

    @GET("withdrwareqnonworking")
    Call<WithdrawalWorkBean> getWithdrawaNonWorking(@Query("msrno") String umsrno, @Query("amount") String amount);

    @GET("sendOtp")
    Call<BaseResponseBean> getSendOtp(@Query("msrno") String umsrno);

    @GET("level_team")
    Call<TeamLevelBean> getLevelTeam(@Query("msrno") String umsrno);

    @GET("direct_list")
    Call<DirectListBean> getDirectList(@Query("msrno") String umsrno);

    @GET("DirectIncome")
    Call<DirectIncomeBean> getDirectIncomeList(@Query("msrno") String umsrno);

    @GET("incomehistory")
    Call<IncomeHistoryBean> getIncomeHistory(@Query("msrno") String umsrno);

    @GET("fundhistory")
    Call<FundHistoryBean> getFundHistory(@Query("msrno") String umsrno);

    @GET("dashboard")
    Call<DashboardDataBean> DashboardData(@Query("msrno") String msrno);

    @GET("mining")
    Call<BaseResponseBean> firstScreenApi(@Query("msrno") String msrno, @Query("packid") String packID);

    @GET("sociallink")
    Call<SocialLinkBean> socialLinkApi(@Query("msrno") String msrno);

    @GET("countrylist")
    Call<CountryBean> countrylist();

    @GET("getprofile")
    Call<ProfileBean> getprofile(@Query("msrno") String msrno);

    @GET("packagelist")
    Call<PackageListBean> getPackageList();

    @GET("returntype")
    Call<ReturnTypeBean> getReturnType(@Query("memberid") String memberid);

    @GET("walletbalance")
    Call<BalanceBean> getBalance(@Query("msrno") String msrno);

    @GET("incomebalance")
    Call<IncomeBalanceBean> getIncomeBalance(@Query("msrno") String msrno);


    @GET("activation")
    Call<InvestBean> getInvest(@Query("memberid") String memberid,
                               @Query("loginmemberid") String loginmemberid,
                               @Query("packageid") String packageid,
                               @Query("investamount") String investamount);

    @GET("retopup")
    Call<BalanceBean> getRetopup(@Query("memberid") String memberid,
                                 @Query("loginmemberid") String loginmemberid,
                                 @Query("packageid") String packageid,
                                 @Query("investamount") String investamount);


    @GET("sponsorname")
    Call<ReferralIDBean> getSponserName(@Query("memberid") String memberid);

    @GET("checksponsor")
    Call<ReferralIDBean> getActivationCheckUser(@Query("memberid") String memberid);

    @GET("coinqr")
    Call<QRCodeBean> getQRCode(@Query("msrno") String msrno);

    @GET("depositrequest")
    Call<DespositRequestBean> getDespositReq(@Query("msrno") String msrno, @Query("amount") String amt, @Query("referrance") String ref, @Query("remark") String remark);


    @GET("fund_transfer_history")
    Call<FundTransferHisBean> getFundTransferHistory(@Query("umsrno") String msrno);

    @GET("countrylist")
    Call<CountryListBean> getCountryList();

    @GET("changepassword")
    Call<ChangePassBean> changePassword(@Query("msrno") String msrn_No,
                                        @Query("NewPassword") String new_password,
                                        @Query("oldpassword") String old_password);

    @GET("register")
    Call<RegistrationBean> userRegister(@Query("sponsor") String sponsor,
                                        @Query("Title") String Title,
                                        @Query("membername") String membername,
                                        @Query("mobile") String mobile,
                                        @Query("EmailID") String EmailID,
                                        @Query("Password") String Password,
                                        @Query("Address") String Address,
                                        @Query("countryid") String countryid,
                                        @Query("AccountNumber") String AccountNumber,
                                        @Query("IFSCCode") String IFSCCode,
                                        @Query("WalletTRXAddress") String WalletTRXAddress);

    @GET("editprofile")
    Call<EditProfileBean> getEditProfile(@Query("msrno") String msrno,
                                         @Query("mobile") String mobile,
                                         @Query("emailid") String emailid,
                                         @Query("bank") String bank,
                                         @Query("AccountNumber") String AccountNumber,
                                         @Query("IFSCCode") String IFSCCode,
                                         @Query("Address") String Address,
                                         @Query("country") String country,
                                         @Query("state") String state,
                                         @Query("city") String city,
                                         @Query("pin") String pin,
                                         @Query("walletaddress") String walletaddress);


    @GET("me")
    Call<ProfileBean> getProfile();

    @POST("logout")
    Call<ChangePassBean> logout();
*/
    @GET("webhp")
    Call<String> checkInternet();

}
