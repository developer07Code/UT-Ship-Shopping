package com.data.utship.viewmodel

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.data.utship.activity.DashboardActivity
import com.data.utship.activity.LoginActivity
import com.data.utship.apihelper.Repository
import com.data.utship.apihelper.RetrofitService
import com.data.utship.model.*
import com.data.utship.utills.Constants
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.dialog.DialogsProvider
import com.data.utship.utills.utility.UserAccountManager
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import okhttp3.FormBody
import com.data.utship.R
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginViewsModel(
    val activity: LoginActivity,
    val appPreferences: SharedPrefUtils,
  //  private val repository: MainRepository
) : ViewModel() {
    val loginList = MutableLiveData<List<LoginBean>>()
    val errorMessage = MutableLiveData<String>()

    private lateinit var facebookCallbackManager: CallbackManager
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSocialAuthModel: GoogleSocialAuthBean
    private lateinit var facebookSocialAuthModel: FacebookSocialAuthBean
    val JUST_SIGNED_IN = "justSignedIn"

     fun getLogin(activity: LoginActivity, email: String, pass: String) {
        DialogsProvider.get(activity).setLoading(true)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("Token", "LoginID", "Password"),
            arrayOf(
               "OkszNYgQ06ayVljROcZ0OxYMooNm",
                email,
                pass
            )
        )
        val response = RetrofitService.getInstance().getLoginApi(builder.build())
        response.enqueue(object : Callback<LoginResponseBean> {
            override fun onResponse(call: Call<LoginResponseBean>, response: Response<LoginResponseBean>) {
                DialogsProvider.get(activity).setLoading(false)

                when (response.code()) {
                    BaseResponseModel.SUCCESSFUL_OPERATION -> {
                        appPreferences.setValue(Constants.RememberMe, true)
                     //   movieList.postValue(response.body()?.mList)


                       /* appPreferences.setStringValue(
                            AppPreferences.ID,
                            java.lang.String.valueOf(response.body()!!.data.memberID)
                        )
                        appPreferences.setStringValue(
                            AppPreferences.msrNo,
                            java.lang.String.valueOf(response.body()!!.data.msrNo)
                        )
                        appPreferences.setStringValue(
                            AppPreferences.NAME,
                            java.lang.String.valueOf(response.body()!!.data.memberName)
                        )
                        appPreferences.setStringValue(
                            AppPreferences.loginID,
                            java.lang.String.valueOf(response.body()!!.data.loginID)
                        )
                        appPreferences.setStringValue(
                            AppPreferences.PASSWORD,
                            java.lang.String.valueOf(response.body()!!.data.password)
                        )
                        appPreferences.setStringValue(
                            AppPreferences.SponsorId,
                            java.lang.String.valueOf(response.body()!!.data.password)
                        )

                        appPreferences.setStringValue(
                            AppPreferences.email,
                            java.lang.String.valueOf(response.body()!!.data.email)
                        )*/
                       // startActivity(Intent(applicationContext, DashboardActivity::class.java))

                        val intent = Intent(activity, DashboardActivity::class.java)
                        intent.putExtra(JUST_SIGNED_IN, true)
                        activity.finish()
                        // SharedPrefManager.get(applicationContext).setRememberMe(binding.signInRememberMe.isChecked())

                        UserAccountManager.signIn(
                            activity,
                            intent,
                            response.headers().get(Repository.AUTH_TOKEN_HEADER),
                            UserAccountManager.getUser(activity)
                        )
                    }
                    BaseResponseModel.FAILED_NOT_FOUND -> DialogsProvider.get(activity)
                        .messageDialog(
                            activity.getString(com.data.utship.R.string.Welcome),
                            activity.getString(R.string.you_dont_have_an_account)
                        )
                    BaseResponseModel.FAILED_AUTH -> DialogsProvider.get(activity)
                        .messageDialog(
                            activity.getString(R.string.Wrong_Password),
                            activity.getString(R.string.You_can_reset_password)
                        )
                    BaseResponseModel.FAILED_REQUEST_FAILURE -> DialogsProvider.get(activity)
                        .messageDialog(
                            activity.getString(R.string.Request_Error),
                            activity.getString(R.string.Please_Check_your_connection)
                        )
                    else -> DialogsProvider.get(activity).messageDialog(

                        activity.getString(R.string.Server_Error),
                        activity.getString(R.string.Code) + response.code()
                    )
                }
            }

            override fun onFailure(call: Call<LoginResponseBean>, t: Throwable) {
                DialogsProvider.get(activity).setLoading(false)
                errorMessage.postValue(t.message)
            }
        })
    }

    /*fun login(email: String?, password: String?): LiveData<Response<LoginBean?>?>? {
        val signInModel = LoginBean()
        signInModel.data.email = email
        signInModel.data.password = password
        return repository.signIn(signInModel)
    }
*/

    fun googleAuth(loginActivity: LoginActivity) {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(loginActivity.getString(R.string.google_backend_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(loginActivity, googleSignInOptions)
        if (GoogleSignIn.getLastSignedInAccount(loginActivity) != null) googleSignInClient.signOut()
        loginActivity.startActivityForResult(googleSignInClient.signInIntent, 1)
    }

    fun googleAuthOnActivityResult(
        data: Intent?,
        loginActivity: LoginActivity
    ): MutableLiveData<Response<SocialAuthBean>>? {
        val backendResponse: MutableLiveData<Response<SocialAuthBean>> =
            MutableLiveData<Response<SocialAuthBean>>()
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        if (task.isSuccessful) {
          /*  try {
                val account = task.getResult(ApiException::class.java)
                googleSocialAuthModel = GoogleSocialAuthBean()
                googleSocialAuthModel.clientId = account.id
                googleSocialAuthModel.email = account.email
                googleSocialAuthModel.fullName = account.givenName + " " + account.familyName
                googleSocialAuthModel.image =
                    account.photoUrl.toString().replace("s96-c", "s500-c")
                repository.googleAuth(googleSocialAuthModel)
                    .observe(loginActivity, backendResponse::setValue)
            } catch (e: ApiException) {
                e.printStackTrace()
            }*/
        }
        return backendResponse
    }

    fun facebookAuth(loginActivity: LoginActivity): MutableLiveData<Response<SocialAuthBean>>? {
        //    AppEventsLogger.activateApp(loginActivity.application)
        FacebookSdk.sdkInitialize(loginActivity);
        facebookCallbackManager = CallbackManager.Factory.create()
        val backendResponse: MutableLiveData<Response<SocialAuthBean>> =
            MutableLiveData<Response<SocialAuthBean>>()
        if (AccessToken.getCurrentAccessToken() != null) LoginManager.getInstance().logOut()
        LoginManager.getInstance().logInWithReadPermissions(
            loginActivity,facebookCallbackManager, Arrays.asList("email", "public_profile"))
        LoginManager.getInstance()
            .registerCallback(facebookCallbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val request = GraphRequest.newMeRequest(
                        result.accessToken,
                        object : GraphRequest.GraphJSONObjectCallback {
                            override fun onCompleted(obj: JSONObject?, response: GraphResponse?) {
                                try {
                                    val first_name = obj!!.getString("first_name")
                                    val last_name = obj.getString("last_name")
                                    val email = obj.getString("email")
                                    val id = obj.getString("id")
                                    val picture =
                                        obj.getJSONObject("picture").getJSONObject("data")
                                            .getString("url")
                                    facebookSocialAuthModel = FacebookSocialAuthBean()
                                    facebookSocialAuthModel.clientId = id
                                    facebookSocialAuthModel.email = email
                                    facebookSocialAuthModel.fullName = "$first_name $last_name"
                                    facebookSocialAuthModel.image = picture
                                 /*   repository!!.facebookAuth(facebookSocialAuthModel).observe(
                                        loginActivity,
                                        backendResponse::setValue
                                    )*/
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                        })
                    val parameters = Bundle()
                    parameters.putString(
                        "fields",
                        "first_name,last_name,email,id,picture.type(large)"
                    )
                    request.parameters = parameters
                    request.executeAsync()
                }
                override fun onCancel() {

                }
                override fun onError(e: FacebookException) {

                }
            })
        return backendResponse
    }

    fun googleServicesNotAvailable(): Boolean {
        return GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(activity) != ConnectionResult.SUCCESS
    }

    fun getGoogleSocialAuthModel(): GoogleSocialAuthBean? {
        return googleSocialAuthModel
    }

    fun getFacebookSocialAuthModel(): FacebookSocialAuthBean? {
        return facebookSocialAuthModel
    }
}