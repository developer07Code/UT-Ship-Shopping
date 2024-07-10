package com.data.utship.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.data.utship.R
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityProfileBinding
import com.data.utship.model.BaseResponseBean
import com.data.utship.model.ProfileDataBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileActivity : AppCompatActivity() {
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    private lateinit var binding: ActivityProfileBinding
    lateinit var appPreferences: SharedPrefUtils
    lateinit var progressDialog: ProgressDialog
    var file1: File? = null
    var SELECT_PICTURES1 = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_login_new)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        bindViews()
        /*if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {

        }*/
      val  profileResData=intent.getSerializableExtra("profileRes") as  ProfileDataBean.DataDTO
      //  apiProfileDetail()
        setData(profileResData)
    }

   fun setData(profileResData: ProfileDataBean.DataDTO) {
       binding.editName.setText(profileResData.name)
       binding.editMobNo.setText(profileResData.mobile)
       binding.editEmailID.setText(profileResData.email)
       binding.editPassword.setText(profileResData.password)
  //     binding.imageUser.setText(response.body()!!.data.password)
   }
    private fun bindViews() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.toolbarTitle.imgClose.setOnClickListener { finish() }
        binding.toolbarTitle.tvTitleProName.text = "Profile"
        binding.toolbarTitle.imgCart.visibility = View.GONE
        binding.toolbarTitle.imgFav.visibility = View.GONE
        binding.toolbarTitle.tvAddAddress.visibility = View.GONE
        binding.ivEditProfilePic.setOnClickListener {
            uploadImage(SELECT_PICTURES1)
        }
        binding.rlButton.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.btn1) {
                binding.btn1.setTextColor(resources.getColor(R.color.white))
                binding.btn2.setTextColor(resources.getColor(R.color.black))

            } else if (i == R.id.btn2) {
                binding.btn1.setTextColor(resources.getColor(R.color.black))
                binding.btn2.setTextColor(resources.getColor(R.color.white))

            }
        })

        binding.btnSave.setOnClickListener {

            if (binding.editName.text.toString().equals("")) {
                binding.editName.error = "Enter Name"
                binding.editName.requestFocus()
            } else if (binding.editMobNo.text.toString().equals("")) {
                binding.editMobNo.error = "Enter Mobile Number"
                binding.editMobNo.requestFocus()
            } else if (binding.editEmailID.text.toString().equals("")) {
                binding.editEmailID.error = "Enter Email ID"
                binding.editEmailID.requestFocus()
            }  else if (binding.editPassword.text.toString().equals("")) {
                binding.editPassword.error = "Enter Password"
                binding.editPassword.requestFocus()
            } else {
                apiSaveProfileDetail()
            }
        }
    }
    private fun uploadImage(SELECT_PICTURES: Int) {
        val selectImage = Intent()
        selectImage.type = "image/*"
        selectImage.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                selectImage,
                "Select Picture"
            ),
            SELECT_PICTURES
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == SELECT_PICTURES1) {
                try {
                    val uri = data!!.data
                    val picturePath: String = GeneralUtilities.getPath(this, uri)
                    Log.d("Picture Path", picturePath)
                    file1 = File(picturePath)
                    val myBitmap = BitmapFactory.decodeFile(file1!!.absolutePath)

                    binding.ivUserImage.setImageBitmap(myBitmap)

                    //Toast.makeText(getContext(), ""+picturePath, Toast.LENGTH_SHORT).show();
                } catch (e: java.lang.Exception) {
                    Log.e("Path Error", e.toString())
                    Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun apiProfileDetail() {
        GeneralUtilities.showDialog(this)
        //  DialogsProvider.get(this).setLoading(true)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UserID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID,0).toString()
            )
        )
        val response = RetrofitService.getInstance().getProfileApi(builder.build())
        response.enqueue(object : Callback<ProfileDataBean> {
            override fun onResponse(
                call: Call<ProfileDataBean>,
                response: Response<ProfileDataBean>
            ) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        binding.editName.setText(response.body()!!.data.name)
                        binding.editMobNo.setText(response.body()!!.data.mobile)
                        binding.editEmailID.setText(response.body()!!.data.email)
                        binding.editPassword.setText(response.body()!!.data.password)

                    } else {

                        Toast.makeText(
                            this@ProfileActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()
                    Toast.makeText(
                        this@ProfileActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<ProfileDataBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                GeneralUtilities.hideDialog()
                Toast.makeText(this@ProfileActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun apiSaveProfileDetail() {
        //   progressDialog.setMessage("Please Wait")
        //   progressDialog.show()
        GeneralUtilities.showDialog(this)

        //  DialogsProvider.get(this).setLoading(true)


        /*  val builder = MultipartBody.Builder()
          builder.setType(MultipartBody.FORM)
          builder.addFormDataPart("ID", "1")
          builder.addFormDataPart("Name", binding.editName.text.toString())
          builder.addFormDataPart("Email", binding.editEmailID.text.toString())
          builder.addFormDataPart("Mobile", binding.editMobNo.text.toString())
          builder.addFormDataPart("Password", binding.editPassword.text.toString())
          builder.addFormDataPart("AddDate", "")
          builder.addFormDataPart("ProfileIMG", "")
  */
        /*  builder.addFormDataPart("ProfileIMG", file1?.name,
              RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file1!!))*/


        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("ID","Name","Email","Mobile","Password","AddDate","ProfileIMG"),
            arrayOf(
                "1",binding.editName.text.toString(),binding.editEmailID.text.toString(),binding.editMobNo.text.toString(),
                binding.editMobNo.text.toString(),binding.editPassword.text.toString(),"",""
            )
        )
        val response = RetrofitService.getInstance().getUpdateProfileApi(builder.build())
        response.enqueue(object : Callback<BaseResponseBean> {
            override fun onResponse(
                call: Call<BaseResponseBean>,
                response: Response<BaseResponseBean>
            ) {
                try {
                 GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {

                        Toast.makeText(
                            this@ProfileActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {

                        Toast.makeText(
                            this@ProfileActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                 GeneralUtilities.hideDialog()

                    Toast.makeText(
                        this@ProfileActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<BaseResponseBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
             GeneralUtilities.hideDialog()

                Toast.makeText(this@ProfileActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun checkPermissionREAD_EXTERNAL_STORAGE(
        context: Context?
    ): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        return if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (context as Activity?)!!,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    showDialog(
                        "External storage", context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                } else {
                    ActivityCompat
                        .requestPermissions(
                            (context as Activity?)!!,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                }
                false
            } else {
                true
            }
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do your stuff

            } else {

                Toast.makeText(
                    applicationContext, "GET_ACCOUNTS Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> super.onRequestPermissionsResult(
                requestCode, permissions!!,
                grantResults
            )
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun showDialog(msg: String, context: Context?, permission: String) {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle("Permission necessary")
        alertBuilder.setMessage("$msg permission is necessary")
        alertBuilder.setPositiveButton(
            android.R.string.yes
        ) { dialog, which ->
            ActivityCompat.requestPermissions(
                (context as Activity?)!!, arrayOf(permission),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
        }
        val alert = alertBuilder.create()
        alert.show()
    }


}