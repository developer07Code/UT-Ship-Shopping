package com.data.utship.utills

import android.app.Dialog
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication


class AppCon : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }



    override fun attachBaseContext(base: Context) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        lateinit var localeManager: LocaleManager
        private lateinit var appInstance: AppCon
        var sharedPrefUtils: SharedPrefUtils? = null
        var noInternetDialog: Dialog? = null

        lateinit var language: String
        var appTheme: Int = 0
        fun getAppInstance(): AppCon = appInstance
        var mModeFlag: Boolean = false


    }
}
