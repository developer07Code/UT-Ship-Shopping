package com.data.utship.activity


import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.adapter.BaseAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityPrdocutBinding
import com.data.utship.databinding.ActivitySubcatBinding
import com.data.utship.databinding.ActivitySubtosubcatBinding
import com.data.utship.model.GetBalanceBean
import com.data.utship.model.ProductBean
import com.data.utship.model.SubCategoryBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.rvItemAnimation
import com.data.utship.utills.extensions.setGridLayout
import com.data.utship.utills.extensions.setHorizontalLayout
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrdocutBinding
  //  lateinit var appPreferences: PreferenceManager
    lateinit var progressDialog: ProgressDialog
    lateinit var appPreferences: SharedPrefUtils
    lateinit var listSubCat: kotlin.collections.ArrayList<SubCategoryBean.DataDTO>
    lateinit var listSubToSubCat: kotlin.collections.ArrayList<SubCategoryBean.DataDTO>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_login)
        binding = ActivityPrdocutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        bindViews()

        if (appPreferences.getIntValue(AppConstant.UserID,0) != 0){

            apiGetTotalCount()
        }

    }

    private fun bindViews() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.apply {
            toolbarTitle.imgClose.setOnClickListener { finish() }
            toolbarTitle.tvTitleProName.text = "Product"
            toolbarTitle.imgCart.visibility = View.GONE
            toolbarTitle.imgFav.visibility = View.GONE
        }
//sid=intent.getIntExtra("scid",0)

        Log.d("zczxc",intent.getIntExtra("subToSubCatID",0).toString())
        Log.d("zczxc", intent.getIntExtra("CID",0).toString())
        Log.d("zczxc", intent.getIntExtra("subCatID",0).toString())

        apiProduct(intent.getIntExtra("subCatID",0),intent.getIntExtra("CID",0),intent.getIntExtra("subToSubCatID",0))

    }
    private val mAdapter =BaseAdapter<ProductBean.DataDTO>(R.layout.item_product_list,onBind = { view, model, position ->

        //      view.findViewById<TextView>(R.id.tvActualPrice).setPaintFlags(view.findViewById<TextView>(R.id.tvActualPrice).getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

        view.findViewById<TextView>(R.id.tvTitle).text = model.name
        /* view.findViewById<TextView>(R.id.tvPriceApp).text =AppConstant.CurrencySymbole+ model.price
         view.findViewById<TextView>(R.id.tvActualPrice).text = AppConstant.CurrencySymbole+model.mrp
         view.findViewById<TextView>(R.id.tvOff).text = AppConstant.CurrencySymbole+model.discount*/
        //  view.findViewById<RatingBar>(R.id.ratingBar).rating =model.rating
        Glide.with(this).load(model.img)
            .into(view.findViewById<ImageView>(R.id.ivImage))
        view.findViewById<TextView>(R.id.tvActualPrice).paintFlags =
            view.findViewById<TextView>(R.id.tvActualPrice).getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG


        view.findViewById<CardView>(R.id.cvProductItem).setOnClickListener {
            startActivity(Intent(this, ProductDetailActivity::class.java)
                .putExtra("PID",model.id))

            /*   startActivity(Intent(requireContext(), ProductDetailActivity::class.java)
                   .putExtra("productList",model))*/
        }
    })

    fun apiProduct(SCID: Int,CID: Int,subToSubCatID: Int) {
        GeneralUtilities.showDialog(this)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("SCID","CID","SSCID"),
            arrayOf(
                SCID.toString(),
                CID.toString(),
                subToSubCatID.toString()))
        val response = RetrofitService.getInstance().getGetProductApi(builder.build())
        response.enqueue(object : Callback<ProductBean> {
            override fun onResponse(call: Call<ProductBean>, response: Response<ProductBean>) {
                try {
                    GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {

                       /* listSubCat= ArrayList()
                        listSubCat.clear()
                        listSubCat.addAll(response.body()!!.data)
*/
                        binding.rvProduct.rvItemAnimation()
                        mAdapter.addItems(response.body()!!.data)
                        binding.rvProduct.setGridLayout(2)
                        binding.rvProduct.adapter = mAdapter

                    }else{
                        Toast.makeText(this@ProductActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    GeneralUtilities.hideDialog()

                    Toast.makeText(this@ProductActivity,e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<ProductBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@ProductActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun apiGetTotalCount() {
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
                        if (response.body()!!.data.totalCart.toString().equals("0")
                        )
                        //    binding.toolbarTitle.tvCount.visibility = View.GONE

                        else
                        //    binding.toolbarTitle.tvCount.text =response.body()!!.data.totalCart.toString()

                            Toast.makeText(this@ProductActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(this@ProductActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    // GeneralUtilities.hideDialog()
                    Toast.makeText(this@ProductActivity,e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<GetBalanceBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                // GeneralUtilities.hideDialog()
                Toast.makeText(this@ProductActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}