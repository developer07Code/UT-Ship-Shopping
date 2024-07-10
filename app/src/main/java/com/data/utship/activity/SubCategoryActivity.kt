package com.data.utship.activity


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.adapter.BaseAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivitySubcatBinding
import com.data.utship.model.DashboardBean
import com.data.utship.model.GetAllCategoryBean
import com.data.utship.model.GetBalanceBean
import com.data.utship.utills.RvClickListner
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.setGridLayout
import com.data.utship.utills.extensions.setVerticalLayout
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import com.example.SubCatAdapter
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class SubCategoryActivity : AppCompatActivity() {
    private var subCatId: Int?=null
    private var catID: Int? = null
    private lateinit var binding: ActivitySubcatBinding
  //  lateinit var appPreferences: PreferenceManager
    lateinit var progressDialog: ProgressDialog
    lateinit var appPreferences: SharedPrefUtils
    lateinit var listCat: kotlin.collections.ArrayList<DashboardBean.DataDTO.CategoriesDTO>
    lateinit var listSubCat: kotlin.collections.ArrayList<GetAllCategoryBean.DataDTO.SubToSubCategoriesDTO>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_login)
        binding = ActivitySubcatBinding.inflate(layoutInflater)
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
            toolbarTitle.tvTitleProName.text = "Category"
            toolbarTitle.imgCart.visibility = View.GONE
            toolbarTitle.imgFav.visibility = View.GONE
        }

        apiDashboardApi()
       catID =intent.getIntExtra("CID",0)
        apiSubCategory(catID)
    }

    fun apiDashboardApi() {
        GeneralUtilities.showDialog(this)
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
                        handleCatRecyler(response.body()!!.data )
                   //     apiSubCategory(response.body()!!.data.categories.get(0).id)
                    }else{
                        Toast.makeText(this@SubCategoryActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    GeneralUtilities.hideDialog()

                    Toast.makeText(this@SubCategoryActivity,e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<DashboardBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                GeneralUtilities.hideDialog()

                Toast.makeText(this@SubCategoryActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun handleCatRecyler(data: DashboardBean.DataDTO) {
        listCat= ArrayList()
        listCat.clear()
        listCat.addAll(data.categories)
        //  binding.rvCategory.rvItemAnimation()
        mAdapterCategory.addItems(listCat)
        binding.rvCategory.setVerticalLayout()
        binding.rvCategory.adapter = mAdapterCategory
        mAdapterCategory.notifyDataSetChanged()
    }

    private val mAdapterCategory =
        BaseAdapter<DashboardBean.DataDTO.CategoriesDTO>(R.layout.item_category_list, onBind = { view, model, position ->
            view.findViewById<TextView>(R.id.tvTitle).text = model.name
            Glide.with(this@SubCategoryActivity).load(model.img)
                .into(view.findViewById<ImageView>(R.id.ivImage))

            view.findViewById<LinearLayout>(R.id.llCatgroy).setOnClickListener {
               catID= model.id
                Log.d("cat",model.id.toString())
                apiSubCategory(model.id)

            }
        })

    fun apiSubCategory(cid: Int?) {
       // GeneralUtilities.showDialog(this)
        //  DialogsProvider.get(this).setLoading(true)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("CID"),
            arrayOf(
                cid.toString()))
        val response = RetrofitService.getInstance().GetAllCategoryApi(builder.build())
        response.enqueue(object : Callback<GetAllCategoryBean> {
            override fun onResponse(call: Call<GetAllCategoryBean>, response: Response<GetAllCategoryBean>) {
                try {
               //     GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        val layoutManager2 = LinearLayoutManager(this@SubCategoryActivity)
                        binding.rvSubCategory.layoutManager = layoutManager2
                        binding.rvSubCategory.isNestedScrollingEnabled = true
                        binding.rvSubCategory.setHasFixedSize(true)
                        val  customAdapter = SubCatAdapter(response.body()!!.data,catID, this@SubCategoryActivity, object :
                            RvClickListner {
                            override fun clickPos(cityID: Int) {
                                //  addCityApi(cityID)
                            }

                            override fun clickDeletePos(pos: Int) {

                            }
                        })
                        binding.rvSubCategory.adapter = customAdapter
                    }else{
                        Toast.makeText(this@SubCategoryActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
               //     GeneralUtilities.hideDialog()
                    Toast.makeText(this@SubCategoryActivity,e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<GetAllCategoryBean>, t: Throwable) {
           //     GeneralUtilities.hideDialog()
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                Toast.makeText(this@SubCategoryActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private val mAdapteSubCat =BaseAdapter<GetAllCategoryBean.DataDTO>(R.layout.item_subcategory,onBind = { view, model, position ->
        view.findViewById<TextView>(R.id.tvTitle).text = model.name
        subCatId=model.id
        Log.d("sucat",model.id.toString())
        // view.findViewById<RecyclerView>(R.id.rvSubToSubCat).rvItemAnimation()

     //   adptSubToSubCat.clearItems()
        listSubCat= ArrayList()
        listSubCat.clear()
        listSubCat.addAll(model.subToSubCategories)
        adptSubToSubCat.addItems(listSubCat)
        view.findViewById<RecyclerView>(R.id.rvSubToSubCat).setGridLayout(3)
        view.findViewById<RecyclerView>(R.id.rvSubToSubCat).adapter = adptSubToSubCat
        adptSubToSubCat.notifyDataSetChanged()
    })

    private val adptSubToSubCat =BaseAdapter<GetAllCategoryBean.DataDTO.SubToSubCategoriesDTO>(R.layout.item_subcat_list,onBind = { view, model, position ->
        view.findViewById<TextView>(R.id.tvTitle).text = model.name
        Glide.with(this).load(model.img)
            .into(view.findViewById<CircleImageView>(R.id.ivImage))
        view.findViewById<LinearLayout>(R.id.llCatgroy).setOnClickListener {

            startActivity(Intent(this@SubCategoryActivity,ProductActivity::class.java)
                .putExtra("subToSubCatID",model.id)
                .putExtra("CID",catID)
                .putExtra("subCatID",subCatId)
            )
            Log.d("zczxc", model.id.toString())
            Log.d("zczxc", catID.toString())
            Log.d("zczxc", subCatId.toString())
        }

    })

    fun apiGetTotalCount() {
        // GeneralUtilities.showDialog(this)
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
                          //  binding.toolbarTitle.tvCount.visibility = View.GONE

                        else
                            Toast.makeText(this@SubCategoryActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()

                    //    binding.toolbarTitle.tvCount.text =response.body()!!.data.totalCart.toString()

                    }else{
                        Toast.makeText(this@SubCategoryActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    // GeneralUtilities.hideDialog()
                    Toast.makeText(this@SubCategoryActivity,e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<GetBalanceBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                // GeneralUtilities.hideDialog()
                Toast.makeText(this@SubCategoryActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}