package com.data.utship.activity


import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.data.utship.adapter.AddressListAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityAllAddressBinding
import com.data.utship.model.AddressListBean
import com.data.utship.utills.RvClickListner
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllAddressActivity : AppCompatActivity() {
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
            toolbarTitle.tvTitleProName.text = "All Address"
            toolbarTitle.imgCart.visibility=View.GONE
            toolbarTitle.imgFav.visibility=View.GONE
        }
        getAddressListApi(0)
    }
    fun getAddressListApi(id: Int) {
         GeneralUtilities.showDialog(this)
      //   DialogsProvider.get(this).setLoading(true)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID", "ID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString(),id.toString()
            )
        )
        val response = RetrofitService.getInstance().getAddressApi(builder.build())
        response.enqueue(object : Callback<AddressListBean> {
            override fun onResponse(
                call: Call<AddressListBean>,
                response: Response<AddressListBean>
            ) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        addressList(response.body()!!.data)
                    } else {

                        Toast.makeText(
                            this@AllAddressActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@AllAddressActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<AddressListBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@AllAddressActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun addressList(listAddress: MutableList<AddressListBean.DataDTO>) {
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
    }

}