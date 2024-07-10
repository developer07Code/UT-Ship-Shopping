package com.data.utship.activity


import android.app.ProgressDialog
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.data.utship.R
import com.data.utship.apihelper.Repository
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityLoginBinding
import com.data.utship.model.BaseResponseModel
import com.data.utship.model.LoginBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.broadcastreceiver.NetworkBroadcastReceiver
import com.data.utship.utills.dialog.DialogsProvider
import com.data.utship.utills.utility.GeneralUtilities
import com.data.utship.utills.utility.TextFieldValidator
import com.data.utship.utills.utility.UserAccountManager
import com.data.utship.viewmodel.LoginViewsModel

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"

    private lateinit var binding: ActivityLoginBinding
    lateinit var appPreferences: SharedPrefUtils
    //    lateinit var appPreferences: PreferenceManager
    lateinit var progressDialog: ProgressDialog

    //lateinit var viewModel: LoginViewModel
    lateinit var viewModel: LoginViewsModel
    lateinit var networkBroadcastReceiver: NetworkBroadcastReceiver
    private val retrofitService = RetrofitService.getInstance()
    val JUST_SIGNED_IN = "justSignedIn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        clickOnMyView()

//        if (viewModel != null) return
/*        viewModel =
            ViewModelProvider(this, MyViewModelFactory(this, MainRepository(retrofitService))).get(
                LoginViewsModel::class.java
            )

        viewModel.loginList.observe(this, Observer {
            Log.d(TAG, "movieList: $it")
            //adapter.setMovieList(it)
        })*/

        //invoked when a network exception occurred
        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
        })
        //  if (viewModel.googleServicesNotAvailable()) `binding.signInGoogle.visibility = View.GONE

        bindViews()

        binding.btnRegister.text = "Login"

        /*  binding.Registration.setOnClickListener {
              val intent = Intent(this, RegisterActivity::class.java)
              startActivity(intent)
              finish()

          }*/

        /*  binding.tvForgotPass.setOnClickListener {
             forgotPasswordDialog()
          }*/

    }

    private fun bindViews() {

        networkBroadcastReceiver = NetworkBroadcastReceiver(this)
        registerReceiver(
            networkBroadcastReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        val forcedSignOut = intent.getBooleanExtra(UserAccountManager.FORCED_SIGN_OUT, false)
        if (forcedSignOut) DialogsProvider.get(this).messageDialog(
            getString(R.string.Session_Expired),
            getString(R.string.You_are_signed_out_for_account_security)
        )

        binding.edEmailId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (binding.edEmailId.getText().length > 0) {
                    if (TextFieldValidator.isValidEmail(editable.toString())) binding.edEmailId.setError(
                        null
                    ) else binding.edEmailId.setError(getString(R.string.Email_not_complete_or_not_valid))
                } else binding.edEmailId.setError(null)
            }
        })

        binding.edPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (binding.edPass.getText().length > 0) {
                    if (TextFieldValidator.isValidPassword(editable.toString())) binding.edPass.setError(
                        null
                    ) else if (TextFieldValidator.outLengthRange(
                            editable.toString(),
                            TextFieldValidator.PASSWORD_MIN,
                            TextFieldValidator.NO_MAX_VALUE
                        )
                    ) binding.edPass.setError(getString(R.string.Password_Minimum_length_is) + " " + TextFieldValidator.PASSWORD_MIN) else binding.edPass.setError(
                        getString(R.string.Password_must_contain)
                    )
                } else binding.edPass.setError(null)
            }
        })

        binding.signInGoogle.setOnClickListener {
            //    googleAuth()
        }

        binding.signInFacebook.setOnClickListener {
            //facebookAuth()
        }

        binding.tvLogin.setOnClickListener {
            binding.tvLogin.setTextColor(resources.getColor(R.color.white))
            binding.tvSignup.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.llMobileNo.visibility = View.GONE
            binding.llOTP.visibility = View.GONE
            binding.tvForgotPass.visibility = View.VISIBLE
            binding.btnRegister.text = "Login"

            binding.edEmailId.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    /*                if (binding.edEmailId.getText().length > 0) {
                                        if (TextFieldValidator.isValidEmail(editable.toString())) binding.edEmailId.setError(
                                            null
                                        ) else binding.edEmailId.setError(getString(R.string.Email_not_complete_or_not_valid))
                                    } else binding.edEmailId.setError(null)*/
                }
            })

            binding.edPass.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    /*             if (binding.edPass.getText().length > 0) {
                                     if (TextFieldValidator.isValidPassword(editable.toString())) binding.edPass.setError(
                                         null
                                     ) else if (TextFieldValidator.outLengthRange(
                                             editable.toString(),
                                             TextFieldValidator.PASSWORD_MIN,
                                             TextFieldValidator.NO_MAX_VALUE
                                         )
                                     ) binding.edPass.setError(getString(R.string.Password_Minimum_length_is) + " " + TextFieldValidator.PASSWORD_MIN) else binding.edPass.setError(
                                         getString(R.string.Password_must_contain)
                                     )
                                 } else binding.edPass.setError(null)*/
                }
            })

            binding.btnRegister.setOnClickListener {
            //    if (isDataValid())
                    GeneralUtilities.launchActivity(this, DashboardActivity::class.java)
                //   loginUp()
                //    Toast.makeText(applicationContext, "Click", Toast.LENGTH_SHORT).show()
            }

            binding.signInGoogle.setOnClickListener {
                googleAuth()
            }

            binding.signInFacebook.setOnClickListener { facebookAuth() }

        }

        binding.tvSignup.setOnClickListener {
            binding.tvSignup.setTextColor(resources.getColor(R.color.white))
            binding.tvLogin.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.llMobileNo.visibility = View.VISIBLE
            binding.llOTP.visibility = View.VISIBLE
            binding.tvForgotPass.visibility = View.GONE
            binding.btnRegister.text = "Register"
            binding.btnRegister.setOnClickListener {
                if (isRegisterDataValid())
                //  signIn()
                    Toast.makeText(applicationContext, "Click", Toast.LENGTH_SHORT).show()
            }
        }

