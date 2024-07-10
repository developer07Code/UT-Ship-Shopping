package com.data.utship.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.data.utship.R
import com.data.utship.databinding.ActivitySplashBinding
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.isNetworkAvailable
import com.data.utship.utills.extensions.openNetworkDialog
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    lateinit var appPreferences: SharedPrefUtils
    var onNetworkRetry: (() -> Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //   setContentView(R.layout.activity_splash)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
   //    appPreferences= AppPreferences(this)
        val animation4 = AnimationUtils.loadAnimation(this, R.anim.slide_up_enter)
        //GeneralUtilities.getInstance().setStatusBarColor(SplashActivity.this, ContextCompat.getColor(context, R.color.colorPrimaryDark));
        binding.imagea.startAnimation(animation4)

        if (!isNetworkAvailable()) {
            openNetworkDialog {
                recreate();onNetworkRetry?.invoke()
            }
        }else{
            Handler(Looper.getMainLooper()).postDelayed({
                // callNextActivity()
                GeneralUtilities.launchActivity(this, DashboardActivity::class.java)
                finish()
              /*  if (appPreferences.getBooleanValue(AppConstant.isPrivacyPolicy)!=true) {
                    GeneralUtilities.launchActivity(this, PrivacyPolicyActivity::class.java)
                    finish()
                }else{
                    GeneralUtilities.launchActivity(this, DashboardActivity::class.java)
                    appPreferences.setValue(AppConstant.isPrivacyPolicy, true)
                    finish()
                }*/

            }, 2500)
        }

    }

    private fun callNextActivity() {
        if(appPreferences.getBooleanValue(AppConstant.RememberMe)) {
            GeneralUtilities.launchActivity(this, DashboardActivity::class.java)
            finish()
        }else{
            GeneralUtilities.launchActivity(this, LoginActivity::class.java)
            finish()
        }
    }
}