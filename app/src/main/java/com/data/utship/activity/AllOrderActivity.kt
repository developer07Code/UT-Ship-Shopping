package com.data.utship.activity


import android.app.ProgressDialog
import android.content.Intent
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
import com.data.utship.databinding.ActivityAllAddressBinding
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

class AllOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllAddressBinding
    lateinit var appPreferences: SharedPrefUtils
    lateinit var progressDialog: ProgressDialog
  //  lateinit var appPreferences: PreferenceManager
  var addressID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_login)
        binding = ActivityAllAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        bindViews()
    }

    private fun bindViews() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.apply {
            toolbarTitle.imgClose.setOnClickListener { finish() }
            toolbarTitle.tvTitleProName.text = "My Order"
            toolbarTitle.imgCart.visibility=View.GONE
            toolbarTitle.imgFav.visibility=View.GONE
        }

        getAddressListApi(0)
    }
    fun getAddressListApi(id: Int) {
         GeneralUtilities.showDialog(this)

        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString()
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
                        orderHistoryList(response.body()!!.data)
                    } else {

                        Toast.makeText(
                            this@AllOrderActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@AllOrderActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<AllOrderHistoryBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@AllOrderActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

   /* fun orderHistoryList(listAddress: MutableList<AllOrderHistoryBean.DataDTO>) {
        binding.rcAddressList.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        binding.rcAddressList.isNestedScrollingEnabled = true
        binding.rcAddressList.setHasFixedSize(true)
        val customAdapter = AddressListAdapter(listAddress,this, object : RvClickListner {
            override fun clickPos(pos: Int) {
                addressID=pos
            }

            override fun clickDeletePos(pos: Int) {
                getAddressListApi(pos)

            }
        })
        binding.rcAddressList.adapter = customAdapter
    }*/

    fun orderHistoryList(listAddress: List<AllOrderHistoryBean.DataDTO>){
        mAdapter.clearItems()
        binding.rcAddressList.rvItemAnimation()
        mAdapter.addItems(listAddress)
        binding.rcAddressList.setVerticalLayout()
        binding.rcAddressList.adapter = mAdapter
    }

    private val mAdapter =
        BaseAdapter<AllOrderHistoryBean.DataDTO>(R.layout.item_orderlist,onBind = { view, model, position ->

            view.findViewById<TextView>(R.id.tvTitle).text = model.name
            view.findViewById<TextView>(R.id.tvDate).text = model.adddate
            view.findViewById<TextView>(R.id.tvStatus).text ="Order Status : "+ model.status
            view.findViewById<TextView>(R.id.tvPaymentMode).text = model.paymentMode
            Glide.with(this).load(model.img)
                .into(view.findViewById<ImageView>(R.id.ivImage))

            view.findViewById<CardView>(com.data.utship.R.id.cvProductItem).setOnClickListener {
                startActivity(
                    Intent(this, ProductDetailActivity::class.java)
                        .putExtra("PID",model.id))

            /*   startActivity(Intent(requireContext(), ProductDetailActivity::class.java)
                   .putExtra("productList",model))*/
        }
    })

}