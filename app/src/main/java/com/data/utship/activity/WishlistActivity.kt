package com.data.utship.activity


import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.adapter.BaseAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityWishlistBinding
import com.data.utship.model.WhislistBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.rvItemAnimation
import com.data.utship.utills.extensions.setVerticalLayout
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class WishlistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWishlistBinding
    lateinit var listWishlist: kotlin.collections.ArrayList<WhislistBean.DataDTO>
    //  lateinit var appPreferences: PreferenceManager
    lateinit var progressDialog: ProgressDialog
    lateinit var appPreferences: SharedPrefUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_login)
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        bindViews()
        apiMyWishlist(0)
    }

    private val mAdapter =
        BaseAdapter<WhislistBean.DataDTO>(
            R.layout.item_wishlist_list,
            onBind = { view, model, position ->
                view.findViewById<TextView>(R.id.tvTitle).text = model.name
                view.findViewById<TextView>(R.id.tvPriceApp).text =
                    AppConstant.CurrencySymbole + model.price
                view.findViewById<TextView>(R.id.tvActualPrice).text =
                    AppConstant.CurrencySymbole + model.mrp
                //   view.findViewById<TextView>(R.id.tvValue).text = model.q
                /*  view.findViewById<TextView>(R.id.tvColor).text =
                      model.color + " (" + model.size + ")"*/
                Glide.with(this).load(model.img).into(view.findViewById<ImageView>(R.id.ivImage))
                view.findViewById<TextView>(R.id.tvActualPrice).setPaintFlags(
                    view.findViewById<TextView>(R.id.tvActualPrice)
                        .getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG
                )
                view.findViewById<ImageView>(R.id.ivDelete).setOnClickListener {
                    apiMyWishlist(model.id)
                }
                 view.findViewById<CardView>(R.id.CardImage).setOnClickListener {
                     startActivity(
                         Intent(this, ProductDetailActivity::class.java)
                         .putExtra("PID",model.pid))
                }

            })

    private fun bindViews() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.apply {
            toolbarTitle.imgClose.setOnClickListener { finish() }
            toolbarTitle.tvTitleProName.text = "My Wishlist"
            toolbarTitle.imgCart.visibility = View.VISIBLE
            toolbarTitle.imgFav.visibility = View.GONE
        }


        /*   getMenus().forEachIndexed { i, details ->
               if (i > 0) {
                   mAdapter.addItem(details)
               }
           }*/

    }

    fun apiMyWishlist(id: Int) {


        GeneralUtilities.showDialog(this)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID", "ID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString(), id.toString()
            )
        )
        val response = RetrofitService.getInstance().getWishlistApi(builder.build())
        response.enqueue(object : Callback<WhislistBean> {
            override fun onResponse(call: Call<WhislistBean>, response: Response<WhislistBean>) {
                try {
                    GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {

                        listWishlist= ArrayList()
                        listWishlist.clear()
                        listWishlist.addAll(response.body()!!.data)

                        mAdapter.clearItems()
                        binding.recylerList.rvItemAnimation()
                        mAdapter.addItems(listWishlist)
                        binding.recylerList.setVerticalLayout()
                        binding.recylerList.adapter = mAdapter
                        mAdapter.notifyDataSetChanged()
                    } else {
                        binding.recylerList.visibility=View.INVISIBLE
                        Toast.makeText(
                            this@WishlistActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()

                    Toast.makeText(this@WishlistActivity, e.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<WhislistBean>, t: Throwable) {
                GeneralUtilities.hideDialog()

                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                Toast.makeText(this@WishlistActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}