/*        binding.btnRegister.setOnClickListener {
               if (isDataValid())
            GlobalScope.launch (Dispatchers.Main) {
                viewModel.getLogin(
                this@LoginActivity, binding.edEmailId.getText().toString(),
                binding.edPass.getText().toString()
            ) }

        //    Toast.makeText(applicationContext, "Click", Toast.LENGTH_SHORT).show()
        }*/

        /* binding.edPass.setOnTouchListener( View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_LEFT = 0;
                 final int DRAWABLE_TOP = 1;
                 final int DRAWABLE_RIGHT = 2;
                 final int DRAWABLE_BOTTOM = 3;
                 if (event.getAction() == MotionEvent.ACTION_UP) {
                     if (event.getRawX() >= (edtMobile.getRight() - edtMobile.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         drawableClickListner();
                         return true;
                     }
                 }
                 return false;
             }
         });*/

          binding.btnRegister.setOnClickListener {
              GeneralUtilities.launchActivity(this, DashboardActivity::class.java)
              finish()

          }
    }

    //12demo@12345
//    raku12222
    private fun clickOnMyView() {
          appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //    GeneralUtilities.launchActivity(this, SplashActivity::class.java)
        //   GeneralUtilities.showErrorSnackBar(this, binding.containerid, "Enter Your Username or Email")
        //    GeneralUtilities.showNormalSnackBar(this, binding.containerid, "Login Successfully")
        //  GeneralUtilities.hideSoftKeyboard(this, binding.containerid)

    }

    fun isDataValid(): Boolean {
        var validData = true

        if (binding.edEmailId.getError() != null || binding.edEmailId.getText().length === 0) {
            binding.edEmailId.requestFocus()
            binding.edEmailId.startAnimation(
                AnimationUtils.loadAnimation(
                    applicationContext,
                    R.anim.fieldmissing
                )
            )
            validData = false
        }
        if (binding.edPass.getError() != null || binding.edPass.getText().length === 0) {
            binding.edPass.requestFocus()
            binding.edPass.startAnimation(
                AnimationUtils.loadAnimation(
                    applicationContext,
                    R.anim.fieldmissing
                )
            )
            validData = false
        }
        return validData
    }

    fun googleAuth() {
        DialogsProvider.get(this).setLoading(true)
        viewModel.googleAuth(this)
    }

    fun facebookAuth() {
        DialogsProvider.get(this).setLoading(true)
        viewModel.facebookAuth(this)!!.observe(this) { response ->
            DialogsProvider.get(this).setLoading(false)
            when (response.code()) {
                BaseResponseModel.SUCCESSFUL_OPERATION, BaseResponseModel.SUCCESSFUL_CREATION -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.putExtra(JUST_SIGNED_IN, true)
                    val userModel = LoginBean()
//                    userModel.setId(viewModel.getFacebookSocialAuthModel()!!.clientId)
//                    userModel.setEmail(viewModel.getFacebookSocialAuthModel()!!.email)
//                    userModel.setFullName(viewModel.getFacebookSocialAuthModel()!!.fullName)
//                    userModel.setImageUrl(viewModel.getFacebookSocialAuthModel()!!.image)
//                    userModel.setSignedInWith(LoginBean.SIGNED_IN_WITH_FACEBOOK)
                    UserAccountManager.signIn(
                        this,
                        intent,
                        response.headers()[Repository.AUTH_TOKEN_HEADER],
                        userModel
                    )
                }
                BaseResponseModel.FAILED_INVALID_DATA -> DialogsProvider.get(this)
                    .messageDialog(

                        getString(R.string.Authentication_Failed),
                        getString(R.string.please_try_again_later)
                    )
                BaseResponseModel.FAILED_REQUEST_FAILURE -> DialogsProvider.get(this)
                    .messageDialog(

                        getString(R.string.Request_Error),
                        getString(R.string.Please_Check_your_connection)
                    )
                else -> DialogsProvider.get(this).messageDialog(

                    getString(R.string.Server_Error),
                    getString(R.string.Code) + response.code()
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) getGoogleAuthResult(data)
    }

    fun getGoogleAuthResult(data: Intent?) {
        viewModel.googleAuthOnActivityResult(data, this)!!
            .observe(this) { response ->
                DialogsProvider.get(this).setLoading(false)
                when (response.code()) {
                    BaseResponseModel.SUCCESSFUL_OPERATION, BaseResponseModel.SUCCESSFUL_CREATION -> {
                        val intent = Intent(this, DashboardActivity::class.java)
                        intent.putExtra(JUST_SIGNED_IN, true)
                        val userModel = LoginBean()
//                        userModel.setId(viewModel.getGoogleSocialAuthModel()!!.clientId)
//                        userModel.setEmail(viewModel.getGoogleSocialAuthModel()!!.email)
//                        userModel.setFullName(viewModel.getGoogleSocialAuthModel()!!.fullName)
//                        userModel.setImageUrl(viewModel.getGoogleSocialAuthModel()!!.image)
//                        userModel.setSignedInWith(LoginBean.SIGNED_IN_WITH_GOOGLE)
                        UserAccountManager.signIn(
                            this,
                            intent,
                            response.headers()[Repository.AUTH_TOKEN_HEADER],
                            userModel
                        )
                    }
                    BaseResponseModel.FAILED_INVALID_DATA -> DialogsProvider.get(this)
                        .messageDialog(

                            getString(R.string.Authentication_Failed),
                            getString(R.string.Please_try_again)
                        )
                    BaseResponseModel.FAILED_REQUEST_FAILURE -> DialogsProvider.get(this)
                        .messageDialog(

                            getString(R.string.Request_Error),
                            getString(R.string.Please_Check_your_connection)
                        )
                    else -> DialogsProvider.get(this).messageDialog(

                        getString(R.string.Server_Error),
                        getString(R.string.Code) + response.code()
                    )
                }
            }
    }

    /* fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
         when (view.id) {
             com.example.omsweetappproject.R.id.ivPasswordToggle -> when (motionEvent.action) {
                 MotionEvent.ACTION_DOWN -> {
                     Toast.makeText(this, "show", Toast.LENGTH_SHORT).show()
                     binding.edPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                 }
                 MotionEvent.ACTION_UP -> {
                     binding.edPass.setTransformationMethod(PasswordTransformationMethod.getInstance())
                     Toast.makeText(this, "hide", Toast.LENGTH_SHORT).show()
                 }
             }
         }
         return true
     }*/

    //////////////Registeration//////////////

    fun isRegisterDataValid(): Boolean {
        var validData = true

        if (binding.edEmailId.getError() != null || binding.edEmailId.text.length == 0) {
            binding.edEmailId.requestFocus()
            binding.edEmailId.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.fieldmissing
                )
            )
            validData = false
        }

        if (binding.edPass.getError() != null || binding.edPass.text.length == 0) {
            binding.edPass.requestFocus()
            binding.edPass.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.fieldmissing
                )
            )
            validData = false
        }

        if (binding.edMobNo.getError() != null || binding.edMobNo.text.length == 0
        ) {
            binding.edMobNo.requestFocus()
            binding.edMobNo.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.fieldmissing
                )
            )
            validData = false
        }
        if (binding.edOTP.getError() != null || binding.edOTP.text.length == 0
        ) {
            binding.edOTP.requestFocus()
            binding.edOTP.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.fieldmissing
                )
            )
            validData = false
        }
        return validData
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkBroadcastReceiver)
    }
}

/*class MyViewModelFactory(
    val activity: LoginActivity,
    val mainRepository: MainRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return if (modelClass.isAssignableFrom(LoginViewsModel::class.java)) {
            LoginViewsModel(activity, getSharedPrefInstance(), mainRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}*/





    
  