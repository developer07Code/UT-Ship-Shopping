package com.data.utship.activity


import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.data.utship.R
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityAddAddressBinding
import com.data.utship.model.*
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAddressBinding
    lateinit var appPreferences: SharedPrefUtils
    lateinit var progressDialog: ProgressDialog
    lateinit var stateList: kotlin.collections.ArrayList<StateBean.DataDTO>
var stateID=""
var cityID=""
    var addressId=0
    lateinit  var context: Context
    private var addressTypeItem = "Home"
    var addressType = arrayOf("Home(All day delivery)", "Work(Delivery between 10AM-5PM)")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        clickOnMyView()

    }


    private fun clickOnMyView() {
        context = this@AddAddressActivity
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.apply {
            toolbarTitle.imgClose.setOnClickListener { finish() }

            toolbarTitle.imgCart.visibility = View.GONE
            toolbarTitle.imgFav.visibility = View.GONE
            val way=intent.getStringExtra("way")
            if (way.equals("Edit")){
                getAddressApi()
                addressId=intent.getIntExtra("addressId",0)
                btnAddAddress.setText("Update Address")
                toolbarTitle.tvTitleProName.text = "Update Address"

            }
            else{
                //  addressId=intent.getIntExtra("addressId",0)
                toolbarTitle.tvTitleProName.text = "Add Address"
                addressId=0
                addressMode()
                getStateApi()
            }

            binding.btnAddAddress.setOnClickListener {
                if (editName.text.toString().equals("")) {
                    editName.error = "Enter Your Name"
                    editName.requestFocus()
                } else if (editMobNo.text.toString().equals("")) {
                    editMobNo.error = "Enter Your Mobile Number"
                    editMobNo.requestFocus()
                }else if (autoState.text.toString().equals("")) {
                    Toast.makeText(this@AddAddressActivity,"Select State",Toast.LENGTH_SHORT).show()
                }else if (autoCity.text.toString().equals("")) {
                    Toast.makeText(this@AddAddressActivity,"Select City",Toast.LENGTH_SHORT).show()
                }else if (editFullAdd.text.toString().equals("")) {
                    Toast.makeText(this@AddAddressActivity,"Enter Your Full Address",Toast.LENGTH_SHORT).show()
                }else if (editPincode.text.toString().equals("")) {
                    editPincode.error = "Enter Your Pincode"
                    editPincode.requestFocus()
                } else {
                    getManageAddressApi()
                }
            }
            //    GeneralUtilities.launchActivity(this, SplashActivity::class.java)
            //   GeneralUtilities.showErrorSnackBar(this, binding.containerid, "Enter Your Username or Email")
            //    GeneralUtilities.showNormalSnackBar(this, binding.containerid, "Login Successfully")
            //  GeneralUtilities.hideSoftKeyboard(this, binding.containerid)
        }


    }

    fun addressMode() {
        binding.radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                if (checkedId == R.id.rbHome) {
                    addressTypeItem = "Home"
                } else if (checkedId == R.id.rbWork) {
                    addressTypeItem = "Work"
                }
            }
        })
    }


    fun getAddressApi() {
        GeneralUtilities.showDialog(this)
        //  DialogsProvider.get(this).setLoading(true)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("ID", "UID"),
            arrayOf(
                "0",appPreferences.getIntValue(AppConstant.UserID,0).toString())
        )
        val response = RetrofitService.getInstance().GetAddressApi(builder.build())
        response.enqueue(object : Callback<GetAddressBean> {
            override fun onResponse(
                call: Call<GetAddressBean>,
                response: Response<GetAddressBean>
            ) {
                try {
                   // DialogsProvider.get(this@AddAddressActivity).setLoading(false)
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        setAdressData(response.body()!!.data)
                    } else {

                        Toast.makeText(
                            this@AddAddressActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@AddAddressActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<GetAddressBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                GeneralUtilities.hideDialog()
                Toast.makeText(this@AddAddressActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun setAdressData(data: List<GetAddressBean.DataDTO>) {
        binding.apply {
            editName.setText(data.get(0).name)
            editMobNo.setText(data.get(0).mobile)
            editFullAdd.setText(data.get(0).address)
            editPincode.setText(data.get(0).pincode)
            autoState.setText(data.get(0).state)
            autoCity.setText(data.get(0).city)
            cityID=data.get(0).cityID.toString()
            stateID=data.get(0).stateID.toString()
            if (data.get(0).addressType.equals("Home")){
                rbHome.isChecked=true
                rbWork.isChecked=false
            }
            else{
                rbHome.isChecked=false
                rbWork.isChecked=true
            }
        }


    }

    fun getManageAddressApi() {
        GeneralUtilities.showDialog(this)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("ID","UID", "Name","Mobile","StateID","CityID","State","City","Address","Landmark","Pincode","AddressType"),
            arrayOf(
                addressId.toString(),appPreferences.getIntValue(AppConstant.UserID,0).toString(),binding.editName.text.toString(),binding.editMobNo.text.toString(), stateID, cityID,"","",binding.editFullAdd.text.toString(),"",
                binding.editPincode.text.toString(),addressTypeItem)
        )
        val response = RetrofitService.getInstance().getManageAddressApi(builder.build())
        response.enqueue(object : Callback<BaseResponseBean> {
            override fun onResponse(
                call: Call<BaseResponseBean>,
                response: Response<BaseResponseBean>
            ) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        Toast.makeText(
                            this@AddAddressActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        Toast.makeText(
                            this@AddAddressActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@AddAddressActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<BaseResponseBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@AddAddressActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    fun getStateApi() {

        GeneralUtilities.showDialog(this)
        val response = RetrofitService.getInstance().getStateApi()
        response.enqueue(object : Callback<StateBean> {
            override fun onResponse(
                call: Call<StateBean>,
                response: Response<StateBean>
            ) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        stateList(response.body()!!.data)
                    } else {

                        Toast.makeText(
                            this@AddAddressActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@AddAddressActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<StateBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@AddAddressActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun getCityApi(id: String) {

        GeneralUtilities.showDialog(this)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("StateID"),
            arrayOf(
                id
            )
        )
        val response = RetrofitService.getInstance().getCityApi(builder.build())
        response.enqueue(object : Callback<StateBean> {
            override fun onResponse(
                call: Call<StateBean>,
                response: Response<StateBean>
            ) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        cityList(response.body()!!.data)
                    } else {

                        Toast.makeText(
                            this@AddAddressActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@AddAddressActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<StateBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@AddAddressActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun stateList(data: MutableList<StateBean.DataDTO>) {
        val fuelType = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            fuelType[i] = data.get(i).getName()
        }
        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AddAddressActivity,
            android.R.layout.simple_list_item_1,
            fuelType
        )
        binding.autoState.setAdapter(adapte1)
        binding.autoState.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            stateID = data.get(position).getId().toString()
            binding.autoState.setText(data.get(position).name)
            Log.d("fuelTypeId", "" + stateID)
            getCityApi(stateID)
        })
    }


    fun cityList(data: MutableList<StateBean.DataDTO>) {
        val fuelType = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            fuelType[i] = data.get(i).getName()
        }
        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AddAddressActivity,
            android.R.layout.simple_list_item_1,
            fuelType
        )
        binding.autoCity.setAdapter(adapte1)
        binding.autoCity.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            cityID = data.get(position).getId().toString()
            binding.autoCity.setText(data.get(position).name)
            Log.d("fuelTypeId", "" + cityID)

        })
    }

}






    
  