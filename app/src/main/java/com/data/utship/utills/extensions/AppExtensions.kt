package com.data.utship.utills.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.*
import android.net.Uri
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.text.SimpleDateFormat
import java.util.*
import android.content.DialogInterface
import android.view.KeyEvent
import com.data.utship.R
import com.data.utship.utills.AppCon
import com.data.utship.utills.AppCon.Companion.getAppInstance
import com.data.utship.utills.AppCon.Companion.noInternetDialog
import com.data.utship.utills.Constants.AppBroadcasts.CARTITEM_UPDATE
import com.data.utship.utills.Constants.AppBroadcasts.CART_COUNT_CHANGE
import com.data.utship.utills.Constants.AppBroadcasts.ORDER_COUNT_CHANGE
import com.data.utship.utills.Constants.AppBroadcasts.PROFILE_UPDATE
import com.data.utship.utills.Constants.AppBroadcasts.WISHLIST_UPDATE
import com.data.utship.utills.Constants.SharedPref.ACCENTCOLOR
import com.data.utship.utills.Constants.SharedPref.BACKGROUNDCOLOR
import com.data.utship.utills.Constants.SharedPref.DASHBOARDDATA
import com.data.utship.utills.Constants.SharedPref.DEFAULT_CURRENCY
import com.data.utship.utills.Constants.SharedPref.DEFAULT_CURRENCY_FORMATE
import com.data.utship.utills.Constants.SharedPref.IS_LOGGED_IN
import com.data.utship.utills.Constants.SharedPref.KEY_CART_COUNT
import com.data.utship.utills.Constants.SharedPref.KEY_ORDER_COUNT
import com.data.utship.utills.Constants.SharedPref.KEY_PRODUCT_DETAIL
import com.data.utship.utills.Constants.SharedPref.KEY_WISHLIST_COUNT
import com.data.utship.utills.Constants.SharedPref.LANGUAGE
import com.data.utship.utills.Constants.SharedPref.PRIMARYCOLOR
import com.data.utship.utills.Constants.SharedPref.PRIMARYCOLORDARK
import com.data.utship.utills.Constants.SharedPref.TEXTPRIMARYCOLOR
import com.data.utship.utills.Constants.SharedPref.TEXTSECONDARYCOLOR
import com.data.utship.utills.Constants.SharedPref.USER_DISPLAY_NAME
import com.data.utship.utills.Constants.SharedPref.USER_EMAIL
import com.data.utship.utills.Constants.SharedPref.USER_FIRST_NAME
import com.data.utship.utills.Constants.SharedPref.USER_ID
import com.data.utship.utills.Constants.SharedPref.USER_LAST_NAME
import com.data.utship.utills.Constants.SharedPref.USER_PASSWORD
import com.data.utship.utills.Constants.SharedPref.USER_PROFILE
import com.data.utship.utills.Constants.SharedPref.USER_TOKEN
import com.data.utship.utills.Constants.SharedPref.USER_USERNAME
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.getDisplayWidth
import com.data.utship.utills.utility.AppConstant
import com.iqonic.woobox.models.BuilderDashboard
import com.iqonic.woobox.models.DashBoardResponse


fun isLoggedIn(): Boolean = getSharedPrefInstance().getBooleanValue(IS_LOGGED_IN)

//fun isTokenExpired(): Boolean = getSharedPrefInstance().getBooleanValue(IS_TOKEN_EXPIRED)
fun getUserId(): String = getSharedPrefInstance().getStringValue(USER_ID)
fun getDefaultCurrency(): String = getSharedPrefInstance().getStringValue(DEFAULT_CURRENCY)
fun getDefaultCurrencyFormate(): String = getSharedPrefInstance().getStringValue(
    DEFAULT_CURRENCY_FORMATE
)

