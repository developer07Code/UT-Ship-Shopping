package com.data.utship.fragment


import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.data.utship.activity.*

import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.FragmentProfileBinding
import com.data.utship.model.ProfileDataBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.clearLoginPref
import com.data.utship.utills.utility.AppConstant
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {

    var mobeNo=""
    lateinit  var profileResponse: ProfileDataBean.DataDTO
    private var _binding: FragmentProfileBinding? = null
    lateinit var progressDialog: ProgressDialog
    lateinit var appPreferences: SharedPrefUtils
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bindViews()
        if (appPreferences.getIntValue(AppConstant.UserID,0) != 0)
            apiProfileDetail()

        return root
    }

    private fun bindViews() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.apply {
            constraintProfile.setOnClickListener {
                startActivity(Intent(requireContext(), ProfileActivity::class.java)
                    .putExtra("profileRes",profileResponse))
              /*  if (appPreferences.getIntValue(AppConstant.UserID,0) == 0)
                    startActivity(Intent(requireContext(), LoginNewActivity::class.java))
                else
                    startActivity(Intent(requireContext(), ProfileActivity::class.java)
                        .putExtra("profileRes",profileResponse))*/
            }
            linearOrders.visibility=View.VISIBLE
          /*  if (appPreferences.getIntValue(AppConstant.UserID,0) == 0){
                linearOrders.visibility=View.GONE
            }
            else{
                linearOrders.visibility=View.VISIBLE
            }*/

            linearAllOrders.setOnClickListener {
                (context as DashboardActivity).replaceFragment(OrderHisFragment(),"OrderHistory")
              //  Navigation.findNavController(it).navigate(com.data.testshop.R.id.navigation_notifications)
            }

            linearAllAddress.setOnClickListener {

               startActivity(Intent(requireContext(), AllAddressActivity::class.java))
            }
            linearBilling.setOnClickListener {

               startActivity(Intent(requireContext(), WalletActivity::class.java))
            }
            linearReferal.setOnClickListener {

               startActivity(Intent(requireContext(), RefferalActivity::class.java)
                   .putExtra("mobileNo",mobeNo))
            }
            linearRegional.setOnClickListener {
                val dialog = getAlertDialog(
                    getString(com.data.utship.R.string.lbl_logout_confirmation),
                    onPositiveClick = { _, _ ->
                        clearLoginPref()
                        startActivity(Intent(requireContext(), LoginNewActivity::class.java))
                        activity?.finish()
                    },
                    onNegativeClick = { dialog, _ ->
                        dialog.dismiss()
                    })
                dialog.show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getAlertDialog(
        aMsgText: String,
        aTitleText: String = getString(com.data.utship.R.string.lbl_dialog_title),
        aPositiveText: String = getString(com.data.utship.R.string.lbl_yes),
        aNegativeText: String = getString(com.data.utship.R.string.lbl_no),
        onPositiveClick: (dialog: DialogInterface, Int) -> Unit,
        onNegativeClick: (dialog: DialogInterface, Int) -> Unit,
    ): AlertDialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(aTitleText)
        builder.setMessage(aMsgText)
        builder.setPositiveButton(aPositiveText) { dialog, which ->
            onPositiveClick(dialog, which)
        }

        builder.setNegativeButton(aNegativeText) { dialog, which ->
            onNegativeClick(dialog, which)
        }
        return builder.create()
    }


    fun apiProfileDetail() {
      //  GeneralUtilities.showDialog(requireActivity())
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
               //     GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {

                        profileResponse=response.body()!!.data
                        binding.tvUserName.setText(response.body()!!.data.name)
                        binding.tvMobNo.setText(response.body()!!.data.mobile)
                        mobeNo=response.body()!!.data.mobile
                            //   binding.imageUser.setText(response.body()!!.data.password)

                    } else {

                        Toast.makeText(
                            requireActivity(),
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                 //   GeneralUtilities.hideDialog()

                    Toast.makeText(
                        requireActivity(),
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<ProfileDataBean>, t: Throwable) {
           //     GeneralUtilities.hideDialog()
                Toast.makeText(requireActivity(), t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
    override fun onResume() {
        super.onResume()
        if (isAdded)
        if (appPreferences.getIntValue(AppConstant.UserID,0) != 0)
            apiProfileDetail()
      //  (context as DashboardActivity).changeTabIndex(com.data.testshop.R.id.navigation_profile)

    }

}