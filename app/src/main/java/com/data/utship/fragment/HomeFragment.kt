package com.data.utship.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.activity.ProductActivity
import com.data.utship.activity.SubCategoryActivity
import com.data.utship.activity.SubToSubCatActivity
import com.data.utship.adapter.AllCategoryAdapter
import com.data.utship.adapter.BaseAdapter
import com.data.utship.adapter.FindStyleAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.FragmentHomeBinding
import com.data.utship.model.DashboardBean
import com.data.utship.model.GetAllCategoryBean
import com.data.utship.model.SubCategoryBean
import com.data.utship.utills.RvClickListner
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.rvItemAnimation
import com.data.utship.utills.extensions.setGridLayout
import com.data.utship.utills.extensions.setHorizontalLayout
import com.data.utship.utills.extensions.setVerticalLayout
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import com.data.utship.utills.utility.GeneralUtilities.intent
import com.example.SubCatAdapter
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class HomeFragment : Fragment() {
    private var subCatId: Int?=null
    private var catID: Int? = null
    lateinit var progressDialog: ProgressDialog
    private var _binding: FragmentHomeBinding? = null
    lateinit var listCat: kotlin.collections.ArrayList<DashboardBean.DataDTO.CategoriesDTO>
    // This property is only valid between onCreateView and
    // onDestroyView.
    lateinit var appPreferences: SharedPrefUtils
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        clickOnMyView()
        apiDashboardApi()
        return root
    }
    private fun clickOnMyView() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

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
   fun handleCatRecyler(data: DashboardBean.DataDTO) {
       listCat= ArrayList()
       listCat.clear()
       listCat.addAll(data.categories)
      //  binding.rvCategory.rvItemAnimation()
        mAdapterCategory.addItems(listCat)
        binding.rvCategory.setVerticalLayout()
        binding.rvCategory.adapter = mAdapterCategory
    }

    private val mAdapterCategory =
        BaseAdapter<DashboardBean.DataDTO.CategoriesDTO>(R.layout.item_category_list, onBind = { view, model, position ->

        view.findViewById<TextView>(R.id.tvTitle).text = model.name
            catID=model.id
        Glide.with(requireContext()).load(model.img)
            .into(view.findViewById<ImageView>(R.id.ivImage))

        view.findViewById<LinearLayout>(R.id.llCatgroy).setOnClickListener {
          /*  startActivity(
                Intent(requireContext(), SubCategoryActivity::class.java)
                .putExtra("CID",model.id)
            )*/

            apiSubCategory(model.id)
        }
    })
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
                        handleCatRecyler(response.body()!!.data )
                        apiSubCategory(response.body()!!.data.categories.get(0).id)
                   //     handleSubCatRecyler(response.body()!!.data )
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
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                GeneralUtilities.hideDialog()

                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun apiSubCategory(cid: Int) {
        GeneralUtilities.showDialog(requireActivity())
        //  DialogsProvider.get(this).setLoading(true)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("CID"),
            arrayOf(
                cid.toString()))
        val response = RetrofitService.getInstance().GetAllCategoryApi(builder.build())
        response.enqueue(object : Callback<GetAllCategoryBean> {
            override fun onResponse(call: Call<GetAllCategoryBean>, response: Response<GetAllCategoryBean>) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        val layoutManager2 = LinearLayoutManager(requireContext())
                        binding.rvSubCategory.layoutManager = layoutManager2
                        binding.rvSubCategory.isNestedScrollingEnabled = true
                        binding.rvSubCategory.setHasFixedSize(true)
                        val  customAdapter = SubCatAdapter(response.body()!!.data,catID, requireContext(), object :
                            RvClickListner {
                            override fun clickPos(cityID: Int) {
                                //  addCityApi(cityID)
                            }

                            override fun clickDeletePos(pos: Int) {

                            }
                        })
                        binding.rvSubCategory.adapter = customAdapter
                    }else{
                        Toast.makeText(requireContext(),response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    GeneralUtilities.hideDialog()
                    Toast.makeText(requireContext(),e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<GetAllCategoryBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private val adptSubToSubCat =BaseAdapter<GetAllCategoryBean.DataDTO.SubToSubCategoriesDTO>(R.layout.item_subcat_list,onBind = { view, model, position ->
        view.findViewById<TextView>(R.id.tvTitle).text = model.name
     //   Log.d("zczxc",model.subToSubCategories.get(position).name)
     //   view.findViewById<TextView>(R.id.tvSubTitle).text = model.subToSubCategories.get(position).name
        Glide.with(this).load(model.img)
            .into(view.findViewById<CircleImageView>(R.id.ivImage))
        view.findViewById<LinearLayout>(R.id.llCatgroy).setOnClickListener {
            startActivity(Intent(requireContext(), ProductActivity::class.java)
                .putExtra("subToSubCatID",model.id)
                .putExtra("CID",catID)
                .putExtra("subCatID",subCatId)
            )
        }

    })

    private val mAdapteSubCat =BaseAdapter<GetAllCategoryBean.DataDTO>(R.layout.item_subcategory,onBind = { view, model, position ->
        view.findViewById<TextView>(R.id.tvTitle).text = model.name
        subCatId=model.id
       // view.findViewById<RecyclerView>(R.id.rvSubToSubCat).rvItemAnimation()
        adptSubToSubCat.clearItems()
        adptSubToSubCat.addItems(model.subToSubCategories)
        view.findViewById<RecyclerView>(R.id.rvSubToSubCat).setGridLayout(3)
        view.findViewById<RecyclerView>(R.id.rvSubToSubCat).adapter = adptSubToSubCat
        adptSubToSubCat.notifyDataSetChanged()

    })

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*override fun onResume() {
        super.onResume()
        (context as DashboardActivity).changeTabIndex(com.data.testshop.R.id.navigation_home)
    }*/
}