fun getAppLanguage(): String = getSharedPrefInstance().getStringValue(LANGUAGE, defaultValue = "en")
fun getUserName(): String = getSharedPrefInstance().getStringValue(USER_USERNAME)
fun getFirstName(): String = getSharedPrefInstance().getStringValue(USER_FIRST_NAME)
fun getDisplayName(): String = getSharedPrefInstance().getStringValue(USER_DISPLAY_NAME)
fun getLastName(): String = getSharedPrefInstance().getStringValue(USER_LAST_NAME)
fun getEmail(): String = getSharedPrefInstance().getStringValue(USER_EMAIL)
fun getPassword(): String = getSharedPrefInstance().getStringValue(USER_PASSWORD)
//fun getCartCount(): String = getSharedPrefInstance().getIntValue(KEY_CART_COUNT, 0).toString()
fun getCartCount(): String = getSharedPrefInstance().getStringValue(KEY_CART_COUNT)
fun getWishListCount(): String =
    getSharedPrefInstance().getIntValue(KEY_WISHLIST_COUNT, 0).toString()

fun getOrderCount(): String = getSharedPrefInstance().getIntValue(KEY_ORDER_COUNT, 0).toString()
fun getApiToken(): String = getSharedPrefInstance().getStringValue(USER_TOKEN)
fun getUserProfile(): String = getSharedPrefInstance().getStringValue(USER_PROFILE)

@SuppressLint("ResourceType")
fun getPrimaryColor(): String {
    return getSharedPrefInstance().getStringValue(PRIMARYCOLOR)
}

fun getPrimaryColorDark(): String {
    return getSharedPrefInstance().getStringValue(PRIMARYCOLORDARK)
}

@SuppressLint("ResourceType")
fun getAccentColor(): String {
    return getSharedPrefInstance().getStringValue(ACCENTCOLOR)
}

@SuppressLint("ResourceType")
fun getButtonColor(): String {
    return getSharedPrefInstance().getStringValue(PRIMARYCOLORDARK)
}

@SuppressLint("ResourceType")
fun getTextPrimaryColor(): String {
    return getSharedPrefInstance().getStringValue(TEXTPRIMARYCOLOR)
}

@SuppressLint("ResourceType")
fun getTextSecondaryColor(): String {
    return getSharedPrefInstance().getStringValue(TEXTSECONDARYCOLOR)
}

@SuppressLint("ResourceType")
fun getBackgroundColor(): String {
    return getSharedPrefInstance().getStringValue(BACKGROUNDCOLOR)

}

@SuppressLint("ResourceType")
fun getTextTitleColor(): String {
    return getAppInstance().resources.getString(R.color.white)
}

fun getProductDetailConstant(): Int = getSharedPrefInstance().getIntValue(KEY_PRODUCT_DETAIL, 0)

/*fun getUserFullName(): String {
    return when {
        isLoggedIn() -> (getSharedPrefInstance().getStringValue(USER_FIRST_NAME) + " " + getSharedPrefInstance().getStringValue(
            USER_LAST_NAME
        )).toCamelCase()
        else -> "Guest User"
    }
}*/

fun Context.openCustomTab(url: String) =
    CustomTabsIntent.Builder().build().launchUrl(this, Uri.parse(url))


/*fun getProfile(): String = getSharedPrefInstance().getStringValue(USER_PROFILE)
fun Activity.fetchAndStoreCartData() {
    getRestApiImpl().getCart(onApiSuccess = {
        getSharedPrefInstance().setValue(KEY_CART_COUNT, it.total_quantity)
        sendCartCountChangeBroadcast()
    }, onApiError = {
        getSharedPrefInstance().setValue(KEY_CART_COUNT, 0)
        sendCartCountChangeBroadcast()
    })
}*/


fun convertOrderDataToLocalDate(ourDate: String): String? {
    var ourDate: String? = ourDate
    ourDate = try {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.000000", Locale.US)
        formatter.timeZone = TimeZone.getTimeZone("Etc/UTC")
        val value: Date = formatter.parse(ourDate)
        val dateFormatter =
            SimpleDateFormat("dd-MM-yyyy hh:mm a") //this format changeable
        dateFormatter.timeZone = TimeZone.getDefault()
        dateFormatter.format(value)
    } catch (e: Exception) {
        e.printStackTrace()
        "00-00-0000 00:00"
    }
    return ourDate
}

