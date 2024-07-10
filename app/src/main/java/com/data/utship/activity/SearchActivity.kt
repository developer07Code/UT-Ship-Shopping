package com.data.utship.activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.adapter.BaseAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivitySearchBinding
import com.data.utship.model.SearchProductBean
import com.data.utship.model.SearchSuggeationListBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.rvItemAnimation
import com.data.utship.utills.extensions.setGridLayout
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import com.google.gson.Gson
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    lateinit var appPreferences: SharedPrefUtils
    lateinit var progressDialog: ProgressDialog
    var searchText=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_search)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        clickOnMyView()
        getSearchSuggationApi()
    }

    private fun clickOnMyView() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.apply {
            toolbarTitle.imgClose.setOnClickListener { finish() }

            toolbarTitle.tvTitleProName.text = "Search"
            toolbarTitle.imgCart.visibility = View.GONE
            toolbarTitle.imgFav.visibility = View.GONE

            //    GeneralUtilities.launchActivity(this, SplashActivity::class.java)
            //   GeneralUtilities.showErrorSnackBar(this, binding.containerid, "Enter Your Username or Email")
            //    GeneralUtilities.showNormalSnackBar(this, binding.containerid, "Login Successfully")
            //  GeneralUtilities.hideSoftKeyboard(this, binding.containerid)
        }
    }


    fun getProductSearchApi(nameText: String) {
   GeneralUtilities.showDialog(this)

        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("search"),
            arrayOf(
                nameText)
        )
        val response = RetrofitService.getInstance().GetProductBySearchApi(builder.build())
        response.enqueue(object : Callback<SearchProductBean> {
            override fun onResponse(
                call: Call<SearchProductBean>,
                response: Response<SearchProductBean>
            ) {
                try {

                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        searchProductList(response.body()!!.data)
                    } else {

                        Toast.makeText(
                            this@SearchActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {

                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@SearchActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<SearchProductBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@SearchActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun getSearchSuggationApi() {
        GeneralUtilities.showDialog(this)
        val response = RetrofitService.getInstance().getSuggationListApi()
        response.enqueue(object : Callback<SearchSuggeationListBean> {
            override fun onResponse(
                call: Call<SearchSuggeationListBean>,
                response: Response<SearchSuggeationListBean>
            ) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        setSearchData(response.body()!!.data)
                    } else {

                        Toast.makeText(
                            this@SearchActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@SearchActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<SearchSuggeationListBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@SearchActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
    private fun setSearchData(data: MutableList<SearchSuggeationListBean.DataDTO>) {
        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).getName()
        }
        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@SearchActivity,
            android.R.layout.simple_list_item_1,
            state
        )
        binding.autoSearch.setAdapter(adapte1)
        binding.autoSearch.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("xcvxcvc", Gson().toJson(data.get(position).name))
            for (i in data.indices) {
                if (data.get(i).name.equals(parent.getItemAtPosition(position))) {
                    Log.d(
                        "StateID",
                        "" + data.get(i).name
                    )
                    searchText = data.get(i).name.toString()
                    binding.autoSearch.setText(data.get(i).name)
                    val input = searchText.replace(" ", "");
                  //  searchText="Urbane Latest Men Shirts".trim()
                    getProductSearchApi(input)
                }
            }
        })
    }


    fun searchProductList(data: List<SearchProductBean.DataDTO>) {
        mAdapter.clearItems()
        binding.rcProductList.rvItemAnimation()
        mAdapter.addItems(data)
        binding.rcProductList.setGridLayout(2)
        binding.rcProductList.adapter = mAdapter
    }
    private val mAdapter =BaseAdapter<SearchProductBean.DataDTO>(R.layout.item_product_list,onBind = { view, model, position ->

        //      view.findViewById<TextView>(R.id.tvActualPrice).setPaintFlags(view.findViewById<TextView>(R.id.tvActualPrice).getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

        view.findViewById<TextView>(R.id.tvTitle).text = model.name
        view.findViewById<TextView>(R.id.tvPriceApp).text = AppConstant.CurrencySymbole+ model.price
        view.findViewById<TextView>(R.id.tvActualPrice).text = AppConstant.CurrencySymbole+model.mrp
        view.findViewById<TextView>(R.id.tvOff).text = AppConstant.CurrencySymbole+model.discount

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
            /*   if(!model.rating.equals(""))
               view.findViewById<RatingBar>(R.id.ratingBar).rating =model.rating.toFloat()
   */
        }
    })

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}