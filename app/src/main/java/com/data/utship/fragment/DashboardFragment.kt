package com.data.utship.fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.activity.*
import com.data.utship.adapter.BaseAdapter
import com.data.utship.adapter.FindStyleAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.FragmentDashboardBinding
import com.data.utship.model.DashboardBean
import com.data.utship.utills.Constants
import com.data.utship.utills.RvClickListner
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.*
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

//https://themes.pixelstrap.com/multikart-app/
class DashboardFragment : Fragment() {
    //  lateinit var appPreferences: PreferenceManager
    private var mMenuCart: View? = null
    lateinit var listCat: kotlin.collections.ArrayList<DashboardBean.DataDTO.CategoriesDTO>
    lateinit var listBanner: kotlin.collections.ArrayList<DashboardBean.DataDTO.SlidersDTO>
    lateinit var listFindStyle: kotlin.collections.ArrayList<DashboardBean.DataDTO.FindStyleDTO>
    lateinit var listProduct: kotlin.collections.ArrayList<DashboardBean.DataDTO.ProductsDTO>
    lateinit var listDealDay: kotlin.collections.ArrayList<DashboardBean.DataDTO.DealofDayDTO>
    lateinit var progressDialog: ProgressDialog
    private var _binding: FragmentDashboardBinding? = null
    lateinit var appPreferences: SharedPrefUtils
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        clickOnMyView()
        apiDashboardApi()
        /*if (appPreferences.getIntValue(AppConstant.UserID,0) != 0){
            apiGetBalance()
        }
*/
        return root

    }

    private fun clickOnMyView() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //toolbarTitle.ivCart
      /*      val requestBody = HashMap<String, RequestBody>()
        requestBody["name"] = "Karishma".toRequestBody("text/plain".toMediaTypeOrNull())
        requestBody["email_id"] = "karishma.agr1996@gmail.com".toRequestBody("text/plain".toMediaTypeOrNull())
        requestBody["phone"] = "9876543210".toRequestBody("text/plain".toMediaTypeOrNull())
*/

        //    GeneralUtilities.launchActivity(this, SplashActivity::class.java)
        //   GeneralUtilities.showErrorSnackBar(this, binding.containerid, "Enter Your Username or Email")
        //    GeneralUtilities.showNormalSnackBar(this, binding.containerid, "Login Successfully")
        //  GeneralUtilities.hideSoftKeyboard(this, binding.containerid)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
     //   (context as DashboardActivity).changeTabIndex(com.data.testshop.R.id.navigation_dashboard)
    }

    fun handleServiceRecyler(data:DashboardBean.DataDTO) {

        listCat= ArrayList()
        listCat.clear()
        listBanner= ArrayList()
        listBanner.clear()
        listProduct= ArrayList()
        listProduct.clear()
        listDealDay= ArrayList()
        listDealDay.clear()
        listFindStyle= ArrayList()
        listFindStyle.clear()

        listCat.addAll(data.categories)
        listProduct.addAll(data.products)
        listBanner.addAll(data.sliders)
        listDealDay.addAll(data.dealofDay)
        listFindStyle.addAll(data.findStyle)
        binding.apply {
            binding.dealAll.setOnClickListener {
                startActivity(Intent(requireContext(), DealDayAllActivity::class.java)
                    .putExtra("responseAll",listDealDay))
            }
            mAdapterCategory.clearItems()
            rvCategory.rvItemAnimation()
            mAdapterCategory.addItems(data.categories)
            rvCategory.setHorizontalLayout()
            rvCategory.adapter = mAdapterCategory
            //  binding.rvCategory?.adapter?.notifyDataSetChanged()



            mAdapterBanner.clearItems()
            rvBanner.rvItemAnimation()
            mAdapterBanner.addItems(listBanner)
            rvBanner.setHorizontalLayout()
            rvBanner.adapter = mAdapterBanner

            mAdapterDealDay.clearItems()
            rvDealsOfDays.rvItemAnimation()
            mAdapterDealDay.addItems(listDealDay)
            rvDealsOfDays.setVerticalLayout()
            rvDealsOfDays.adapter = mAdapterDealDay

            mAdapter.clearItems()
            rvBestProduct.rvItemAnimation()
            mAdapter.addMoreItems(listProduct)
            rvBestProduct.setGridLayout(2)
            rvBestProduct.adapter = mAdapter
        }

        handleRecyclerFindStyle(data.findStyle)
    }

    private val mAdapter =BaseAdapter<DashboardBean.DataDTO.ProductsDTO>(R.layout.item_product_list,onBind = { view, model, position ->

             //      view.findViewById<TextView>(R.id.tvActualPrice).setPaintFlags(view.findViewById<TextView>(R.id.tvActualPrice).getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

        view.findViewById<TextView>(R.id.tvTitle).text = model.name
        view.findViewById<TextView>(R.id.tvPriceApp).text =AppConstant.CurrencySymbole+ model.price
        view.findViewById<TextView>(R.id.tvActualPrice).text = AppConstant.CurrencySymbole+model.mrp
        view.findViewById<TextView>(R.id.tvOff).text = AppConstant.CurrencySymbole+model.discount

        Glide.with(requireContext()).load(model.img)
            .into(view.findViewById<ImageView>(R.id.ivImage))
        view.findViewById<TextView>(R.id.tvActualPrice).paintFlags =
            view.findViewById<TextView>(R.id.tvActualPrice).getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG

        /*        view.findViewById<CardView>(R.id.cvProductItem).setOnClickListener {
                    startActivity(Intent(requireContext(), SubCategoryActivity::class.java)
                        .putExtra("CID",model.cid)
                        .putExtra("scid",model.scid))
                }*/

        view.findViewById<CardView>(R.id.cvProductItem).setOnClickListener {
            startActivity(Intent(requireContext(), ProductDetailActivity::class.java)
                .putExtra("PID",model.id))

         /*   startActivity(Intent(requireContext(), ProductDetailActivity::class.java)
                .putExtra("productList",model))*/
         /*   if(!model.rating.equals(""))
            view.findViewById<RatingBar>(R.id.ratingBar).rating =model.rating.toFloat()
*/
        }
            })

    private val mAdapterCategory =BaseAdapter<DashboardBean.DataDTO.CategoriesDTO>(R.layout.item_category_list, onBind = { view, model, position ->

        view.findViewById<TextView>(R.id.tvTitle).text = model.name
        Glide.with(requireContext()).load(model.img)
            .into(view.findViewById<ImageView>(R.id.ivImage))

        view.findViewById<LinearLayout>(R.id.llCatgroy).setOnClickListener {
            startActivity(Intent(requireContext(), SubCategoryActivity::class.java)
                .putExtra("CID",model.id)
            )
        }
        })

    private val mAdapterBanner =BaseAdapter<DashboardBean.DataDTO.SlidersDTO>(R.layout.beanviewpager, onBind = { view, model, position ->
        var cardWidth = 0
        Glide.with(requireContext()).load(model.img)
            .into(view.findViewById<ImageView>(R.id.view_pager_img))
//        if (list.size()==4) {
//            cardWidth = (GeneralUtilities.getScreenWidth(context)-150)/4;
//        } else {
//            cardWidth = GeneralUtilities.getScreenWidth(context)-200/4;
//        }
        cardWidth = (GeneralUtilities.getScreenWidth(context) - 50) / 1
        val parms: LinearLayout.LayoutParams = LinearLayout.LayoutParams(cardWidth, 520)
        if (position == 0) {
            parms.setMargins(4, 4, 10, 8)
        } else if (position == listBanner.size - 1) {
            parms.setMargins(4, 4, 10, 8)
        } else parms.setMargins(4, 4, 10, 8)

        view.findViewById<CardView>(R.id.card).layoutParams = parms

    })

    private val mAdapterDealDay =BaseAdapter<DashboardBean.DataDTO.DealofDayDTO>(R.layout.item_deals_day,onBind = { view, model, position ->

        //      view.findViewById<TextView>(R.id.tvActualPrice).setPaintFlags(view.findViewById<TextView>(R.id.tvActualPrice).getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

        view.findViewById<TextView>(R.id.tvTitle).text = model.name
        view.findViewById<TextView>(R.id.tvPriceApp).text =AppConstant.CurrencySymbole+ model.price
        view.findViewById<TextView>(R.id.tvActualPrice).text = AppConstant.CurrencySymbole+model.mrp
        view.findViewById<TextView>(R.id.tvSubTitle).text = model.category
        //    view.findViewById<TextView>(R.id.tvOff).text = AppConstant.CurrencySymbole+model.per
        //    view.findViewById<RatingBar>(R.id.tvSubTitle).rating = model.rating.toFloat()
        Glide.with(requireContext()).load(model.img)
            .into(view.findViewById<ImageView>(R.id.ivImage))
        view.findViewById<TextView>(R.id.tvActualPrice).paintFlags =
            view.findViewById<TextView>(R.id.tvActualPrice).getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG

        /*        view.findViewById<CardView>(R.id.cvProductItem).setOnClickListener {
                    startActivity(Intent(requireContext(), SubCategoryActivity::class.java)
                        .putExtra("CID",model.cid)
                        .putExtra("scid",model.scid))
                }*/

        view.findViewById<CardView>(R.id.cvProductItem).setOnClickListener {
            startActivity(Intent(requireContext(), ProductDetailActivity::class.java)
                .putExtra("PID",model.id))

            /*   startActivity(Intent(requireContext(), ProductDetailActivity::class.java)
                   .putExtra("productList",model))*/
        }
    })

    fun handleRecyclerFindStyle(findStyle: MutableList<DashboardBean.DataDTO.FindStyleDTO>) {
        binding.rvFindStyle.rvItemAnimation()
        binding.rvFindStyle.setHorizontalLayout()
        var mAdapterFindStyle = FindStyleAdapter(requireActivity(),findStyle , object :
            RvClickListner {
            override fun clickPos(pos: Int) {
            //    apiFindStyleApi(pos)
            }

            override fun clickDeletePos(pos: Int) {
            }
        })

        // rvMyAcFiled.isNestedScrollingEnabled = false
        binding.rvFindStyle.adapter = mAdapterFindStyle
    }

    fun apiDashboardApi() {
        GeneralUtilities.showDialog(requireActivity())
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID"),
            arrayOf(
               appPreferences.getIntValue(AppConstant.UserID, 0).toString()
            )
        )
        val response = RetrofitService.getInstance().getDashboardApi(builder.build())
        response.enqueue(object : Callback<DashboardBean> {
            override fun onResponse(call: Call<DashboardBean>, response: Response<DashboardBean>) {
                try {
                  GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {

                        handleServiceRecyler(response.body()!!.data )

                        Glide.with(requireContext()).load(response.body()!!.data.banner1)
                            .into(binding.ivBannerOne)
                        Glide.with(requireContext()).load(response.body()!!.data.banner2)
                            .into(binding.ivBannerTwo)
                     //   appPreferences.setValue("CartCount",response.body()!!.data.totalCart)
                       // appPreferences.setValue(Constants.SharedPref.KEY_CART_COUNT,response.body()!!.data.totalCart)
                        getSharedPrefInstance().setValue(Constants.SharedPref.KEY_CART_COUNT, response.body()!!.data.totalCart)
                        (activity as DashboardActivity).sendCartCountChangeBroadcast()
                        //  generateSlider(response.body()!!.data.sliders)
                    }else{
                        Toast.makeText(requireContext(),response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
                catch (e:Exception){
                  GeneralUtilities.hideDialog()

                    Toast.makeText(requireContext(),e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<DashboardBean>, t: Throwable) {
              GeneralUtilities.hideDialog()

                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun apiFindStyleApi(id: Int) {
        GeneralUtilities.showDialog(requireActivity())

        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("TCID"),
            arrayOf(
                id.toString(),
            )
        )
        val response = RetrofitService.getInstance().getGetFndStyleApi(builder.build())
        response.enqueue(object : Callback<DashboardBean> {
            override fun onResponse(call: Call<DashboardBean>, response: Response<DashboardBean>) {
                try {
                  GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {
                        handleRecyclerFindStyle(response.body()!!.data.findStyle)

                    }else{
                        Toast.makeText(requireContext(),response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                  GeneralUtilities.hideDialog()

                    Toast.makeText(requireContext(),e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<DashboardBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
              GeneralUtilities.hideDialog()

                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

  /*  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_dashboard, menu)
        val menuCartItem: MenuItem = menu.findItem(R.id.action_cart)
        menuCartItem.isVisible = true
        mMenuCart = menuCartItem.actionView
        mMenuCart?.onClick {
            requireContext().startActivity(Intent(requireContext(),MyCartActivity::class.java))

            *//*if (isLoggedIn()) {
                requireContext().startActivity(Intent(requireContext(),MyCartActivity::class.java))
                launchActivity<MyCartActivity>()
            } else {
                launchActivity<SignInUpActivity>()
            }*//*
        }
        val item = menu.findItem(R.id.action_search)
        val icon = resources.getDrawable(R.drawable.ic_search_black_24dp)
        icon.setColorFilter(Color.parseColor(getTextTitleColor()), PorterDuff.Mode.SRC_IN)
        item.icon = icon
        setCartCount()
    }

    fun setCartCount() {
        val count = getCartCount()
        mMenuCart?.ivCart?.changeBackgroundImageTint(getTextTitleColor())
        mMenuCart?.tvNotificationCount?.changeTint(getTextTitleColor())
        mMenuCart?.tvNotificationCount?.text = count
        mMenuCart?.tvNotificationCount?.changeAccentColor()
        if (count.checkIsEmpty() || count == "0") {
            mMenuCart?.tvNotificationCount?.hide()
        } else {
            mMenuCart?.tvNotificationCount?.show()
        }
    }*/



}

class SmoothCenterScroller(private val mContext: Context) : LinearSmoothScroller(mContext) {
    override fun calculateDtToFit(
        viewStart: Int,
        viewEnd: Int,
        boxStart: Int,
        boxEnd: Int,
        snapPreference: Int
    ): Int {
        return (boxStart+(boxStart-boxEnd) /2) - (viewStart + (viewEnd - viewStart)-2)
    }
}