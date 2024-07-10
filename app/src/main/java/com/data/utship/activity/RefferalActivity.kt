package com.data.utship.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.adapter.BaseAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityRefferalBinding
import com.data.utship.model.ListDirectBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.rvItemAnimation
import com.data.utship.utills.extensions.setVerticalLayout
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RefferalActivity : AppCompatActivity() {
    lateinit var progressDialog: ProgressDialog
    private lateinit var binding: ActivityRefferalBinding
    lateinit var appPreferences: SharedPrefUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_refferal)
        binding = ActivityRefferalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        clickOnMyView()
        getReferralHistoryApi()
    }
    private fun clickOnMyView() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.apply {
            toolbarTitle.imgClose.setOnClickListener { finish() }
            toolbarTitle.tvTitleProName.text = "Referral"
            toolbarTitle.imgCart.visibility = View.GONE
            toolbarTitle.imgFav.visibility = View.GONE
            toolbarTitle.tvAddAddress.visibility = View.GONE

        }

        binding.tvShare.setOnClickListener {
          GeneralUtilities.shareContent(this,intent.getStringExtra("mobileNo"))
                  }
    }

    fun getReferralHistoryApi() {
         GeneralUtilities.showDialog(this)

        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString())
        )
        val response = RetrofitService.getInstance().GetList_DirectApi(builder.build())
        response.enqueue(object : Callback<ListDirectBean> {
            override fun onResponse(
                call: Call<ListDirectBean>,
                response: Response<ListDirectBean>
            ) {
                try {

                     GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        searchProductList(response.body()!!.data)
                    } else {

                        Toast.makeText(
                            this@RefferalActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {

                     GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@RefferalActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<ListDirectBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@RefferalActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun searchProductList(data: List<ListDirectBean.DataDTO>) {
        mAdapter.clearItems()
        binding.rcRefferalList.rvItemAnimation()
        mAdapter.addItems(data)
        binding.rcRefferalList.setVerticalLayout()
        binding.rcRefferalList.adapter = mAdapter
    }
    private val mAdapter =
        BaseAdapter<ListDirectBean.DataDTO>(R.layout.item_refferal_list,onBind = { view, model, position ->

        view.findViewById<TextView>(R.id.tvName).text = model.introName
        view.findViewById<TextView>(R.id.tvDate).text = model.introDOJ

        Glide.with(this).load(model.introProfile)
            .into(view.findViewById<ImageView>(R.id.ivProfile))

    })

}