package com.data.utship.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityOtpBinding
import com.data.utship.model.LoginResponseBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.utility.AppConstant
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    lateinit var progressDialog: ProgressDialog
    lateinit var appPreferences: SharedPrefUtils
    var OTPData=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_otp)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        bindViews()
        startTimer()
    }

    private fun bindViews() {
        appPreferences=SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
         OTPData= intent.getStringExtra("otp").toString()
        val mobileNo=intent.getStringExtra("mobileNo")
        binding.otp.setText("OTP sent on your\n number +91" + mobileNo+" ")
        binding.edOtpBox.setText(OTPData)
        binding.resendOTP.setOnClickListener {
      //      apiSendOtp(mobileNo!!)
            binding.tvTimer.visibility = View.VISIBLE
            binding.resendOTP.visibility = View.GONE
            startTimer()

        }
        binding.verify.setOnClickListener {
            if (binding.edOtpBox.text.toString().equals("")) {
                binding.edOtpBox.error = "Empty Field"
                binding.edOtpBox.requestFocus()
            }else if (!binding.edOtpBox.text.toString().equals(OTPData)) {
                binding.edOtpBox.error = "Invalid OTP"
                binding.edOtpBox.requestFocus()
            } else {
                apiLogin(mobileNo!!)
            }
        }
    }

    private fun startTimer() {
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.OtpTimer.setText("seconds remaining: " + millisUntilFinished / 1000)
                // logic to set the EditText could go here
            }

            override fun onFinish() {
                binding.resendOTP.setVisibility(View.VISIBLE)
                binding.OtpTimer.setVisibility(View.GONE)
            }
        }.start()
    }

    fun apiLogin(mobileNo: String) {
        progressDialog.show()
      //  DialogsProvider.get(this).setLoading(true)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("mobile"),
            arrayOf(
                mobileNo))
        val response = RetrofitService.getInstance().getLoginApi(builder.build())
        response.enqueue(object : Callback<LoginResponseBean> {
            override fun onResponse(call: Call<LoginResponseBean>, response: Response<LoginResponseBean>) {
                try {
                    progressDialog.dismiss()
                    if (response.body()?.status == 1) {
                        appPreferences.setValue(AppConstant.RememberMe,true)
                        appPreferences.setValue(AppConstant.UserID,response.body()?.userID)
                        appPreferences.setValue(AppConstant.MobileNo,mobileNo)
                        startActivity(Intent(this@OtpActivity,DashboardActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@OtpActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    Toast.makeText(this@OtpActivity,e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<LoginResponseBean>, t: Throwable) {
               // DialogsProvider.get(this@OtpActivity).setLoading(false)
                Toast.makeText(this@OtpActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

/*    fun apiSendOtp(mobileNo: String) {
        progressDialog.show()
       // DialogsProvider.get(this).setLoading(true)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("type", "mobile"),
            arrayOf(
                "1",
                mobileNo))
        val response = RetrofitService.getInstance().getSendOtpApi(builder.build())
        response.enqueue(object : Callback<SendOTPBean> {
            override fun onResponse(call: Call<SendOTPBean>, response: Response<SendOTPBean>) {
                try {
                    progressDialog.dismiss()
                    if (response.body()?.status == 1) {
                        OTPData = response.body()?.data?.otp.toString()
                    }else{
                        Toast.makeText(this@OtpActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    Toast.makeText(this@OtpActivity,e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)

            }

            override fun onFailure(call: Call<SendOTPBean>, t: Throwable) {
            //    DialogsProvider.get(this@OtpActivity).setLoading(false)
                Toast.makeText(this@OtpActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }*/
}