package com.data.utship.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityLoginNewBinding
import com.data.utship.model.LoginResponseBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginNewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginNewBinding
    lateinit var appPreferences: SharedPrefUtils
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_login_new)
        binding = ActivityLoginNewBinding.inflate(layoutInflater)
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
        binding.Registration.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        binding.tvForgotPass.setOnClickListener {
            startActivity(Intent(this,ForgotActivity::class.java))
        }

        binding.ivClose.setOnClickListener {
           finish()
        }

        binding.btnLogin.setOnClickListener {
            if (binding.editMobNo.text.toString().equals("")) {
                binding.editMobNo.error = "Enter Mobile Number"
                binding.editMobNo.requestFocus()
            } else if (binding.editPassword.text.toString().equals("")) {
                binding.editPassword.error = "Enter Password"
                binding.editPassword.requestFocus()
            } else {
                loginApi()
            }
        }
    }

    fun loginApi() {
        GeneralUtilities.showDialog(this)

        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("mobile", "password"),
            arrayOf(
                binding.editMobNo.text.toString(), binding.editPassword.text.toString()
            )
        )
        val response = RetrofitService.getInstance().getLoginApi(builder.build())
        response.enqueue(object : Callback<LoginResponseBean> {
            override fun onResponse(
                call: Call<LoginResponseBean>,
                response: Response<LoginResponseBean>
            ) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        //handleRecyler(response.body()!!.data)

                        appPreferences.setValue(AppConstant.UserID, response.body()!!.userID)
                        appPreferences.setValue(AppConstant.RememberMe, true)
                        Toast.makeText(
                            this@LoginNewActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        GeneralUtilities.launchclearbackActivity(this@LoginNewActivity, DashboardActivity::class.java)
                        finish()


                    } else {

                        Toast.makeText(
                            this@LoginNewActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@LoginNewActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<LoginResponseBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@LoginNewActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}