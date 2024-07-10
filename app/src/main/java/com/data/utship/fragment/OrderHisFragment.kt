package com.data.utship.fragment


import android.app.AlertDialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.data.utship.R

import com.data.utship.adapter.BaseAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.FragmentOrderHisBinding
import com.data.utship.model.AllOrderHistoryBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.rvItemAnimation
import com.data.utship.utills.extensions.setVerticalLayout
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderHisFragment : Fragment() {
    lateinit var alertDialog: AlertDialog
    private var _binding: FragmentOrderHisBinding? = null
    lateinit var progressDialog: ProgressDialog
    lateinit var appPreferences: SharedPrefUtils
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOrderHisBinding.inflate(inflater, container, false)
        val root: View = binding.root
     //   startActivity(Intent(requireContext(), AllOrderActivity::class.java))
   //    activity?.finish()
      //  GeneralUtilities.launchclearbackActivity(activity as AppCompatActivity?, AllOrderActivity::class.java)

          bindViews()
       apiOrderHistory()

        return root
    }

    private fun bindViews() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun apiOrderHistory() {
   GeneralUtilities.showDialog(requireActivity())

        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID,0).toString()
            )
        )
        val response = RetrofitService.getInstance().getMyOrderApi(builder.build())
        response.enqueue(object : Callback<AllOrderHistoryBean> {
            override fun onResponse(
                call: Call<AllOrderHistoryBean>,
                response: Response<AllOrderHistoryBean>
            ) {
                try {
                     GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {


                        handleCatRecyler(response.body()!!.data)

                    } else {

                        Toast.makeText(
                            requireContext(),
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                     GeneralUtilities.hideDialog()

                    Toast.makeText(
                        requireContext(),
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<AllOrderHistoryBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                 GeneralUtilities.hideDialog()

                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun handleCatRecyler(data: List<AllOrderHistoryBean.DataDTO>) {
        binding.rvOrder.rvItemAnimation()
        mAdapter.addItems(data)
        binding.rvOrder.setVerticalLayout()
        binding.rvOrder.adapter = mAdapter
    }

    private val mAdapter =
        BaseAdapter<AllOrderHistoryBean.DataDTO>(com.data.utship.R.layout.item_orderlist,onBind = { view, model, position ->

            //      view.findViewById<TextView>(R.id.tvActualPrice).setPaintFlags(view.findViewById<TextView>(R.id.tvActualPrice).getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

            /*
                view.findViewById<TextView>(R.id.tvPriceApp).text =AppConstant.CurrencySymbole+ model.price
                view.findViewById<TextView>(R.id.tvActualPrice).text = AppConstant.CurrencySymbole+model.mrp
                view.findViewById<TextView>(R.id.tvOff).text = AppConstant.CurrencySymbole+model.discount
                //  view.findViewById<RatingBar>(R.id.ratingBar).rating =model.rating
               */
            view.findViewById<TextView>(R.id.tvTitle).text = model.name
            view.findViewById<TextView>(R.id.tvDate).text = model.adddate
            view.findViewById<TextView>(R.id.tvStatus).text ="Order Status : "+ model.status
            view.findViewById<TextView>(R.id.tvPaymentMode).text = model.paymentMode
            Glide.with(this).load(model.img)
                .into(view.findViewById<ImageView>(R.id.ivImage))

            view.findViewById<CardView>(com.data.utship.R.id.cvProductItem).setOnClickListener {
                orderDetailDialog(model)

                /*   startActivity(Intent(requireContext(), ProductDetailActivity::class.java)
                       .putExtra("productList",model))*/
            }
        })

    fun orderDetailDialog(model: AllOrderHistoryBean.DataDTO) {

        val dialogss = AlertDialog.Builder(context, R.style.DialogFullscreen)
        val dialog: View = layoutInflater.inflate(R.layout.dailog_order_detail, null)
        dialogss.setView(dialog)

        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val ivProductImg = dialog.findViewById<ImageView>(R.id.ivProductImg)
        val tvProductName = dialog.findViewById<TextView>(R.id.tvProductName)
     //   val tvPaymentMode = dialog.findViewById<TextView>(R.id.tvPaymentMode)
        val tvOrderStatus = dialog.findViewById<TextView>(R.id.tvOrderStatus)

        val tvPriceApp = dialog.findViewById<TextView>(R.id.tvPriceApp)
        val tvActualPrice = dialog.findViewById<TextView>(R.id.tvActualPrice)
        val tvQty = dialog.findViewById<TextView>(R.id.tvQty)
        val tvSize = dialog.findViewById<TextView>(R.id.tvSize)
        val tvColor = dialog.findViewById<TextView>(R.id.tvColor)

        val tvAddName = dialog.findViewById<TextView>(R.id.tvAddName)
        val tvAddType = dialog.findViewById<TextView>(R.id.tvAddType)
        val tvCityName = dialog.findViewById<TextView>(R.id.tvCityName)

        val tvPaymentMod = dialog.findViewById<TextView>(R.id.tvPaymentMod)
        val tvTotalAmtValue = dialog.findViewById<TextView>(R.id.tvTotalAmtValue)
        alertDialog = dialogss.create()
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        Glide.with(this).load(model.img).into(ivProductImg)

        tvProductName.setText(model.name)
        tvPaymentMod.setText("Payment Mode : "+model.paymentMode)
        tvOrderStatus.setText(model.status)

        tvPriceApp.setText(AppConstant.CurrencySymbole+model.price)
      //  tvActualPrice.setText(AppConstant.CurrencySymbole+model.mrp)
        tvQty.setText("( Qty : "+model.qty+")")
        tvSize.setText(model.size)
        tvColor.background.setColorFilter(Color.parseColor(model.color), PorterDuff.Mode.SRC_IN)
        tvTotalAmtValue.setText(AppConstant.CurrencySymbole+model.netAmount)
        tvAddName.setText(model.address)
        tvAddType.setText(model.addressType)
        tvCityName.setText(model.stateName+"\n"+model.cityName+"\n"+model.pincode)

        ivClose.setOnClickListener { alertDialog.dismiss() }
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()
     //   (context as DashboardActivity).changeTabIndex(R.id.navigation_notifications)
    }
}