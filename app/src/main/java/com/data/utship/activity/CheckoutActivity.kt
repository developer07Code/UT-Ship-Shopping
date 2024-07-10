package com.data.utship.activity


import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cashfree.pg.api.CFPaymentGatewayService
import com.cashfree.pg.base.exception.CFException
import com.cashfree.pg.core.api.CFSession
import com.cashfree.pg.core.api.CFTheme
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback
import com.cashfree.pg.core.api.utils.CFErrorResponse
import com.cashfree.pg.ui.api.CFDropCheckoutPayment
import com.cashfree.pg.ui.api.CFPaymentComponent
import com.data.utship.R
import com.data.utship.adapter.AddressListAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityCheckoutBinding
import com.data.utship.model.AddressListBean
import com.data.utship.model.CashfreeBean
import com.data.utship.model.RazorpayBean
import com.data.utship.model.TotalCartBean
import com.data.utship.utills.RvClickListner
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import com.google.gson.Gson
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import okhttp3.FormBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CheckoutActivity : AppCompatActivity(), CFCheckoutResponseCallback ,
    PaymentResultWithDataListener {
    private lateinit var binding: ActivityCheckoutBinding
    lateinit var appPreferences: SharedPrefUtils
    var paymentModeType = ""
    var netamt = ""
    var addressID = 0

    //  lateinit var appPreferences: PreferenceManager
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_login)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        clickOnMyView()
        bindViews()
        getAddressListApi(0)
        apiTotalCart(0)
        paymentMode()
        // handleRecyclerCheckout()
    }

    fun paymentMode() {
        binding.rgPaymentMode.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                if (checkedId == R.id.rbCOD) {
                    paymentModeType = "COD"
                } else if (checkedId == R.id.rbPG) {
                    paymentModeType = "Online"
                }
            }
        })
    }

    fun addressList(listAddress: MutableList<AddressListBean.DataDTO>) {
        binding.rcAddressList.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        binding.rcAddressList.isNestedScrollingEnabled = true
        binding.rcAddressList.setHasFixedSize(true)
        val customAdapter = AddressListAdapter(listAddress, this, object : RvClickListner {
            override fun clickPos(pos: Int) {
                addressID = pos
            }

            override fun clickDeletePos(pos: Int) {
                getAddressListApi(pos)

            }
        })
        binding.rcAddressList.adapter = customAdapter
        customAdapter.notifyDataSetChanged()
    }

    private fun bindViews() {
        appPreferences = SharedPrefUtils()
        binding.apply {
            toolbarTitle.imgClose.setOnClickListener { finish() }
            toolbarTitle.tvTitleProName.text = "Checkout"
            toolbarTitle.imgCart.visibility = View.GONE
            toolbarTitle.imgFav.visibility = View.GONE
            toolbarTitle.tvAddAddress.visibility = View.GONE
            btnAddAddress.setOnClickListener {
                startActivity(
                    Intent(this@CheckoutActivity, AddAddressActivity::class.java).putExtra(
                        "way",
                        "Add"
                    )
                        .putExtra("addressId", 0)
                )
            }
        }

        binding.rbWallet.setOnClickListener {
            if (binding.rbWallet.isChecked) {
                apiTotalCart(1)
            } else {
                apiTotalCart(0)
            }

        }
        // val cartTotal = intent.getSerializableExtra("cartPayment") as CartBean.DataDTO.CartTotalDTO
        //  Log.d("zxczx", Gson().toJson(cartTotal))
        // setCartPrice(cartTotal)
    }

    private fun setCartPrice(cartTotal: TotalCartBean.DataDTO) {
        binding.apply {
            tvBagTotalValue.setText(AppConstant.CurrencySymbole + cartTotal.totalAmount)
            tvDiscountValue.setText(AppConstant.CurrencySymbole + cartTotal.useWallet)
            tvDeliveryValue.setText(AppConstant.CurrencySymbole + cartTotal.delivery)
            tvTotalAmtValue.setText(AppConstant.CurrencySymbole + cartTotal.netAmount)
            tvPriceCart.setText(AppConstant.CurrencySymbole + cartTotal.totalAmount)

        }

        netamt = cartTotal.totalAmount
    }

    private fun clickOnMyView() {
        //    appPreferences = PreferenceManager(this)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.btnPlaceOrder.setOnClickListener {
            if (addressID == 0)
                Toast.makeText(this, "Please Select Address", Toast.LENGTH_SHORT).show()
            else if (paymentModeType.equals(""))
                Toast.makeText(this, "Please Select Payment Method", Toast.LENGTH_SHORT).show()
            else
             /*   apiCashfreeTocken(
                    binding.tvPriceCart.text.toString().trim(),
                    addressID,
                    paymentModeType
                )*/
            apiRazorpay(
                    addressID,
                    paymentModeType
                )
        }
    }

    fun getAddressListApi(id: Int) {
        //GeneralUtilities.showDialog(this)

        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID", "ID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString(), id.toString()
            )
        )
        val response = RetrofitService.getInstance().getAddressApi(builder.build())
        response.enqueue(object : Callback<AddressListBean> {
            override fun onResponse(
                call: Call<AddressListBean>,
                response: Response<AddressListBean>
            ) {
                try {
                    //         GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        binding.rcAddressList.visibility = View.VISIBLE
                        addressList(response.body()!!.data)
                    } else {
                        binding.rcAddressList.visibility = View.GONE
                        Toast.makeText(
                            this@CheckoutActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    //   GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@CheckoutActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<AddressListBean>, t: Throwable) {
                //  GeneralUtilities.hideDialog()
                Toast.makeText(this@CheckoutActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun apiTotalCart(id: Int) {
        GeneralUtilities.showDialog(this)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID", "Iswallet"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString(), id.toString()
            )
        )
        //   "1",id.toString()))
        val response = RetrofitService.getInstance().getGetCartTotal(builder.build())
        response.enqueue(object : Callback<TotalCartBean> {
            override fun onResponse(call: Call<TotalCartBean>, response: Response<TotalCartBean>) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        setCartPrice(response.body()!!.data)
                        netamt=response.body()!!.data.totalAmount
                    } else {

                        Toast.makeText(
                            this@CheckoutActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(this@CheckoutActivity, e.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<TotalCartBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@CheckoutActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun apiCashfreeTocken(edAmt: String, addressID: Int, paymentType: String) {
        GeneralUtilities.showDialog(this)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UserID", "AID", "Amount", "Paymode"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString(),
                addressID.toString(),
                netamt,
                paymentType
            )
        )
        val response = RetrofitService.getInstance().GetCashfreeTokenApi(builder.build())
        response.enqueue(object : Callback<CashfreeBean> {
            override fun onResponse(call: Call<CashfreeBean>, response: Response<CashfreeBean>) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        if (paymentType.equals("COD")) {
                            openDialog("Order placed successfully ")
                            /* AwesomeDialog.build(this@CheckoutActivity)
                                 .title("Congratulations")
                                 .body("Order Successful placed")
                                 .icon(R.drawable.icon_success)
                                 .onPositive("Continue") {
                                     GeneralUtilities.launchclearbackActivity(this@CheckoutActivity,DashboardActivity::class.java)

                                 }
                                 .show()*/
                        } else {
                            if (response.body()!!.paymentSessionId != null || !response.body()!!.paymentSessionId.equals(
                                    ""
                                )
                            )

                            //    instilizeRazorpayPG()
                                callCashfree(
                                    response.body()!!.paymentSessionId as String,
                                    response.body()!!.orderId as String
                                )

                        }

                        //    val jsonobj = JSONObject(response.body()!!.token)
                        // Log.d("asdasd", jsonobj.getString("payment_session_id"))
                        //  Log.d("asdasd", jsonobj.getString("order_id"))

                    } else {
                        Toast.makeText(
                            this@CheckoutActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(this@CheckoutActivity, e.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<CashfreeBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@CheckoutActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun apiRazorpay( addressID: Int, paymentType: String) {
        GeneralUtilities.showDialog(this)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UserID", "AID", "Amount", "Paymode"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString(),
                addressID.toString(),
                "449",
                paymentType
            )
        )
        //netamt
        val response = RetrofitService.getInstance().getRazorpayTokenApi(builder.build())
        response.enqueue(object : Callback<RazorpayBean> {
            override fun onResponse(call: Call<RazorpayBean>, response: Response<RazorpayBean>) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        if (paymentType.equals("COD")) {
                            openDialog("Order placed successfully ")
                            /* AwesomeDialog.build(this@CheckoutActivity)
                                 .title("Congratulations")
                                 .body("Order Successful placed")
                                 .icon(R.drawable.icon_success)
                                 .onPositive("Continue") {
                                     GeneralUtilities.launchclearbackActivity(this@CheckoutActivity,DashboardActivity::class.java)

                                 }
                                 .show()*/
                        } else {


                            instilizeRazorpayPG(response.body()!!.data)

                        }

                        //    val jsonobj = JSONObject(response.body()!!.token)
                        // Log.d("asdasd", jsonobj.getString("payment_session_id"))
                        //  Log.d("asdasd", jsonobj.getString("order_id"))

                    } else {
                        Toast.makeText(
                            this@CheckoutActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(this@CheckoutActivity, e.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<RazorpayBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@CheckoutActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun callCashfree(cfSession: String, orderId: String) {
        try {
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this)
        } catch (e: CFException) {
            e.printStackTrace()
        }

        val cfPaymentComponent = CFPaymentComponent.CFPaymentComponentBuilder()
            .add(CFPaymentComponent.CFPaymentModes.CARD)
            .add(CFPaymentComponent.CFPaymentModes.UPI)
            .add(CFPaymentComponent.CFPaymentModes.WALLET)
            .add(CFPaymentComponent.CFPaymentModes.NB)
            .build()
        val cfSession: CFSession = CFSession.CFSessionBuilder()
            .setEnvironment(CFSession.Environment.SANDBOX)
            .setPaymentSessionID(cfSession)
            .setOrderId(orderId)
            .build();

        val cfTheme = CFTheme.CFThemeBuilder()
            .setNavigationBarBackgroundColor("#006EE1")
            .setNavigationBarTextColor("#ffffff")
            .setButtonBackgroundColor("#006EE1")
            .setButtonTextColor("#ffffff")
            .setPrimaryTextColor("#000000")
            .setSecondaryTextColor("#000000")
            .build()

        val cfDropCheckoutPayment = CFDropCheckoutPayment.CFDropCheckoutPaymentBuilder()
            .setSession(cfSession)
            .setCFUIPaymentModes(cfPaymentComponent)
            .setCFNativeCheckoutUITheme(cfTheme)
            .build()

        val gatewayService = CFPaymentGatewayService.getInstance()
        gatewayService.doPayment(this, cfDropCheckoutPayment)
    }

    override fun onPaymentVerify(orderID: String?) {
        Log.d("sdsad", orderID + "")
        openDialog("Order placed successfully ")
        /* AwesomeDialog.build(this@CheckoutActivity)
                            .title("Congratulations")
                            .body("Order Successful placed")
                            .icon(R.drawable.icon_success)
                            .onPositive("Continue") {
                                GeneralUtilities.launchclearbackActivity(this@CheckoutActivity,DashboardActivity::class.java)

                            }
                            .show()*/
        //    Order13549
    }

    override fun onPaymentFailure(cfErrorResponse: CFErrorResponse?, orderID: String?) {
        Log.d("sdsad", Gson().toJson(cfErrorResponse) + "\n" + orderID + "")
        Toast.makeText(
            this@CheckoutActivity,
            cfErrorResponse?.message,
            Toast.LENGTH_SHORT
        ).show()
        //    {"code":"payment_failed","message":"Transaction pending","status":"FAILED","type":"request_failed"}
        //    Order88813
    }

    override fun onResume() {
        super.onResume()
        //    getAddressListApi(0)
    }

    fun openDialog(message: String) {
        val dialog = GeneralUtilities.openDailog(R.layout.dialog_success, this@CheckoutActivity)
        val tvSubTitle = dialog.findViewById<TextView>(R.id.tvSubTitle)
        val btnContinue = dialog.findViewById<TextView>(R.id.btnContinue)

        tvSubTitle.setText(message)

        btnContinue.setOnClickListener {
            GeneralUtilities.launchclearbackActivity(
                this@CheckoutActivity,
                DashboardActivity::class.java
            )
        }
    }


    /////////////////////////////RazorpayPaymentGateway/////////////////////////////////
    private fun instilizeRazorpayPG(responseRazor: RazorpayBean.DataDTO) {
        val activity: Activity = this
        val checkout = Checkout()
        checkout.setKeyID(responseRazor.username);//ID
        try {
            val options = JSONObject()

           /* options.put("name", data[0].name)
            options.put("description", data[0].description)
            options.put("image", AppController.domainMain + "images/logo.png")
            //  options.put("theme.color", ContextCompat.getColor(this, R.color.colorPrimary))
            options.put("theme.color","#0096DE")
            options.put("currency", data[0].currency);
            options.put("order_id", data[0].ordeR_ID);
            options.put("amount", data[0].amount)//pass amount in currency subunits

            val prefill = JSONObject()
            prefill.put("name", name)
            prefill.put("email", email)
            prefill.put("contact", mobileNo)
            prefill.put("method", data[0].discriminator)
            options.put("prefill", prefill)*/

            options.put("name", responseRazor.name)
            options.put("description", responseRazor.description)
            options.put("image", responseRazor.logo)
            //  options.put("theme.color", ContextCompat.getColor(this, R.color.colorPrimary))
            options.put("theme.color","#0096DE")
            options.put("currency", responseRazor.currency);
            options.put("order_id", responseRazor.orderId);
            options.put("amount", responseRazor.amount)//pass amount in currency subunits

            val prefill = JSONObject()
            prefill.put("name", responseRazor.name)
            prefill.put("email", responseRazor.email)
            prefill.put("contact", responseRazor.mobileNo)
            prefill.put("method", "")
            options.put("prefill", prefill)
            checkout.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            Log.d("ReadRdaJSONFeedTask", e.localizedMessage);
            e.printStackTrace()
        }



    }
    override fun onPaymentSuccess(razorpayPaymentId: String?, paymentData: PaymentData?) {
        try {
            Toast.makeText(this, "Payment Successful $razorpayPaymentId", Toast.LENGTH_LONG).show()
            Log.d("paymentSuccessful", "$razorpayPaymentId \n " + Gson().toJson(paymentData))
            val orderID = paymentData?.orderId
            openDialog("Order placed successfully ")
          /*  startActivity(
                Intent(this, PaymentGatewayStatusActivity::class.java)
                    .putExtra("orderID", orderID)
            )*/

        } catch (e: Exception) {
            Log.e("TAG", "Exception in onPaymentSuccess", e)
        }
    }

    override fun onPaymentError(errorCode: Int, response: String?, paymentData: PaymentData?) {
        try {
            if (errorCode == Checkout.NETWORK_ERROR) {
                Toast.makeText(this, "Payment failed $errorCode \n $response", Toast.LENGTH_LONG).show()
                Log.d(
                    "paymentFailed", "$errorCode \n" + " $response" + " \n" +
                            Gson().toJson(paymentData)
                )
                finish()
            } else if (errorCode == Checkout.INVALID_OPTIONS) {
                Toast.makeText(this, "Payment failed $errorCode \n $response", Toast.LENGTH_LONG).show()
                Log.d(
                    "paymentFailed", "$errorCode \n" + " $response" + " \n" +
                            Gson().toJson(paymentData)
                )
                finish()
            } else if (errorCode == Checkout.PAYMENT_CANCELED) {
                Toast.makeText(this, "Payment failed $errorCode \n $response", Toast.LENGTH_LONG).show()
                Log.d(
                    "paymentFailed", "$errorCode \n" + " $response" + " \n" +
                            Gson().toJson(paymentData)
                )
                finish()
            } else if (errorCode == Checkout.TLS_ERROR) {
                Toast.makeText(this, "Payment failed $errorCode \n $response", Toast.LENGTH_LONG)
                    .show()
                Log.d(
                    "paymentFailed", "$errorCode \n" + " $response" + " \n" +
                            Gson().toJson(paymentData)
                )
                finish()
            } else {

            }
        } catch (e: Exception) {
            Log.e("TAG", "Exception in onPaymentSuccess", e)
        }
    }

}