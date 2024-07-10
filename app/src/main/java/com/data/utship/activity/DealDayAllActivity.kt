package com.data.utship.activity


import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.adapter.BaseAdapter
import com.data.utship.databinding.ActivityDealdaysAllBinding
import com.data.utship.model.DashboardBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.rvItemAnimation
import com.data.utship.utills.extensions.setGridLayout
import com.data.utship.utills.utility.AppConstant
import com.google.gson.Gson


class DealDayAllActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDealdaysAllBinding
    lateinit var appPreferences: SharedPrefUtils
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_login)
        binding = ActivityDealdaysAllBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        bindViews()
       val allDeal= intent.getSerializableExtra("responseAll") as ArrayList<DashboardBean.DataDTO.DealofDayDTO>

        Log.d("sczcz",Gson().toJson(allDeal))
        allDealsDays(allDeal)
    }


    private fun bindViews() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.toolbarTitle.imgClose.setOnClickListener { finish() }
        binding.toolbarTitle.tvTitleProName.text = "All Product"
        binding.toolbarTitle.imgCart.visibility=View.GONE
        binding.toolbarTitle.imgFav.visibility=View.GONE

    }

  fun allDealsDays(allDeal: ArrayList<DashboardBean.DataDTO.DealofDayDTO>) {
      mAdapterDealDay.clearItems()
      binding.rvDealsOfDays.rvItemAnimation()
      mAdapterDealDay.addItems(allDeal)
      binding.rvDealsOfDays.setGridLayout(2)
      binding.rvDealsOfDays.adapter = mAdapterDealDay
  }

    private val mAdapterDealDay =BaseAdapter<DashboardBean.DataDTO.DealofDayDTO>(R.layout.item_deals_all,onBind = { view, model, position ->

        //      view.findViewById<TextView>(R.id.tvActualPrice).setPaintFlags(view.findViewById<TextView>(R.id.tvActualPrice).getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

        view.findViewById<TextView>(R.id.tvTitle).text = model.name
        view.findViewById<TextView>(R.id.tvPriceApp).text =AppConstant.CurrencySymbole+ model.price
        view.findViewById<TextView>(R.id.tvActualPrice).text = AppConstant.CurrencySymbole+model.mrp
        //    view.findViewById<TextView>(R.id.tvOff).text = AppConstant.CurrencySymbole+model.per
         //  view.findViewById<RatingBar>(R.id.tvSubTitle).rating = model.rating.toFloat()

        Glide.with(this).load(model.img)
            .into(view.findViewById<ImageView>(R.id.ivImage))
        view.findViewById<TextView>(R.id.tvActualPrice).paintFlags =
            view.findViewById<TextView>(R.id.tvActualPrice).getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG

        /*        view.findViewById<CardView>(R.id.cvProductItem).setOnClickListener {
                    startActivity(Intent(requireContext(), SubCategoryActivity::class.java)
                        .putExtra("CID",model.cid)
                        .putExtra("scid",model.scid))
                }*/

        view.findViewById<CardView>(R.id.cvProductItem).setOnClickListener {
            startActivity(Intent(this, ProductDetailActivity::class.java)
                .putExtra("PID",model.id))

            /*   startActivity(Intent(requireContext(), ProductDetailActivity::class.java)
                   .putExtra("productList",model))*/
        }
        if(!model.rating.equals(""))
        view.findViewById<RatingBar>(R.id.ratingBar).rating =model.rating.toFloat()
    })

}