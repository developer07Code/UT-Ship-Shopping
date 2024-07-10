package com.data.utship.activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.adapter.BaseAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityWalletBinding
import com.data.utship.model.BaseResponseBean
import com.data.utship.model.GetBalanceBean
import com.data.utship.model.ListWithdrawalBean
import com.data.utship.model.WalletHistoryBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.rvItemAnimation
import com.data.utship.utills.extensions.setVerticalLayout
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WalletActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWalletBinding
    lateinit var appPreferences: SharedPrefUtils
    lateinit var progressDialog: ProgressDialog
    var isShow=true
    private lateinit var alertDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   //     setContentView(R.layout.activity_wallet)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        bindViews()
        apiGetBalance()
    }
    private fun bindViews() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.apply {
            toolbarTitle.imgClose.setOnClickListener { finish() }

            toolbarTitle.tvTitleProName.text = "My Wallet"
            toolbarTitle.imgCart.visibility= View.GONE
            toolbarTitle.imgFav.visibility= View.GONE

            linearWithdrawal.setOnClickListener { WithdrawalDialog()}
            linearWalletHis.setOnClickListener { WithdrawalHistoryDialog("Wallet") }
            linearWithdrawalHis.setOnClickListener { WithdrawalHistoryDialog("Withdrawal") }
        }
    }

    fun apiGetBalance() {
        // GeneralUtilities.showDialog(requireContext())
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString(),
            )
        )
        val response = RetrofitService.getInstance().getGetBalance(builder.build())
        response.enqueue(object : Callback<GetBalanceBean> {
            override fun onResponse(call: Call<GetBalanceBean>, response: Response<GetBalanceBean>) {
                try {
                    //   GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {
                      binding.tvWalletBal.text=AppConstant.CurrencySymbole+response.body()!!.data.balance

                    }else{
                        Toast.makeText(this@WalletActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
                catch (e:Exception){
                    // GeneralUtilities.hideDialog()
                    Toast.makeText(this@WalletActivity,e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                // DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<GetBalanceBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                // GeneralUtilities.hideDialog()
                Toast.makeText(this@WalletActivity ,t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun WithdrawalHistoryDialog(way: String) {
        val dialog = AlertDialog.Builder(this, R.style.DialogFullscreen)
        val dialogView: View = layoutInflater.inflate(R.layout.dailog_withdrawal_his, null)
        dialog.setView(dialogView)

        val ivClose = dialogView.findViewById<ImageView>(R.id.ivClose)
        val tvTitle = dialogView.findViewById<TextView>(R.id.tvTitle)
        val rcWithdrawList = dialogView.findViewById<RecyclerView>(R.id.rcWithdrawList)
        alertDialog = dialog.create()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        if (way.equals("Wallet")){
            tvTitle.setText("Wallet History")
            getSubmitWalletHisApi(rcWithdrawList)
        }

        else{
            tvTitle.setText("Withdrawal History")
            getSubmitWithdraHisApi(rcWithdrawList)
        }


        ivClose.setOnClickListener { alertDialog.dismiss() }
        alertDialog.show()

    }

    fun WithdrawalDialog() {
        val dialog: Dialog = GeneralUtilities.openBootmSheetDailog(
            R.layout.dailog_withdrawal,
            R.style.AppBottomSheetDialogTheme,
            this
        )

            val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
            val btnSubmit = dialog.findViewById<TextView>(R.id.btnSubmit)
            val editUPIID = dialog.findViewById<TextView>(R.id.editUPIID)
            val editAmt = dialog.findViewById<TextView>(R.id.editAmt)


            ivClose.setOnClickListener { dialog.dismiss() }
            btnSubmit.setOnClickListener {
                if (editAmt.text.toString().equals("")) {
                    editAmt.error = "Enter Amount"
                    editAmt.requestFocus()
                } else if (editUPIID.text.toString().equals("")) {
                    editUPIID.error = "Enter UPI ID"
                    editUPIID.requestFocus()
                }else{
                    getSubmitWithdrawalApi(editAmt.text.toString(),editUPIID.text.toString())
                    dialog.dismiss()
                }

            }
    }

    fun getSubmitWithdrawalApi(amt: String, upiID: String) {
         GeneralUtilities.showDialog(this)

        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID", "Amount","UPIID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString(),amt,upiID
            )
        )
        val response = RetrofitService.getInstance().getSendWithdrawalApi(builder.build())
        response.enqueue(object : Callback<BaseResponseBean> {
            override fun onResponse(
                call: Call<BaseResponseBean>,
                response: Response<BaseResponseBean>
            ) {
                try {
                     GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {
                        Toast.makeText(
                            this@WalletActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {

                        Toast.makeText(
                            this@WalletActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                     GeneralUtilities.hideDialog()

                    Toast.makeText(
                        this@WalletActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<BaseResponseBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                 GeneralUtilities.hideDialog()

                Toast.makeText(this@WalletActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun getSubmitWithdraHisApi(rcWithdrawList: RecyclerView) {
        GeneralUtilities.showDialog(this)


        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString()
            )
        )
        val response = RetrofitService.getInstance().getList_WithdrawalApi(builder.build())
        response.enqueue(object : Callback<ListWithdrawalBean> {
            override fun onResponse(
                call: Call<ListWithdrawalBean>,
                response: Response<ListWithdrawalBean>
            ) {
                try {
                     GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {
                        mAdapter.clearItems()
                        rcWithdrawList.rvItemAnimation()
                        mAdapter.addItems(response.body()!!.data)
                        rcWithdrawList.setVerticalLayout()
                        rcWithdrawList.adapter = mAdapter
                    } else {

                        Toast.makeText(
                            this@WalletActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                     GeneralUtilities.hideDialog()

                    Toast.makeText(
                        this@WalletActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<ListWithdrawalBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                 GeneralUtilities.hideDialog()

                Toast.makeText(this@WalletActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun getSubmitWalletHisApi(rcWithdrawList: RecyclerView) {
        GeneralUtilities.showDialog(this)

        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString()
            )
        )
        val response = RetrofitService.getInstance().getListWalletHistoryApi(builder.build())
        response.enqueue(object : Callback<WalletHistoryBean> {
            override fun onResponse(
                call: Call<WalletHistoryBean>,
                response: Response<WalletHistoryBean>
            ) {
                try {
                     GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {
                        mAdapterr.clearItems()
                        rcWithdrawList.rvItemAnimation()
                        mAdapterr.addItems(response.body()!!.data)
                        rcWithdrawList.setVerticalLayout()
                        rcWithdrawList.adapter = mAdapterr
                    } else {

                        Toast.makeText(
                            this@WalletActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                     GeneralUtilities.hideDialog()

                    Toast.makeText(
                        this@WalletActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<WalletHistoryBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                 GeneralUtilities.hideDialog()

                Toast.makeText(this@WalletActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private val mAdapter =
        BaseAdapter<ListWithdrawalBean.DataDTO>(R.layout.item_withdrawa_list,onBind = { view, model, position ->
            view.findViewById<TextView>(R.id.tvStatusValue).text = model.status
            view.findViewById<TextView>(R.id.txt_Main).text = model.status
            view.findViewById<TextView>(R.id.tvOrderIdValue).text = model.upiid
            view.findViewById<TextView>(R.id.tvDate).text = model.date
            view.findViewById<TextView>(R.id.tvNarration).text = model.transactionID

            if (model.status.equals("Success"))
                view.findViewById<TextView>(R.id.tvStatusValue).setTextColor(getResources().getColor(R.color.green))

            else  if (model.status.equals("Pending"))
                view.findViewById<TextView>(R.id.tvStatusValue).setTextColor(getResources().getColor(R.color.color_yellow))

              else
                view.findViewById<TextView>(R.id.tvStatusValue).setTextColor(getResources().getColor(R.color.red))

            view.findViewById<ImageView>(R.id.ivEye).setOnClickListener {
                if (isShow) {
                    isShow=false
                    view.findViewById<ImageView>(R.id.ivEye).setImageResource(R.drawable.ic_eye_line)
                    view.findViewById<ImageView>(R.id.ivScreenShort).visibility=View.VISIBLE
                    view.findViewById<TextView>(R.id.tvScreen).visibility = View.VISIBLE
                } else {
                    isShow=true
                    view.findViewById<ImageView>(R.id.ivEye).setImageResource(R.drawable.ic_eye_off_line)
                    view.findViewById<ImageView>(R.id.ivScreenShort).visibility=View.GONE
                    view.findViewById<TextView>(R.id.tvScreen).visibility = View.GONE
                }
            }
            view.findViewById<TextView>(R.id.tvAmount).text = AppConstant.CurrencySymbole+model.amount

            Glide.with(this).load(model.screenshot).into(view.findViewById<ImageView>(R.id.ivScreenShort))

        })

    private val mAdapterr =
        BaseAdapter<WalletHistoryBean.DataDTO>(R.layout.item_wallet_list,onBind = { view, model, position ->

            view.findViewById<TextView>(R.id.txt_Main).text = model.factor
            view.findViewById<TextView>(R.id.tvOrderIdValue).text =AppConstant.CurrencySymbole+ model.amount
            view.findViewById<TextView>(R.id.tvDate).text = model.addDate
            view.findViewById<TextView>(R.id.tvNarration).text = model.narration

            view.findViewById<TextView>(R.id.tvAmount).text = AppConstant.CurrencySymbole+model.balance
            if (model.factor.equals("Cr")) {

                view.findViewById<TextView>(R.id.tvOrderIdValue).setTextColor(getResources().getColor(R.color.green))
                view.findViewById<TextView>(R.id.tvOrderIdValue).setText("₹ +" + AppConstant.CurrencySymbole+ model.amount)
                view.findViewById<TextView>(R.id.txt_Main).setBackgroundTintList(
                    ContextCompat.getColorStateList(
                        this,
                        R.color.green
                    )
                )
            } else if (model.factor.equals("Dr")) {
                view.findViewById<TextView>(R.id.tvOrderIdValue).setTextColor(getResources().getColor(R.color.red))
                view.findViewById<TextView>(R.id.tvOrderIdValue).setText("₹ " + AppConstant.CurrencySymbole+ model.amount)
                view.findViewById<TextView>(R.id.txt_Main).setBackgroundTintList(
                    ContextCompat.getColorStateList(
                        this,
                        R.color.red
                    )
                )
            } else {
            }
        })
}