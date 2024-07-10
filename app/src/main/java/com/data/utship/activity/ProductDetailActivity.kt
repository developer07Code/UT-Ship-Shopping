package com.data.utship.activity


import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.adapter.BaseAdapter
import com.data.utship.adapter.ColorAdapter
import com.data.utship.adapter.SizeAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityProductDetailBinding
import com.data.utship.model.BaseResponseBean
import com.data.utship.model.ProductDetailBean
import com.data.utship.utills.RvClickListner
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.dialog.DialogsProvider
import com.data.utship.utills.extensions.rvItemAnimation
import com.data.utship.utills.extensions.setHorizontalLayout
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer


class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    lateinit var appPreferences: SharedPrefUtils

    //    lateinit var appPreferences: PreferenceManager
    lateinit var progressDialog: ProgressDialog
    private lateinit var timer: Timer
    lateinit var listBanner: kotlin.collections.ArrayList<ProductDetailBean.DataDTO.ImgsDTO>
    lateinit var listSize: kotlin.collections.ArrayList<ProductDetailBean.DataDTO.SizeDTO>
    lateinit var listColor: kotlin.collections.ArrayList<ProductDetailBean.DataDTO.ColorDTO>
    var pid = 0
    var colorID = 0
    var sizeID = 0
    var goToCart = false
    var UID = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_login)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        bindViews()
        Log.d("asds", appPreferences.getIntValue(AppConstant.UserID, 0).toString())

        if (appPreferences.getIntValue(AppConstant.UserID, 0) == 0)
            UID = "0"
        else
            UID = appPreferences.getIntValue(AppConstant.UserID, 0).toString()

        apiProductDetail(pid, "0")
    }

    private fun bindViews() {
        //    appPreferences = PreferenceManager(this)
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //   val detailResponse =intent.getSerializableExtra("productList") as DashboardBean.DataDTO.ProductsDTO
        //   Log.d("sdszd", Gson().toJson(detailResponse))


        pid = intent.getIntExtra("PID", 0)
        binding.apply {
            toolbarTitle.imgClose.setOnClickListener { finish() }
            toolbarTitle.tvTitleProName.text = "Product Detail"
            toolbarTitle.imgCart.visibility = View.GONE
            toolbarTitle.imgCart.setOnClickListener {
                startActivity(Intent(this@ProductDetailActivity, MyCartActivity::class.java))
                finish()
            }

            toolbarTitle.imgFav.setOnClickListener {
                if (appPreferences.getIntValue(AppConstant.UserID, 0) == 0) {
                    GeneralUtilities.launchclearbackActivity(
                        this@ProductDetailActivity,
                        LoginNewActivity::class.java
                    )

                } else {
                    apiAddToWishlist()
                }

            }

            btnAddToCart.setOnClickListener {
                if (appPreferences.getIntValue(AppConstant.UserID, 0) == 0) {
                    GeneralUtilities.launchclearbackActivity(
                        this@ProductDetailActivity,
                        LoginNewActivity::class.java
                    )

                } else
                /*  if (sizeID!=0&& colorID!=0)
                  apiAddToCart()
              else
                  Toast.makeText(this,"Plz Select Color and Size",Toast.LENGTH_SHORT).show()*/

                    apiAddToCart("AddToCart")
            }
            btnBuyNow.setOnClickListener {
                if (appPreferences.getIntValue(AppConstant.UserID, 0) == 0) {
                    GeneralUtilities.launchclearbackActivity(
                        this@ProductDetailActivity,
                        LoginNewActivity::class.java
                    )

                } else
                /*  if (sizeID!=0&& colorID!=0)
                  apiAddToCart()
              else
                  Toast.makeText(this,"Plz Select Color and Size",Toast.LENGTH_SHORT).show()*/

                    apiAddToCart("checkout")
            }


            btnGoToCart.setOnClickListener {
                startActivity(Intent(this@ProductDetailActivity, MyCartActivity::class.java))
           finish()
            }
            btnGotoWishlist.setOnClickListener {
                if (appPreferences.getIntValue(AppConstant.UserID, 0) == 0) {
                    GeneralUtilities.launchclearbackActivity(
                        this@ProductDetailActivity,
                        LoginNewActivity::class.java
                    )

                } else
                    startActivity(Intent(this@ProductDetailActivity, WishlistActivity::class.java))

            }
        }

    }

    private fun setData(detailResponse: ProductDetailBean.DataDTO) {
        binding.apply {
            tvProductName.setText(detailResponse.productname)
            tvProductDetail.text = Html.fromHtml(detailResponse.description)
            tvOff.setText(detailResponse.discount)
            tvActualPrice.paintFlags =
                tvActualPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG

            tvPriceApp.setText(AppConstant.CurrencySymbole + detailResponse.price)
            tvActualPrice.setText(AppConstant.CurrencySymbole + detailResponse.mrp)

            if (detailResponse.isStock.toInt() > 0) {
                if (detailResponse.isStock.toInt() <= 10) {
                    //   Utitlity.setHtmlText(tvValueStock, "<font color='red'> Hurry! Only </font> ${data[0].stock.toInt()}  <font color='red'>left</font>")
                    tvStock.text = "Hurry! Only ${detailResponse.isStock.toInt()} left"
                    tvStock.setTextColor(
                        ContextCompat.getColor(
                            this@ProductDetailActivity,
                            R.color.green
                        )
                    );
                    tvStock.visibility = View.VISIBLE
                    ivOutOfStock.visibility = View.GONE
                }
                btnAddToCart.isEnabled = true
                btnBuyNow.isEnabled = true
            }
            else {
                btnBuyNow.isEnabled = false
                btnAddToCart.isEnabled = false
                btnAddToCart.alpha = 0.4f
                btnBuyNow.alpha = 0.4f
                ivOutOfStock.visibility = View.VISIBLE
                tvStock.visibility = View.GONE
                tvStock.setTextColor(
                    ContextCompat.getColor(
                        this@ProductDetailActivity,
                        R.color.errorcolor
                    )
                );

                //  tvStock.setTextColor(ContextCompat.getColor(shopActivity, R.color.colorRed));
            }
            if (!detailResponse.rating.equals(""))
                ratingBar.rating = detailResponse.rating.toFloat()

           /* tvProductDescription.setOnClickListener {
                dailogProductDetail(detailResponse.description,"Product Detail")
            }
*/
        }

        handleServiceRecyler(detailResponse)
        //       binding.ratingBar.rating = detailResponse.rating.toFloat()

    }

    fun handleServiceRecyler(detailResponse: ProductDetailBean.DataDTO) {
        listSize = ArrayList()
        listSize.clear()
        listColor = ArrayList()
        listColor.clear()
        listBanner = ArrayList()
        listBanner.clear()
        listSize.addAll(detailResponse.size)
        listColor.addAll(detailResponse.color)
        listBanner.addAll(detailResponse.imgs)

        binding.rvBanner.rvItemAnimation()
        mAdapterBanner.addItems(listBanner)
        binding.rvBanner.setHorizontalLayout()
        binding.rvBanner.adapter = mAdapterBanner

        colorList(listColor)
        sizeList(listSize)
    }


    private val mAdapterBanner = BaseAdapter<ProductDetailBean.DataDTO.ImgsDTO>(
        R.layout.item_detail_img,
        onBind = { view, model, position ->

            Glide.with(this).load(model.img)
                .into(view.findViewById<ImageView>(R.id.view_pager_img))

        })

    fun colorList(listColor: ArrayList<ProductDetailBean.DataDTO.ColorDTO>) {
        binding.rvColors.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        binding.rvColors.isNestedScrollingEnabled = true
        binding.rvColors.setHasFixedSize(true)
        val customAdapter = ColorAdapter(listColor, this, object : RvClickListner {
            override fun clickPos(pos: Int) {
                //   addId = data[pos].addressID
                colorID = pos
            }

            override fun clickDeletePos(pos: Int) {
            }
        })
        binding.rvColors.adapter = customAdapter
    }

    fun sizeList(listSize: ArrayList<ProductDetailBean.DataDTO.SizeDTO>) {
        binding.rvSizes.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        binding.rvSizes.isNestedScrollingEnabled = true
        binding.rvSizes.setHasFixedSize(true)
        val customAdapter = SizeAdapter(listSize, this, object : RvClickListner {
            override fun clickPos(pos: Int) {
                //   addId = data[pos].addressID
                sizeID = pos
            }

            override fun clickDeletePos(pos: Int) {
            }
        })
        binding.rvSizes.adapter = customAdapter
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Int>() {

        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

    }

    fun apiAddToCart(way: String) {
        DialogsProvider.get(this@ProductDetailActivity).setLoading(true)

        //  DialogsProvider.get(this).setLoading(true)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID", "PID", "SizeID", "ColorID", "Qty"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString(),
                pid.toString(),
                sizeID.toString(),
                colorID.toString(),
                "1"
            )
        )
        val response = RetrofitService.getInstance().getAddToCartApi(builder.build())
        response.enqueue(object : Callback<BaseResponseBean> {
            override fun onResponse(
                call: Call<BaseResponseBean>,
                response: Response<BaseResponseBean>
            ) {
                try {
                    DialogsProvider.get(this@ProductDetailActivity).setLoading(false)

                    if (response.body()?.status == 1) {
                        //handleRecyler(response.body()!!.data)
                        binding.btnGoToCart.visibility = View.VISIBLE
                        binding.btnAddToCart.visibility = View.GONE
                        //  binding.btnAddToCart.setText("Go To Cart")
                        if (way.equals("checkout"))
                        startActivity(Intent(this@ProductDetailActivity, CheckoutActivity::class.java))
else{

                        }
                        Toast.makeText(
                            this@ProductDetailActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {

                        Toast.makeText(
                            this@ProductDetailActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    DialogsProvider.get(this@ProductDetailActivity).setLoading(false)

                    Toast.makeText(
                        this@ProductDetailActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<BaseResponseBean>, t: Throwable) {
                DialogsProvider.get(this@ProductDetailActivity).setLoading(false)

                Toast.makeText(this@ProductDetailActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun apiAddToWishlist() {
        DialogsProvider.get(this@ProductDetailActivity).setLoading(true)

        //  DialogsProvider.get(this).setLoading(true)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID", "PID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString(), pid.toString()
            )
        )
        val response = RetrofitService.getInstance().getAddToWishlistApi(builder.build())
        response.enqueue(object : Callback<BaseResponseBean> {
            override fun onResponse(
                call: Call<BaseResponseBean>,
                response: Response<BaseResponseBean>
            ) {
                try {
                    DialogsProvider.get(this@ProductDetailActivity).setLoading(false)

                    if (response.body()?.status == 1) {
                        //handleRecyler(response.body()!!.data)
                        Toast.makeText(
                            this@ProductDetailActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.toolbarTitle.imgFav.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@ProductDetailActivity,
                                R.drawable.ic_baseline_favoritefill_24
                            )
                        );
                    } else {
                        binding.toolbarTitle.imgFav.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@ProductDetailActivity,
                                R.drawable.ic_baseline_favorite_border_24
                            )
                        );

                        Toast.makeText(
                            this@ProductDetailActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    DialogsProvider.get(this@ProductDetailActivity).setLoading(false)

                    Toast.makeText(
                        this@ProductDetailActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<BaseResponseBean>, t: Throwable) {
                DialogsProvider.get(this@ProductDetailActivity).setLoading(false)

                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                Toast.makeText(this@ProductDetailActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun apiProductDetail(pid: Int, UID: String) {
        // progressDialog.show()
        //    GeneralUtilities.hideSoftKeyboard(this, containerid)

       GeneralUtilities.showDialog(this)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("PID", "UID"),
            arrayOf(pid.toString(), UID)
        )
        val response = RetrofitService.getInstance().getGetProductDetailApi(builder.build())
        response.enqueue(object : Callback<ProductDetailBean> {
            override fun onResponse(
                call: Call<ProductDetailBean>,
                response: Response<ProductDetailBean>
            ) {
                try {
                   GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {
                        setData(response.body()!!.data)
                        RealtedProduct(response.body()!!.data.reletedProduct)

                        if (response.body()!!.data.isWishlist == 1)
                            binding.toolbarTitle.imgFav.setImageDrawable(
                                ContextCompat.getDrawable(
                                    this@ProductDetailActivity,
                                    R.drawable.ic_baseline_favoritefill_24
                                )
                            );
                        else
                            binding.toolbarTitle.imgFav.setImageDrawable(
                                ContextCompat.getDrawable(
                                    this@ProductDetailActivity,
                                    R.drawable.ic_baseline_favorite_border_24
                                )
                            );
                    } else {

                        Toast.makeText(
                            this@ProductDetailActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                   GeneralUtilities.hideDialog()

                    Toast.makeText(
                        this@ProductDetailActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<ProductDetailBean>, t: Throwable) {
               GeneralUtilities.hideDialog()

                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                Toast.makeText(this@ProductDetailActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun RealtedProduct(reletedProduct: List<ProductDetailBean.DataDTO.ReletedProductDTO>) {
        mAdapterCategory.clearItems()
        binding.rvBestProduct.rvItemAnimation()
        mAdapterCategory.addItems(reletedProduct)
        binding.rvBestProduct.setHorizontalLayout()
        binding.rvBestProduct.adapter = mAdapterCategory
    }

    private val mAdapterCategory = BaseAdapter<ProductDetailBean.DataDTO.ReletedProductDTO>(
        R.layout.item_realtedproduct,
        onBind = { view, model, position ->
            view.findViewById<TextView>(R.id.tvTitle).text = model.name
            view.findViewById<TextView>(R.id.tvPriceApp).text =
                AppConstant.CurrencySymbole + model.price
            view.findViewById<TextView>(R.id.tvActualPrice).text =
                AppConstant.CurrencySymbole + model.mrp
            view.findViewById<TextView>(R.id.tvOff).text =
                AppConstant.CurrencySymbole + model.discount

            Glide.with(this).load(model.img)
                .into(view.findViewById<ImageView>(R.id.ivImage))
            view.findViewById<TextView>(R.id.tvActualPrice).paintFlags =
                view.findViewById<TextView>(R.id.tvActualPrice)
                    .getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG

            /*        view.findViewById<CardView>(R.id.cvProductItem).setOnClickListener {
                        startActivity(Intent(requireContext(), SubCategoryActivity::class.java)
                            .putExtra("CID",model.cid)
                            .putExtra("scid",model.scid))
                    }*/

            view.findViewById<CardView>(R.id.cvProductItem).setOnClickListener {
                startActivity(
                    Intent(this, ProductDetailActivity::class.java)
                        .putExtra("PID", model.id)
                )
                finish()
                /*   startActivity(Intent(requireContext(), ProductDetailActivity::class.java)
                       .putExtra("productList",model))*/
                /*   if(!model.rating.equals(""))
                   view.findViewById<RatingBar>(R.id.ratingBar).rating =model.rating.toFloat()
       */
            }
        })


    fun dailogProductDetail(policyRemark: String, tvTitl: String) {
        val dialog: Dialog = GeneralUtilities.openBootmSheetDailog(
            R.layout.dailog_product_detail,
            R.style.AppBottomSheetDialogTheme,
          this
        )
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val tvProductDetail = dialog.findViewById<TextView>(R.id.tvProductDetail)
        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
        ivClose.setOnClickListener { dialog.dismiss() }
        tvTitle.text = tvTitl
        tvProductDetail.text=Html.fromHtml(policyRemark)
    /*    if (tvTitl == "Policy Remark") {
            tvProductDetail.text = policyRemark
            //   tvTitle.text= policyRemark
        } else {
            tvProductDetail.text = Html.fromHtml(policyRemark)
            //    tvTitle.text=Html.fromHtml(policyRemark)
        }*/

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}