fun convertToLocalDate(ourDate: String): String? {
    var ourDate: String? = ourDate
    ourDate = try {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        formatter.timeZone = TimeZone.getTimeZone("Etc/UTC")
        val value: Date = formatter.parse(ourDate)
        val dateFormatter =
            SimpleDateFormat("dd-MM-yyyy hh:mm a") //this format changeable
        dateFormatter.timeZone = TimeZone.getDefault()
        dateFormatter.format(value)
    } catch (e: Exception) {
        e.printStackTrace()
        "00-00-0000 00:00"
    }
    return ourDate
}

/**
 * Add shared preference related to user session here
 */
fun clearLoginPref() {
    getSharedPrefInstance().removeKey(AppConstant.RememberMe)
    getSharedPrefInstance().removeKey(AppConstant.UserID)
   /* getSharedPrefInstance().removeKey(USER_DISPLAY_NAME)
    getSharedPrefInstance().removeKey(USER_EMAIL)
    getSharedPrefInstance().removeKey(USER_NICE_NAME)
    getSharedPrefInstance().removeKey(USER_TOKEN)
    getSharedPrefInstance().removeKey(USER_FIRST_NAME)
    getSharedPrefInstance().removeKey(USER_LAST_NAME)
    getSharedPrefInstance().removeKey(USER_PROFILE)
    getSharedPrefInstance().removeKey(USER_ROLE)
    getSharedPrefInstance().removeKey(USER_USERNAME)
    getSharedPrefInstance().removeKey(KEY_USER_ADDRESS)
    getSharedPrefInstance().removeKey(KEY_CART_COUNT)
    getSharedPrefInstance().removeKey(KEY_ORDER_COUNT)
    getSharedPrefInstance().removeKey(KEY_WISHLIST_COUNT)
    getSharedPrefInstance().removeKey(USER_PASSWORD)*/
}

fun getSharedPrefInstance(): SharedPrefUtils {
    return if (AppCon.sharedPrefUtils == null) {
        AppCon.sharedPrefUtils = SharedPrefUtils()
        AppCon.sharedPrefUtils!!
    } else {
        AppCon.sharedPrefUtils!!
    }
}

fun RecyclerView.rvItemAnimation() {
    layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
}


fun ImageView.displayBlankImage(aContext: Context, aPlaceHolderImage: Int) {
    Glide.with(aContext)
        .load(aPlaceHolderImage)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}



fun Activity.getAlertDialog(
    aMsgText: String,
    aTitleText: String = getString(R.string.lbl_dialog_title),
    aPositiveText: String = getString(R.string.lbl_yes),
    aNegativeText: String = getString(R.string.lbl_no),
    onPositiveClick: (dialog: DialogInterface, Int) -> Unit,
    onNegativeClick: (dialog: DialogInterface, Int) -> Unit,
): AlertDialog {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(aTitleText)
    builder.setMessage(aMsgText)
    builder.setPositiveButton(aPositiveText) { dialog, which ->
        onPositiveClick(dialog, which)
    }

    builder.setNegativeButton(aNegativeText) { dialog, which ->
        onNegativeClick(dialog, which)
    }
    return builder.create()
}

fun Activity.productLayoutParams(): RelativeLayout.LayoutParams {
    val width = (getDisplayWidth() / 4.2).toInt()
    val imgHeight = width + (width / 12)
    return RelativeLayout.LayoutParams(width, imgHeight)
}

fun Activity.productLayoutParamsForDealOffer(): RelativeLayout.LayoutParams {
    val width = (getDisplayWidth() / 3.2).toInt()
    val imgHeight = width + (width / 10)
    return RelativeLayout.LayoutParams(width, imgHeight)
}

fun Activity.sendCartCountChangeBroadcast() {
    sendBroadcast(CART_COUNT_CHANGE)
}


