package com.data.utship.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.data.utship.activity.LoginActivity
import com.data.utship.apihelper.Repository
import com.data.utship.model.FacebookSocialAuthBean
import com.data.utship.model.GoogleSocialAuthBean
import com.data.utship.model.SocialAuthBean
import com.facebook.*
import com.data.utship.R
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.util.*

class LoginViewModel(con: Context) : ViewModel() {
    private var cont = con
    private lateinit var repository: Repository
    private lateinit var facebookCallbackManager: CallbackManager
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSocialAuthModel: GoogleSocialAuthBean
    private lateinit var facebookSocialAuthModel: FacebookSocialAuthBean
  //  private val context = getApplication<Application>().applicationContext

/*    fun login(email: String?, password: String?): LiveData<Response<LoginBean?>?>? {
        val signInModel = LoginBean()
        signInModel.data.email = email
        signInModel.data.password = password
        return repository.signIn(signInModel)
    }*/

    fun googleAuth(loginActivity: LoginActivity) {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(loginActivity.getString(R.string.google_backend_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(loginActivity, googleSignInOptions)
        if (GoogleSignIn.getLastSignedInAccount(cont) != null) googleSignInClient.signOut()
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
            try {
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
            }
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
                override fun onSuccess(loginResult: LoginResult) {
                    val request = GraphRequest.newMeRequest(
                        loginResult.accessToken,
                        object : GraphRequest.GraphJSONObjectCallback {
                            override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                                try {
                                    val first_name = `object`!!.getString("first_name")
                                    val last_name = `object`.getString("last_name")
                                    val email = `object`.getString("email")
                                    val id = `object`.getString("id")
                                    val picture =
                                        `object`.getJSONObject("picture").getJSONObject("data")
                                            .getString("url")
                                    facebookSocialAuthModel = FacebookSocialAuthBean()
                                    facebookSocialAuthModel!!.clientId = id
                                    facebookSocialAuthModel!!.email = email
                                    facebookSocialAuthModel!!.fullName = "$first_name $last_name"
                                    facebookSocialAuthModel!!.image = picture
                                    repository!!.facebookAuth(facebookSocialAuthModel).observe(
                                        loginActivity,
                                        backendResponse::setValue
                                    )
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
            .isGooglePlayServicesAvailable(cont) != ConnectionResult.SUCCESS
    }

    fun getGoogleSocialAuthModel(): GoogleSocialAuthBean? {
        return googleSocialAuthModel
    }

    fun getFacebookSocialAuthModel(): FacebookSocialAuthBean? {
        return facebookSocialAuthModel
    }
}