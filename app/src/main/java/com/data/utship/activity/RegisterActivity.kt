package com.data.utship.activity


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityRegisterBinding
import com.data.utship.model.BaseResponseBean
import com.data.utship.model.SendOTPBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    lateinit var appPreferences: SharedPrefUtils
    lateinit var progressDialog: ProgressDialog

var otp=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_login)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        clickOnMyView()

        bindViews()
    }

    private fun bindViews() {


/*
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

          binding.btnRegister.setOnClickListener {
              GeneralUtilities.launchActivity(this, DashboardActivity::class.java)
              finish()

          }*/

    }

    //12demo@12345
//    raku12222

    private fun clickOnMyView() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.btnLogin.alpha=0.5f
        binding.btnLogin.isEnabled=false
     /*   binding.toolbarTitle.imgClose.setOnClickListener { finish() }
        binding.toolbarTitle.tvTitleProName.text = "Register"
        binding.toolbarTitle.imgCart.visibility = View.GONE
        binding.toolbarTitle.imgFav.visibility = View.GONE*/
        binding.apply {
            login.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginNewActivity::class.java))
            }
            btnOtp.setOnClickListener {
                if (editName.text.toString().equals("")) {
                    editName.error = "Enter Your Name"
                    editName.requestFocus()
                } else if (editMobNo.text.toString().equals("")) {
                    editMobNo.error = "Enter Your Mobile Number"
                    editMobNo.requestFocus()
                }else if (editEmailID.text.toString().equals("")) {
                    editEmailID.error = "Enter Your EmailID"
                    editEmailID.requestFocus()
                } else if (editPassword.text.toString().equals("")) {
                    editPassword.error = "Enter Password"
                    editPassword.requestFocus()
                } else if (editCpass.text.toString().equals("")) {
                    editCpass.error = "Enter Confirm Password"
                    editCpass.requestFocus()
                }  else if (!editCpass.text.toString().equals(binding.editPassword.text.toString())) {
                    editCpass.error = "Password Doesn't Match"
                    editCpass.requestFocus()
                }else{
                    sendOTPApi()

                }

            }
            ivClose.setOnClickListener {
                finish()
            }
            btnLogin.setOnClickListener {
                if (editName.text.toString().equals("")) {
                    editName.error = "Enter Your Name"
                    editName.requestFocus()
                } else if (editMobNo.text.toString().equals("")) {
                    editMobNo.error = "Enter Your Mobile Number"
                    editMobNo.requestFocus()
                }else if (editEmailID.text.toString().equals("")) {
                    editEmailID.error = "Enter Your EmailID"
                    editEmailID.requestFocus()
                } else if (editPassword.text.toString().equals("")) {
                    editPassword.error = "Enter Password"
                    editPassword.requestFocus()
                } else if (editCpass.text.toString().equals("")) {
                    editCpass.error = "Enter Confirm Password"
                    editCpass.requestFocus()
                }  else if (!editCpass.text.toString().equals(binding.editPassword.text.toString())) {
                    editCpass.error = "Password Doesn't Match"
                    editCpass.requestFocus()
                } else if (editOTP.text.toString().equals("")) {
                    editOTP.error = "Enter OTP"
                    editOTP.requestFocus()
                }  else if (!otp.equals(binding.editOTP.text.toString())) {
                    editOTP.error = "OTP Doesn't Match"
                    editOTP.requestFocus()
                } else {
                    registerationApi()
                }
            }
        }

        //    GeneralUtilities.launchActivity(this, SplashActivity::class.java)
        //   GeneralUtilities.showErrorSnackBar(this, binding.containerid, "Enter Your Username or Email")
        //    GeneralUtilities.showNormalSnackBar(this, binding.containerid, "Login Successfully")
        //  GeneralUtilities.hideSoftKeyboard(this, binding.containerid)

    }

    fun registerationApi() {
        GeneralUtilities.showDialog(this)

        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("ID", "Name","Email","Mobile","Password","AddDate","ProfileIMG","ReferralID"),
            arrayOf(
               "0",binding.editName.text.toString(),binding.editEmailID.text.toString(), binding.editMobNo.text.toString(), binding.editPassword.text.toString(),"",""
            ,binding.editRefferal.text.toString())
        )


        val response = RetrofitService.getInstance().getRegisterApi(builder.build())
        response.enqueue(object : Callback<BaseResponseBean> {
            override fun onResponse(
                call: Call<BaseResponseBean>,
                response: Response<BaseResponseBean>
            ) {
                try {

                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        Toast.makeText(
                            this@RegisterActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@RegisterActivity, LoginNewActivity::class.java))
                 finish()
                    } else {

                        Toast.makeText(
                            this@RegisterActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@RegisterActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<BaseResponseBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@RegisterActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun sendOTPApi() {
        GeneralUtilities.showDialog(this)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("type", "mobile"),
            arrayOf( "1",binding.editMobNo.text.toString()))
        val response = RetrofitService.getInstance().getSendOTPApi(builder.build())
        response.enqueue(object : Callback<SendOTPBean> {
            override fun onResponse(
                call: Call<SendOTPBean>,
                response: Response<SendOTPBean>
            ) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        otp=response.body()!!.data.otp
                        binding.btnLogin.isEnabled=true
                        binding.btnLogin.alpha=1f
                        Toast.makeText(
                            this@RegisterActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        binding.btnLogin.alpha=0.5f
                        binding.btnLogin.isEnabled=false
                        Toast.makeText(
                            this@RegisterActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@RegisterActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<SendOTPBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@RegisterActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}





    
  