fun Activity.sendProfileUpdateBroadcast() {
    sendBroadcast(PROFILE_UPDATE)
}

fun Activity.sendWishlistBroadcast() {
    sendBroadcast(WISHLIST_UPDATE)
}

fun Activity.sendCartBroadcast() {
    sendBroadcast(CARTITEM_UPDATE)
}

fun Activity.sendOrderCountChangeBroadcast() {
    sendBroadcast(ORDER_COUNT_CHANGE)
}

fun Activity.sendBroadcast(action: String) {
    val intent = Intent()
    intent.action = action
    sendBroadcast(intent)
}

fun Activity.registerBroadCastReceiver(action: String, receiver: BroadcastReceiver) {
    val intent = IntentFilter()
    intent.addAction(action)
    registerReceiver(receiver, intent)
}

/*fun getbillingList(): Billing {
    val string = getSharedPrefInstance().getStringValue(BILLING, "")
    if (string.isEmpty()) {
        return Billing()
    }
    return Gson().fromJson(string, object : TypeToken<Billing>() {}.type)
}*/

/*fun getShippingList(): Shipping {
    val string = getSharedPrefInstance().getStringValue(Constants.SharedPref.SHIPPING, "")
    if (string.isEmpty()) {
        return Shipping()
    }
    return Gson().fromJson(string, object : TypeToken<Shipping>() {}.type)
}*/

fun ImageView.loadImageFromDrawable(@DrawableRes aPlaceHolderImage: Int) {
    Glide.with(getAppInstance()).load(aPlaceHolderImage).diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}

/*fun fetchCountry(
    onApiSuccess: (ArrayList<CountryModel>) -> Unit,
    onApiError: (aError: String) -> Unit,
) {
    val string = getSharedPrefInstance().getStringValue(COUNTRY, "")
    Log.e("country", string)
    if (string.isEmpty()) {
        getRestApiImpl().listAllCountry(onApiSuccess = { its ->
            getSharedPrefInstance().setValue(
                COUNTRY,
                Gson().toJson(its)
            )
            onApiSuccess(its)
        }, onApiError = {
            onApiError(it)
        })
    } else {
        onApiSuccess(Gson().fromJson(string, object : TypeToken<ArrayList<CountryModel>>() {}.type))
    }
}*/

fun Activity.openNetworkDialog(onClick: () -> Unit) {
    try {
        if (noInternetDialog == null) {
            noInternetDialog = Dialog(this, R.style.FullScreenDialog)
     //       noInternetDialog?.setContentView(R.layout.activity_no);
            noInternetDialog?.setContentView(R.layout.layout_network);
            noInternetDialog?.setCanceledOnTouchOutside(false);
            noInternetDialog?.setCancelable(false)

            noInternetDialog?.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )

            noInternetDialog?.setOnKeyListener(DialogInterface.OnKeyListener { arg0, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish()
                    noInternetDialog?.dismiss()
                }
                true
            })
       /*     noInternetDialog?.tvBackRetry?.onClick {
                if (!isNetworkAvailable()) {
                    snackBarError(getAppInstance().getString(R.string.error_no_internet))
                    return@onClick
                } else {
                    launchActivityWithNewTask<DashboardActivity>()

                }
            }*/

        }
        if (!this.isFinishing && !noInternetDialog!!.isShowing) {
            noInternetDialog?.show()
        }
    } catch (e: Exception) {

    }

}

fun Context.fromJson(file: String): String =
    assets.open(file).bufferedReader().use {
        it.readText()
    }

fun Context.mainContent(onSuccess: (DashBoardResponse) -> Unit) =
    onSuccess(Gson().fromJson(fromJson("builder.json"), DashBoardResponse::class.java))

fun getBuilderDashboard(): BuilderDashboard {
    val string = getSharedPrefInstance().getStringValue(DASHBOARDDATA, "")
    if (string.isEmpty()) {
        return BuilderDashboard()
    }
    return Gson().fromJson(string, object : TypeToken<BuilderDashboard>() {}.type)
}