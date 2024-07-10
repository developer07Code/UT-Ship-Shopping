package com.data.utship.activity


import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
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
import com.data.utship.databinding.ActivitySubcatBinding
import com.data.utship.databinding.ActivitySubtosubcatBinding
import com.data.utship.model.GetBalanceBean
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

class SubToSubCatActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubtosubcatBinding
  //  lateinit var appPreferences: PreferenceManager
    lateinit var progressDialog: ProgressDialog
    lateinit var appPreferences: SharedPrefUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_login)
        binding = ActivitySubtosubcatBinding.inflate(layoutInflater)
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
            toolbarTitle.tvTitleProName.text = "SubToSubCategory"
            toolbarTitle.imgCart.visibility = View.GONE
            toolbarTitle.imgFav.visibility = View.GONE
        }
//sid=intent.getIntExtra("scid",0)
        apiSubToSubCategory(intent.getIntExtra("subCatID",0))

    }
    private val mAdapterr =BaseAdapter<SubCategoryBean.DataDTO>(R.layout.item_subcategory,onBind = { view, model, position ->
        view.findViewById<TextView>(R.id.tvTitle).text = model.name
        Glide.with(this).load(model.img)
            .into(view.findViewById<ImageView>(R.id.ivImage))

        view.findViewById<LinearLayout>(R.id.llCatgroy).setOnClickListener {
            startActivity(Intent(this@SubToSubCatActivity,ProductActivity::class.java)
                .putExtra("subToSubCatID",model.id)
                .putExtra("CID",intent.getIntExtra("CID",0))
                .putExtra("subCatID",intent.getIntExtra("subCatID",0))

            )
           // apiSubToSubCategory(model.id)
          //  apiSubToSubCat(model.id)

        }

    })

    fun apiSubToSubCategory(cid: Int) {
        GeneralUtilities.showDialog(this)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("SCID"),
            arrayOf(
                cid.toString()))
        val response = RetrofitService.getInstance().getSubToSubcategoryApi(builder.build())
        response.enqueue(object : Callback<SubCategoryBean> {
            override fun onResponse(call: Call<SubCategoryBean>, response: Response<SubCategoryBean>) {
                try {
                    GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {

                       /* listSubCat= ArrayList()
                        listSubCat.clear()
                        listSubCat.addAll(response.body()!!.data)
*/
                        binding.rvSubCategory.rvItemAnimation()
                        mAdapterr.addItems(response.body()!!.data)
                        binding.rvSubCategory.setGridLayout(3)
                        binding.rvSubCategory.adapter = mAdapterr

                    }else{
                        Toast.makeText(this@SubToSubCatActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    GeneralUtilities.hideDialog()

                    Toast.makeText(this@SubToSubCatActivity,e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<SubCategoryBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@SubToSubCatActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()
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
                         //   binding.toolbarTitle.tvCount.visibility = View.GONE

                        else
                            Toast.makeText(this@SubToSubCatActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()

                       // binding.toolbarTitle.tvCount.text =response.body()!!.data.totalCart.toString()

                    }else{
                        Toast.makeText(this@SubToSubCatActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    // GeneralUtilities.hideDialog()
                    Toast.makeText(this@SubToSubCatActivity,e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<GetBalanceBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                // GeneralUtilities.hideDialog()
                Toast.makeText(this@SubToSubCatActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}