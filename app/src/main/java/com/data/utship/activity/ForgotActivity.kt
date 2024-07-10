package com.data.utship.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityForgotBinding
import com.data.utship.model.LoginResponseBean
import com.data.utship.model.SendOTPBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotActivity : AppCompatActivity() {
    var otp=""
    private lateinit var binding: ActivityForgotBinding
    lateinit var appPreferences: SharedPrefUtils
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_login_new)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        clickOnMyView()
    }
    private fun clickOnMyView() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
 /*       binding.toolbarTitle.imgClose.setOnClickListener { finish() }
        binding.toolbarTitle.tvTitleProName.text = "Login"
        binding.toolbarTitle.imgCart.visibility= View.GONE
        binding.toolbarTitle.imgFav.visibility= View.GONE*/
        //    GeneralUtilities.launchActivity(this, SplashActivity::class.java)
        //   GeneralUtilities.showErrorSnackBar(this, binding.containerid, "Enter Your Username or Email")
        //    GeneralUtilities.showNormalSnackBar(this, binding.containerid, "Login Successfully")
        //  GeneralUtilities.hideSoftKeyboard(this, binding.containerid)


        binding.ivClose.setOnClickListener {
           finish()
        }

        binding.btnLogin.setOnClickListener {
            if (binding.editOTP.text.toString().equals("")) {
                binding.editOTP.error = "Enter OTP"
                binding.editOTP.requestFocus()
            }else if (!otp.equals(binding.editOTP.text.toString())) {
                binding.editOTP.error = "OTP Doesn't Match"
                binding.editOTP.requestFocus()
            }else if (binding.editPassword.text.toString().equals("")) {
                binding. editPassword.error = "Enter Password"
                binding.editPassword.requestFocus()
            } else if (binding.editCpass.text.toString().equals("")) {
                binding.editCpass.error = "Enter Confirm Password"
                binding.editCpass.requestFocus()
            }  else if (!binding.editCpass.text.toString().equals(binding.editPassword.text.toString())) {
                binding.editCpass.error = "Password Doesn't Match"
                binding.editCpass.requestFocus()
            }  else {
                forgotApi()
            }
        }
        binding.btnSubmitOTP.setOnClickListener {
            if (binding.editMobNo.text.toString().equals("")) {
                binding.editMobNo.error = "Enter Mobile Number"
                binding.editMobNo.requestFocus()
            }  else {
                sendOTPApi()
            }
        }
    }
    fun sendOTPApi() {
        GeneralUtilities.showDialog(this)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("type", "mobile"),
            arrayOf( "3",binding.editMobNo.text.toString()))

        val response = RetrofitService.getInstance().getSendOTPApi(builder.build())
        response.enqueue(object : Callback<SendOTPBean> {
            override fun onResponse(
                call: Call<SendOTPBean>,
                response: Response<SendOTPBean>
            ) {
                try {

                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        otp=response.body()!!.data.otp;
                        binding.llOTPSection.visibility=View.GONE
                        binding.llPSWDSection.visibility=View.VISIBLE
                        Toast.makeText(
                            this@ForgotActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            this@ForgotActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@ForgotActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<SendOTPBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@ForgotActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
    fun forgotApi() {
        GeneralUtilities.showDialog(this)

        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("Mobile", "Password"),
            arrayOf(
                binding.editMobNo.text.toString(),binding.editCpass.text.toString()
            )
        )
        val response = RetrofitService.getInstance().forgetPassword(builder.build())
        response.enqueue(object : Callback<LoginResponseBean> {
            override fun onResponse(
                call: Call<LoginResponseBean>,
                response: Response<LoginResponseBean>
            ) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        //handleRecyler(response.body()!!.data)

                        Toast.makeText(
                            this@ForgotActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        GeneralUtilities.launchclearbackActivity(this@ForgotActivity, LoginNewActivity::class.java)
                        finish()


                    } else {

                        Toast.makeText(
                            this@ForgotActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@ForgotActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<LoginResponseBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@ForgotActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}